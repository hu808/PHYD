package com.example.app01;

public class FetchData {
    String username;
    String email;

    public FetchData() {
    }

    public FetchData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}
