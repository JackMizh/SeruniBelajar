package com.serunibelajar.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapelAdapter extends RecyclerView.Adapter<MapelAdapter.ViewHolder>{

    private Context context;
    private List<Mapel> list;

    public MapelAdapter(Context context, List<Mapel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MapelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mapel_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mapel mapel = list.get(position);

        holder.no.setText(String.valueOf(position+1));
        holder.nama.setText(mapel.getNama_mapel());
        holder.selesai.setText(mapel.getJamselesai_mapel());
        holder.mulai.setText(mapel.getJammulai_mapel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView no, nama, mulai, selesai;

        public ViewHolder(View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            nama = itemView.findViewById(R.id.nama);
            mulai = itemView.findViewById(R.id.mulai);
            selesai = itemView.findViewById(R.id.selesai);
        }
    }
}
