package com.serunibelajar.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.ArrayList;

public class SliderAdapter extends CardSliderAdapter<SliderAdapter.MovieViewHolder> {

    private ArrayList<SliderData> data;

    public SliderAdapter(ArrayList<SliderData> data){
        this.data = data;
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_content, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void bindVH(MovieViewHolder movieViewHolder, int i) {
        SliderData sliderData = data.get(i);
        ImageView images = movieViewHolder.itemView.findViewById(R.id.images);
        Glide.with(movieViewHolder.itemView).load(sliderData.getImage()).into(images);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        public MovieViewHolder(View view){
            super(view);
        }
    }
}