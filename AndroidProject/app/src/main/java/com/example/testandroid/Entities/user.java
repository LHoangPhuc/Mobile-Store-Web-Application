package com.example.testandroid.Entities;

public class user {
    private int iduser;
    private String gmail;
    private String username;
    private String password;
    private int phone;

    public user() {
        super();
    }

    public user(int iduser, String gmail, String username, String password, int phone) {
        super();
        this.iduser = iduser;
        this.gmail = gmail;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
