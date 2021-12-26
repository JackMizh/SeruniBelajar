package com.serunibelajar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PengumumanAdapter extends RecyclerView.Adapter<PengumumanAdapter.ViewHolder>{
    private Context context;
    private List<Pengumuman> list;

    public PengumumanAdapter(Context context, List<Pengumuman> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PengumumanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pengumuman_layout, parent, false);
        return new PengumumanAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PengumumanAdapter.ViewHolder holder, int position) {
        Pengumuman pengumuman = list.get(position);

        holder.title.setText(pengumuman.getTitle_pengumuman());
        holder.subtitle.setText(String.valueOf(pengumuman.getSubtitle_pengumuman()));
        if(pengumuman.getFoto_pengumuman().equals(""))
        {
            holder.foto.setImageResource(R.drawable.ic_notification);
        }else {
            Glide.with(holder.itemView.getContext()).load(pengumuman.getFoto_pengumuman()).into(holder.foto);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            foto = itemView.findViewById(R.id.foto);
        }
    }
}
