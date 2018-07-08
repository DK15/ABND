package com.example.android.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lhu513 on 5/1/18.
 */

public class LodgeFragment extends Fragment {

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
        listAttr.add(new NatPark(getString(R.string.stayOne), (getString(R.string.stayOneDesc)), (getString(R.string.restOneAddr)))
        );
        listAttr.add(new NatPark(getString(R.string.stayTwo), (getString(R.string.stayTwoDesc)), (getString(R.string.stayTwoAddr)))
        );
        listAttr.add(new NatPark(getString(R.string.stayThree), (getString(R.string.stayThreeDesc)), (getString(R.string
            .stayThreeAddr)))
        );
        return listAttr;
    }
}
