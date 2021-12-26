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

public class ElearningAdapter extends RecyclerView.Adapter<ElearningAdapter.ViewHolder>{

    private Context context;
    private List<Elearning> list;

    public ElearningAdapter(Context context, List<Elearning> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ElearningAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.elearning_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Elearning elearning = list.get(position);

        holder.judul_elearning.setText(elearning.getJudul_elearning());
        holder.mapel_elearning.setText(elearning.getMapel_elearning());
        holder.guru_elearning.setText("Guru : " + elearning.getGuru_elearning());
        holder.youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(elearning.getYoutube_elearning().toString()));
                view.getContext().startActivity(webIntent);
            }
        });

        holder.file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(elearning.getFile_elearning().toString()));
                view.getContext().startActivity(webIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judul_elearning, mapel_elearning, guru_elearning;
        public LinearLayout youtube, file;

        public ViewHolder(View itemView) {
            super(itemView);

            judul_elearning = itemView.findViewById(R.id.judul_elearning);
            mapel_elearning = itemView.findViewById(R.id.mapel_elearning);
            guru_elearning = itemView.findViewById(R.id.guru_elearning);
            youtube = itemView.findViewById(R.id.youtube);
            file = itemView.findViewById(R.id.file);
        }
    }
}
