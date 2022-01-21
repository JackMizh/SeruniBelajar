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

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.ViewHolder>{

    private Context context;
    private List<Absensi> list;

    public AbsensiAdapter(Context context, List<Absensi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AbsensiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.absensi_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Absensi absensi = list.get(position);

        holder.no.setText(String.valueOf(position+1));
        holder.nama.setText(absensi.getNama());
        holder.selesai.setText(absensi.getJam_selesai());
        holder.mulai.setText(absensi.getJam_mulai());
        holder.status.setText(absensi.getKehadiran());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView no, nama, mulai, selesai, status;

        public ViewHolder(View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            nama = itemView.findViewById(R.id.nama);
            mulai = itemView.findViewById(R.id.mulai);
            selesai = itemView.findViewById(R.id.selesai);
            status = itemView.findViewById(R.id.status);
        }
    }
}
