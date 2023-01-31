package com.example.app01;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class rScorePage extends AppCompatActivity {

    //宣告 雷達圖 RadarChart radarChart
    RadarChart radarChart;
    //宣告 雷達圖 x軸上的文字
    String[] labels = {"闖紅燈", "重油", "急煞", "夜騎", "雨騎","里程數"};
    private int color1 =R.color.red2;
    private int color2 =R.color.red1;
    private int color3 =R.color.blue2;
    private int color4 =R.color.blue1;
    private int tt =R.id.radar_chart;
    private DatabaseReference reference;
    private DatabaseReference mReferenceScoreWrite,mReferenceForCompany;
    private TextView tv1;
    private TextView tv2;
    private View mView;
    private Spinner mSpinner;
    private ArrayAdapter<String> adapter;
    private AlertDialog mBuilder;
    private String key,catch月份;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_score_page);

        final GlobalVariable gv = (GlobalVariable)getApplicationContext();
        TextView tv = findViewById(R.id.mscoretxt);

        key = getIntent().getStringExtra("key");
        catch月份=getIntent().getStringExtra("月份");

        if (catch月份==null) {
            //本月
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            Calendar cal = Calendar.getInstance();
            final CharSequence ym = DateFormat.format("yyyy-MM", cal.getTime());
            tv.setText(ym + "月   安全分數");
            reference = FirebaseDatabase.getInstance()
                    .getReference("PHYD");
            reference.child("騎士").child(uid).child("危險駕駛次數").child("本月").child(ym.toString())
                    .addListenerForSingleValueEvent(listener_Month_num);
            mReferenceScoreWrite = reference.child("騎士").child(uid).child("危險駕駛次數").child("本月").child(ym.toString());
        }else {
            //catch月份
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            tv.setText(catch月份 + "月   安全分數");
            reference = FirebaseDatabase.getInstance()
                    .getReference("PHYD");
            reference.child("騎士").child(uid).child("危險駕駛次數").child("本月").child(catch月份)
                    .addListenerForSingleValueEvent(listener_Month_num);
            mReferenceScoreWrite = reference.child("騎士").child(uid).child("危險駕駛次數").child("本月").child(catch月份);
        }

        /*按一下跳到歷史紀錄介面*/
        Button historybtn = (Button) findViewById(R.id.historybtn);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rScorePage.this, rHistoryPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到行車紀錄介面*/
        Button recordbtn = (Button) findViewById(R.id.recordbtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rScorePage.this, rRecordPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到首頁介面*/
        Button main1btn = (Button) findViewById(R.id.main1btn);
        main1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rScorePage.this, rMainPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到申訴介面*/
        Button questbtn = (Button) findViewById(R.id.questbtn);
        questbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rScorePage.this, rQuestPage.class);
                startActivity(intent);
            }
        });

        /*按一下回到本日危險駕駛統計(HistoryPage)介面*/
        Button rsearchbtn = (Button) findViewById(R.id.searchbtn);
        rsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                gv.setAll(0);
                Intent intent = new Intent();
                intent.setClass(rScorePage.this, SearchscorePage.class);
                startActivity(intent);
                finish();
            }
        });

        Button rctcbtn = (Button) findViewById(R.id.contactbtn);
        rctcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder = new AlertDialog.Builder(rScorePage.this).create();
                mView = getLayoutInflater().inflate(R.layout.ui_dialog, null);
                mBuilder.setCancelable(false);
                mSpinner = (Spinner) mView.findViewById(R.id.rspinnercom);
                adapter = new ArrayAdapter<String>(rScorePage.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.companies));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
//                mSpinner.setSelection(1);
                mSpinner.setOnItemSelectedListener(spnOnItemSelected3);

//                if(mSpinner.getSelectedItem().toString().contentEquals("00富邦產險")){
//                    TextView tv1 = mView.findViewById(R.id.timelong);
//                    TextView tv2 = mView.findViewById(R.id.callabel);
//                    tv1.setText("3 個月");
//                    tv2.setText("闖紅燈、重油、急煞、夜騎、雨騎、里程數");
//                }
//                if(mSpinner.getSelectedItem().toString().contentEquals("01南山產險")){
//                     TextView tv1 = mView.findViewById(R.id.timelong);
//                     TextView tv2 = mView.findViewById(R.id.callabel);
//                    tv1.setText("6 個月");
//                    tv2.setText("闖紅燈、重油、急煞、夜騎");
//                }
//                if(mSpinner.getSelectedItem().toString().contentEquals("02國泰產險")){
//                     TextView tv1 = mView.findViewById(R.id.timelong);
//                     TextView tv2 = mView.findViewById(R.id.callabel);
//                    tv1.setText("6 個月");
//                    tv2.setText("闖紅燈、重油、急煞、里程數");
//                }

                Button calc = mView.findViewById(R.id.rcaculate);
                Button cancel = mView.findViewById(R.id.rcancel);
                calc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(rScorePage.this, mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                        mBuilder.dismiss();
                        mBuilder.setCancelable(false);
                        mBuilder.setCanceledOnTouchOutside(false);

                        final AlertDialog mBuilder2 = new AlertDialog.Builder(rScorePage.this).create();
                        View mView2 = getLayoutInflater().inflate(R.layout.cal_dialog, null);
                        Button recalc = mView2.findViewById(R.id.recal);
                        Button fin = mView2.findViewById(R.id.rfinish);
                        Button rctc12 = mView2.findViewById(R.id.rctcbtn);
                        TextView tv02 = mView2.findViewById(R.id.callabel2);
                        //判斷資格
                        GlobalVariable gv = (GlobalVariable)getApplicationContext();
                        String Time符合,r符合,k符合,f符合,h符合,n符合,w符合;
                        //Toast.makeText(rScorePage.this,tv1.getText().toString(),Toast.LENGTH_SHORT).show();
                        if(tv1.getText().toString()=="1個月"){
                            tv02.setText("符合優惠條件資格");
                        }else{
                            tv02.setText("不符合優惠條件資格\n 原因:不符合資料時程長度");
                        }
                        recalc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBuilder2.dismiss();
                                mBuilder.setView(mView);
                                mBuilder.show();
                            }
                        });
                        fin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) { mBuilder2.dismiss(); }
                        });
                        rctc12.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mBuilder2.dismiss();
                                final AlertDialog mBuilder3 = new AlertDialog.Builder(rScorePage.this).create();
                                View mView3 = getLayoutInflater().inflate(R.layout.cal_dialog2, null);
                                Button disagree = mView3.findViewById(R.id.rfinish2);
                                Button agree = mView3.findViewById(R.id.rctcbtn2);
                                disagree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mBuilder3.dismiss();
                                    }
                                });
                                agree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mBuilder3.dismiss();
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = user.getUid();
                                        reference.child("騎士").child(uid).addListenerForSingleValueEvent(listener);

                                        Toast.makeText(rScorePage.this, "已授權駕駛資料給保險公司",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mBuilder3.setCancelable(false);
                                mBuilder3.setCanceledOnTouchOutside(false);
                                mBuilder3.setView(mView3);
                                mBuilder3.show();
                            }
                        });
                        mBuilder2.setCancelable(false);
                        mBuilder2.setCanceledOnTouchOutside(false);
                        mBuilder2.setView(mView2);
                        mBuilder2.show();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBuilder.dismiss();
                        mBuilder.setCancelable(false);
                        mBuilder.setCanceledOnTouchOutside(false);
                    }
                });
                mBuilder.setView(mView);
                mBuilder.show();
            }
        });

    }

    private AdapterView.OnItemSelectedListener spnOnItemSelected3
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setCom洽談r("");gv.setCom洽談km("");gv.setCom洽談f("");gv.setCom洽談h("");gv.setCom洽談n("");gv.setCom洽談w("");
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString())
                    .addListenerForSingleValueEvent(listener2);
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString()).child("條件").child("闖紅燈")
                    .addListenerForSingleValueEvent(listenerR);
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString()).child("條件").child("里程數")
                    .addListenerForSingleValueEvent(listenerK);
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString()).child("條件").child("重油")
                    .addListenerForSingleValueEvent(listenerF);
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString()).child("條件").child("急煞")
                    .addListenerForSingleValueEvent(listenerH);
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString()).child("條件").child("夜騎")
                    .addListenerForSingleValueEvent(listenerN);
            reference.child("保險公司").child(mSpinner.getSelectedItem().toString()).child("條件").child("雨騎")
                    .addListenerForSingleValueEvent(listenerW);
            gv.setCom洽談(mSpinner.getSelectedItem().toString());

            //可以偵測到選了什麼，問題在setText
//            if(pos == 0){
//                tv2 = mView.findViewById(R.id.callabel);
//                tv2.setText("闖紅燈、重油、急煞、夜騎、雨騎、里程數");
//            }
//            if(pos == 1){
//                tv2 = mView.findViewById(R.id.callabel);
//                tv2.setText("闖紅燈、重油、急煞、夜騎");
//            }
//            if(pos == 2){
//                tv2 = mView.findViewById(R.id.callabel);
//                tv2.setText("闖紅燈、重油、急煞、里程數");
//            }
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    ValueEventListener listenerR = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int r1 = dataSnapshot.child("是否採用").getValue(int.class);
            //如果有採用則顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if(r1==1){
                gv.setCom洽談r("闖紅燈");
            }else {
                gv.setCom洽談r("");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerK = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int k1 = dataSnapshot.child("是否採用").getValue(int.class);
            //如果有採用則顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if(k1==1){
                gv.setCom洽談km("里程數");
            }else {
                gv.setCom洽談km("");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerF = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int f1 = dataSnapshot.child("是否採用").getValue(int.class);
            //如果有採用則顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if(f1==1){
                gv.setCom洽談f("重油");
            }else {
                gv.setCom洽談f("");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerH = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int h1 = dataSnapshot.child("是否採用").getValue(int.class);
            //如果有採用則顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if(h1==1){
                gv.setCom洽談h("急煞");
            }else {
                gv.setCom洽談h("");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerN = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int n1 = dataSnapshot.child("是否採用").getValue(int.class);
            //如果有採用則顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if(n1==1){
                gv.setCom洽談n("夜騎");
            }else {
                gv.setCom洽談n("");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener listenerW = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //firebase讀取
            int w1 = dataSnapshot.child("是否採用").getValue(int.class);
            //如果有採用則顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if(w1==1){
                gv.setCom洽談w("雨騎");
            }else {
                gv.setCom洽談w("");
            }
            tv2 = mView.findViewById(R.id.callabel);
            tv2.setText(gv.getCom洽談r()+" "+gv.getCom洽談km()+" "+gv.getCom洽談f()+" "+gv.getCom洽談h()+" "+gv.getCom洽談n()+" "+gv.getCom洽談w());
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    //洽談讀保險公司時程長度
    ValueEventListener listener2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            String ct = dataSnapshot.child("時程")
                    .getValue(String.class);
            tv1 = mView.findViewById(R.id.timelong);
            tv2 = mView.findViewById(R.id.callabel);
            tv1.setText(ct);
            gv.setCom洽談Time(ct);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    //把當月危險行為設入表格、執行圖表
    ValueEventListener listener_Month_num = new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        TextView rScore_listtxt_km = findViewById(R.id.rScore_listtxt_km);
        TextView rScore_listtxt_r = findViewById(R.id.rScore_listtxt_r);
        TextView rScore_listtxt_f = findViewById(R.id.rScore_listtxt_f);
        TextView rScore_listtxt_h = findViewById(R.id.rScore_listtxt_h);
        TextView rScore_listtxt_n = findViewById(R.id.rScore_listtxt_n);
        TextView rScore_listtxt_w = findViewById(R.id.rScore_listtxt_w);
        TextView textView_Score = findViewById(R.id.textView_Score);

        int R = dataSnapshot.child("闖紅燈").getValue(int.class);
        int F = dataSnapshot.child("重油").getValue(int.class);
        int H = dataSnapshot.child("急煞").getValue(int.class);
        int N = dataSnapshot.child("夜騎").getValue(int.class);
        int W = dataSnapshot.child("雨騎").getValue(int.class);
        double KM = dataSnapshot.child("里程數").getValue(double.class);
        int KMS,S;
        if(KM>200){
            KMS= (int) ((KM-200)/5);
            int r = 100-R*2;
            int k = 100-KMS*1;
            int f = 100-F*2;
            int h = 100-H*2;
            int n = 100-N*2;
            int w = 100-W*2;
            if(r<0){r=0;}
            if(k<0){k=0;}
            if(h<0){h=0;}
            if(f<0){f=0;}
            if(n<0){n=0;}
            if(w<0){w=0;}
            S = r+k+f+n+h+w;
        }else {
            KMS= 0;
            int r = 100-R*2;
            int k = 100-KMS*1;
            int f = 100-F*2;
            int h = 100-H*2;
            int n = 100-N*2;
            int w = 100-W*2;
            if(r<0){r=0;}
            if(k<0){k=0;}
            if(h<0){h=0;}
            if(f<0){f=0;}
            if(n<0){n=0;}
            if(w<0){w=0;}
            S = r+k+f+n+h+w;
        }



        mReferenceScoreWrite.child("安全分數").setValue(S);

        rScore_listtxt_km.setText(KM+"");
        rScore_listtxt_r.setText(R+"");
        rScore_listtxt_f.setText(F+"");
        rScore_listtxt_h.setText(H+"");
        rScore_listtxt_n.setText(N+"");
        rScore_listtxt_w.setText(W+"");
        textView_Score.setText(S+"");

        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        gv.setrScore_R(R);
        gv.setrScore_F(F);
        gv.setrScore_H(H);
        gv.setrScore_N(N);
        gv.setrScore_W(W);
        gv.setrScore_KM(KMS);

        //宣告 radarChart -> id.radar_chart
        radarChart = findViewById(tt);
        chartshow( R, F, H, N, W, KMS);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
    }
};

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String name = dataSnapshot.child("username")
                    .getValue(String.class);
            String email = dataSnapshot.child("email")
                    .getValue(String.class);
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            Calendar cal = Calendar.getInstance();
            final CharSequence ymd = DateFormat.format("yyyy-MM-d", cal.getTime());
            String com = gv.getCom洽談();
            mReferenceForCompany = reference.child("保險公司").child(com).child("洽詢");
//          String key = mReferenceForCompany.push().getKey();
            HashMap<String,Object> comUid = new HashMap<>();
            comUid.put("年月日",ymd);
            comUid.put("uid",uid);
            comUid.put("是否已聯絡",0);
            comUid.put("username",name);
            comUid.put("email",email);
            mReferenceForCompany.child(uid).setValue(comUid);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rScorePage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void chartshow(int R,int F,int H,int N,int W,int KMS){
        //宣告 雷達圖 dataSet1 -> 本人
        RadarDataSet dataSet1 = new RadarDataSet(dataValues1( R, F, H, N, W, KMS),"本人");
        //宣告 雷達圖 dataSet2 -> 其他用戶
        RadarDataSet dataSet2 = new RadarDataSet(dataValues2(),"其他用戶");
        //設定圖表的描述文字，會顯示在圖表的右下角
        radarChart.setDescription(null);

        /*chart為空時，顯示的文字 部分*/
        //設定當chart為空時，顯示的描述文字
        radarChart.setNoDataText("No Data");
        //描述文字的顏色
        radarChart.setNoDataTextColor(Color.BLUE);

        //設定 雷達圖是否繪製多邊形網格(網格背景)
        radarChart.setDrawWeb(true);

        //宣告 雷達圖 dataSet1線條顏色
        dataSet1.setColor(getResources().getColor(color1));
        //宣告 雷達圖 dataSet1線條粗細
        dataSet1.setLineWidth(3);
        //宣告 雷達圖 dataSet1是否填滿顏色
        dataSet1.setDrawFilled(true);
        //宣告 雷達圖 dataSet1填滿顏色
        dataSet1.setFillColor(getResources().getColor(color2));
        //設定 雷達圖 dataSet1填滿顏色alpha值（即透明度 0-255）。默認：85【255 =完全不透明，0 =完全透明】
        dataSet1.setFillAlpha(120);
        //宣告 雷達圖 dataSet2線條顏色
        dataSet2.setColor(getResources().getColor(color3));
        //宣告 雷達圖 dataSet2線條粗細
        dataSet2.setLineWidth(3);
        //宣告 雷達圖 dataSet2是否填滿顏色
        dataSet2.setDrawFilled(true);
        //宣告 雷達圖 dataSet2填滿顏色
        dataSet2.setFillColor(getResources().getColor(color4));
        //設定 雷達圖 dataSet2填滿顏色alpha值（即透明度 0-255）。默認：85【255 =完全不透明，0 =完全透明】
        dataSet2.setFillAlpha(50);

        // 啟用/禁用與圖表的所有可能的觸控互動
        radarChart.setTouchEnabled(false);
        // 啟用/禁用滾動圖表
        radarChart.setDragDecelerationEnabled(false);

        //是否繪製 圖例標籤
        Legend legend = radarChart.getLegend();
        legend.setEnabled(false);
        //設定圖例標籤的文字顏色 legend.setTextColor(int color); 。
        //設定圖例標籤的文字大小
//        legend.setTextSize(10f);
//        //設定圖例標籤文字方向
//        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//        legend.setDrawInside(true);
//        //legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        //legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//
//        //設定在水平軸上 legend-entries 的間隔
//        legend.setXEntrySpace(10f);
//        //設定在垂直軸上 legend-entries 的間隔
//        legend.setYEntrySpace(14f);
//        //設定 legend-form(圖例形狀) 和 legend-label(圖例文字) 之間的間隔
//        legend.setFormToTextSpace(4f) ;
//        //legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);


        RadarData data = new RadarData();
        data.addDataSet(dataSet1);
        data.addDataSet(dataSet2);
        dataSet1.setValueFormatter(new AxisFormatter());
        dataSet1.setDrawValues(false);
        dataSet1.setValueTextColor(Color.BLACK);
        dataSet1.setValueTextSize(14f);
        dataSet2.setDrawValues(false);
        dataSet2.setValueFormatter(new AxisFormatter());
        dataSet2.setValueTextColor(Color.BLACK);
        dataSet2.setValueTextSize(12f);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTextSize(12f);
        xAxis.setXOffset(2);
        xAxis.setYOffset(2);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        YAxis yAxis = radarChart.getYAxis(); // this method radarchart only
        yAxis.setEnabled(true);
        yAxis.setDrawLabels(true);
        yAxis.setDrawZeroLine(false);
        yAxis.setDrawTopYLabelEntry(true);
        yAxis.setGranularity(20f);
        radarChart.getYAxis().setAxisMaximum(80f);
        radarChart.getYAxis().setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(5);
//        yAxis.setGranularity(20f);
//        radarChart.getYAxis().setAxisMaximum(60f);
//        radarChart.getYAxis().setAxisMinimum(0f);
//        yAxis.setAxisMaximum(60f);
//        yAxis.setAxisMinimum(0f);
//        yAxis.setLabelCount(5);

        radarChart.setData(data);
        radarChart.invalidate();
    }

    private ArrayList<RadarEntry> dataValues1(int R,int F,int H,int N,int W,int KMS){
        int r = 100-R*2;
        int k = 100-KMS*1;
        int f = 100-F*2;
        int h = 100-H*2;
        int n = 100-N*2;
        int w = 100-W*2;
        if(r<0){r=0;}
        if(k<0){k=0;}
        if(h<0){h=0;}
        if(f<0){f=0;}
        if(n<0){n=0;}
        if(w<0){w=0;}
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        dataVals.add(new RadarEntry(r));
        dataVals.add(new RadarEntry(f));
        dataVals.add(new RadarEntry(h));
        dataVals.add(new RadarEntry(n));
        dataVals.add(new RadarEntry(w));
        dataVals.add(new RadarEntry(k));
        return  dataVals;
    }

    private ArrayList<RadarEntry> dataValues2(){
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        dataVals.add(new RadarEntry(80));
        dataVals.add(new RadarEntry(76));
        dataVals.add(new RadarEntry(88));
        dataVals.add(new RadarEntry(86));
        dataVals.add(new RadarEntry(92));
        dataVals.add(new RadarEntry(91));
        return  dataVals;
    }

    //宣告 各項數值格式 = 數值
    private class AxisFormatter extends ValueFormatter {
        public String getFormattedValue(float value) {
            int v = (int)value;
            return String.valueOf(v);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}