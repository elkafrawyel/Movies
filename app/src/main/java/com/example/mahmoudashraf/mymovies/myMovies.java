package com.example.mahmoudashraf.mymovies;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Mahmoud Ashraf on 1/9/2018.
 */

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

    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

}
