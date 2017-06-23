package com.example.andreiiorga.electronicmenu.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.example.andreiiorga.electronicmenu.activities.OrderManager;
import com.example.andreiiorga.electronicmenu.asyncTasks.CategoriesService;
import com.example.andreiiorga.electronicmenu.listeners.OnScrollObserver;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.example.andreiiorga.electronicmenu.models.Product;

import java.util.List;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class CategoryFragment extends Fragment {

    View view;
    private ListView categoryList;
    private CategoryListViewAdapter categoryListViewAdapter;
    private OrderManager orderManager;


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

        CategoriesService categoriesService = new CategoriesService();
        categoriesService.setFragment(this);
        categoriesService.execute();


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
        for (Category category: categoryList){
                categoryListViewAdapter.add(category);
        }
        categoryListViewAdapter.notifyDataSetChanged();
    }



    public void nextPage(int listIndex) {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        Bundle args = new Bundle();
        Category category =  (Category) categoryList.getItemAtPosition(listIndex);
        args.putString("id", category.getId() + "");

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(args);

        transaction.replace(R.id.root_frame, productFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
