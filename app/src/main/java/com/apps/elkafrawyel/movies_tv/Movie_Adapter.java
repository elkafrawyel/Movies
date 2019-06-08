package com.apps.elkafrawyel.movies_tv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.github.florent37.picassopalette.PicassoPalette;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ashraf on 1/9/2018.
 */

public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.Movie_ViewHolder> {

    private InterstitialAd OpenAd;
    private Context context;
    private Movie mMovie;
    private ArrayList<Movie> Data=new ArrayList<>();
    private static final String Base_URL="https://image.tmdb.org/t/p/w500";
    Movie_Adapter(Context context, ArrayList<Movie> data) {
        this.context = context;
        Data = data;
        OpenAd = new InterstitialAd(context);
        OpenAd.setAdUnitId("ca-app-pub-5669751081498672/6390675115");
        OpenAd.loadAd(new AdRequest.Builder().build());
        OpenAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                OpenMovie();
                OpenAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdClicked() {
                OpenAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    @NonNull
    @Override
    public Movie_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return new Movie_ViewHolder(inflater.inflate(R.layout.r_movie,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Movie_ViewHolder holder, int position) {
        if (Data.size()>0){
            Movie movie=Data.get(position);
            holder.SetMovie(movie);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    class Movie_ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        @BindView(R.id.Movie_Image)
        ImageView MovieImage;
        @BindView(R.id.Movie_Name)
        TextView MovieName;
        Movie_ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            ButterKnife.bind(this,mView);
        }
        void SetMovie(final Movie movie) {
            MovieName.setText(movie.getTitle());
            MovieName.setSelected(true);
            String Poster_Path = Base_URL + movie.getPosterPath();
            Glide.with(context)
                    .load(Poster_Path)
                    .listener(GlidePalette.with(Poster_Path)
                            .use(GlidePalette.Profile.MUTED_DARK)
                            .intoBackground(mView)
                            .intoTextColor(MovieName)
                            .use(GlidePalette.Profile.VIBRANT)
                            .intoBackground(mView, GlidePalette.Swatch.RGB)
                            .intoTextColor(MovieName, GlidePalette.Swatch.BODY_TEXT_COLOR)
                            .crossfade(true)
                    ).into(MovieImage);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMovie = movie;
                    ShowOpenAd();
                }
            });
        }
    }

    private void ShowOpenAd() {
        if (OpenAd.isLoaded()) {
            OpenAd.show();
        } else {
            OpenMovie();
        }
    }

    private void OpenMovie(){
        Intent i = new Intent(context, Movie_Details.class);
        i.putExtra("movie", mMovie);
        context.startActivity(i);
    }
}
