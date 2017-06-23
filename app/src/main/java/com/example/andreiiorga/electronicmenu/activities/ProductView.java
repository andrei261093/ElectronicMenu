package com.example.andreiiorga.electronicmenu.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.models.Product;
import com.squareup.picasso.Picasso;

public class ProductView extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        product = (Product) getIntent().getExtras().getParcelable("product");

        TextView priceLabel = (TextView) findViewById(R.id.product_price_text);
        priceLabel.setText(product.getPrice() + " lei");

        TextView longDescriotion = (TextView) findViewById(R.id.long_description);
        longDescriotion.setText(product.getLongDescription());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(product.getPrice() + " lei");
        actionBar.setTitle(product.getName());
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));





        ImageView image = (ImageView) findViewById(R.id.product_details_image);

        Picasso.with(this)
                .load(product.getImageUrl())
                .into(image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adaugat la comanda", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent();
                intent.putExtra("result", product);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }
}
