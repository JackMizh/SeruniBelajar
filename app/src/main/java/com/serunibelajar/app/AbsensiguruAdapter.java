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

public class AbsensiguruAdapter extends RecyclerView.Adapter<AbsensiguruAdapter.ViewHolder>{

    private Context context;
    private List<Absensi> list;

    public AbsensiguruAdapter(Context context, List<Absensi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AbsensiguruAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.absensiguru_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Absensi absensi = list.get(position);

        holder.no.setText(String.valueOf(position+1));
        holder.nama.setText(absensi.getNamasiswa());
        holder.status.setText(absensi.getKehadiran());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView no, nama, status;

        public ViewHolder(View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            nama = itemView.findViewById(R.id.nama);
            status = itemView.findViewById(R.id.status);
        }
    }
}
