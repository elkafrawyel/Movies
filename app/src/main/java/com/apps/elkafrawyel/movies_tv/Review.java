package com.apps.elkafrawyel.movies_tv;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mahmoud Ashraf on 11/10/2016.
 */

public class Review {
    @SerializedName("author")
    private String ReviewAuthor;
    @SerializedName("content")
    private String ReviewContent;

    public String getReviewAuthor() {
        return ReviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        ReviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return ReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        ReviewContent = reviewContent;
    }
}
