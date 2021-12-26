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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.islamkhsh.CardSliderViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomesiswaFragment extends Fragment {

    CardSliderViewPager cardSliderViewPager;
    private List<SliderData> sliderData;
    private SliderAdapter sliderAdapter;
    private JSONArray resultprofilesiswa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_homesiswa, container, false);

        cardSliderViewPager = root.findViewById(R.id.viewPager);
        sliderData = new ArrayList<>();
        sliderData.add(new SliderData("https://i.ibb.co/WgCFQGn/Mask-Group-9.png"));
        sliderData.add(new SliderData("https://i.ibb.co/WgCFQGn/Mask-Group-9.png"));
        sliderData.add(new SliderData("https://i.ibb.co/WgCFQGn/Mask-Group-9.png"));
        sliderAdapter = new SliderAdapter((ArrayList<SliderData>) sliderData);
        cardSliderViewPager.setAdapter(sliderAdapter);
        cardSliderViewPager.setSmallScaleFactor(0.9f);
        cardSliderViewPager.setSmallAlphaFactor(0.5f);
        cardSliderViewPager.setAutoSlideTime(5);

        SessionManager sessionManager = new SessionManager(getContext());
        TextView nama = root.findViewById(R.id.nama);
        nama.setText("Hai, " + sessionManager.getUserDetail().get("NAMALENGKAP"));
        TextView previllage = root.findViewById(R.id.previllage);
        previllage.setText(sessionManager.getUserDetail().get("PREVILLAGE"));
        CircleImageView circleImageView = root.findViewById(R.id.profile_image);
        if(sessionManager.getUserDetail().get("FOTO").equals(""))
        {
            circleImageView.setImageResource(R.drawable.logohijau);
        }else {
            Glide.with(getContext()).load(sessionManager.getUserDetail().get("FOTO")).into(circleImageView);
        }

        RelativeLayout btnelearning = root.findViewById(R.id.btnelearning);
        btnelearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/profilesiswa.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    resultprofilesiswa = j.getJSONArray("result");
                                    getElearning(resultprofilesiswa);

                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        SessionManager sessionManager = new SessionManager(getActivity());
                        parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        RelativeLayout btnmapel = root.findViewById(R.id.btn_mapel);
        btnmapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/profilesiswa.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    resultprofilesiswa = j.getJSONArray("result");
                                    getMapel(resultprofilesiswa);

                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        SessionManager sessionManager = new SessionManager(getActivity());
                        parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        RelativeLayout btntugas = root.findViewById(R.id.btntugas);
        btntugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/profilesiswa.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    resultprofilesiswa = j.getJSONArray("result");
                                    getTugas(resultprofilesiswa);

                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        SessionManager sessionManager = new SessionManager(getActivity());
                        parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        RelativeLayout btnnilai = root.findViewById(R.id.btn_nilai);
        btnnilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Segera Hadir!", Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout btn_sekolah = root.findViewById(R.id.btn_sekolah);
        btn_sekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/profilesiswa.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    resultprofilesiswa = j.getJSONArray("result");
                                    getProfilesiswa(resultprofilesiswa);

                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        SessionManager sessionManager = new SessionManager(getActivity());
                        parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        return root;
    }

    private void getTugas(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                Intent in = new Intent(getActivity(), TugasActivity.class);
                in.putExtra("previllage", "Siswa");
                in.putExtra("sekolah", json.getString("sekolah"));
                startActivity(in);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getElearning(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                Intent in = new Intent(getActivity(), ElearningActivity.class);
                in.putExtra("previllage", "Siswa");
                in.putExtra("sekolah", json.getString("sekolah"));
                startActivity(in);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getProfilesiswa(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                Intent in = new Intent(getActivity(), SekolahActivity.class);
                in.putExtra("previllage", "Siswa");
                in.putExtra("sekolah", json.getString("sekolah"));
                startActivity(in);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getMapel(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                Intent in = new Intent(getActivity(), MapelActivity.class);
                in.putExtra("previllage", "Siswa");
                in.putExtra("sekolah", json.getString("sekolah"));
                startActivity(in);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}