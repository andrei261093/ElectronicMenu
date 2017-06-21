package com.example.andreiiorga.electronicmenu.fragments;

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
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.models.Category;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class CategoryFragment extends Fragment {

    View view;
    ListView categoryList;
    CategoryListViewAdapter categoryListViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_list, container, false);

        categoryList = (ListView) view.findViewById(R.id.category_list);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_LONG);
                nextPage();
            }
        });
        categoryList.setItemsCanFocus(true);
        showList();

        return view;
    }

    public void showList(){
        categoryListViewAdapter = new CategoryListViewAdapter(getActivity(), R.layout.row_category);
        categoryList.setAdapter(categoryListViewAdapter);

        Category fripturi = new Category(1, "Fripturi", "http://storage0.dms.mpinteractiv.ro/media/2/2/24986/10533179/14/miel-main.jpg?width=470");
        Category pizza = new Category(2, "Pizza", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqFcPFlg8v_QdOcVryz1xp2cmwaWkwbd7MYegAP1zSQHeCMHYa");
        Category ciorbe = new Category(3, "Supe", "http://ciorbesisupe.ro/wp-content/uploads/2016/03/Ciorba-de-perisoare.jpg");
        Category desert = new Category(4, "Desert", "https://www.retetecalamama.ro/wp-content/uploads/2012/02/papanasi+cu+dulceata+si+smantana.jpg");
        Category bauturi = new Category(5, "Racoritoare", "http://adevarul.ro/assets/adevarul.ro/MRImage/2015/10/28/5630bf98f5eaafab2c1bd096/646x404.jpg");
        Category vinuri = new Category(6, "Vinuri", "http://marca-ro.ca/wp-content/uploads/2015/09/poza-vinuri.jpg");

        categoryListViewAdapter.add(fripturi);
        categoryListViewAdapter.add(pizza);
        categoryListViewAdapter.add(ciorbe);
        categoryListViewAdapter.add(desert);
        categoryListViewAdapter.add(bauturi);
        categoryListViewAdapter.add(vinuri);

    }

    public void nextPage(){
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.root_frame, new ProductFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
