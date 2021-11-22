package com.example.a11_9project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Creates runnable in order to switch from SplashActivity to MainActivity
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent test = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(test);
                finish();
            }
        };

        //Sets a 6.5 second wait timer on the SplashActivity before switching to Main Activity
        Handler h = new Handler();
        h.postDelayed(r, 6500);
    }
}