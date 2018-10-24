package com.example.michael.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class OneClosetRecyclerViewAdapter extends RecyclerView.Adapter<OneClosetRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Clothing> mData;
    //TODO: Switch to wearable abstract class

    public OneClosetRecyclerViewAdapter(Context mContext, List<Clothing> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_wearable_closet, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        viewHolder.tv_wearable_title.setText(mData.get(i).getClothingName());
        viewHolder.img_wearable_thumbnail.setImageResource(mData.get(i).getThumbnail());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(mContext, ClothingActivity.class);
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_wearable_title;
        ImageView img_wearable_thumbnail;
        CardView cardView;

        public MyViewHolder(View closetView) {
            super(closetView);

            tv_wearable_title = (TextView) itemView.findViewById(R.id.wearable_id);
            img_wearable_thumbnail = (ImageView) itemView.findViewById(R.id.wearable_image_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
