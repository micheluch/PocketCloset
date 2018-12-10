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

public class ClothingRecyclerViewAdapter extends RecyclerView.Adapter<ClothingRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Clothing> mData;

    public ClothingRecyclerViewAdapter(Context mContext, List<Clothing> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_clothing_item, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {

        viewHolder.tv_clothing_title.setText(mData.get(i).getEntryName());
        viewHolder.img_clothing_thumbnail.setImageBitmap(mData.get(i).retrieveImageFromFolder());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, ViewClothing.class);
                intent.putExtra("name", mData.get(i).getEntryName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_clothing_title;
        ImageView img_clothing_thumbnail;
        CardView cardView;

        public MyViewHolder(View closetView) {
            super(closetView);

            tv_clothing_title = (TextView) itemView.findViewById(R.id.specific_clothing_id);
            img_clothing_thumbnail = (ImageView) itemView.findViewById(R.id.specific_clothing_image_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
