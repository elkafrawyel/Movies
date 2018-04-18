package com.example.mahmoudashraf.mymovies;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class myMovies extends Application {
    private static myMovies mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        //To allow image offline storage
        Picasso.Builder  builder=new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built=builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);


        MobileAds.initialize(this,"ca-app-pub-5669751081498672~3824399133");
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

}
