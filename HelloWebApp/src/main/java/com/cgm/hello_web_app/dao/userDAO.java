package com.cgm.hello_web_app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cgm.hello_web_app.eitities.user;

public class userDAO {
    private String url = "jdbc:mysql://localhost:3306/product-k15pm07";
    private String user = "root";
    private String password = "phuc1234562";

    public List<user> getUserList() {
        List<user> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int iduser = rs.getInt("iduser");
                String gmail = rs.getString("gmail");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int phone = rs.getInt("phone");
                user user = new user(iduser, gmail, username, password, phone);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public boolean addUser(user user) {
        if (user == null) {
            // Xử lý khi đối tượng user là null
            return false;
        }

        String sql = "INSERT INTO user (gmail, username, password, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, this.user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getGmail());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setInt(4, user.getPhone());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(user user) {
        String sql = "UPDATE user SET gmail = ?, username = ?, password = ?, phone = ? WHERE iduser = ?";

        try (Connection conn = DriverManager.getConnection(url, this.user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getGmail());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setInt(4, user.getPhone());
            pst.setInt(5, user.getIduser());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE iduser = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
