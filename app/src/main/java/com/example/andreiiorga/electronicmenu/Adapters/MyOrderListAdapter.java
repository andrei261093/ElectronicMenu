package com.example.andreiiorga.electronicmenu.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.fragments.MyOrderFragment;
import com.example.andreiiorga.electronicmenu.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andreiiorga on 22/06/2017.
 */

public class MyOrderListAdapter extends ArrayAdapter {
    private List<Product> list = new ArrayList<>();
    private MyOrderFragment myOrderFragment;
    private Boolean[] buttonsStates = new Boolean[150];

    public MyOrderListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void add(Product product) {
        super.add(product);
        list.add(product);
        buttonsStates[list.size()-1] = false;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Product getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        row = convertView;
        final ProductHolder productHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_my_order_products, parent, false);
            productHolder = new MyOrderListAdapter.ProductHolder();


            productHolder.tx_name = (TextView) row.findViewById(R.id.product_name);
            productHolder.img = (ImageView) row.findViewById(R.id.product_image);
            productHolder.tx_price = (TextView) row.findViewById(R.id.product_price_text);
            productHolder.btn_eliminate = (Button) row.findViewById(R.id.remove_product_btn);
            productHolder.tx_shortDescription = (TextView) row.findViewById(R.id.short_description);

            row.setTag(productHolder);

        } else {
            productHolder = (MyOrderListAdapter.ProductHolder) row.getTag();
        }

        final Product product = (Product) this.getItem(position);
        productHolder.tx_name.setText(product.getName());
        productHolder.tx_price.setText("Pret: " + product.getPrice() + " lei");
        productHolder.tx_shortDescription.setText(product.getShortDescription());
        Picasso.with(getContext())
                .load(product.getImageUrl())
                .into(productHolder.img);

        productHolder.btn_eliminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOrderFragment.removeProduct(product);

            }
        });

        if(buttonsStates[position]){
            productHolder.btn_eliminate.setEnabled(false);
        }else{
            productHolder.btn_eliminate.setEnabled(true);
        }

        return row;
    }

    static class ProductHolder {
        TextView tx_name;
        ImageView img;
        TextView tx_price;
        Button btn_eliminate;
        TextView tx_shortDescription;
    }

    public void setMyOrderFragment(MyOrderFragment myOrderFragment) {
        this.myOrderFragment = myOrderFragment;
    }


    @Override
    public void remove(@Nullable Object object) {
        super.remove(object);
        list.remove(object);
    }

    public Boolean[] getButtonsStates() {
        return buttonsStates;
    }

    public void clearData(){
        list.clear();
    }

    public List<Product> getList() {
        return list;
    }
}
