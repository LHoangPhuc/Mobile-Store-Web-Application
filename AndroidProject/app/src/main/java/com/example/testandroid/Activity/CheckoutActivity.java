package com.example.testandroid.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testandroid.Entities.Cart;
import com.example.testandroid.Entities.DonHang;
import com.example.testandroid.Service.DonHangService;
import com.example.testandroid.Entities.Product;
import com.example.testandroid.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private Button checkoutButton;
    private TextView totalTextView;

    private Retrofit retrofit;
    private DonHangService donHangService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView productNameTextView = findViewById(R.id.product_name);

        // Lấy giỏ hàng từ Application
        Cart cart = ((MyApplication) getApplication()).getCart();

        // Lấy danh sách sản phẩm từ giỏ hàng
        List<Product> productList = cart.getProducts();

        // Tạo một StringBuilder để xây dựng chuỗi tên sản phẩm
        StringBuilder productNames = new StringBuilder();

        // Duyệt qua danh sách sản phẩm
        for (int i = 0; i < productList.size(); i++) {
            // Thêm tên sản phẩm vào chuỗi
            productNames.append(productList.get(i).getName());

            // Nếu không phải là sản phẩm cuối cùng, thêm dấu phẩy và một khoảng trắng
            if (i < productList.size() - 1) {
                productNames.append(", ");
            }
        }

        // Đặt chuỗi tên sản phẩm vào TextView
        productNameTextView.setText(productNames.toString());

        totalTextView = findViewById(R.id.DonHang_total);
        nameEditText = findViewById(R.id.DonHang_name);
        phoneEditText = findViewById(R.id.DonHang_phone);
        addressEditText = findViewById(R.id.DonHang_address);
        checkoutButton = findViewById(R.id.btndathang);

        double total = getIntent().getDoubleExtra("DonHang_total", 0);
        totalTextView.setText(String.valueOf(total));

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/HelloWebApp/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        donHangService = retrofit.create(DonHangService.class);

        Button checkoutButton = findViewById(R.id.btndathang);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });
    }

    private void checkout() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String total = totalTextView.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || total.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        DonHang donHang = new DonHang(0, name, Integer.parseInt(phone), address, Double.parseDouble(total));
        Log.i("CheckoutActivity", "Sending: " + donHang); // Log the data being sent

        Call<Void> call = donHangService.addDonHang(donHang);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                    // Clear the cart
                    ((MyApplication) getApplication()).getCart().getProducts().clear();

                    // Close CheckoutActivity and return to MainActivity
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // This flag ensures all activities on top of the stack are cleared
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("API Error", "Error code: " + response.code() + " Error body: " + response.errorBody());
                    try {
                        Log.e("API Error", "Error message: " + response.errorBody().string()); // Log the error message from the server
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CheckoutActivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API Error", t.getMessage());
                Toast.makeText(CheckoutActivity.this, "Đã xảy ra lỗi khi kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
