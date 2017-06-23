package com.example.andreiiorga.electronicmenu.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.Adapters.MyOrderListAdapter;
import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.activities.IntroActivity;
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
                nextPage();
            }
        });
        myOrderList.setItemsCanFocus(true);
        myOrderListAdapter = new MyOrderListAdapter(getActivity(), R.layout.row_my_order);
        myOrderListAdapter.setMyOrderFragment(this);
        myOrderList.setAdapter(myOrderListAdapter);

        Button sendOrder = (Button) rootView.findViewById(R.id.send_order);
        Button checkButton = (Button) rootView.findViewById(R.id.check_button);

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Trimite Comanda")
                        .setMessage("Esti sigur ca vrei sa trimiti comanda?")
                        .setPositiveButton("Trimite", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < myOrderListAdapter.getCount(); i++) {
                                    myOrderListAdapter.getButtonsStates()[i] = true;
                                }
                                myOrderListAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Comanda a fost trimisa la bucatarie", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Nota de plata")
                        .setMessage("Esti sigur ca vrei sa inchizi nota? \n Total pret: " + price.getText())
                        .setPositiveButton("Inchide Nota", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                myOrderListAdapter.clearData();
                                myOrderListAdapter.notifyDataSetChanged();
                                ApplicationController.instance.getMyOrderList().clear();
                                updatePrice();
                                Intent intent = new Intent(getContext(), IntroActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
