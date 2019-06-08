package com.apps.elkafrawyel.movies_tv;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;


public class Handler {

    public static String volleyErrorHandler(VolleyError error, Context context) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return "Connection Error";
        } else if (error instanceof AuthFailureError) {
            return "Connection Error";
        } else if (error instanceof ServerError) {
            return "Connection Error";
        } else if (error instanceof NetworkError) {
            return "Connection Error";
        } else if (error instanceof ParseError) {
            return "Connection Error";
        } else {
            return "Connection Error";
        }
    }
}
