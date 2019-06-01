package com.apps.elkafrawyel.movies_tv;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    Fragment F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Obtain the FireBase Analytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        F = new F_Movie();
        Bundle bundle = new Bundle();
        bundle.putString("Type", "upcoming");
        F.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
        this.setTitle("UpComing Movies");

        Fabric.with(this, new Crashlytics());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Now_Playing) {
            Fragment F = new F_Movie();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "now_playing");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("Now Playing Movies");

            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Now Playing Movies");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Movies Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.UpComing_Movies) {
            Fragment F = new F_Movie();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "upcoming");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("UpComing Movies");

            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "UpComing Movies");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Movies Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.Popular_Movies) {
            Fragment F = new F_Movie();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "popular");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("Popular Movies");


            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Popular Movies");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Movies Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.Top_Rated) {
            Fragment F = new F_Movie();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "top_rated");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("Top Rated Movies");

            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Top Rated Movies");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Movies Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.Popular_Series) {
            Fragment F = new F_TV();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "popular");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("Popular Series");

            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Popular Series");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Series Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.Top_Rated_Series) {
            Fragment F = new F_TV();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "top_rated");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("Top_Rated Series");

            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Top_Rated Series");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Series Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.OnAir_Series) {
            Fragment F = new F_TV();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "on_the_air");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("On Air Series");

            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "On Air Series");
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Series Click");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        } else if (id == R.id.privacy) {
            WebView webView = new WebView(this);
            webView.loadUrl("https://eaststar1.com/PrivacyPolicy.html");
            webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            new AlertDialog.Builder(this)
                    .setView(webView)
                    .setTitle("Privacy Policy")
                    .setPositiveButton("OK", null)
                    .setCancelable(true)
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
