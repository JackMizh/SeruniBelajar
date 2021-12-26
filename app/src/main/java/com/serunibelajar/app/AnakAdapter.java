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

public class AnakAdapter extends RecyclerView.Adapter<AnakAdapter.ViewHolder>{

    private Context context;
    private List<Anak> list;

    public AnakAdapter(Context context, List<Anak> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AnakAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_anak, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnakAdapter.ViewHolder holder, int position) {
        Anak anak = list.get(position);

        holder.nama_siswa.setText(anak.getNama_siswa());
        holder.nama_sekolah.setText(String.valueOf(anak.getNama_sekolah()));
        if(String.valueOf(anak.getFoto_siswa()).equals(""))
        {
            holder.foto_siswa.setImageResource(R.drawable.logoputih);
        }else {
            Glide.with(holder.itemView.getContext()).load(String.valueOf(anak.getFoto_siswa())).into(holder.foto_siswa);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(view.getContext(), SekolahActivity.class);
                in.putExtra("previllage", "Orang Tua");
                in.putExtra("sekolah", String.valueOf(anak.getId_sekolah()));
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_siswa, nama_sekolah;
        public CircleImageView foto_siswa;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            nama_siswa = itemView.findViewById(R.id.nama);
            nama_sekolah = itemView.findViewById(R.id.sekolah);
            foto_siswa = itemView.findViewById(R.id.profile_image);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
