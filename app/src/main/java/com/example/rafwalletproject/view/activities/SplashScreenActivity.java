package com.example.rafwalletproject.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.rafwalletproject.R;

public class SplashScreenActivity extends AppCompatActivity {

    //code
    private static String PREFERENCES_NAME = "loginPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin();
    }

    private static int SPLASH_TIME_OUT = 2000;


    private void checkLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("loggedIn", false);
        /*
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(loggedIn){
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

         */
        if(loggedIn){
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(i);
        }
        finish();
    }
}