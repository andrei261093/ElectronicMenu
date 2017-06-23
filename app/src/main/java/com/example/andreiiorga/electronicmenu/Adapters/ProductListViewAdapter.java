package com.example.andreiiorga.electronicmenu.Adapters;

import android.app.Application;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.activities.OrderManager;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.example.andreiiorga.electronicmenu.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class ProductListViewAdapter  extends ArrayAdapter<Product> {
    List<Product> list = new ArrayList<>();
    private OrderManager orderManager;

    public ProductListViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        if(getContext() instanceof OrderManager) {
            orderManager = (OrderManager) context;
        }
    }

    public void add(Product product) {
        super.add(product);
        list.add(product);
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
            row = layoutInflater.inflate(R.layout.row_products, parent, false);
            productHolder = new ProductHolder();

            productHolder.tx_name = (TextView) row.findViewById(R.id.product_name);
            productHolder.img = (ImageView) row.findViewById(R.id.product_image);
            productHolder.tx_price= (TextView) row.findViewById(R.id.product_price_text);
            productHolder.addProductBtn = (Button) row.findViewById(R.id.add_product_btn);
            productHolder.tx_shortDescription = (TextView) row.findViewById(R.id.short_description);

            row.setTag(productHolder);

        } else {
            productHolder = (ProductHolder) row.getTag();
        }

        final Product product = (Product) this.getItem(position);
        productHolder.tx_name.setText(product.getName());
        productHolder.tx_price.setText(product.getPrice() + " lei");
        productHolder.tx_shortDescription.setText(product.getShortDescription());
        Picasso.with(getContext())
                .load(product.getImageUrl())
                .into(productHolder.img);

        productHolder.addProductBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), product.getName() + " a fost adaugat", Toast.LENGTH_LONG).show();

                Product clone = new Product(product);
                orderManager.addProductToOrder(clone);
            }
        });


        return row;
    }

    static class ProductHolder {
        TextView tx_name;
        TextView tx_price;
        TextView tx_shortDescription;
        ImageView img;
        Button addProductBtn;

    }
}
