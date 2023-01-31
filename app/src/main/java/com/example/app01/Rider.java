package com.example.app01;

import android.view.KeyEvent;

public class Rider {
    public String username, email, password;
    public String usertype;
    public String uid;

    public Rider(String uid, String username, String email, String password, String usertype){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.usertype = usertype;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

}
