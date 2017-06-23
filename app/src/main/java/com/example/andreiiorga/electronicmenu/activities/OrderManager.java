package com.example.andreiiorga.electronicmenu.activities;

import com.example.andreiiorga.electronicmenu.models.Product;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public interface OrderManager {

    void addProductToOrder(Product product);

    void setFloatButtonVisibility(Boolean state);
}
