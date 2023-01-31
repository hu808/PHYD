package com.example.app01;

public class Com {

    private String uid;
    private String 年月日;
    private String username;
    private String email;
    private int 是否已聯絡;

    public Com() {
    }

    public Com(String uid, String 年月日, String username, String email, int 是否已聯絡) {
        this.uid = uid;
        this.年月日 = 年月日;
        this.username = username;
        this.email = email;
        this.是否已聯絡 = 是否已聯絡;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String get年月日() {
        return 年月日;
    }

    public void set年月日(String 年月日) {
        this.年月日 = 年月日;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int get是否已聯絡() {
        return 是否已聯絡;
    }

    public void set是否已聯絡(int 是否已聯絡) {
        this.是否已聯絡 = 是否已聯絡;
    }
}
