package com.example.testandroid.Service;

import com.example.testandroid.Entities.AdminUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminUserService {
    @GET("adminUsers")
    Call<List<AdminUser>> getAdminUsers();

    @POST("adminUsers")
    Call<Void> createAdminUser(@Body AdminUser adminUser);

    @PUT("adminUsers/{id}")
    Call<Void> updateAdminUser(@Path("id") int id, @Body AdminUser adminUser);

    @DELETE("adminUsers/{id}")
    Call<Void> deleteAdminUser(@Path("id") int id);
}