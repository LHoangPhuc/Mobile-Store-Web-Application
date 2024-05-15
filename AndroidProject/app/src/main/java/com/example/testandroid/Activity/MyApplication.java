package com.example.testandroid.Activity;

import android.app.Application;

import com.example.testandroid.Entities.Cart;

public class MyApplication extends Application {
    private Cart mCart;

    @Override
    public void onCreate() {
        super.onCreate();
        mCart = new Cart();
    }

    public Cart getCart() {
        return mCart;
    }
}