package com.example.testandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testandroid.Entities.Cart;
import com.example.testandroid.Entities.Product;
import com.example.testandroid.R;
import com.squareup.picasso.Picasso;
import com.nex3z.notificationbadge.NotificationBadge;

public class DetailActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private TextView productDetailTextView;
    private Button addToCartButton;
    private Product product;
    private NotificationBadge badge; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize the NotificationBadge
        badge = findViewById(R.id.menu_SL);

        updateCartBadge();


        productImageView = findViewById(R.id.product_image);
        productNameTextView = findViewById(R.id.product_name);
        productPriceTextView = findViewById(R.id.product_price);
        productDetailTextView = findViewById(R.id.detail_product);
        addToCartButton = findViewById(R.id.addtocart);

        // Get the product data from an Intent
        product = (Product) getIntent().getSerializableExtra("product");

        // Update the views with the product data
        Picasso.get().load(product.getImage()).into(productImageView);
        productNameTextView.setText(product.getName());
        productPriceTextView.setText(String.valueOf(product.getPrice()));
        productDetailTextView.setText(product.getDetail());

        // Set up click listener for add to cart button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the Cart instance from the Application class
                Cart cart = ((MyApplication) getApplication()).getCart();

                // Add the current product to the cart
                cart.addProduct(product);

                // Show a message to the user
                Toast.makeText(DetailActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                // Update the cart badge number
                updateCartBadge();
            }
        });

        ImageView cartImageView = findViewById(R.id.cart);
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateCartBadge() {
        int totalQuantity = 0;
        for (Product product : ((MyApplication) getApplication()).getCart().getProducts()) {
            totalQuantity += product.getQuantity();
        }
        badge.setNumber(totalQuantity);
    }
}