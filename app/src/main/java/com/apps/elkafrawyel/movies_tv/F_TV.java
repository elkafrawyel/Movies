package com.apps.elkafrawyel.movies_tv;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by Mahmoud Ashraf on 1/12/2018.
 */

public class F_TV extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Series_Adapter adapter;
    @BindView(R.id.Rec_TV)
    RecyclerView Rec_TV;
    @BindView(R.id.swipeRefreshLayoutTV)
    SwipeRefreshLayout swipeRefreshLayout;
    String Type;

    @BindView(R.id.adViewS)
    AdView mAdView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Root_View=inflater.inflate(R.layout.f_tv,container,false);
        ButterKnife.bind(this,Root_View);

        Type= Objects.requireNonNull(getArguments()).getString("Type");
        Rec_TV.setHasFixedSize(true);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        Rec_TV.setLayoutManager(layoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mCurrentPage++;
                GetSeries(CollectType(Type));
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        Rec_TV.addOnScrollListener(scrollListener);
        GetSeries(CollectType(Type));
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
        final String BASE_URL = "https://api.themoviedb.org/3/tv/".concat(Type);
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



    Integer mCurrentPage=1;
    private static final String Tag="Movies";
    ArrayList<Series> Series=new ArrayList<>();
    private void GetSeries(String Url){
        // Showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue mRequestQueue=VolleySingleton.getInstance().getRequestQueue();
        mRequestQueue.add(VolleySingleton.getInstance().makeStringResponse(
                Url, new VolleySingleton.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                        JSONObject response=new JSONObject(result);
                        JSONArray array=response.getJSONArray("results");
                        for (int i=0;i<array.length();i++){
                            String object=  array.get(i).toString();
                            // Now do the magic.
                            Series mSeries = new Gson().fromJson(object, Series.class);
                            Series.add(mSeries);
                        }
                        if (mCurrentPage==1){
                            adapter = new Series_Adapter(getContext(),Series);
                            Rec_TV.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                        }else {
                            adapter.notifyItemRangeInserted(adapter.getItemCount(), Series.size() - 1);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new VolleySingleton.JsonVolleyCallbackError() {
                    @Override
                    public void onError(VolleyError error) {
                        MDToast.makeText(Objects.requireNonNull(getContext()),Handler.volleyErrorHandler(error,getContext()),
                                Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
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
        GetSeries(CollectType(Type));
    }
}