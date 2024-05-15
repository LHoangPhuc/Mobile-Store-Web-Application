package com.example.testandroid.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testandroid.Entities.Product;
import com.example.testandroid.Service.ProductService;
import com.example.testandroid.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProductActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText imageLinkEditText;
    private EditText productDetailEditText; // New EditText for product detail
    private Button saveButton;
    private Button backToHomeButton;

    private Retrofit retrofit;
    private ProductService productService;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_layout);

        productNameEditText = findViewById(R.id.product_name_edittext);
        productPriceEditText = findViewById(R.id.product_price_edittext);
        imageLinkEditText = findViewById(R.id.image_link_edittext);
        productDetailEditText = findViewById(R.id.product_detail_edittext); // Initialize EditText for product detail
        saveButton = findViewById(R.id.save_button);
        backToHomeButton = findViewById(R.id.back_to_home_button);

        product = (Product) getIntent().getSerializableExtra("product");

        productNameEditText.setText(product.getName());
        productPriceEditText.setText(String.valueOf(product.getPrice()));
        imageLinkEditText.setText(product.getImage());
        productDetailEditText.setText(product.getDetail()); // Set product detail EditText

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/HelloWebApp/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateProduct() {
        String productName = productNameEditText.getText().toString().trim();
        String productPriceString = productPriceEditText.getText().toString().trim();
        String imageLink = imageLinkEditText.getText().toString().trim();
        String productDetail = productDetailEditText.getText().toString().trim(); // Get product detail

        if (productName.isEmpty() || productPriceString.isEmpty() || imageLink.isEmpty() || productDetail.isEmpty()) {
            Toast.makeText(EditProductActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double productPrice = Double.parseDouble(productPriceString);

        product.setName(productName);
        product.setPrice(productPrice);
        product.setImage(imageLink);
        product.setDetail(productDetail); // Set new product detail

        Call<Void> call = productService.updateProduct(product.getId(), product);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditProductActivity.this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("API Error", "Error message: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}