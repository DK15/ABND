package com.example.android.tourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lhu513 on 4/28/18.
 */

public class AttractionsAdapter extends BaseAdapter {

    Context context;

    ArrayList<Attractions> attractions;

    LayoutInflater layoutInflater;

    public AttractionsAdapter(Context c, ArrayList<Attractions> attractions) {
        this.context = c;
        this.attractions = attractions;
    }


    @Override
    public int getCount() {
        return attractions.size();
    }

    @Override
    public Object getItem(int i) {
        return attractions.get(i);
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
        String name = attractions.get(i).getName();
        textView.setText(name);

        TextView textView1 = (TextView) view.findViewById(R.id.desc);
        String desc = attractions.get(i).getDesc();
        textView1.setText(desc);

        ImageView imageView = (ImageView) view.findViewById(R.id.list_item_icon);
        int image = attractions.get(i).getImage();
        imageView.setImageResource(image);

        TextView textView2 = (TextView) view.findViewById(R.id.address);
        String addr = attractions.get(i).getAddr();
        textView2.setText(addr);

        return view;
    }
}
