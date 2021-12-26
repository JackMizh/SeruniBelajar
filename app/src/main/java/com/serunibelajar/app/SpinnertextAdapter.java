package com.serunibelajar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnertextAdapter extends BaseAdapter {
    Context context;
    String[] texts;
    LayoutInflater inflter;

    public SpinnertextAdapter(Context applicationContext, String[] texts) {
        this.context = applicationContext;
        this.texts = texts;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return texts.length;
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
        view = inflter.inflate(R.layout.text_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(texts[i]);
        return view;
    }
}
