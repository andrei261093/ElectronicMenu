package com.example.andreiiorga.electronicmenu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andreiiorga.electronicmenu.R;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class MyOrderFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_order, container, false);
        return rootView;
    }
}
