package com.example.mahmoudashraf.mymovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ashraf on 11/10/2016.
 */

public class Trailer_Adapter extends RecyclerView.Adapter<Trailer_Adapter.TrailerViewHolder> {
    Context C;
    private ArrayList<Trailer> horizontalTrailers;

    Trailer_Adapter(Context C, ArrayList<Trailer> Data) {
        this.horizontalTrailers = Data;
        this.C=C;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Trailer_name)
        TextView trailer_name;
        @BindView(R.id.Trailer_img)
        ImageView trailer_image;
        View mView;
        TrailerViewHolder(View v) {
            super(v);
            mView=v;
            ButterKnife.bind(this,mView);
        }
    }
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)C.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View v=inflater.inflate(R.layout.r_trailer,parent,false);
        return new TrailerViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int i) {
        final Trailer T = GetItem(i);
        holder.trailer_name.setText(T.getTrailerName());
        holder.trailer_name.setSelected(true);

        String TrailerImageUrl = "http://img.youtube.com/vi/" + T.getTrailerKey() + "/default.jpg";
        Picasso.with(C).load(TrailerImageUrl).into(holder.trailer_image);
        holder.trailer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(C, "Opening " + T.getTrailerName(), Toast.LENGTH_LONG).show();
                String VideoUrl = "https://www.youtube.com/watch?v=" + T.getTrailerKey();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(VideoUrl));
                holder.trailer_image.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return horizontalTrailers.size();
    }

    private Trailer GetItem(int position)
    {
        return horizontalTrailers.get(position);
    }
}
