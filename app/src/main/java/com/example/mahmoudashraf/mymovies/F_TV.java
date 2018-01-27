package com.example.mahmoudashraf.mymovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private boolean Searching;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Root_View=inflater.inflate(R.layout.f_tv,container,false);
        ButterKnife.bind(this,Root_View);
        Type=getArguments().getString("Type");
        Rec_TV.setHasFixedSize(true);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        Rec_TV.setLayoutManager(layoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if (!Searching){
                        mCurrentPage++;
                        JSON_To_Model(GetFinalUrl(Type,mCurrentPage));
                        swipeRefreshLayout.setRefreshing(true);
                    }
            }
        };
        Rec_TV.addOnScrollListener(scrollListener);
        JSON_To_Model(GetFinalUrl(Type,mCurrentPage));
        // SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        setHasOptionsMenu(true);

        return Root_View;
    }
    private boolean isNetworkAvailable(Context C){
        ConnectivityManager mConnectivity = (ConnectivityManager) C.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mConnectivity != null;
        NetworkInfo networkInfo = mConnectivity.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    String FinalUrl;
    private String GetFinalUrl(String Type,int page){
        FinalUrl="https://api.themoviedb.org/3/tv/"+Type+"?api_key=83900e399daa5d56e8aaefb7871cf094&language=en-US&page="+page;
        return FinalUrl;
    }
    Integer mCurrentPage=1;
    private static final String Tag="Movies";
    ArrayList<Series> Series=new ArrayList<>();
    RequestQueue mRequestQueue= VolleySingleton.getInstance().getRequestQueue();
    private void JSON_To_Model( String Url){
        // Showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!Searching){
                                Log.e(Tag,"Page :"+mCurrentPage);
                                JSONArray array=response.getJSONArray("results");
                                for (int i=0;i<array.length();i++){
                                    String object=  array.get(i).toString();
                                    // Now do the magic.
                                    Series mSeries = new Gson().fromJson(object, Series.class);
                                    Series.add(mSeries);
                                    Log.e(Tag,i+" : "+mSeries.getName());
                                }
                                if (mCurrentPage==1){
                                    adapter = new Series_Adapter(getContext(),Series);
                                    Rec_TV.setAdapter(adapter);
                                    swipeRefreshLayout.setRefreshing(false);
                                }else {
                                    adapter.notifyItemRangeInserted(adapter.getItemCount(), Series.size()
                                            - 1);
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }else {
                                JSONArray array=response.getJSONArray("results");
                                for (int i=0;i<array.length();i++){
                                    String object=  array.get(i).toString();
                                    // Now do the magic.
                                    Series movie = new Gson().fromJson(object, Series.class);
                                    SearchTV.add(movie);
                                }
                                Log.e(Tag,"SearchMovies:"+SearchTV.size());
                                adapter = new Series_Adapter(getContext(),SearchTV);
                                Rec_TV.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        } catch (JSONException e ) {
                            Log.e(Tag,"Error :"+e );
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    swipeRefreshLayout.setRefreshing(false);
                    return Response.success(new JSONObject(jsonString), cacheEntry);

                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        mRequestQueue.add(jsonObjectRequest).setShouldCache(true);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (!Searching)
            JSON_To_Model(GetFinalUrl(Type,mCurrentPage));
    }
    ArrayList<Series> SearchTV=new ArrayList<>();
    String SearchText;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.Search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Searching=true;
                    SearchTV.clear();
                    SearchText=s.replace(" ","%20");
                    swipeRefreshLayout.setRefreshing(false);
                    String SearchUrl="https://api.themoviedb" +
                            ".org/3/search/tv?api_key=83900e399daa5d56e8aaefb7871cf094&query="+SearchText;
                    JSON_To_Model(SearchUrl);
                }else {
                    Searching=false;
                    JSON_To_Model(GetFinalUrl(Type,mCurrentPage));
                }

                return false;
            }
        });
    }
}