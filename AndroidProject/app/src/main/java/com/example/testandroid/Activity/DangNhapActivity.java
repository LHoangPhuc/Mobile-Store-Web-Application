package com.example.testandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testandroid.R;
import com.example.testandroid.Entities.user;
import com.example.testandroid.Service.userService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangNhapActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private com.example.testandroid.Service.userService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btndangnhap);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/HelloWebApp/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(userService.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String inputUsername = usernameEditText.getText().toString();
        String inputPassword = passwordEditText.getText().toString();

        userService.getUsers().enqueue(new Callback<List<user>>() {
            @Override
            public void onResponse(Call<List<user>> call, Response<List<user>> response) {
                if (response.isSuccessful()) {
                    List<user> users = response.body();
                    for (user user : users) {
                        if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                            Intent intent = new Intent(DangNhapActivity.this, AdminMainActivity.class); // Change here
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    Toast.makeText(DangNhapActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DangNhapActivity.this, "Failed to retrieve users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<user>> call, Throwable t) {
                Toast.makeText(DangNhapActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}