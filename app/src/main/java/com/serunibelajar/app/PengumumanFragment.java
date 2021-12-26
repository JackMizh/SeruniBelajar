package com.serunibelajar.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PengumumanFragment extends Fragment {

    private List<Pengumuman> pengumumanList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pengumuman, container, false);

        mList = root.findViewById(R.id.rvanak);
        pengumumanList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PengumumanAdapter(getContext(), pengumumanList);
        getPengumuman();

        return root;
    }

    private void getPengumuman() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/pengumuman.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Pengumuman pengumuman = new Pengumuman();
                        pengumuman.setId_pengumuman(ob.getString("id_pengumuman"));
                        pengumuman.setTitle_pengumuman(ob.getString("title_pengumuman"));
                        pengumuman.setSubtitle_pengumuman(ob.getString("subtitle_pengumuman"));
                        pengumuman.setFoto_pengumuman(ob.getString("foto_pengumuman"));

                        pengumumanList.add(pengumuman);
                    }

                    mList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parms = new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(getContext());
                parms.put("previllage", sessionManager.getUserDetail().get("PREVILLAGE"));
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}