package com.example.mahmoudashraf.mymovies;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * A placeholder fragment containing a simple view.
 */
public class F_Details extends Fragment {

    String Base_URL = "https://image.tmdb.org/t/p/w500";
    public static Movie movie=null;
    @BindView(R.id.horizontal_recycler_view)
    RecyclerView Rec_Trailers;
    @BindView(R.id.ReviewsList)
    RecyclerView Rec_Reviews;
    @BindView(R.id.txtTralier)
    TextView txtTrailer;
    @BindView(R.id.Reviewtxt)
    TextView txtreview;
    @BindView(R.id.Poster_Image)
    ImageView Poster;
    @BindView(R.id.txt_movie_name)
    TextView Title;
    @BindView(R.id.txt_movie_vote)
    TextView Vote;
    @BindView(R.id.txt_release_date)
    TextView ReleaseDate;
    @BindView(R.id.txt_overview)
    TextView Description;

    public F_Details() {
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.f_movie_details, container, false);
        ButterKnife.bind(this,v);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        Rec_Trailers.setLayoutManager(horizontalLayoutManagaer);
        Rec_Trailers.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        Rec_Reviews.setLayoutManager(linearLayoutManager);
        Rec_Reviews.setHasFixedSize(true);

        Configuration conf= v.getResources().getConfiguration();
        if(conf.smallestScreenWidthDp>=600) {
            movie=getArguments().getParcelable("movie");
        }else {
            movie = getActivity().getIntent().getExtras().getParcelable("movie");
        }
        if (movie == null) {
            Toast.makeText(getContext(), "Can't display any Movie", Toast.LENGTH_SHORT).show();
            return v;
        }
        SetMovieToViews(movie);
        GetTrailers(movie.getId());
        GetReviews(movie.getId());
        return  v;
    }

    private void SetMovieToViews(Movie movie) {
        try {
            String Path = Base_URL + movie.getPosterPath();
            Picasso.with(getContext()).load(Path).into(Poster);
            Title.setText(movie.getTitle());
            Title.setSelected(true);
            Vote.setText(movie.getVoteAverage());
            String year=movie.getReleaseDate();
            ReleaseDate.setText(year.substring(0,4));
            Description.setText(movie.getOverview());
            Description.setSelected(true);
        }catch (Exception ignored){

        }
    }
    ArrayList<Trailer> Trailers=new ArrayList<>();
    RequestQueue mRequestQueue=VolleySingleton.getInstance().getRequestQueue();
    public void GetTrailers(int movie_id) {
        final String TrailersUrl="https://api.themoviedb.org/3/movie/"+movie_id+"/videos?api_key=83900e399daa5d56e8aaefb7871cf094&language=en" +
                "-US";
        JsonObjectRequest mObjectRequest=new JsonObjectRequest(TrailersUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array=response.getJSONArray("results");
                            for (int i=0;i<array.length();i++){
                                String arrayToString=array.get(i).toString();
                                Trailer trailer= new Gson().fromJson(arrayToString,Trailer.class);
                                Trailers.add(trailer);
                            }
                            Trailer_Adapter adapter=new Trailer_Adapter(getContext(),Trailers);
                            Rec_Trailers.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mObjectRequest);
    }
    ArrayList<Review> Reviews=new ArrayList<>();
    public void GetReviews(int movie_id)    {
        final String TrailersUrl="https://api.themoviedb.org/3/movie/"+movie_id+"/reviews?api_key=83900e399daa5d56e8aaefb7871cf094&language=en-US";
        JsonObjectRequest mObjectRequest=new JsonObjectRequest(TrailersUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array=response.getJSONArray("results");
                            for (int i=0;i<array.length();i++){
                                String arrayToString=array.get(i).toString();
                                Review review= new Gson().fromJson(arrayToString,Review.class);
                                Reviews.add(review);
                            }
                            Review_Adapter adapter=new Review_Adapter(getContext(),Reviews);
                            Rec_Reviews.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mObjectRequest);
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager mConnectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mConnectivity != null;
        NetworkInfo networkInfo = mConnectivity.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
