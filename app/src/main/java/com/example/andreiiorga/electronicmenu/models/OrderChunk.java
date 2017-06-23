package com.example.andreiiorga.electronicmenu.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreiiorga on 23/06/2017.
 */

public class OrderChunk {
    List<Product> productList = new ArrayList<>();

    public OrderChunk(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
