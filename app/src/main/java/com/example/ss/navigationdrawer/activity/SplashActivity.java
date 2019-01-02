package com.example.ss.navigationdrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.session.Session;

public class SplashActivity extends AppCompatActivity {

    private  final int SPLASH_DISPLAY_LENGTH = 4000;
    Activity activity;
    Session session;
    public static final String TAG = SplashActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = SplashActivity.this;
        session = new Session(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!session.getUser_id().equalsIgnoreCase("")&& session.getUser_id()!=null) {


                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }else {


                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();

                }
            }


        }, SPLASH_DISPLAY_LENGTH);


    }



}
