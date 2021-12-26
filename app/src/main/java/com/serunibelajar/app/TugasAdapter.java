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

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.ViewHolder>{

    private Context context;
    private List<Tugas> list;

    public TugasAdapter(Context context, List<Tugas> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TugasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.elearning_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tugas tugas = list.get(position);

        holder.judul_tugas.setText(tugas.getJudul_tugas());
        holder.mapel_tugas.setText(tugas.getMapel_tugas());
        holder.guru_tugas.setText("Guru : " + tugas.getGuru_tugas());
        holder.youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tugas.getYoutube_tugas().toString()));
                view.getContext().startActivity(webIntent);
            }
        });

        holder.file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tugas.getFile_tugas().toString()));
                view.getContext().startActivity(webIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judul_tugas, mapel_tugas, guru_tugas;
        public LinearLayout youtube, file;

        public ViewHolder(View itemView) {
            super(itemView);

            judul_tugas = itemView.findViewById(R.id.judul_elearning);
            mapel_tugas = itemView.findViewById(R.id.mapel_elearning);
            guru_tugas = itemView.findViewById(R.id.guru_elearning);
            youtube = itemView.findViewById(R.id.youtube);
            file = itemView.findViewById(R.id.file);
        }
    }
}
