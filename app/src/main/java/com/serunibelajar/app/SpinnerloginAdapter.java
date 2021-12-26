package com.serunibelajar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SpinnerloginAdapter extends BaseAdapter {

    Context context;
    String[] previllages;
    LayoutInflater inflter;

    public SpinnerloginAdapter(Context applicationContext, String[] previllages) {
        this.context = applicationContext;
        this.previllages = previllages;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return previllages.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.login_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(previllages[i]);
        return view;
    }
}
