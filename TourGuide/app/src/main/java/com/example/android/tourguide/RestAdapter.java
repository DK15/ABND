package com.example.android.tourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lhu513 on 4/29/18.
 */

public class RestAdapter extends BaseAdapter {

    Context context;

    ArrayList<Food> rest;

    LayoutInflater layoutInflater;

    public RestAdapter(Context c, ArrayList<Food> rest) {
        this.context = c;
        this.rest = rest;
    }


    @Override
    public int getCount() {
        return rest.size();
    }

    @Override
    public Object getItem(int i) {
        return rest.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater.from(context.getApplicationContext()));
        }
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_view, viewGroup, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.name);
        String name = rest.get(i).getName();
        textView.setText(name);

        TextView textView1 = (TextView) view.findViewById(R.id.desc);
        String desc = rest.get(i).getDesc();
        textView1.setText(desc);

        TextView textView2 = (TextView) view.findViewById(R.id.address);
        String addr = rest.get(i).getAddr();
        textView2.setText(addr);

        return view;
    }
}
