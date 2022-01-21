package com.serunibelajar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context ctx;
    private ArrayList<String> text;
    private ArrayList<String> id;

    public SpinnerAdapter(Context context, ArrayList<String> text,
                          ArrayList<String> id) {
        super(context,  R.layout.text_spinner, id);
        this.ctx = context;
        this.text = text;
        this.id = id;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.text_spinner, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.textView);
        textView.setText(text.get(position));

        return row;
    }
}
