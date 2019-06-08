package com.apps.elkafrawyel.movies_tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;


public class Splash extends AppCompatActivity {

    private static final long delayTime = 3000;
    Handler handler = new Handler();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        handler.postDelayed(postTask, delayTime);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(postTask);
        super.onDestroy();
    }

    Runnable postTask = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(context, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    };
}
