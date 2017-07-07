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
import com.example.andreiiorga.electronicmenu.StaticElements.StaticStrings;
import com.example.andreiiorga.electronicmenu.activities.MenuTabbed;
import com.example.andreiiorga.electronicmenu.activities.OrderManager;
import com.example.andreiiorga.electronicmenu.activities.ProductView;
import com.example.andreiiorga.electronicmenu.asyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.electronicmenu.asyncTasks.ProductService;
import com.example.andreiiorga.electronicmenu.listeners.OnScrollObserver;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.example.andreiiorga.electronicmenu.models.Product;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

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
        productList.setEmptyView(view.findViewById(R.id.empty_list_image_view));

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

        getProducts(categoryID);

   //     setProductList(ApplicationController.instance.getProductList(), categoryID);

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

    private void getProducts(final int categoryId){
        AsynchronousHttpClient.get(StaticStrings.GET_PRODUCTS_ROUTE, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                ApplicationController.instance.getCategoryList().clear();
                try {
                    if(timeline != null) {
                        JSONArray resultsArray = timeline;
                        ApplicationController.instance.getProductList().clear();
                        int a = 0;

                        for (int i = 0; i < resultsArray.length(); i++) {
                            Product product = new Product(Integer.parseInt(resultsArray.getJSONObject(i).getString("id")), resultsArray.getJSONObject(i).getString("name"), resultsArray.getJSONObject(i).getString("imageUrl"));
                            product.setShortDescription(resultsArray.getJSONObject(i).getString("shortDescription"));
                            product.setLongDescription(resultsArray.getJSONObject(i).getString("longDescription"));
                            product.setCategoryId(Integer.parseInt(resultsArray.getJSONObject(i).getString("category")));
                            product.setPrice(Integer.parseInt(resultsArray.getJSONObject(i).getString("price")));
                            product.setWeight(Integer.parseInt(resultsArray.getJSONObject(i).getString("weight")));
                            product.setNeedsPreparation(resultsArray.getJSONObject(i).getBoolean("needsPreparation"));
                            ApplicationController.instance.addProduct(product);
                        }
                        setProductList(ApplicationController.instance.getProductList(), categoryId);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getContext(), "Nu s-au putut obtine categoriile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
