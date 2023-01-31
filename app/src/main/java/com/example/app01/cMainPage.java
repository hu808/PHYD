package com.example.app01;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cMainPage extends AppCompatActivity {

    private DatabaseReference reference;
    private TextView cfm1Text,cfm2Text,ctText;
    private int count=0;
    private RecyclerView mRecyclerView;
    private TextView listtxt_r,listtxt_r因子,listtxt_k,listtxt_k因子,listtxt_f,listtxt_f因子,listtxt_h,listtxt_h因子,listtxt_n,listtxt_n因子,listtxt_w,listtxt_w因子;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_main_page);
        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        String Companyuser = gv.getCompanyuser();

        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_ctocatch);
        /*new FirebaseDatabaseHelper(Companyuser).readPHYD(new FirebaseDatabaseHelper.Datastatus() {
            @Override
            public void DataIsLoad(List<Stringtocatch> tocatch, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, cMainPage.this, tocatch, keys);
            }
        });*/

//        cfm1Text = findViewById(R.id.cfm1txv);
//        cfm2Text = findViewById(R.id.cfm2txv);
        ctText = findViewById(R.id.ctimetxv);

        listtxt_r = findViewById(R.id.listtxt_r);
        listtxt_r因子 = findViewById(R.id.listtxt_r因子);
        listtxt_k = findViewById(R.id.listtxt_k);
        listtxt_k因子 = findViewById(R.id.listtxt_k因子);
        listtxt_f = findViewById(R.id.listtxt_f);
        listtxt_f因子 = findViewById(R.id.listtxt_f因子);
        listtxt_h = findViewById(R.id.listtxt_h);
        listtxt_h因子 = findViewById(R.id.listtxt_h因子);
        listtxt_n = findViewById(R.id.listtxt_n);
        listtxt_n因子 = findViewById(R.id.listtxt_n因子);
        listtxt_w = findViewById(R.id.listtxt_w);
        listtxt_w因子 = findViewById(R.id.listtxt_w因子);


        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");

        reference.child("保險公司").child(Companyuser)
                .addListenerForSingleValueEvent(listener);

        reference.child("保險公司").child(Companyuser).child("條件").child("闖紅燈")
                .addListenerForSingleValueEvent(listenerR);
        reference.child("保險公司").child(Companyuser).child("條件").child("里程數")
                .addListenerForSingleValueEvent(listenerK);
        reference.child("保險公司").child(Companyuser).child("條件").child("重油")
                .addListenerForSingleValueEvent(listenerF);
        reference.child("保險公司").child(Companyuser).child("條件").child("急煞")
                .addListenerForSingleValueEvent(listenerH);
        reference.child("保險公司").child(Companyuser).child("條件").child("夜騎")
                .addListenerForSingleValueEvent(listenerN);
        reference.child("保險公司").child(Companyuser).child("條件").child("雨騎")
                .addListenerForSingleValueEvent(listenerW);


        /*按一下登出回到首頁介面*/
        Button clogoutbtn = (Button) findViewById(R.id.clogoutbtn);
        clogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cMainPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*按一下到cSelect1介面*/
        Button changebtn = (Button) findViewById(R.id.changebtn);
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                gv.set暫存rClick(count);
                gv.set暫存kClick(count);
                gv.set暫存fClick(count);
                gv.set暫存hClick(count);
                gv.set暫存nClick(count);
                gv.set暫存wClick(count);
                gv.set暫存ctime("1個月");
                gv.set門檻rClick(null);
                gv.set門檻kClick(null);
                gv.set門檻fClick(null);
                gv.set門檻hClick(null);
                gv.set門檻nClick(null);
                gv.set門檻wClick(null);

                Intent intent = new Intent();
                intent.setClass(cMainPage.this, cSelectPage1.class);
                startActivity(intent);
                finish();
            }
        });

        /*按一下到cCustomerPage介面*/
        Button cCustomerPage = (Button) findViewById(R.id.smembtn2);
        cCustomerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cMainPage.this, cCustomerPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String ct = dataSnapshot.child("時程")
                        .getValue(String.class);
            //初始保費全額保費時程 顯示
//                cfm1Text.setText(cfm1 + "元");
//                cfm2Text.setText(cfm2 + "元");
            if(ct==null) {ctText.setText("未設定");}
            else {
                ctText.setText(ct);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerR = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int r1 = dataSnapshot.child("是否採用").getValue(int.class);
            String r門檻 = dataSnapshot.child("門檻值").getValue(String.class);

            listtxt_r.setText("平均每月少於 " + r門檻 + " 次");

            //如果有採用則顯示
            if(r1==1){
                listtxt_r因子.setVisibility(View.VISIBLE);
                listtxt_r.setVisibility(View.VISIBLE);
                TextView 分隔2 = findViewById(R.id.分隔2);
                分隔2.setVisibility(View.VISIBLE);
            }else {
                listtxt_r因子.setVisibility(View.GONE);
                listtxt_r.setVisibility(View.GONE);
                TextView 分隔2 = findViewById(R.id.分隔2);
                分隔2.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerK = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int k1 = dataSnapshot.child("是否採用").getValue(int.class);
            String k門檻 = dataSnapshot.child("門檻值").getValue(String.class);

            listtxt_k.setText("平均每月至少 " + k門檻 + " km");

            //如果有採用則顯示
            if(k1==1){
                listtxt_k因子.setVisibility(View.VISIBLE);
                listtxt_k.setVisibility(View.VISIBLE);
                TextView 分隔3 = findViewById(R.id.分隔3);
                分隔3.setVisibility(View.VISIBLE);
            }else {
                listtxt_k因子.setVisibility(View.GONE);
                listtxt_k.setVisibility(View.GONE);
                TextView 分隔3 = findViewById(R.id.分隔3);
                分隔3.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerF = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int f1 = dataSnapshot.child("是否採用").getValue(int.class);
            String f門檻 = dataSnapshot.child("門檻值").getValue(String.class);

            listtxt_f.setText("平均每月少於 " + f門檻 + " 次");

            //如果有採用則顯示
            if(f1==1){
                listtxt_f因子.setVisibility(View.VISIBLE);
                listtxt_f.setVisibility(View.VISIBLE);
                TextView 分隔4 = findViewById(R.id.分隔4);
                分隔4.setVisibility(View.VISIBLE);
            }else {
                listtxt_f因子.setVisibility(View.GONE);
                listtxt_f.setVisibility(View.GONE);
                TextView 分隔4 = findViewById(R.id.分隔4);
                分隔4.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerH = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int h1 = dataSnapshot.child("是否採用").getValue(int.class);
            String h門檻 = dataSnapshot.child("門檻值").getValue(String.class);

            listtxt_h.setText("平均每月少於 " + h門檻 + " 次");

            //如果有採用則顯示
            if(h1==1){
                listtxt_h因子.setVisibility(View.VISIBLE);
                listtxt_h.setVisibility(View.VISIBLE);
                TextView 分隔5 = findViewById(R.id.分隔5);
                分隔5.setVisibility(View.VISIBLE);
            }else {
                listtxt_h因子.setVisibility(View.GONE);
                listtxt_h.setVisibility(View.GONE);
                TextView 分隔5 = findViewById(R.id.分隔5);
                分隔5.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerN = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int n1 = dataSnapshot.child("是否採用").getValue(int.class);
            String n門檻 = dataSnapshot.child("門檻值").getValue(String.class);

            listtxt_n.setText("平均每月少於 " + n門檻 + " 次");

            //如果有採用則顯示
            if(n1==1){
                listtxt_n因子.setVisibility(View.VISIBLE);
                listtxt_n.setVisibility(View.VISIBLE);
                TextView 分隔6 = findViewById(R.id.分隔6);
                分隔6.setVisibility(View.VISIBLE);
            }else {
                listtxt_n因子.setVisibility(View.GONE);
                listtxt_n.setVisibility(View.GONE);
                TextView 分隔6 = findViewById(R.id.分隔6);
                分隔6.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerW = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int w1 = dataSnapshot.child("是否採用").getValue(int.class);
            String w門檻 = dataSnapshot.child("門檻值").getValue(String.class);

            listtxt_w.setText("平均每月少於 " + w門檻 + " 次");

            //如果有採用則顯示
            if(w1==1){
                listtxt_w因子.setVisibility(View.VISIBLE);
                listtxt_w.setVisibility(View.VISIBLE);
            }else {
                listtxt_w因子.setVisibility(View.GONE);
                listtxt_w.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}