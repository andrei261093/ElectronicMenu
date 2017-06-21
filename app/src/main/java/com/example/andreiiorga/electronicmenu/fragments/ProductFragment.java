package com.example.andreiiorga.electronicmenu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.Adapters.CategoryListViewAdapter;
import com.example.andreiiorga.electronicmenu.Adapters.ProductListViewAdapter;
import com.example.andreiiorga.electronicmenu.R;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class ProductFragment extends Fragment{
    View view;
    ListView productList;
    ProductListViewAdapter productListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        productList = (ListView) view.findViewById(R.id.category_list);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_LONG);
                nextPage();
            }
        });
        productList.setItemsCanFocus(true);
        showList();

        return view;
    }

    private void showList() {
    }

    public void nextPage(){

    }
}
