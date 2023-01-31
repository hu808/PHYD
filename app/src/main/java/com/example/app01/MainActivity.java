package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();

        HashMap<String,Object> map = new HashMap<>();
        map.put("帳號","fb0");
        map.put("密碼","000000");
        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child("00富邦產險").updateChildren(map);

        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("帳號","ns1");
        map2.put("密碼","111111");
        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child("01南山產險").updateChildren(map2);

        HashMap<String,Object> map3 = new HashMap<>();
        map3.put("帳號","gt2");
        map3.put("密碼","222222");
        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child("02國泰產險").updateChildren(map3);

        /*HashMap<String,Object> first = new HashMap<>();
        first.put("初始保費","未設定");
        first.put("全額保費","未設定");
        first.put("時程","未設定");
        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child("00富邦產險").updateChildren(first);
        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child("01南山產險").updateChildren(first);
        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child("02國泰產險").updateChildren(first);*/

        /*判斷是否已經登入了?*/
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),rMainPage.class));
            finish();
        }

        /*按一下跳到保險公司登入介面*/
        Button com1 = (Button) findViewById(R.id.cbtn);
        com1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, cLogin.class);
                startActivity(intent);
                finish();
            }
        });

        /*按一下跳到種機騎士登入介面*/
        Button rider = (Button) findViewById(R.id.rbtn);
        rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, rLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}