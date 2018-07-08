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

public class NatParksFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_item_attr, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.attrListView);
        NatParkAdapter natParkAdapter = new NatParkAdapter(this.getActivity(), getNatParkList());
        listView.setAdapter(natParkAdapter);

        return rootView;

    }

    ArrayList<NatPark> getNatParkList() {

        ArrayList<NatPark> listAttr = new ArrayList<NatPark>();
        listAttr.add(new NatPark(getString(R.string.natParkOne), (getString(R.string.natParkOneDesc)), (getString(R.string.natParkOneAddr)))
        );
        listAttr.add(new NatPark(getString(R.string.natParkTwo), (getString(R.string.natParkTwoDesc)), (getString(R.string.natParkTwoAddr)))
        );
        listAttr.add(new NatPark(getString(R.string.natParkThree), (getString(R.string.natParkThreeDesc)), (getString(R.string
            .natParkThreeAddr)))
        );
        return listAttr;
    }
}
