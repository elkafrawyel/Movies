package com.apps.elkafrawyel.movies_tv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ashraf on 11/10/2016.
 */

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ReviewViewHolder> {

    ArrayList<Review> Reviews;
    Context C;

    public Review_Adapter(Context c,ArrayList<Review> reviews) {
        Reviews = reviews;
        C = c;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) C.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return  new ReviewViewHolder(inflater.inflate(R.layout.r_review,parent,false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review R=Reviews.get(position);
        holder.ReviewAuthor.setText(R.getReviewAuthor() + " :");
        holder.ReviewContent.setText(R.getReviewContent());
    }

    @Override
    public int getItemCount() {
        return Reviews.size();
    }



    class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ReviewAuthor)
        TextView ReviewAuthor;
        @BindView(R.id.ReviewContent)
        TextView ReviewContent;

        View mView;
        ReviewViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            ButterKnife.bind(this,mView);
        }
    }
}








