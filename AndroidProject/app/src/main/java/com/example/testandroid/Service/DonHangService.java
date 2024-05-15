package com.example.testandroid.Service;

import com.example.testandroid.Entities.DonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Body;

public interface DonHangService {
    @GET("/DonHangs")
    Call<List<DonHang>> getDonHangs();

    @POST("DonHangs")
    Call<Void> addDonHang(@Body DonHang donHang);

    @PUT("/DonHangs/{maDonHang}")
    Call<Void> updateDonHang(@Path("maDonHang") int maDonHang, @Body DonHang donHang);
}
