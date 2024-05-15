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


public class AddProductActivity extends AppCompatActivity {

    private EditText productIdEditText;
    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText imageLinkEditText;
    private EditText productDetailEditText;
    private Button saveButton;
    private Button backToHomeButton;

    private Retrofit retrofit;
    private ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_layout);

        productIdEditText = findViewById(R.id.product_id_edittext);
        productNameEditText = findViewById(R.id.product_name_edittext);
        productPriceEditText = findViewById(R.id.product_price_edittext);
        imageLinkEditText = findViewById(R.id.image_link_edittext);
        productDetailEditText = findViewById(R.id.product_detail_edittext);
        saveButton = findViewById(R.id.save_button);
        backToHomeButton = findViewById(R.id.back_to_home_button);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/HelloWebApp/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addProduct() {
        String productId = productIdEditText.getText().toString().trim();
        String productName = productNameEditText.getText().toString().trim();
        String productPriceString = productPriceEditText.getText().toString().trim();
        String imageLink = imageLinkEditText.getText().toString().trim();
        String productDetail = productDetailEditText.getText().toString().trim();

        if (productId.isEmpty() || productName.isEmpty() || productPriceString.isEmpty() || imageLink.isEmpty() || productDetail.isEmpty()) {
            Toast.makeText(AddProductActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(productId);
        double productPrice = Double.parseDouble(productPriceString);

        Product newProduct = new Product(id, productName, productPrice, imageLink, productDetail);

        Call<Void> call = productService.addProduct(newProduct);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("API Error", "Error message: " + response.errorBody().string()); // Log the error message from the server
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage()); // Log the error message
            }
        });
    }
}