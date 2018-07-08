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

public class FoodFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_item_attr, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.attrListView);
        RestAdapter restAdapter = new RestAdapter(this.getActivity(), getRestaurantList());
        listView.setAdapter(restAdapter);

        return rootView;
    }

    ArrayList<Food> getRestaurantList() {

        ArrayList<Food> listAttr = new ArrayList<Food>();
        listAttr.add(new Food(getString(R.string.restOne), (getString(R.string.restOneDesc)), (getString(R.string.restOneAddr))));
        listAttr.add(new Food(getString(R.string.restTwo), (getString(R.string.restTwoDesc)), (getString(R.string.restTwoAddr))));
        listAttr.add(new Food(getString(R.string.restThree), (getString(R.string.restThreeDesc)), (getString(R.string.restThreeAddr)
        )));
        listAttr.add(new Food(getString(R.string.restFour), (getString(R.string.restFourDesc)), (getString(R.string.restFourAddr))));
        return listAttr;
    }

}
