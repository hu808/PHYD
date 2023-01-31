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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class rRegist extends AppCompatActivity {
    private EditText etrremail,etrrpwd,etrruser;
    private String username,email,password;
    private String usertype = "騎士";
    private Button rregistbtn;
    private TextView rrLoginBtn;
    private Button forgotpass;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_regist);

        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");

        auth = FirebaseAuth.getInstance();

        etrruser = findViewById(R.id.rruser);
        etrremail = findViewById(R.id.rremail);
        etrrpwd = findViewById(R.id.rrpwd);
        rregistbtn = findViewById(R.id.rregistbtn);
        rrLoginBtn = findViewById(R.id.createText);
        forgotpass =findViewById(R.id.forgotpass);

        rregistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etrruser.getText().toString();
                email = etrremail.getText().toString().trim();
                password = etrrpwd.getText().toString().trim();


                if (TextUtils.isEmpty(username)){
                    etrremail.setError("Email不可為空");
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    etrrpwd.setError("密碼不可為空");
                    return;
                }
                else if (TextUtils.isEmpty(email)){
                    etrremail.setError("Email不可為空");
                    return;
                }
                else if (password.length() < 6){
                    etrrpwd.setError("密碼至少需要6個字元");
                    return;
                }
               /* //register the user in firebase
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(rRegist.this, "註冊成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(rRegist.this,rLogin.class));
                            finish();
                        }
                        else{
                            Toast.makeText(rRegist.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Rider r = new Rider(auth.getCurrentUser().getUid(), username, email, password, usertype);
                                reference.child(usertype).child(auth.getCurrentUser().getUid()).setValue(r)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(rRegist.this, e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(rRegist.this, "註冊成功", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(rRegist.this,rLogin.class));
                                                finish();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(rRegist.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        rrLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),rLogin.class));
                finish();
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
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