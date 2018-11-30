package com.apps.elkafrawyel.movies_tv;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class Movie_Details extends AppCompatActivity  {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollToolBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    String Base_URL = "https://image.tmdb.org/t/p/w500";
    @BindView(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_details);
        ButterKnife.bind(this);
        //=====================================Display Navigation button with toolbar==================================
        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //========================Collapsing Toolbar Layout=============================================================
        Movie movie = Objects.requireNonNull(getIntent().getExtras()).getParcelable("movie");
        if (movie != null) {
            mCollToolBar.setTitle(movie.getTitle());
            SetMovieToViews(movie);
            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, movie.getTitle());
            bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Open Movie");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Fabric.with(this, new Crashlytics());

    }
    private void SetMovieToViews(Movie movie) {
        String BackdropUrl = Base_URL + movie.getBackdropPath();
        Picasso.with(this).load(BackdropUrl).into(mBackdrop);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
