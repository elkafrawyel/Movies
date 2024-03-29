package com.apps.elkafrawyel.movies_tv;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Series_Adapter extends RecyclerView.Adapter<Series_Adapter.SeriesViewHolder> {

    private InterstitialAd OpenAd;

    private ArrayList<Series> mData = new ArrayList<>();
    private Context context;
    private static final String Base_URL = "https://image.tmdb.org/t/p/w500";

    Series_Adapter(final Context context, ArrayList<Series> mData) {
        this.mData = mData;
        this.context = context;

        OpenAd = new InterstitialAd(context);
        OpenAd.setAdUnitId("ca-app-pub-5669751081498672/3763065868");
        OpenAd.loadAd(new AdRequest.Builder().build());
        OpenAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                OpenAd.loadAd(new AdRequest.Builder().build());
                Toast.makeText(context, "Series Details Coming Soon.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                OpenAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    private void ShowOpenAd() {
//        if (OpenAd.isLoaded()) {
//            OpenAd.show();
//        }

        Toast.makeText(context, "Series Details Coming Soon.", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        return new SeriesViewHolder(layoutInflater.inflate(R.layout.r_tv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        if (mData.size() > 0) {
            Series series = mData.get(position);
            holder.SetSeries(series);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class SeriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.TV_Image)
        ImageView TVImage;
        @BindView(R.id.TV_Name)
        TextView TVName;
        View mView;

        SeriesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowOpenAd();
                }
            });
        }

        void SetSeries(final Series series) {
            TVName.setText(series.getName());
            TVName.setSelected(true);
            String Poster_Path = Base_URL + series.getPosterPath();

            Glide.with(context)
                    .load(Poster_Path)
                    .listener(GlidePalette.with(Poster_Path)
                            .use(GlidePalette.Profile.MUTED_DARK)
                            .intoBackground(mView)
                            .intoTextColor(TVName)

                            .use(GlidePalette.Profile.VIBRANT)
                            .intoBackground(mView, GlidePalette.Swatch.RGB)
                            .intoTextColor(TVName, GlidePalette.Swatch.BODY_TEXT_COLOR)
                            .crossfade(true)
                    ).into(TVImage);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowOpenAd();
                }
            });

        }
    }
}
