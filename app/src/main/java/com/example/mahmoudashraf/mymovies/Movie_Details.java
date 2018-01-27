package com.example.mahmoudashraf.mymovies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Movie_Details extends AppCompatActivity  {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollToolBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    String Base_URL = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_details);
        ButterKnife.bind(this);
        //=====================================Display Navigation button with toolbar==================================
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //========================Collapsing Toolbar Layout=============================================================
        Movie movie = getIntent().getExtras().getParcelable("movie");
        if (movie != null) {
            mCollToolBar.setTitle(movie.getTitle());
            SetMovieToViews(movie);
        }
    }
    private void SetMovieToViews(Movie movie) {
        String BackdropUrl = Base_URL + movie.getBackdropPath();
        Picasso.with(this).load(BackdropUrl).into(mBackdrop);
    }
}
