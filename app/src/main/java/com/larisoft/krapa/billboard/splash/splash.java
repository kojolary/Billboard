package com.larisoft.krapa.billboard.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.larisoft.krapa.billboard.R;
import com.larisoft.krapa.billboard.login.login;

public class splash extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    LinearLayout l1, l2;
    Button btnsub;
    Animation uptodown, downtoup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);


        btnsub = findViewById(R.id.buttonsub);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);

        uptodown = AnimationUtils.loadAnimation(splash.this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(splash.this, R.anim.downtoup);

        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent myIntent = new Intent(splash.this, login.class);
                startActivity(myIntent);

                // close this activity
                finish();
            }


        }, SPLASH_TIME_OUT);


    }
}
