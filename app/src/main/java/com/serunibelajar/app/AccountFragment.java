package com.serunibelajar.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        SessionManager sessionManager = new SessionManager(getContext());
        TextView nama = root.findViewById(R.id.nama);
        nama.setText(sessionManager.getUserDetail().get("NAMALENGKAP"));
        TextView previllage = root.findViewById(R.id.previllage);
        previllage.setText(sessionManager.getUserDetail().get("PREVILLAGE"));
        CircleImageView circleImageView = root.findViewById(R.id.profile_image);
        if(sessionManager.getUserDetail().get("FOTO").equals(""))
        {
            circleImageView.setImageResource(R.drawable.logoputih);
        }else {
            Glide.with(getContext()).load(sessionManager.getUserDetail().get("FOTO")).into(circleImageView);
        }

        RelativeLayout layoutprofile = root.findViewById(R.id.layoutprofile);
        layoutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.getUserDetail().get("PREVILLAGE").equals("Guru")){
                    startActivity(new Intent(getActivity(), ProfileguruActivity.class));
                }
                else if(sessionManager.getUserDetail().get("PREVILLAGE").equals("Orang Tua")){
                    startActivity(new Intent(getActivity(), ProfileorangtuaActivity.class));
                }
                else if(sessionManager.getUserDetail().get("PREVILLAGE").equals("Siswa")){
                    startActivity(new Intent(getActivity(), ProfilesiswaActivity.class));
                }
            }
        });

        RelativeLayout layoutkebijakan = root.findViewById(R.id.layoutkebijakan);
        layoutkebijakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Segera Hadir!", Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout layoutsyarat = root.findViewById(R.id.layoutsyarat);
        layoutsyarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Segera Hadir!", Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout layoutlogot = root.findViewById(R.id.layoutlogout);
        layoutlogot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return root;
    }
}