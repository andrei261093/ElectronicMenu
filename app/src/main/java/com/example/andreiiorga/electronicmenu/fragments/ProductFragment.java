package com.example.andreiiorga.electronicmenu.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.Adapters.CategoryListViewAdapter;
import com.example.andreiiorga.electronicmenu.Adapters.ProductListViewAdapter;
import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.activities.MenuTabbed;
import com.example.andreiiorga.electronicmenu.activities.OrderManager;
import com.example.andreiiorga.electronicmenu.activities.ProductView;
import com.example.andreiiorga.electronicmenu.listeners.OnScrollObserver;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.example.andreiiorga.electronicmenu.models.Product;

import java.util.List;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class ProductFragment extends Fragment {
    View view;
    ListView productList;
    ProductListViewAdapter productListViewAdapter;

    private OrderManager orderManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_products, container, false);

        productList = (ListView) view.findViewById(R.id.product_list);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nextPage(i);
            }
        });
        productList.setItemsCanFocus(true);

        productList.setOnScrollListener(new OnScrollObserver() {
            @Override
            public void onScrollUp() {
                orderManager.setFloatButtonVisibility(true);
            }

            @Override
            public void onScrollDown() {
                orderManager.setFloatButtonVisibility(false);
            }
        });

        productListViewAdapter = new ProductListViewAdapter(getActivity(), R.layout.row_products);
        productList.setAdapter(productListViewAdapter);

        int categoryID = Integer.parseInt(getArguments().getString("id"));

        setProductList(ApplicationController.instance.getProductList(), categoryID);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderManager) {
            orderManager = (OrderManager) context;
        }
    }

    public void setProductList(List<Product> productList, int categoryID) {
        productListViewAdapter.clear();
        productListViewAdapter.addAll(productList);
        for (Product product: productList){
            if(product.getCategoryId() == categoryID)
            productListViewAdapter.add(product);
        }
        productListViewAdapter.notifyDataSetChanged();
    }

    public void nextPage(int productPosition) {
        Product product = (Product) productList.getItemAtPosition(productPosition);

        Intent productViewActivity = new Intent(getContext(), ProductView.class);
        productViewActivity.putExtra("product", product);
        startActivityForResult(productViewActivity, 1);
    }
}
