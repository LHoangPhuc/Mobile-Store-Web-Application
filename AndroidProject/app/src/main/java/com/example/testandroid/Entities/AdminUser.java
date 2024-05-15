package com.example.testandroid.Entities;

import java.util.Objects;

public class AdminUser {
    private int idAdminUser;
    private String username;
    private String password;

    public AdminUser() {
        super();
    }

    public AdminUser(int idAdminUser, String username, String password) {
        super();
        this.idAdminUser = idAdminUser;
        this.username = username;
        this.password = password;
    }

    public int getIdAdminUser() {
        return idAdminUser;
    }

    public void setIdAdminUser(int idAdminUser) {
        this.idAdminUser = idAdminUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdminUser, username, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AdminUser other = (AdminUser) obj;
        return idAdminUser == other.idAdminUser && Objects.equals(username, other.username)
                && Objects.equals(password, other.password);
    }
}
