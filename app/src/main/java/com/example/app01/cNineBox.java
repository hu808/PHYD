package com.example.app01;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class cNineBox extends AppCompatActivity {
    private Button rbtn,kbtn,fbtn,hbtn,nbtn,wbtn;
    private int rClick,kClick,fClick,hClick,nClick,wClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_nine_box);

        rbtn=findViewById(R.id.rbtn);
        kbtn=findViewById(R.id.kbtn);
        fbtn=findViewById(R.id.fbtn);
        hbtn=findViewById(R.id.hbtn);
        nbtn=findViewById(R.id.nbtn);
        wbtn=findViewById(R.id.wbtn);

        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        rClick = gv.get暫存rClick();
        kClick = gv.get暫存kClick();
        fClick = gv.get暫存fClick();
        hClick = gv.get暫存hClick();
        nClick = gv.get暫存nClick();
        wClick = gv.get暫存wClick();
        if(rClick==1){
            rbtn.setBackground(getResources().getDrawable(R.drawable.red2));
        }else {
            rbtn.setBackground(getResources().getDrawable(R.drawable.r));
        }
        if(kClick==1){
            kbtn.setBackground(getResources().getDrawable(R.drawable.km2));
        }else {
            kbtn.setBackground(getResources().getDrawable(R.drawable.k));
        }
        if(fClick==1){
            fbtn.setBackground(getResources().getDrawable(R.drawable.feet2));
        }else {
            fbtn.setBackground(getResources().getDrawable(R.drawable.feet));
        }
        if(hClick==1){
                    hbtn.setBackground(getResources().getDrawable(R.drawable.hand2));
                }else {
                    hbtn.setBackground(getResources().getDrawable(R.drawable.h));
                }
        if(nClick==1){
                    nbtn.setBackground(getResources().getDrawable(R.drawable.night2));
                }else {
                    nbtn.setBackground(getResources().getDrawable(R.drawable.n));
                }
        if(wClick==1){
                    wbtn.setBackground(getResources().getDrawable(R.drawable.weather2));
                }else {
                    wbtn.setBackground(getResources().getDrawable(R.drawable.w));
                }

        /*按一下回到cSelectPage1介面*/
        Button cbackSelect1btn = (Button) findViewById(R.id.cbackSelect1btn);
        cbackSelect1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cNineBox.this, cSelectPage1.class);
                startActivity(intent);
                finish();
            }
        });

        /*按一下到c-SelectScore介面*/
        Button ctnSelectScorebtn = (Button) findViewById(R.id.ctnSelectScorebtn);
        ctnSelectScorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                String Companyuser = gv.getCompanyuser();
                gv.set暫存rClick(rClick);
                gv.set暫存kClick(kClick);
                gv.set暫存fClick(fClick);
                gv.set暫存hClick(hClick);
                gv.set暫存nClick(nClick);
                gv.set暫存wClick(wClick);

                Intent intent = new Intent();
                intent.setClass(cNineBox.this, cSelectScorePage.class);
                startActivity(intent);
                finish();
            }
        });

        //按鈕事件
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rClick==0){
                    rClick=1;
                    rbtn.setBackground(getResources().getDrawable(R.drawable.red2));
                }else {
                    rClick=0;
                    rbtn.setBackground(getResources().getDrawable(R.drawable.r));
                }
            }
        });
        kbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kClick==0){
                    kClick=1;
                    kbtn.setBackground(getResources().getDrawable(R.drawable.km2));
                }else {
                    kClick=0;
                    kbtn.setBackground(getResources().getDrawable(R.drawable.k));
                }
            }
        });
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fClick==0){
                    fClick=1;
                    fbtn.setBackground(getResources().getDrawable(R.drawable.feet2));
                }else {
                    fClick=0;
                    fbtn.setBackground(getResources().getDrawable(R.drawable.feet));
                }
            }
        });
        hbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hClick==0){
                    hClick=1;
                    hbtn.setBackground(getResources().getDrawable(R.drawable.hand2));
                }else {
                    hClick=0;
                    hbtn.setBackground(getResources().getDrawable(R.drawable.h));
                }
            }
        });
        nbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nClick==0){
                    nClick=1;
                    nbtn.setBackground(getResources().getDrawable(R.drawable.night2));
                }else {
                    nClick=0;
                    nbtn.setBackground(getResources().getDrawable(R.drawable.n));
                }
            }
        });
        wbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wClick==0){
                    wClick=1;
                    wbtn.setBackground(getResources().getDrawable(R.drawable.weather2));
                }else {
                    wClick=0;
                    wbtn.setBackground(getResources().getDrawable(R.drawable.w));
                }
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