package com.cgm.hello_web_app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cgm.hello_web_app.eitities.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Statement;
public class ProductDAO {
	public ArrayList<Product> getLatestProductList(){
		//code
		
		String url,user,password;
		Connection conn=null;
		String sql = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Product> list = null;
		try {
			//connect to DB
			//load driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//create a connection
			url="jdbc:mysql://localhost:3306/product-k15pm07";
			user="root";
			password="phuc1234562";
			//Tao ket noi
			conn =DriverManager.getConnection(url, user, password);
			//System.out.print(conn);
			sql="select * from product";
			//create a prepare a statement
			pst=conn.prepareStatement(sql);
			
			//excecute query
			rs = pst.executeQuery();
			list= new ArrayList<Product>();
			while (rs.next()) {
	            // Lấy thông tin từ ResultSet và tạo đối tượng Product
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            double price = rs.getDouble("price");
	            String image = rs.getString("image");
	            String detail = rs.getString("detail");
	            Product product = new Product(id, image, name, price, detail);
	            // Thêm sản phẩm vào danh sách
	            list.add(product);
	        }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//get product from table product ò dtb productr
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return list;
	}
	
	
	
	
	public int addProduct(Product product) {
        String url = "jdbc:mysql://localhost:3306/product-k15pm07";
        String user = "root";
        String password = "phuc1234562";
        String sql = "INSERT INTO product (name, price, image, detail) VALUES (?, ?, ?, ?)";
        int newProductId = -1;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, product.getName());
            pst.setDouble(2, product.getPrice());
            pst.setString(3, product.getImage());
            pst.setString(4, product.getDetail());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    newProductId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newProductId;
    }

    public boolean updateProduct(Product product) {
        String url = "jdbc:mysql://localhost:3306/product-k15pm07";
        String user = "root";
        String password = "phuc1234562";
        String sql = "UPDATE product SET name = ?, price = ?, image = ?, detail = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, product.getName());
            pst.setDouble(2, product.getPrice());
            pst.setString(3, product.getImage());
            pst.setString(4, product.getDetail());
            pst.setInt(5, product.getId());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        String url = "jdbc:mysql://localhost:3306/product-k15pm07";
        String user = "root";
        String password = "phuc1234562";
        String sql = "DELETE FROM product WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, productId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}