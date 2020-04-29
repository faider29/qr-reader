package ru.diploma.qr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Maxim Andrienko
 * 4/26/20
 */
public class SplashScreen extends AppCompatActivity {

    private  final int SPLASH_DELAY = 1000;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },SPLASH_DELAY);

    }

}
