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
import com.example.andreiiorga.electronicmenu.StaticElements.StaticStrings;
import com.example.andreiiorga.electronicmenu.activities.IntroActivity;
import com.example.andreiiorga.electronicmenu.asyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.electronicmenu.models.Product;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

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

        myOrderList.setEmptyView(rootView.findViewById(R.id.empty_list_image_view));

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

        final Button sendOrder = (Button) rootView.findViewById(R.id.send_order);
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

                                ApplicationController.instance.getMyOrderList().addAll(ApplicationController.instance.getChunkOrderList());
                                sendFullOrder(ApplicationController.instance.getChunkOrderList(), StaticStrings.NEW_ORDER_ROUTE, StaticStrings.ORDER_ACTION_ORDER);
                                ApplicationController.instance.getChunkOrderList().clear();

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
                                sendFullOrder(ApplicationController.instance.getMyOrderList(), StaticStrings.CHECK_MY_ORDER_ROUTE, StaticStrings.ORDER_ACTION_CHECK);
                                myOrderListAdapter.clearData();
                                myOrderListAdapter.notifyDataSetChanged();
                                ApplicationController.instance.getMyOrderList().clear();
                                updatePrice();
                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);

                                Intent intentIntro = new Intent(getContext(), IntroActivity.class);
                                startActivity(intentIntro);
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

    private void sendFullOrder(List<Product> myOrder, String url, String action) {
        JSONObject order = new JSONObject();
        Gson gson = new Gson();
        String json = gson.toJson(myOrder);

        try {
            JSONArray productsJson = new JSONArray(json);
            order.put("orderAction", action);
            order.put("tableNo", StaticStrings.TABLE_NO);
            order.put("products", productsJson);
            if(action.equals(StaticStrings.ORDER_ACTION_ORDER)){
                order.put("totalPrice", getChunkPrice());
            }else{
                order.put("totalPrice", getTotalPrice());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestParams params = new RequestParams();
        params.put("order", order);

        AsynchronousHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                String response = responseString;
                System.out.println(response);
            }


        });
    }

    private void nextPage() {
    }

    public void addProduct(Product product) {
        myOrderListAdapter.add(product);
        updatePrice();
        myOrderListAdapter.notifyDataSetChanged();
    }

    private void updatePrice() {
        List<Product> products = myOrderListAdapter.getList();
        int total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        price.setText(total + " lei");
    }

    public int getTotalPrice() {
        int total = 0;
        for (Product product :ApplicationController.instance.getMyOrderList()) {
            total += product.getPrice();
        }
        return total;
    }

    public int getChunkPrice() {
        int total = 0;
        for (Product product :ApplicationController.instance.getChunkOrderList()) {
            total += product.getPrice();
        }
        return total;
    }

    public void removeProduct(Product product) {
        myOrderListAdapter.remove(product);
        myOrderListAdapter.notifyDataSetChanged();
        ApplicationController.instance.getChunkOrderList().remove(product);
        updatePrice();

    }


}
