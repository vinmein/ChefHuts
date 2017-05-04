package com.vinmein.chefhuts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.luseen.simplepermission.permissions.Permission;
import com.luseen.simplepermission.permissions.PermissionActivity;
import com.luseen.simplepermission.permissions.PermissionUtils;
import com.luseen.simplepermission.permissions.SinglePermissionCallback;
import com.vinmein.chefhuts.R;
import com.vinmein.chefhuts.DataClass.dataprocess;

public class SplashScreen extends PermissionActivity {

    dataprocess processor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        processor = dataprocess.getInstance(this);
        if (PermissionUtils.isMarshmallowOrHigher()) {
            requestPermission(Permission.CAMERA, new SinglePermissionCallback() {
                @Override
                public void onPermissionResult(boolean permissionGranted, boolean isPermissionDeniedForever) {
                    Toast.makeText(getApplicationContext(), "Permission granted = " + permissionGranted +
                            "\nPermission denied forever = " + isPermissionDeniedForever, Toast.LENGTH_LONG).show();
                    if (permissionGranted) {
                        Thread background = new Thread() {
                            public void run() {
                                try {
                                    sleep(3 * 1000);
                                    if (processor.getUserId() != null) {
                                        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i1);
                                    } else if (processor.getUserId() == null) {
                                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(i);
                                    }

                                    //Remove activity
                                    finish();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        // start thread
                        background.start();

                    } else if (isPermissionDeniedForever) {
                       // PermissionUtils.openApplicationSettings(getApplicationContext());
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Grant permission manually", Toast.LENGTH_LONG).show();
                    }

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "PermissionUtils.isMarshmallowOrHigher() = false", Toast.LENGTH_LONG).show();
            Thread background = new Thread() {
                public void run() {
                    try {
                        sleep(3 * 1000);
                        if (processor.getUserId() != null) {
                            Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i1);
                        } else if (processor.getUserId() == null) {
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                        }
                        //Remove activity
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            // start thread
            background.start();


        }
    }


}





