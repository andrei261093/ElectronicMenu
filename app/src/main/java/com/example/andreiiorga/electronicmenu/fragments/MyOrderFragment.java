package com.example.andreiiorga.electronicmenu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.Adapters.MyOrderListAdapter;
import com.example.andreiiorga.electronicmenu.Adapters.ProductListViewAdapter;
import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.activities.MenuTabbed;
import com.example.andreiiorga.electronicmenu.models.Product;

import java.util.List;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class MyOrderFragment extends Fragment {
    private ListView myOrderList;
    private MyOrderListAdapter myOrderListAdapter;
    private TextView price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_order, container, false);
        myOrderList = (ListView) rootView.findViewById(R.id.my_order_list);
        price = (TextView) rootView.findViewById(R.id.total_price);
        myOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_LONG);
                nextPage();
            }
        });
        myOrderList.setItemsCanFocus(true);
        myOrderListAdapter = new MyOrderListAdapter(getActivity(), R.layout.row_my_order);
        myOrderListAdapter.setMyOrderFragment(this);
        myOrderList.setAdapter(myOrderListAdapter);

        Button sendOrder = (Button) rootView.findViewById(R.id.send_order);

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < myOrderListAdapter.getCount(); i++) {
                    myOrderListAdapter.getButtonsStates()[i] = true;
                }
                myOrderListAdapter.notifyDataSetChanged();
            }

        });

        return rootView;
    }

    private void nextPage() {
    }

    public void addProduct(Product product) {
        myOrderListAdapter.add(product);
        updatePrice();
    }

    private void updatePrice() {
        List<Product> products = ApplicationController.instance.getMyOrderList();
        int total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        price.setText(total + " lei");
    }

    public void removeProduct(Product product) {
        myOrderListAdapter.remove(product);
        myOrderListAdapter.notifyDataSetChanged();
        ApplicationController.instance.getMyOrderList().remove(product);
        updatePrice();

    }


}
