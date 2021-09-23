package com.thesaugat.appcommerce.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.thesaugat.appcommerce.home.MainActivity;
import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.utils.SharedPrefUtils;
import com.thesaugat.appcommerce.userAccount.UserAccountActivity;

public class SplashScreenActivity extends AppCompatActivity {
    boolean isLoggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIsLoggedInOrNot();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn)
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashScreenActivity.this, UserAccountActivity.class));

                finish();
            }
        }, 1000);


    }

    public void getIsLoggedInOrNot() {
        isLoggedIn = SharedPrefUtils.getBool(this, getString(R.string.isLoggedKey), false);
    }
}