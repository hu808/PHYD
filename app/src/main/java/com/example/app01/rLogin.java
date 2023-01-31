package com.example.app01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rLogin extends AppCompatActivity {
    EditText rluser,rlpwd;
    TextView rlgoregist,rlgomain;
    Button rllogbtn;
    FirebaseAuth auth;
    private DatabaseReference reference;
    Button forgotpassbtn;
    private String usertype = "騎士";
    private String mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_login);
        rluser = findViewById(R.id.rluser);
        rlpwd = findViewById(R.id.rlpwd);
        rllogbtn = findViewById(R.id.rllogbtn);
        forgotpassbtn = findViewById(R.id.forgotpassbtn);
        rlgoregist = findViewById(R.id.goregist);
        rlgomain = findViewById(R.id.gomain);

        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");

        auth = FirebaseAuth.getInstance();

        rllogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rluser.getText().toString().trim();
                String password = rlpwd.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    rluser.setError("Email不可為空");
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    rlpwd.setError("密碼不可為空");
                    return;
                }
                else {
                    loginUser(email,password);
                   /* reference.child(usertype).child(mail)
                            .addListenerForSingleValueEvent(listener);*/
                }
/*
                //authenticate the user
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(rLogin.this,"登入成功",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),rMainPage.class));
                            finish();
                        }
                        else{
                            Toast.makeText(rLogin.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/
            }
        });

        rlgoregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),rRegist.class));
                finish();
            }
        });

        rlgomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        forgotpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
                finish();
            }
        });

    }
    private void loginUser(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String ruser = rluser.getText().toString();
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                gv.setRUser(ruser);
                Toast.makeText(rLogin.this, "登入成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(rLogin.this,rMainPage.class));
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

  /*  ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                String password = dataSnapshot.child("password")
                        .getValue(String.class);
                String email = dataSnapshot.child("email")
                        .getValue(String.class);

                if(pass.equals(password)){
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(rLogin.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    Intent i = new Intent(rLogin.this, rMainPage.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                }
                else{
                    Toast.makeText(rLogin.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(rLogin.this, "此信箱尚未註冊", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rLogin.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };*/

}