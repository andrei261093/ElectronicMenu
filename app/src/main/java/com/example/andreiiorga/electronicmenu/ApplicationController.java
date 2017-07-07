package com.example.andreiiorga.electronicmenu;

import android.app.Application;
import android.icu.util.ULocale;
import android.widget.ListView;

import com.example.andreiiorga.electronicmenu.asyncTasks.CategoriesService;
import com.example.andreiiorga.electronicmenu.asyncTasks.ProductService;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.example.andreiiorga.electronicmenu.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class ApplicationController extends Application {
    List<Category> categoryList;
    List<Product> productList;
    List<Product> myOrderList;
    List<Product> chunkOrderList;

    public static ApplicationController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        categoryList = new ArrayList<>();
        productList = new ArrayList<>();
        myOrderList = new ArrayList<>();
        chunkOrderList = new ArrayList<>();



        seed();
    }

    public void add(Product product) {
        myOrderList.add(product);
    }

    private void seed() {
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getMyOrderList() {
        return myOrderList;
    }

    public void setMyOrderList(List<Product> myOrderList) {
        this.myOrderList = myOrderList;
    }

    public void addCategory(Category category) {
        categoryList.add(category);
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> getChunkOrderList() {
        return chunkOrderList;
    }

    public void setChunkOrderList(List<Product> chunkOrderList) {
        this.chunkOrderList = chunkOrderList;
    }
}
