package com.cgm.hello_web_app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cgm.hello_web_app.eitities.AdminUser;

public class AdminUserDAO {
    private String url = "jdbc:mysql://localhost:3306/product-k15pm07";
    private String user = "root";
    private String password = "phuc1234562";

    public ArrayList<AdminUser> getAdminUserList() {
        ArrayList<AdminUser> list = new ArrayList<>();
        String sql = "SELECT * FROM AdminUser";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int idAdminUser = rs.getInt("idAdminUser");
                String username = rs.getString("username");
                String password = rs.getString("password");
                AdminUser adminUser = new AdminUser(idAdminUser, username, password);
                list.add(adminUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addAdminUser(AdminUser adminUser) {
        if (adminUser == null) {
            return false;
        }

        String sql = "INSERT INTO AdminUser (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, adminUser.getUsername());
            pst.setString(2, adminUser.getPassword());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAdminUser(AdminUser adminUser) {
        String sql = "UPDATE AdminUser SET username = ?, password = ? WHERE idAdminUser = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, adminUser.getUsername());
            pst.setString(2, adminUser.getPassword());
            pst.setInt(3, adminUser.getIdAdminUser());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAdminUser(int idAdminUser) {
        String sql = "DELETE FROM AdminUser WHERE idAdminUser = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idAdminUser);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
