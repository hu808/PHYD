package com.example.app01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class cSelectScorePage extends AppCompatActivity {
    private TextView credtxt,credtxt1,ckmtxt,ckmtxt1,cftxt,cftxt1,chtxt,chtxt1,cnighttxt,cnighttxt1,cweathertxt,cweathertxt1;
    private EditText credtxt2,ckmtxt2,cftxt2,chtxt2,cnighttxt2,cweathertxt2;
    private String r1,k1,f1,h1,n1,w1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_select_score_page);
        credtxt = findViewById(R.id.credtxt);
        credtxt1 = findViewById(R.id.credtxt1);
        credtxt2 = findViewById(R.id.credtxt2);
        ckmtxt = findViewById(R.id.ckmtxt);
        ckmtxt1 = findViewById(R.id.ckmtxt1);
        ckmtxt2 = findViewById(R.id.ckmtxt2);
        cftxt = findViewById(R.id.cftxt);
        cftxt1 = findViewById(R.id.cftxt1);
        cftxt2 = findViewById(R.id.cftxt2);
        chtxt = findViewById(R.id.chtxt);
        chtxt1 = findViewById(R.id.chtxt1);
        chtxt2 = findViewById(R.id.chtxt2);
        cnighttxt = findViewById(R.id.cnighttxt);
        cnighttxt1 = findViewById(R.id.cnighttxt1);
        cnighttxt2 = findViewById(R.id.cnighttxt2);
        cweathertxt = findViewById(R.id.cweathertxt);
        cweathertxt1 = findViewById(R.id.cweathertxt1);
        cweathertxt2 = findViewById(R.id.cweathertxt2);

        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        String Companyuser = gv.getCompanyuser();
        int 存rClick = gv.get暫存rClick();
        int 存kClick = gv.get暫存kClick();
        int 存fClick = gv.get暫存fClick();
        int 存hClick = gv.get暫存hClick();
        int 存nClick = gv.get暫存nClick();
        int 存wClick = gv.get暫存wClick();

        //如果有選取該因子則會顯示讓其設定門檻值
        if(存rClick==1){
            credtxt.setVisibility(View.VISIBLE);
            credtxt1.setVisibility(View.VISIBLE);
            credtxt2.setVisibility(View.VISIBLE);
        }else {
            credtxt.setVisibility(View.GONE);
            credtxt1.setVisibility(View.GONE);
            credtxt2.setVisibility(View.GONE);
        }
        if(存kClick==1){
            ckmtxt.setVisibility(View.VISIBLE);
            ckmtxt1.setVisibility(View.VISIBLE);
            ckmtxt2.setVisibility(View.VISIBLE);
        }else {
            ckmtxt.setVisibility(View.GONE);
            ckmtxt1.setVisibility(View.GONE);
            ckmtxt2.setVisibility(View.GONE);
        }
        if(存fClick==1){
            cftxt.setVisibility(View.VISIBLE);
            cftxt1.setVisibility(View.VISIBLE);
            cftxt2.setVisibility(View.VISIBLE);
        }else {
            cftxt.setVisibility(View.GONE);
            cftxt1.setVisibility(View.GONE);
            cftxt2.setVisibility(View.GONE);
        }
        if(存hClick==1){
            chtxt.setVisibility(View.VISIBLE);
            chtxt1.setVisibility(View.VISIBLE);
            chtxt2.setVisibility(View.VISIBLE);
        }else {
            chtxt.setVisibility(View.GONE);
            chtxt1.setVisibility(View.GONE);
            chtxt2.setVisibility(View.GONE);
        }
        if(存nClick==1){
            cnighttxt.setVisibility(View.VISIBLE);
            cnighttxt1.setVisibility(View.VISIBLE);
            cnighttxt2.setVisibility(View.VISIBLE);
        }else {
            cnighttxt.setVisibility(View.GONE);
            cnighttxt1.setVisibility(View.GONE);
            cnighttxt2.setVisibility(View.GONE);
        }
        if(存wClick==1){
            cweathertxt.setVisibility(View.VISIBLE);
            cweathertxt1.setVisibility(View.VISIBLE);
            cweathertxt2.setVisibility(View.VISIBLE);
        }else {
            cweathertxt.setVisibility(View.GONE);
            cweathertxt1.setVisibility(View.GONE);
            cweathertxt2.setVisibility(View.GONE);
        }

        /*按一下回到cbackninebtn介面*/
        Button cbackninebtn = (Button) findViewById(R.id.cbackninebtn);
        cbackninebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cSelectScorePage.this, cNineBox.class);
                startActivity(intent);
                finish();
            }
        });

        /*按一下跳到surebtn介面*/
        Button surebtn = (Button) findViewById(R.id.surebtn);
        surebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                r1 = credtxt2.getText().toString().trim();
                k1 = ckmtxt2.getText().toString().trim();
                f1 = cftxt2.getText().toString().trim();
                h1 = chtxt2.getText().toString().trim();
                n1 = cnighttxt2.getText().toString().trim();
                w1 = cweathertxt2.getText().toString().trim();

                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                String Companyuser = gv.getCompanyuser();
                String 存fm1 = gv.get暫存fm1();
                String 存fm2 = gv.get暫存fm2();
                String 存ctime = gv.get暫存ctime();
                int 存rClick = gv.get暫存rClick();
                int 存kClick = gv.get暫存kClick();
                int 存fClick = gv.get暫存fClick();
                int 存hClick = gv.get暫存hClick();
                int 存nClick = gv.get暫存nClick();
                int 存wClick = gv.get暫存wClick();
                gv.set門檻rClick(r1);
                gv.set門檻kClick(k1);
                gv.set門檻fClick(f1);
                gv.set門檻hClick(h1);
                gv.set門檻nClick(n1);
                gv.set門檻wClick(w1);

                //判斷是否填入資料
                if(存rClick==1 && credtxt2.getText().toString().matches("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("錯誤");
                    alertDialog.setMessage("闖紅燈的門檻值未設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                        public void onClick(DialogInterface dialog, int which) {  }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else if(存kClick==1 & ckmtxt2.getText().toString().matches("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("錯誤");
                    alertDialog.setMessage("里程數的門檻值未設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {  }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else if(存fClick==1 & cftxt2.getText().toString().matches("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("錯誤");
                    alertDialog.setMessage("重油的門檻值未設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {  }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else if(存hClick==1 & chtxt2.getText().toString().matches("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("錯誤");
                    alertDialog.setMessage("急煞的門檻值未設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {  }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else if(存nClick==1 & cnighttxt2.getText().toString().matches("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("錯誤");
                    alertDialog.setMessage("夜騎的門檻值未設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {  }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else if(存wClick==1 & cweathertxt2.getText().toString().matches("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("錯誤");
                    alertDialog.setMessage("雨騎的門檻值未設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {  }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else {
                    HashMap<String,Object> first = new HashMap<>();
                    first.put("初始保費",存fm1);
                    first.put("全額保費",存fm2);
                    first.put("時程",存ctime);
                    FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).updateChildren(first);

                    //將有輸入門檻值的資料更新到資料庫，沒有的則刪除資料庫的資料
                    /*if(存rClick==1){
                        HashMap<String,Object> R = new HashMap<>();
                        R.put("因子","闖紅燈");
                        R.put("門檻值",r1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("1").updateChildren(R);
                    }else {
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("1").removeValue();
                    }*/

                    //資料更新到資料庫
                        HashMap<String,Object> R = new HashMap<>();
                        R.put("是否採用",存rClick);
                        R.put("門檻值",r1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("闖紅燈").updateChildren(R);

                        HashMap<String,Object> K = new HashMap<>();
                        K.put("是否採用",存kClick);
                        K.put("門檻值",k1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("里程數").updateChildren(K);

                        HashMap<String,Object> F = new HashMap<>();
                        F.put("是否採用",存fClick);
                        F.put("門檻值",f1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("重油").updateChildren(F);

                        HashMap<String,Object> H = new HashMap<>();
                        H.put("是否採用",存hClick);
                        H.put("門檻值",h1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("急煞").updateChildren(H);

                        HashMap<String,Object> N = new HashMap<>();
                        N.put("是否採用",存nClick);
                        N.put("門檻值",n1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("夜騎").updateChildren(N);

                        HashMap<String,Object> W = new HashMap<>();
                        W.put("是否採用",存wClick);
                        W.put("門檻值",w1);
                        FirebaseDatabase.getInstance().getReference().child("PHYD").child("保險公司").child(Companyuser).child("條件").child("雨騎").updateChildren(W);

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(cSelectScorePage.this);
                    alertDialog.setTitle("設定");
                    alertDialog.setMessage("門檻值已重新設定");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(cSelectScorePage.this, cMainPage.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
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