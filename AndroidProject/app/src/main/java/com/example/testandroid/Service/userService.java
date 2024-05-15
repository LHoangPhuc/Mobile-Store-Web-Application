package com.example.testandroid.Service;
import com.example.testandroid.Entities.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface userService {
    @GET("users")
    Call<List<user>> getUsers();

    @POST("users")
    Call<Void> addUser(@Body user user);

    @PUT("users/{id}")
    Call<Void> updateUser(@Path("id") int id, @Body user user);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}