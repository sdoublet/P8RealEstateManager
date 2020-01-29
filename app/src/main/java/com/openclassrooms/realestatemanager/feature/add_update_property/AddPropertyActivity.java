package com.openclassrooms.realestatemanager.feature.add_update_property;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.googleMap.ApiGeocoding;
import com.openclassrooms.realestatemanager.api.googleMap.Result;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.api.geolocation.LocationStream;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.util.AgentId;
import com.openclassrooms.realestatemanager.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.HttpException;

public class AddPropertyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_CODE = 1234;
    public static final int REQUEST_PHOTO_CODE = 2345;
    public static final int REQUEST_GALLERY_CODE = 5678;
    private static final String ESTATEID = "estateId";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public static final String[] cameraPerms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};


    private ActivityAddPropertyBinding binding;
    private Estate estate;
    private Disposable disposable;
    private Result apiResult;
    private ApiGeocoding geocoding;
    private String photoPath = null;
    private Bitmap image;
    private Long estateId;
    private int nbPictures;
    private List<Picture> pictureList;
    private ScaleGestureDetector scaleGestureDetector;
    private boolean mainPhoto;
    private Uri photoUri;


    //FOR DATA
    private EstateViewModel estateViewModel;
    private static long AGENT_ID = AgentId.getInstance().getAgentId();

    //FILE PURPOSE
    // public static final String FOLDERNAME=  "estateFolder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        pictureList = new ArrayList<>();

        //UI spinners
        this.configureSpinners();

        //Configure ViewModel
        this.configureViewModel();
        // estateViewModel.getAllPictures().observe(this, this::updateData);
        addPhoto();

        //Save data on db
        save();


    }

    //------------------------
    //ACTIONS
    //------------------------


    // Save property in database
    private void save() {
        binding.buttonSave.setOnClickListener(v -> {
            checkPermissions();
            //configureViewModel();
            //createEstate();
            String address = binding.editAddress.getText().toString() + " " + binding.editZipCode.getText().toString() + " "
                    + binding.editCity.getText().toString() + ", " + Utils.localeCountry(getApplicationContext());
            // get lat lng
            executeHttpRequestWithretrofit(address);
            //add * picture in list
            //  savePhotoInDb(0);
//                Log.e("estateiidd", String.valueOf(estateId));
            Log.e("agentId", String.valueOf(AGENT_ID));
           // estateViewModel.getAllEstates().observe(this, this::allEstate);

        });
        //c ici le pbm
        //estateViewModel.getLastEstate().observe(this, this::takeLastEntry);
    }

    private void getAllEstates(){
        estateViewModel.getAllEstates().observe(this, this::allEstate);

    }
    private void allEstate(List<Estate> estates) {
        Log.e("help", String.valueOf(estates.size()));
        if (estates.size() > 0) {
            estateViewModel.getLastEstate().observe(this, this::takeLastEntry);

        }
    }
 // it's for why????????????????????
    public void takeLastEntry(int lastId) {
        Log.e("last", String.valueOf(lastId));
        estateId = (long) lastId;
        savePhotoInDb(estateId);
    }



    //-----------------------
    //PHOTO
    //-----------------------
    //Add photo
    private void addPhoto() {
        checkCameraPermissions();
        checkPermissions();
        binding.addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_MaterialComponents_Dialog);
                builder.setTitle("Camera or Gallery?").
                        setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getPhotoSinceGallery();
                            }
                        }).
                        setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takePhoto();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);

            }
        });
    }

    //Take a photo and save in a temp file
    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // test to control if phone has a camera
        if (intent.resolveActivity(getPackageManager()) != null) {
            // create a unique file

            String time = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile = File.createTempFile("photo" + time, ".jpg", photoDir);
                // save complete way
                photoPath = photoFile.getAbsolutePath();
                // create Uri
                photoUri = FileProvider.getUriForFile(AddPropertyActivity.this,
                        AddPropertyActivity.this.getApplicationContext().getPackageName() + ".provider", photoFile);
                Log.e("uri", String.valueOf(photoUri));
                // transfer Uri to intent
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_PHOTO_CODE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Get a photo since gallery device folder
    private void getPhotoSinceGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_GALLERY_CODE);
    }

    //Save photo in gallery
    private void savePhotoInGallery() {
        MediaStore.Images.Media.insertImage(getContentResolver(), image, "My image", "my descritpion");
    }

    //Save photo in db
    private void savePhotoInDb(long estateId) {
        //Log.e("estate", String.valueOf(estateId));
        for (Picture picture : pictureList) {
            picture.setEstateId(estateId);
            estateViewModel.createPicture(picture);
            Log.e("AddP", String.valueOf(picture.getPhotoId())+" " +  pictureList.size() + " " + estateId);
        }
    }


    // back camera call (startActivityForResult)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check requestCode and result
        //FROM CAMERA
        if (requestCode == REQUEST_PHOTO_CODE && resultCode == RESULT_OK) {
            image = BitmapFactory.decodeFile(photoPath);


            // TODO: 04/12/2019 sauvegarder la photo dans la db
            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Dialog);
            builder.setTitle("Save directory");
            //Save photo in gallery
            builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    savePhotoInGallery();
                    Toast.makeText(getApplicationContext(), "Your photo is save", Toast.LENGTH_SHORT).show();


                    setImageViewWithPicture(photoUri);//jen suis la--------------------
                    Picture picture = new Picture(Uri.parse(photoPath), null, 0);

                    pictureList.add(picture);
                    Log.e("AddP", String.valueOf(pictureList.size()));

                }
            });

            builder.setNegativeButton("App", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setImageViewWithPicture(photoUri);
                    Picture picture = new Picture(Uri.parse(photoPath), null, 0);
                    pictureList.add(picture);
                    Log.e("AddP", String.valueOf(pictureList.size()));
                    //attention ca ne sera que sur la main photo

                }
            });
            dialog = builder.create();
            dialog.show();

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);

            // FROM GALLERY
        } else if (requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK) {

            try {
                final Uri uri = data.getData();
                Log.e("uri", String.valueOf(uri));
                final InputStream imageStream = getContentResolver().openInputStream(uri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image = selectedImage;
                setImageViewWithPicture(uri);
                Picture picture = new Picture(uri, null, 0);
                pictureList.add(picture);
                Log.e("AddP", String.valueOf(pictureList.size()));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }


    // set imageView with picture
    private void setImageViewWithPicture(Uri uri) {

        if (binding.mainPhoto.getDrawable() == null) {
            Glide.with(this).load(uri).into(binding.mainPhoto);

        } else if (binding.photo2.getDrawable() == null) {
            Glide.with(this).load(uri).into(binding.photo2);

        } else if (binding.photo3.getDrawable() == null) {
            Glide.with(this).load(uri).into(binding.photo3);

        } else if (binding.photo4.getDrawable() == null) {
            Glide.with(this).load(uri).into(binding.photo4);

        } else if (binding.photo5.getDrawable() == null) {
            Glide.with(this).load(uri).into(binding.photo5);

        } else {
            Toast.makeText(getApplicationContext(), "you must buy pro version to add more photos", Toast.LENGTH_LONG).show();
        }

    }


    //Popup Image

    @Override
    public void onClick(View v) {
        ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        switch (v.getId()) {

            case R.id.main_photo:
                if (binding.mainPhoto.getDrawable() != null) {
                    imagePopup.initiatePopup(binding.mainPhoto.getDrawable());
                    imagePopup.viewPopup();
                }
                break;
            case R.id.photo_2:
                if (binding.photo2.getDrawable() != null) {
                    imagePopup.initiatePopup(binding.photo2.getDrawable());
                    imagePopup.viewPopup();
                }
                break;
            case R.id.photo_3:
                if (binding.photo3.getDrawable() != null) {
                    imagePopup.initiatePopup(binding.photo3.getDrawable());
                    imagePopup.viewPopup();
                }
                break;
            case R.id.photo_4:
                if (binding.photo4.getDrawable() != null) {
                    imagePopup.initiatePopup(binding.photo4.getDrawable());
                    imagePopup.viewPopup();
                }
                break;
            case R.id.photo_5:
                if (binding.photo5.getDrawable() != null) {
                    imagePopup.initiatePopup(binding.photo5.getDrawable());
                    imagePopup.viewPopup();
                }
                break;
        }

    }


    //----------------------
    //DATA
    //----------------------
    //Configure ViewModel
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.intit(AGENT_ID);
    }


    //Create new Estate
    private void createEstate() {

        try {
            estate = new Estate(binding.spinnerType.getSelectedItem().toString(), Integer.parseInt(binding.editPrice.getText().toString()),
                    Float.parseFloat(binding.editEstateSurface.getText().toString()), Float.parseFloat(binding.editLandSurface.getText().toString()),
                    Integer.parseInt(binding.spinnerRoom.getSelectedItem().toString()), Integer.parseInt(binding.spinnerBedroom.getSelectedItem().toString()),
                    Integer.parseInt(binding.spinnerBathroom.getSelectedItem().toString()), binding.editDescription.getText().toString(),
                    binding.spinnerHeating.getSelectedItem().toString(), binding.editAddress.getText().toString(), Integer.parseInt(binding.editZipCode.getText().toString()),
                    binding.editCity.getText().toString(), false, Utils.convertDate(), null, AGENT_ID, geocoding.getResults().get(0).getGeometry().getLocation().getLat(),
                    geocoding.getResults().get(0).getGeometry().getLocation().getLng(), binding.checkboxSchool.isChecked(), binding.checkboxSchool.isChecked(),
                    binding.checkboxPark.isChecked(), binding.checkboxHospital.isChecked(), binding.checkboxTransport.isChecked(), binding.checkboxAdministration.isChecked());


            Toast.makeText(this, "Your estate is save", Toast.LENGTH_SHORT).show();

            Log.e("EstateTag", estate.getAddress() + ", " + estate.getNbRoom());
            Log.e("entry", estate.getEntryDate());
            this.estateViewModel.createEstate(estate);


        } catch (Exception e) {
            Toast.makeText(this, "you forget some fields", Toast.LENGTH_SHORT).show();
            Log.e("tag", e.getMessage());
        }
        //savePhotoInDb(estateId);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //----------------------
    //HTTP REQUEST
    //----------------------
    public void executeHttpRequestWithretrofit(String address) {
        this.disposable = LocationStream.streamFetchGeocoding(address, BuildConfig.google_maps_api_key).subscribeWith(new DisposableObserver<ApiGeocoding>() {
            @Override
            public void onNext(ApiGeocoding apiGeocoding) {
                Log.e("TAG", apiGeocoding.getStatus());
                updateUI(apiGeocoding);
                AddPropertyActivity.this.geocoding = apiGeocoding;
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "on error" + e.getMessage() + e.getLocalizedMessage() + e.fillInStackTrace() + e.getCause());
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    int code = httpException.code();
                    String message = httpException.getMessage();

                    Log.e("TAG", code + message + httpException.response());
                }

            }

            @Override
            public void onComplete() {
                Log.e("TAG", "on complete");
                getAllEstates();
                createEstate();

            }
        });
    }


    //  Dispose subscription
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //-------------------
    //UPDATE UI
    //-------------------
    private void updateUI(ApiGeocoding results) {
        if (results.getResults() != null) {
            binding.editLongitude.setText(Utils.stringFormat(results.getResults().get(0).getGeometry().getLocation().getLng()));
            binding.editLatitude.setText(Utils.stringFormat(results.getResults().get(0).getGeometry().getLocation().getLat()));
        }
    }

    private void configureSpinners() {
        //For array type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(adapter);
        binding.spinnerType.setOnItemSelectedListener(this);
        //For array number
        ArrayAdapter<CharSequence> adapterNumber = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRoom.setAdapter(adapterNumber);
        binding.spinnerBedroom.setAdapter(adapterNumber);
        binding.spinnerBathroom.setAdapter(adapterNumber);
        binding.spinnerRoom.setOnItemSelectedListener(this);
        binding.spinnerBedroom.setOnItemSelectedListener(this);
        binding.spinnerBathroom.setOnItemSelectedListener(this);
        //For array heating
        ArrayAdapter<CharSequence> adapterHeating = ArrayAdapter.createFromResource(this, R.array.heating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerHeating.setAdapter(adapterHeating);
        binding.spinnerHeating.setOnItemSelectedListener(this);
    }

// TODO: 27/11/2019 ajouter un onbackpressed sur la touche return


    //-------------------
    //PERMISSIONS
    //-------------------
    private void checkPermissions() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            Log.e("TAG", "permission ok");
        } else {
            EasyPermissions.requestPermissions(this, "you must give your permission", REQUEST_CODE, perms);

        }
    }

    private void checkCameraPermissions() {
        if (EasyPermissions.hasPermissions(this, cameraPerms)) {
            Log.e("TAG", "permission ok");

        } else {
            EasyPermissions.requestPermissions(this, "you must give your permission", REQUEST_PHOTO_CODE, cameraPerms);
            Log.e("TAG", "no permission");

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }


}
