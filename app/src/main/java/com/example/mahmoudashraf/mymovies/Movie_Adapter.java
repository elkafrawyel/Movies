package com.example.mahmoudashraf.mymovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

    private Context context;
    private ArrayList<Movie> Data=new ArrayList<>();
    private static final String Base_URL="https://image.tmdb.org/t/p/w500";
    Movie_Adapter(Context context, ArrayList<Movie> data) {
        this.context = context;
        Data = data;
    }

    @Override
    public Movie_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return new Movie_ViewHolder(inflater.inflate(R.layout.r_movie,parent,false));
    }

    @Override
    public void onBindViewHolder(Movie_ViewHolder holder, int position) {
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
        void SetMovie(final Movie movie){
            MovieName.setText(movie.getTitle());
            MovieName.setSelected(true);
            String Poster_Path=Base_URL+movie.getPosterPath();

//            Picasso.with(context)
//                    .load(Poster_Path)
//                    .into(MovieImage,
//                            PicassoPalette.with(Poster_Path,MovieImage)
//                                    .use(PicassoPalette.Profile.MUTED_DARK)
//                                    .intoBackground(mView)
//                                    .intoTextColor(MovieName)
//                                    .use(PicassoPalette.Profile.VIBRANT)
//                                    .intoBackground(mView, PicassoPalette.Swatch.RGB)
//                                    .intoTextColor(MovieName, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
//                    );

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
                    Intent i = new Intent(context, Movie_Details.class);
                    i.putExtra("movie", movie);
                    context.startActivity(i);
                }
            });

        }
    }
}
