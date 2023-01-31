package com.example.app01;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.NonNull;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class cLogin extends AppCompatActivity {
    private String usertype = "保險公司";
    private String cpwd,cuser;
    private EditText etcuser,etcpwd;
    private Spinner spcompany;
    TextView rlgomain2;
    Button clogbtn;
    private String cid = "00富邦產險";

    FirebaseAuth auth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_login);

        etcuser = findViewById(R.id.cuser);
        etcpwd = findViewById(R.id.cpwd);
        spcompany = findViewById(R.id.spcom);
        clogbtn = findViewById(R.id.clogbtn);
        rlgomain2 = findViewById(R.id.gomain2);

        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");

        auth = FirebaseAuth.getInstance();

        rlgomain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        clogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuser = etcuser.getText().toString().trim();
                cpwd = etcpwd.getText().toString().trim();

                String[] spcompanys=getResources().getStringArray(R.array.companies);
                int index = spcompany.getSelectedItemPosition();
                cid = spcompanys[index];

                if (TextUtils.isEmpty(cuser)){
                    etcuser.setError("帳號不可為空");
                    return;
                }
                else if (TextUtils.isEmpty(cpwd)){
                    etcpwd.setError("密碼不可為空");
                    return;
                }
                else {
                   reference.child(usertype).child(cid)
                            .addListenerForSingleValueEvent(listener);
                }

            }
        });

    }
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                String ccpassword = dataSnapshot.child("密碼")
                        .getValue(String.class);
                String ccuser = dataSnapshot.child("帳號")
                        .getValue(String.class);

                if(cpwd.equals(ccpassword) && cuser.equals(ccuser)){

                    GlobalVariable gv = (GlobalVariable)getApplicationContext();
                    gv.setCompanyuser(cid);

                    Intent i = new Intent(cLogin.this, cMainPage.class);
                    startActivity(i);
                    finish();

                }
                else{
                    Toast.makeText(cLogin.this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(cLogin.this, "此信箱尚未註冊", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cLogin.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}