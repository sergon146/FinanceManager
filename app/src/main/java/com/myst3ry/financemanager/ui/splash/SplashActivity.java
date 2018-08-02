package com.myst3ry.financemanager.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        processStart();
    }

    private void processStart() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
