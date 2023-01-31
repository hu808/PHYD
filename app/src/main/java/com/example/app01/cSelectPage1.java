package com.example.app01;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class cSelectPage1 extends AppCompatActivity {
//    private EditText etfmoney1,etfmoney2;
    private Spinner sptime;
    private String tid = "1";
    private String fm1,fm2;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_select_page1);

//        etfmoney1 = findViewById(R.id.fmoney1);
//        etfmoney2 = findViewById(R.id.fmoney2);
        sptime = findViewById(R.id.sptime);

        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");

        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        String Companyuser = gv.getCompanyuser();

        reference.child("保險公司").child(Companyuser)
                .addListenerForSingleValueEvent(listener);

        /*按一下回到cmainpage介面*/
        Button cbackcmainpagebtn = (Button) findViewById(R.id.cbackcmainpagebtn);
        cbackcmainpagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cSelectPage1.this, cMainPage.class);
                startActivity(intent);
                finish();
            }
        });

        /*按一下到c-nine介面*/
        Button ctnninebtn = (Button) findViewById(R.id.ctnninebtn);
        ctnninebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();

                String[] sptimes=getResources().getStringArray(R.array.time);
                int index = sptime.getSelectedItemPosition();
                tid = sptimes[index];

//                fm1 = etfmoney1.getText().toString().trim();
//                fm2 = etfmoney2.getText().toString().trim();
//
//                if(etfmoney1.getText().toString().matches("") && etfmoney2.getText().toString().matches("")){
//                    gv.set暫存fm1("未設定");
//                    gv.set暫存fm2("未設定");
//                }else if(etfmoney2.getText().toString().matches("")){
//                    gv.set暫存fm1(fm1);
//                    gv.set暫存fm2("未設定");
//                }else if(etfmoney1.getText().toString().matches("")){
//                    gv.set暫存fm1("未設定");
//                    gv.set暫存fm2(fm2);
//                }else {
//                    gv.set暫存fm1(fm1);
//                    gv.set暫存fm2(fm2);
//                }
                gv.set暫存ctime(tid);
                gv.set暫存tidindex(index) ;

                Intent intent = new Intent();
                intent.setClass(cSelectPage1.this, cNineBox.class);
                startActivity(intent);
                finish();
            }
        });
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String cfm1 = dataSnapshot.child("初始保費")
                    .getValue(String.class);
            String cfm2 = dataSnapshot.child("全額保費")
                    .getValue(String.class);

//            etfmoney1.setHint(cfm1);
//            etfmoney2.setHint(cfm2);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cSelectPage1.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}