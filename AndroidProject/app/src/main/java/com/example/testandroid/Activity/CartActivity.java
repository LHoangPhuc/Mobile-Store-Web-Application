package com.example.testandroid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testandroid.Entities.Cart;
import com.example.testandroid.Adapter.CartAdapter;
import com.example.testandroid.Entities.Product;
import com.example.testandroid.R;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnRemoveButtonClickListener {

    private Cart mCart;
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private TextView totalCostTextView; // Declare TextView
    private Button btnmuahang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mCart = ((MyApplication) getApplication()).getCart(); // Get Cart from Application

        mRecyclerView = findViewById(R.id.cart_recycler_view);
        mAdapter = new CartAdapter(this, mCart.getProducts(), this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the TextView
        totalCostTextView = findViewById(R.id.txttongtien);
        btnmuahang = findViewById(R.id.btnmuahang);

        updateTotalCost();

        // Thiết lập OnClickListener cho nút
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalCost = mCart.getTotalCost();
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("DonHang_total", totalCost);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRemoveButtonClicked(Product product) {
        mCart.removeProduct(product);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
        updateTotalCost(); // Update total cost after removing a product
    }

    // Method to update total cost
    public void updateTotalCost() {
        double totalCost = mCart.getTotalCost();
        totalCostTextView.setText(String.valueOf(totalCost));
    }

}