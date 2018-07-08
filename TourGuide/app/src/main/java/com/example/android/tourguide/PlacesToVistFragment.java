package com.example.android.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lhu513 on 4/28/18.
 */

public class PlacesToVistFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_item_attr, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.attrListView);
        AttractionsAdapter attractionsAdapter = new AttractionsAdapter(this.getActivity(), getAttractionList());
        listView.setAdapter(attractionsAdapter);

        return rootView;
    }

    ArrayList<Attractions> getAttractionList() {

        ArrayList<Attractions> listAttr = new ArrayList<Attractions>();
        listAttr
            .add(new Attractions(getString(R.string.attrOne), (getString(R.string.attrOneDesc)), ((R.drawable.bkm)), (getString
                (R.string.attrOneAddr))));
        listAttr.add(new Attractions(getString(R.string.attrTwo), (getString(R.string.attrTwoDesc)), ((R.drawable.ellora)), getString(R
            .string.attrTwoAddr)));
        listAttr.add(new Attractions(getString(R.string.attrThree), (getString(R.string.attrThreeDesc)), ((R.drawable.mhml)), ""));
        listAttr.add(new Attractions(getString(R.string.attrFour), (getString(R.string.attrFourDesc)), (R.drawable.bm), getString(R.string
            .attrFourAddr)));
        return listAttr;
    }
}
