package com.cgm.hello_web_app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cgm.hello_web_app.eitities.DonHang;

public class DonHangDAO {
    private String url = "jdbc:mysql://localhost:3306/product-k15pm07";
    private String user = "root";
    private String password = "phuc1234562";

    public ArrayList<DonHang> getLatestDonHangList() {
        ArrayList<DonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM DonHang";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int MaDonHang = rs.getInt("MaDonHang");
                String name = rs.getString("name");
                int phone = rs.getInt("phone");
                String address = rs.getString("address");
                double total = rs.getDouble("total");
                DonHang donHang = new DonHang(MaDonHang, name, phone, address, total);
                list.add(donHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addDonHang(DonHang donHang) {
        if (donHang == null) {
            // Xử lý khi đối tượng donHang là null
            return false;
        }

        String sql = "INSERT INTO DonHang (name, phone, address, total) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, donHang.getName());
            pst.setInt(2, donHang.getPhone());
            pst.setString(3, donHang.getAddress());
            pst.setDouble(4, donHang.getTotal());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean updateDonHang(DonHang donHang) {
        String sql = "UPDATE DonHang SET name = ?, phone = ?, address = ?, total = ? WHERE MaDonHang = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, donHang.getName());
            pst.setInt(2, donHang.getPhone());
            pst.setString(3, donHang.getAddress());
            pst.setDouble(4, donHang.getTotal());
            pst.setInt(5, donHang.getMaDonHang());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
