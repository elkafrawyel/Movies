package com.apps.elkafrawyel.movies_tv;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Mahmoud Ashraf on 1/9/2018.
 */

public class F_Movie extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Movie_Adapter adapter;
    @BindView(R.id.Rec_Movies)
    RecyclerView Rec_Movies;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    String Type;
    @BindView(R.id.adView)
    AdView mAdView;
    Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Root_View = inflater.inflate(R.layout.f_movie, container, false);
        ButterKnife.bind(this, Root_View);
        mContext = Objects.requireNonNull(container).getContext();
        Type = Objects.requireNonNull(getArguments()).getString("Type");
        Rec_Movies.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        Rec_Movies.setLayoutManager(layoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mCurrentPage++;
                GetMovies(CollectType(Type), mCurrentPage);
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        Rec_Movies.addOnScrollListener(scrollListener);
        GetMovies(CollectType(Type), mCurrentPage);
        // SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        setHasOptionsMenu(true);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Fabric.with(getContext(), new Crashlytics());

        return Root_View;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        super.onStart();
    }

    private String CollectType(String Type) {
        final String BASE_URL = "https://api.themoviedb.org/3/movie/".concat(Type);
        String link = null;
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("page", mCurrentPage.toString())
                .appendQueryParameter("language", ("en-US"))
                .appendQueryParameter("api_key", "83900e399daa5d56e8aaefb7871cf094")
                .build();
        try {
            URL mUrl = new URL(uri.toString());
            link = mUrl.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return link;
    }

    Integer mCurrentPage = 1;
    private static final String Tag = "Movies";
    ArrayList<Movie> Movies = new ArrayList<>();

    private void GetMovies(String Url, final Integer page) {
        // Showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        mRequestQueue.add(VolleySingleton.getInstance().makeStringResponse(
                Url, new VolleySingleton.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                        JSONObject response = new JSONObject(result);
                        Log.e(Tag, "Page :" + response.getString("page"));
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            String object = array.get(i).toString();
                            // Now do the magic.
                            Movie movie = new Gson().fromJson(object, Movie.class);
                            Movies.add(movie);
                        }
                        Log.e(Tag, "Movies:" + Movies.size());
                        if (page == 1) {
                            adapter = new Movie_Adapter(mContext, Movies);
                            Rec_Movies.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            if (adapter != null) {
                                adapter.notifyItemRangeInserted(adapter.getItemCount(), Movies.size() - 1);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                }, new VolleySingleton.JsonVolleyCallbackError() {
                    @Override
                    public void onError(VolleyError error) {
                        MDToast.makeText(Objects.requireNonNull(getContext()), Handler.volleyErrorHandler(error, getContext()),
                                Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                }
        ).setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        }));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        GetMovies(CollectType(Type), mCurrentPage);
    }
}
