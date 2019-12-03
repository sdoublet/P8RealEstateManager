package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
public class NetworkConnectivityTest {

    private Context context;


@Rule
public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setup(){
        context = InstrumentationRegistry.getInstrumentation().getContext();
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();

    }

    @Test
    public void networkTypeTest(){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo   = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo.isConnected()){
         onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("Connected by WiFi")));
        }else if (mobileInfo.isConnected()){
            onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("Connected to Mobile data")));
        }else {
            onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("No network available")));
        }

    }


}
