package com.example.app01;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cCustomerScore extends AppCompatActivity {
    private String uid;
    private DatabaseReference databaseReference;
    private DatabaseReference reference,reference2;
    private Spinner spinner;
    List<String> cmonths1;
    private TextView listtxt_r,listtxt_r因子,listtxt_k,listtxt_k因子,listtxt_f,listtxt_f因子,listtxt_h,listtxt_h因子,listtxt_n,listtxt_n因子,listtxt_w,listtxt_w因子;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_customer_score);
        uid = getIntent().getStringExtra("uid");
        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        String Companyuser = gv.getCompanyuser();

        listtxt_r = findViewById(R.id.cScore_listtxt_r);
        listtxt_r因子 = findViewById(R.id.rScore_listtxt_r字);
        listtxt_k = findViewById(R.id.cScore_listtxt_km);
        listtxt_k因子 = findViewById(R.id.rScore_listtxt_km字);
        listtxt_f = findViewById(R.id.cScore_listtxt_f);
        listtxt_f因子 = findViewById(R.id.rScore_listtxt_f字);
        listtxt_h = findViewById(R.id.cScore_listtxt_h);
        listtxt_h因子 = findViewById(R.id.rScore_listtxt_h字);
        listtxt_n = findViewById(R.id.cScore_listtxt_n);
        listtxt_n因子 = findViewById(R.id.rScore_listtxt_n字);
        listtxt_w = findViewById(R.id.cScore_listtxt_w);
        listtxt_w因子 = findViewById(R.id.rScore_listtxt_w字);

        //顯示spinner
        spinner=findViewById(R.id.cspmonth1);
        cmonths1=new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("PHYD");
        databaseReference.child("騎士").child(uid).child("危險駕駛次數").child("本月")
                .addValueEventListener(listener_cMonth);

        reference2 = FirebaseDatabase.getInstance()
                .getReference("PHYD");
        reference2.child("保險公司").child(Companyuser).child("條件").child("闖紅燈")
                .addListenerForSingleValueEvent(listenerR);
        reference2.child("保險公司").child(Companyuser).child("條件").child("里程數")
                .addListenerForSingleValueEvent(listenerK);
        reference2.child("保險公司").child(Companyuser).child("條件").child("重油")
                .addListenerForSingleValueEvent(listenerF);
        reference2.child("保險公司").child(Companyuser).child("條件").child("急煞")
                .addListenerForSingleValueEvent(listenerH);
        reference2.child("保險公司").child(Companyuser).child("條件").child("夜騎")
                .addListenerForSingleValueEvent(listenerN);
        reference2.child("保險公司").child(Companyuser).child("條件").child("雨騎")
                .addListenerForSingleValueEvent(listenerW);

        /*按一下跳到cCpage介面*/
        Button historybtn = (Button) findViewById(R.id.bkbtn);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cCustomerScore.this, cCustomerPage.class);
                startActivity(intent);
            }
        });
    }

    ValueEventListener listener_cMonth = new ValueEventListener() {
        @Override
        public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
            cmonths1.clear();
            for (DataSnapshot chilSnapshot:dataSnapshot.getChildren()){
                String spinnerMonthkey = chilSnapshot.getKey();
                cmonths1.add(spinnerMonthkey);}
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(cCustomerScore.this,android.R.layout.simple_spinner_item,cmonths1);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(spnOnItemSelected);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            uid = getIntent().getStringExtra("uid");
            reference = FirebaseDatabase.getInstance()
                    .getReference("PHYD");
            reference.child("騎士").child(uid).child("危險駕駛次數").child("本月").child(spinner.getSelectedItem().toString())
                    .addListenerForSingleValueEvent(listener_Month_num);
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    ValueEventListener listener_Month_num = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            TextView rScore_listtxt_km = findViewById(R.id.cScore_listtxt_km);
            TextView rScore_listtxt_r = findViewById(R.id.cScore_listtxt_r);
            TextView rScore_listtxt_f = findViewById(R.id.cScore_listtxt_f);
            TextView rScore_listtxt_h = findViewById(R.id.cScore_listtxt_h);
            TextView rScore_listtxt_n = findViewById(R.id.cScore_listtxt_n);
            TextView rScore_listtxt_w = findViewById(R.id.cScore_listtxt_w);

            int R = dataSnapshot.child("闖紅燈").getValue(int.class);
            int F = dataSnapshot.child("重油").getValue(int.class);
            int H = dataSnapshot.child("急煞").getValue(int.class);
            int N = dataSnapshot.child("夜騎").getValue(int.class);
            int W = dataSnapshot.child("雨騎").getValue(int.class);
            double KM = dataSnapshot.child("里程數").getValue(double.class);

            rScore_listtxt_km.setText(KM+"");
            rScore_listtxt_r.setText(R+"");
            rScore_listtxt_f.setText(F+"");
            rScore_listtxt_h.setText(H+"");
            rScore_listtxt_n.setText(N+"");
            rScore_listtxt_w.setText(W+"");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listenerR = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int r1 = dataSnapshot.child("是否採用").getValue(int.class);

            //如果有採用則顯示
            if(r1==1){
                listtxt_r因子.setVisibility(View.VISIBLE);
                listtxt_r.setVisibility(View.VISIBLE);
                TextView 分隔2 = findViewById(R.id.rScore_分隔2);
                分隔2.setVisibility(View.VISIBLE);
            }else {
                listtxt_r因子.setVisibility(View.GONE);
                listtxt_r.setVisibility(View.GONE);
                TextView 分隔2 = findViewById(R.id.rScore_分隔2);
                分隔2.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerK = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int k1 = dataSnapshot.child("是否採用").getValue(int.class);

            //如果有採用則顯示
            if(k1==1){
                listtxt_k因子.setVisibility(View.VISIBLE);
                listtxt_k.setVisibility(View.VISIBLE);
                TextView 分隔3 = findViewById(R.id.rScore_分隔1);
                分隔3.setVisibility(View.VISIBLE);
            }else {
                listtxt_k因子.setVisibility(View.GONE);
                listtxt_k.setVisibility(View.GONE);
                TextView 分隔3 = findViewById(R.id.rScore_分隔1);
                分隔3.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerF = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int f1 = dataSnapshot.child("是否採用").getValue(int.class);

            //如果有採用則顯示
            if(f1==1){
                listtxt_f因子.setVisibility(View.VISIBLE);
                listtxt_f.setVisibility(View.VISIBLE);
                TextView 分隔4 = findViewById(R.id.rScore_分隔3);
                分隔4.setVisibility(View.VISIBLE);
            }else {
                listtxt_f因子.setVisibility(View.GONE);
                listtxt_f.setVisibility(View.GONE);
                TextView 分隔4 = findViewById(R.id.rScore_分隔3);
                分隔4.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerH = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int h1 = dataSnapshot.child("是否採用").getValue(int.class);

            //如果有採用則顯示
            if(h1==1){
                listtxt_h因子.setVisibility(View.VISIBLE);
                listtxt_h.setVisibility(View.VISIBLE);
                TextView 分隔5 = findViewById(R.id.rScore_分隔4);
                分隔5.setVisibility(View.VISIBLE);
            }else {
                listtxt_h因子.setVisibility(View.GONE);
                listtxt_h.setVisibility(View.GONE);
                TextView 分隔5 = findViewById(R.id.rScore_分隔4);
                分隔5.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerN = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int n1 = dataSnapshot.child("是否採用").getValue(int.class);

            //如果有採用則顯示
            if(n1==1){
                listtxt_n因子.setVisibility(View.VISIBLE);
                listtxt_n.setVisibility(View.VISIBLE);
                TextView 分隔6 = findViewById(R.id.rScore_分隔5);
                分隔6.setVisibility(View.VISIBLE);
            }else {
                listtxt_n因子.setVisibility(View.GONE);
                listtxt_n.setVisibility(View.GONE);
                TextView 分隔6 = findViewById(R.id.rScore_分隔5);
                分隔6.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerW = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int w1 = dataSnapshot.child("是否採用").getValue(int.class);

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
            Toast.makeText(cCustomerScore.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}