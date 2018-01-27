package com.example.mahmoudashraf.mymovies;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mahmoud Ashraf on 1/6/2018.
 */

class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(myMovies.getAppContext());
    }

    static VolleySingleton getInstance(){
        if (mInstance==null)
            mInstance=new VolleySingleton();

        return mInstance;
    }

    RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
