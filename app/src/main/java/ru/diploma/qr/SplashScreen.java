package ru.diploma.qr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maxim Andrienko
 * 4/26/20
 */
public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DELAY = 500;
    FirebaseRemoteConfig firebaseRemoteConfig;

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
        }, SPLASH_DELAY);
//        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(10)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        Map<String, Object> defaultData = new HashMap<>();
//        defaultData.put("can_use", true);
//        firebaseRemoteConfig.setDefaultsAsync(defaultData);
//        final Task<Void> fetch = firebaseRemoteConfig.fetch(0);
//        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                firebaseRemoteConfig.fetchAndActivate();
//                boolean boolData = firebaseRemoteConfig.getBoolean("can_use");
//                if(!boolData){
//                    showAlertDialog();
//                } else {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                            startActivity(mainIntent);
//                            finish();
//                        }
//                    }, SPLASH_DELAY);
//                }
//            }
//        });
    }
//    private void showAlertDialog() {
//        final AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Внимание")
//                .setMessage("Время пробной версии приложения истекло. Обратитесь к администратору")
//                .show();
//        dialog.setCancelable(false);
//    }

}
