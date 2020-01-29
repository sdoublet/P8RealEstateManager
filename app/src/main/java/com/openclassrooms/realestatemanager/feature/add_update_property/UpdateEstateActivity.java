package com.openclassrooms.realestatemanager.feature.add_update_property;

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
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.googleMap.ApiGeocoding;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.api.geolocation.LocationStream;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public class UpdateEstateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final int REQUEST_PHOTO_CODE = 2345;
    public static final int REQUEST_GALLERY_CODE = 5678;

    ActivityAddPropertyBinding binding;
    private EstateViewModel estateViewModel;
    private Estate estate;
    Picture picture;
    List<Picture> pictures;
    private List<Picture> pictureList;
    private String estateJson;
    private Disposable disposable;
    private String photoPath = null;
    private Uri photoUri;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
        estateJson = getIntent().getStringExtra("estateJson");
        this.getEstateFromJson();
        this.configureViewModel();
        this.configureSpinners();
        this.displayEstateData();
        this.getPictureByEstate();
        this.updateDb();
        this.addPhoto();

    }

    private void getEstateFromJson() {
        Gson gson = new Gson();
        estate = gson.fromJson(estateJson, Estate.class);
    }


    private void getPictureByEstate() {
        this.estateViewModel.getAllPicturesFromEstate(estate.getEstateId()).observe(this, this::setPicture);
    }

    //UI
    // Display estate values
    private void displayEstateData() {
        binding.editPrice.setText(String.valueOf(estate.getPrice()));
        binding.editEstateSurface.setText(String.valueOf(estate.getSurface()));
        binding.editLandSurface.setText(String.valueOf(estate.getSurfaceLand()));
        binding.editDescription.setText(estate.getDescription());
        binding.editAddress.setText(estate.getAddress());
        binding.editZipCode.setText(String.valueOf(estate.getPostalCode()));
        binding.editCity.setText(estate.getCity());
        binding.checkboxSchool.setChecked(estate.isSchool());
        binding.checkboxShop.setChecked(estate.isShop());
        binding.checkboxPark.setChecked(estate.isPark());
        binding.checkboxHospital.setChecked(estate.isHospital());
        binding.checkboxTransport.setChecked(estate.isTransport());
        binding.checkboxAdministration.setChecked(estate.isAdministration());


    }

    // Display spinner values
    private void configureSpinners() {

        int spinnerSelection;
        //For array type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);

        spinnerSelection = adapter.getPosition(estate.getType());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(adapter);
        binding.spinnerType.setSelection(spinnerSelection);
        binding.spinnerType.setOnItemSelectedListener(this);
        //For array number
        ArrayAdapter<CharSequence> adapterNumber = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Room
        spinnerSelection = adapterNumber.getPosition(String.valueOf(estate.getNbRoom()));
        binding.spinnerRoom.setAdapter(adapterNumber);
        binding.spinnerRoom.setSelection(spinnerSelection);
        //Bedroom
        spinnerSelection = adapterNumber.getPosition(String.valueOf(estate.getBedroom()));
        binding.spinnerBedroom.setAdapter(adapterNumber);
        binding.spinnerBedroom.setSelection(spinnerSelection);
        //Bathroom
        spinnerSelection = adapterNumber.getPosition(String.valueOf(estate.getBathroom()));
        binding.spinnerBathroom.setAdapter(adapterNumber);
        binding.spinnerBathroom.setSelection(spinnerSelection);
        //click listener
        binding.spinnerRoom.setOnItemSelectedListener(this);
        binding.spinnerBedroom.setOnItemSelectedListener(this);
        binding.spinnerBathroom.setOnItemSelectedListener(this);

        //For array heating
        ArrayAdapter<CharSequence> adapterHeating = ArrayAdapter.createFromResource(this, R.array.heating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelection = adapterHeating.getPosition(estate.getHeating());
        binding.spinnerHeating.setAdapter(adapterHeating);
        binding.spinnerHeating.setSelection(spinnerSelection);
    }

    // Display photo
    private void setPicture(List<Picture> pictureList) {
        pictures = pictureList;
        for (int i = 0; i < pictureList.size(); i++) {
            Log.e("UpdatePiceSize", String.valueOf(pictureList.size()));
            switch (pictureList.size()) {
                case 1:
                    Glide.with(this).load(pictureList.get(0).getUri()).into(binding.mainPhoto);
                    break;
                case 2:
                    Glide.with(this).load(pictureList.get(0).getUri()).into(binding.mainPhoto);
                    Glide.with(this).load(pictureList.get(1).getUri()).into(binding.photo2);
                    break;
                case 3:
                    Glide.with(this).load(pictureList.get(0).getUri()).into(binding.mainPhoto);
                    Glide.with(this).load(pictureList.get(1).getUri()).into(binding.photo2);
                    Glide.with(this).load(pictureList.get(2).getUri()).into(binding.photo3);
                    break;
                case 4:
                    Glide.with(this).load(pictureList.get(0).getUri()).into(binding.mainPhoto);
                    Glide.with(this).load(pictureList.get(1).getUri()).into(binding.photo2);
                    Glide.with(this).load(pictureList.get(2).getUri()).into(binding.photo3);
                    Glide.with(this).load(pictureList.get(3).getUri()).into(binding.photo4);
                    break;
                case 5:
                    Glide.with(this).load(pictureList.get(0).getUri()).into(binding.mainPhoto);
                    Glide.with(this).load(pictureList.get(1).getUri()).into(binding.photo2);
                    Glide.with(this).load(pictureList.get(2).getUri()).into(binding.photo3);
                    Glide.with(this).load(pictureList.get(3).getUri()).into(binding.photo4);
                    Glide.with(this).load(pictureList.get(4).getUri()).into(binding.photo5);
                    break;

            }

        }
    }

    // onClick on photo
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_photo:
                if (pictures.size() > 0) {
                    picture = pictures.get(0);
                    displayAlertDialog();
                } else binding.mainPhoto.setClickable(false);
                break;
            case R.id.photo_2:
                if (pictures.size() > 1) {
                    picture = pictures.get(1);
                    displayAlertDialog();
                } else binding.photo2.setClickable(false);
                break;
            case R.id.photo_3:
                if (pictures.size() > 2) {
                    picture = pictures.get(2);
                    displayAlertDialog();
                } else binding.photo3.setClickable(false);
                break;
            case R.id.photo_4:
                if (pictures.size() > 3) {
                    picture = pictures.get(3);
                    displayAlertDialog();
                } else binding.photo4.setClickable(false);
                break;
            case R.id.photo_5:
                if (pictures.size() > 4) {
                    picture = pictures.get(4);
                    displayAlertDialog();
                } else binding.photo5.setClickable(false);
                break;

        }

    }


    // display alertDialog when user click on a picture and delete photo
    private void displayAlertDialog() {
        Log.e("onclick", "clickMonFrere");
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Dialog);
        builder.setTitle("Do you want to delete this picture?");
        //delete photo
        builder.setPositiveButton("Yes", (dialog, which) -> estateViewModel.deletePicture(picture));
        builder.setNegativeButton("No", (dialog, which) -> Log.e("UpdatePicUri", String.valueOf(picture.getUri())));
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
    }


    //------------------ADD PHOTO--------------------------
    // Add new pictures
    private void addPhoto() {
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

    //Get a photo since gallery device folder
    private void getPhotoSinceGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_GALLERY_CODE);
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
                photoUri = FileProvider.getUriForFile(UpdateEstateActivity.this,
                        UpdateEstateActivity.this.getApplicationContext().getPackageName() + ".provider", photoFile);
                Log.e("uri", String.valueOf(photoUri));
                // transfer Uri to intent
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_PHOTO_CODE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            Log.e("saveDb", String.valueOf(picture.getPhotoId())+ pictureList.size() + estateId);
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
                    // TODO: 04/12/2019 save in db


                    setImageViewWithPicture(photoUri);//jen suis la--------------------
                    Picture picture = new Picture(Uri.parse(photoPath), null, 0);


                    pictureList.add(picture);
                    Log.e("AddProPicList", String.valueOf(pictureList.size()));

                }
            });
            // TODO: 04/12/2019 save in db for negative btn
            builder.setNegativeButton("App", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setImageViewWithPicture(photoUri);
                    Picture picture = new Picture(Uri.parse(photoPath), null, 0);
                    pictureList.add(picture);
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

    //--------------------------------------------------------------------------------------

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    //UPDATE UI
    private void updateUi() {
        estateViewModel.updateEstate(estate);

    }

    //save onclick save btn
    private void updateDb() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeValueOfEstate();

                Toast.makeText(getApplicationContext(), "Your estate is update", Toast.LENGTH_SHORT).show();
                String address = binding.editAddress.getText().toString() + " " + binding.editZipCode.getText().toString() + " "
                        + binding.editCity.getText().toString() + ", " + Utils.localeCountry(getApplicationContext());
                executeHttpRequestWithretrofit(address);
                Log.e("onclick", "ok");
                updateUi();
            }
        });
    }

    //take values
    private void takeValueOfEstate() {
        estate.setPrice(Integer.parseInt(binding.editPrice.getText().toString()));
        estate.setDescription(binding.editDescription.getText().toString());
        estate.setAddress(binding.editAddress.getText().toString());
        estate.setPostalCode(Integer.parseInt(binding.editZipCode.getText().toString()));
        estate.setCity(binding.editCity.getText().toString());
        estate.setSurface(Float.parseFloat(binding.editEstateSurface.getText().toString()));
        estate.setSurfaceLand(Float.parseFloat(binding.editLandSurface.getText().toString()));
        estate.setType(binding.spinnerType.getSelectedItem().toString());
        estate.setNbRoom(Integer.parseInt(binding.spinnerRoom.getSelectedItem().toString()));
        estate.setBedroom(Integer.parseInt(binding.spinnerBedroom.getSelectedItem().toString()));
        estate.setBathroom(Integer.parseInt(binding.spinnerBathroom.getSelectedItem().toString()));
        estate.setHeating(binding.spinnerHeating.getSelectedItem().toString());


    }

    // TODO: 14/12/2019 put execute http request for new address
    //----------------------
    //HTTP REQUEST
    //----------------------
    public void executeHttpRequestWithretrofit(String address) {
        this.disposable = LocationStream.streamFetchGeocoding(address, BuildConfig.google_maps_api_key).subscribeWith(new DisposableObserver<ApiGeocoding>() {
            @Override
            public void onNext(ApiGeocoding apiGeocoding) {

                estate.setLatitude(apiGeocoding.getResults().get(0).getGeometry().getLocation().getLat());
                estate.setLongitude(apiGeocoding.getResults().get(0).getGeometry().getLocation().getLng());
                updateUi();


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
            }
        });
    }

}
