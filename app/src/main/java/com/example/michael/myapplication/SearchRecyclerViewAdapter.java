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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {


    private Context myContext;
    private List<Entry> searchItems;

    public SearchRecyclerViewAdapter(Context myContext, List<Entry> myCloset) {
        this.myContext = myContext;
        this.searchItems = myCloset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(myContext);
        view = mInflater.inflate(R.layout.search_item_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_closet_name.setText(searchItems.get(i).getEntryName());
        myViewHolder.img_closet_thumbnail.setImageBitmap(searchItems.get(i).getImage());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Entry.pocketClassType type = searchItems.get(i).getEntryType();
                Intent intent;

                switch(type)
                {
                    case CLOSET_TYPE:
                        intent = new Intent(myContext,OneClosetActivity.class);
                        //intent.putExtra("Name", myCloset.get(i).getClosetName());
                        //intent.putExtra("Description", myCloset.get(i).getDescription());
                        //intent.putExtra("Thumbnail", myCloset.get(i).getThumbnail());
                        myContext.startActivity(intent);
                        break;
                    case CLOTHING_TYPE:
                        intent = new Intent(myContext,ViewClothing.class);
//                        intent.putExtra("Name", searchItems.get(i).getEntryName());
//                        intent.putExtra("Description", searchItems.get(i).;
//                        intent.putExtra("Thumbnail", myCloset.get(i).getThumbnail());
                        myContext.startActivity(intent);
                        break;
                    case OUTFIT_TYPE:
                        intent = new Intent(myContext,AddOutfit.class);
//                        intent.putExtra("Name", searchItems.get(i).getEntryName());
//                        intent.putExtra("Description", myCloset.get(i).getDescription());
//                        intent.putExtra("Thumbnail", myCloset.get(i).getThumbnail());
                        myContext.startActivity(intent);
                        break;
                        default:
                            break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_closet_name;
        ImageView img_closet_thumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_closet_name = (TextView) itemView.findViewById(R.id.closet_name_id);
            img_closet_thumbnail = (ImageView) itemView.findViewById(R.id.closet_img_id);
            cardView = (CardView) itemView.findViewById(R.id.search_card_id);

        }
    }

//    public void updateList(List<Closet> newList){
//
//        myCloset = new ArrayList<>();
//        myCloset.addAll(newList);
//        notifyDataSetChanged();
//
//    }


}
