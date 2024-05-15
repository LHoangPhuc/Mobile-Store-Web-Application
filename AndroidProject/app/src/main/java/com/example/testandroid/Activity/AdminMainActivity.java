package com.example.testandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testandroid.Adapter.AdminAdapter;
import com.example.testandroid.Entities.Cart;
import com.example.testandroid.Entities.Product;
import com.example.testandroid.Service.ProductService;
import com.example.testandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminMainActivity extends AppCompatActivity implements AdminAdapter.OnDeleteButtonClickListener, AdminAdapter.OnAddToCartButtonClickListener {

    private Retrofit retrofit;
    private ProductService productService;
    private ListView productListView;
    private AdminAdapter adapter;
    private Cart mCart;
    private static final int ADD_PRODUCT_REQUEST = 1;
    private static final int EDIT_PRODUCT_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);

        productListView = findViewById(R.id.product_list);
        mCart = ((MyApplication) getApplication()).getCart(); // Get Cart from Application

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/HelloWebApp/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        getProductList();

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AddProductActivity.class);
                startActivityForResult(intent, ADD_PRODUCT_REQUEST);
            }
        });

        Button editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product selectedProduct = adapter.getSelectedProduct();
                if (selectedProduct != null) {
                    Intent intent = new Intent(AdminMainActivity.this, EditProductActivity.class);
                    intent.putExtra("product", selectedProduct);
                    startActivityForResult(intent, EDIT_PRODUCT_REQUEST);
                } else {
                    Toast.makeText(AdminMainActivity.this, "Vui lòng chọn sản phẩm để sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView viewCartImageView = findViewById(R.id.cart);
        viewCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProductList() {
        Call<List<Product>> call = productService.getProductFromRestAPI();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    if (productList != null && !productList.isEmpty()) {
                        adapter = new AdminAdapter(AdminMainActivity.this, productList, AdminMainActivity.this, AdminMainActivity.this);
                        productListView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this, "Không thể tải danh sách sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteButtonClicked(int productId) {
        deleteProduct(productId);
    }

    private void deleteProduct(int productId) {
        Call<Void> call = productService.deleteProduct(productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getProductList();
                    Toast.makeText(AdminMainActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminMainActivity.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this, "Lỗi khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_PRODUCT_REQUEST || requestCode == EDIT_PRODUCT_REQUEST) {
                // Refresh the product list
                getProductList();
            }
        }
    }

    @Override
    public void onAddToCartButtonClicked(Product product) {
        mCart.addProduct(product);
        Toast.makeText(AdminMainActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }
}