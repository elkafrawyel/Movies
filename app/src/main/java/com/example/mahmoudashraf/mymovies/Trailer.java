package com.example.mahmoudashraf.mymovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mahmoud Ashraf on 11/10/2016.
 */

public class Trailer {
    @SerializedName("key")
    private String TrailerKey;
    @SerializedName("name")
    private String TrailerName;

    public Trailer(String trailerKey, String trailerName) {
        TrailerKey = trailerKey;
        TrailerName = trailerName;
    }

    public String getTrailerKey() {
        return TrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        TrailerKey = trailerKey;
    }

    public String getTrailerName() {
        return TrailerName;
    }

    public void setTrailerName(String trailerName) {
        TrailerName = trailerName;
    }
}
