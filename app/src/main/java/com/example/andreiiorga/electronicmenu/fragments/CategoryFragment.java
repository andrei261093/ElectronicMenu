package com.example.andreiiorga.electronicmenu.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.Adapters.CategoryListViewAdapter;
import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.StaticElements.StaticStrings;
import com.example.andreiiorga.electronicmenu.activities.OrderManager;
import com.example.andreiiorga.electronicmenu.asyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.electronicmenu.asyncTasks.CategoriesService;
import com.example.andreiiorga.electronicmenu.listeners.OnScrollObserver;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.example.andreiiorga.electronicmenu.models.Product;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class CategoryFragment extends Fragment {

    View view;
    private ListView categoryList;
    private CategoryListViewAdapter categoryListViewAdapter;
    private OrderManager orderManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_list, container, false);

        categoryList = (ListView) view.findViewById(R.id.category_list);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_LONG);

                nextPage(i);
            }
        });
        categoryList.setItemsCanFocus(true);
        categoryList.setOnScrollListener(new OnScrollObserver() {
            @Override
            public void onScrollUp() {
                orderManager.setFloatButtonVisibility(true);
            }

            @Override
            public void onScrollDown() {
                orderManager.setFloatButtonVisibility(false);
            }
        });

        categoryListViewAdapter = new CategoryListViewAdapter(getActivity(), R.layout.row_category);
        categoryList.setAdapter(categoryListViewAdapter);

        final CategoriesService categoriesService = new CategoriesService();
        categoriesService.setFragment(this);
        categoriesService.execute();


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_categories);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsynchronousHttpClient.get(StaticStrings.GET_CATEGORIES_ROUTE, null, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        ApplicationController.instance.getCategoryList().clear();
                        try {
                            if(timeline != null) {
                                JSONArray resultsArray = timeline;

                                for(int i=0; i< resultsArray.length(); i++){
                                    Category category = new Category(Integer.parseInt(resultsArray.getJSONObject(i).getString("id")), resultsArray.getJSONObject(i).getString("name"), resultsArray.getJSONObject(i).getString("imageUrl"));
                                    ApplicationController.instance.addCategory(category);
                                }
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setCategoryList(ApplicationController.instance.getCategoryList());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getContext(), "Nu s-au putut obtine categoriile", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderManager) {
            orderManager = (OrderManager) context;
        }
    }

    public void setCategoryList(List<Category> categoryList) {
        categoryListViewAdapter.clear();
        for (Category category : categoryList) {
            categoryListViewAdapter.add(category);
        }
        categoryListViewAdapter.notifyDataSetChanged();
    }


    public void nextPage(int listIndex) {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_out_left, R.anim.slide_in_right);

        Bundle args = new Bundle();
        Category category = (Category) categoryList.getItemAtPosition(listIndex);
        args.putString("id", category.getId() + "");

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(args);

        transaction.replace(R.id.root_frame, productFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
