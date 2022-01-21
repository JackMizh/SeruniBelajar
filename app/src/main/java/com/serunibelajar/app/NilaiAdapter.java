package com.serunibelajar.app;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class NilaiAdapter extends RecyclerView.Adapter<NilaiAdapter.ViewHolder>{

    private Context context;
    private List<Nilai> list;

    public NilaiAdapter(Context context, List<Nilai> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NilaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.nilai_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nilai nilai = list.get(position);

        holder.no.setText(String.valueOf(position+1));
        holder.nama.setText(nilai.getNama());
        holder.nilai.setText(nilai.getNilai());
        holder.kkm.setText(nilai.getKkm());
        int nilaiint = Integer.parseInt(nilai.getNilai());
        int kkmint = Integer.parseInt(nilai.getKkm());
        if(nilaiint < kkmint)
        {
            holder.status.setText("Tidak Lulus");
        }
        else {
            holder.status.setText("Lulus");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView no, nama, nilai, kkm, status;

        public ViewHolder(View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            nama = itemView.findViewById(R.id.nama);
            nilai = itemView.findViewById(R.id.nilai);
            kkm = itemView.findViewById(R.id.kkm);
            status = itemView.findViewById(R.id.status);
        }
    }
}
