package com.example.cloudcomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudcomputingproject.DoctorOrPatient;
import com.example.cloudcomputingproject.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    Handler h = new Handler();
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // قم بتهيئة Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // سجّل حدث فتح التطبيق
        logAppOpenEvent();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, DoctorOrPatient.class));
                finish();
            }
        }, 2000);
    }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // سجّل حدث استئناف التطبيق
        logAppResumeEvent();
    }

    private void logAppOpenEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Splash Screen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "app_open");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "App Open");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
    }

    private void logAppResumeEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Main Screen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "app_resume");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "App Resume");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
}