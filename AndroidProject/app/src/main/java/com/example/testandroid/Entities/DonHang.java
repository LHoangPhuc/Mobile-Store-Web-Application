package com.example.testandroid.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class DonHang {
    @SerializedName("maDonHang")
    private int MaDonHang;
    private String name;
    private int phone;
    private String address;
    private double total;

    public DonHang() {
    }
    public DonHang(int maDonHang, String name, int phone, String address, double total) {
        MaDonHang = maDonHang;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
    }


    public int getMaDonHang() {
        return MaDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        MaDonHang = maDonHang;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DonHang)) return false;
        DonHang donHang = (DonHang) o;
        return MaDonHang == donHang.MaDonHang && phone == donHang.phone && Double.compare(donHang.total, total) == 0 && Objects.equals(name, donHang.name) && Objects.equals(address, donHang.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MaDonHang, name, phone, address, total);
    }
}
