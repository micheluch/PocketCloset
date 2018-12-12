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

public class SearchClothesForOutfitRecyclerView extends RecyclerView.Adapter<SearchClothesForOutfitRecyclerView.MyViewHolder> {

    private Context mContext;
    private List<Entry> mData;

    public SearchClothesForOutfitRecyclerView(Context mContext, List<Entry> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SearchClothesForOutfitRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.entry_cardview, parent,false);

        return new SearchClothesForOutfitRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchClothesForOutfitRecyclerView.MyViewHolder viewHolder, final int i) {

        viewHolder.tv_entry_title.setText(mData.get(i).getEntryName());

        viewHolder.img_entry_thumbnail.setImageBitmap(mData.get(i).retrieveImageFromFolder());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, CreateOutfit.class);
                intent.putExtra("name", mData.get(i).getEntryName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_entry_title;
        ImageView img_entry_thumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_entry_title = (TextView) itemView.findViewById(R.id.specific_clothing_id);
            img_entry_thumbnail = (ImageView) itemView.findViewById(R.id.specific_clothing_image_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}
