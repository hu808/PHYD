package com.example.app01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class rQuestPage extends AppCompatActivity {
    private int a=0,b=0,c=0,ok=0;
    private int mYear, mMonth, mDay;
    private RecyclerView mRecyclerView,mRecyclerView2;
    private DatabaseReference reference;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    List<String> rQ1;
    List<String> keys;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    Calendar cal = Calendar.getInstance();
    final CharSequence ymd = DateFormat.format("yyyy-MM-d", cal.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_quest_page);

        //顯示今日spinner
        spinner=findViewById(R.id.spinner_Q);
        rQ1=new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        final CharSequence ymd = DateFormat.format("yyyy-MM-d", cal.getTime());
        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        gv.setrQday(ymd.toString());
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("PHYD");
        databaseReference.child("騎士").child(uid).child("危險駕駛紀錄").orderByChild("年月日").equalTo(ymd.toString())
                .addValueEventListener(listener_Q1);

        /*按一下 顯示行事曆*/
        final TextView ymddate = (TextView)findViewById(R.id.ymddate2);
        ymddate.setText(ymd);
        ymddate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(rQuestPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        ymddate.setText(format);
                        GlobalVariable gv = (GlobalVariable)getApplicationContext();
                        gv.setrQuestY(year);
                        gv.setrQuestM(month);
                        gv.setrQuestD(day);
                        int month1=month+1;
                        //按一下顯示今日時段
                        String a = String.valueOf(year+"-"+month1+"-"+day);

                        spinner=findViewById(R.id.spinner_Q);
                        rQ1=new ArrayList<>();
                        gv.setrQday(a);
                        databaseReference = FirebaseDatabase.getInstance()
                                .getReference("PHYD");
                        databaseReference.child("騎士").child(uid).child("危險駕駛紀錄").orderByChild("年月日").equalTo(a)
                                .addValueEventListener(listener_Q1);

                    }
                }, mYear,mMonth, mDay).show();
            }
        });

        /*按一下submitbtn*/
        Button submitbtn = (Button) findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                if(gv.getrQcount()==0){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(rQuestPage.this);
                    alertDialog.setTitle("申訴");
                    alertDialog.setMessage("請選擇申訴內容!!");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }else {
                    //Toast.makeText(rQuestPage.this,gv.getQuests()+"",Toast.LENGTH_SHORT).show();
                    reference=FirebaseDatabase.getInstance().getReference("Quest");
                    Calendar cal = Calendar.getInstance();
                    final CharSequence s = DateFormat.format("yyyy-MM-d kk:mm:ss", cal.getTime());
                    reference.child(uid).child(gv.getrQday()).child("申訴時間: "+s.toString()).setValue(gv.getQuests());
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(rQuestPage.this);
                    alertDialog.setTitle("申訴");
                    alertDialog.setMessage("已為您進行申訴，審查後將透過信箱聯繫您!!");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(rQuestPage.this, rMainPage.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            }
        });

        /*按一下跳到歷史紀錄介面*/
        Button historybtn = (Button) findViewById(R.id.historybtn);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rQuestPage.this, rHistoryPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到行車紀錄介面*/
        Button recordbtn = (Button) findViewById(R.id.recordbtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rQuestPage.this, rRecordPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到首頁介面*/
        Button main1btn = (Button) findViewById(R.id.main1btn);
        main1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rQuestPage.this, rMainPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到安全分數介面*/
        Button scorebtn = (Button) findViewById(R.id.scorebtn);
        scorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rQuestPage.this, rScorePage.class);
                startActivity(intent);
            }
        });
    }

    ValueEventListener listener_Q1 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            rQ1.clear();
            keys = new ArrayList<>();
            for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                keys.add(keyNode.getKey());
                History1 history1 = keyNode.getValue(History1.class);
                rQ1.add(history1.get開始時間()+"-"+history1.get結束時間());
            }
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(rQuestPage.this,android.R.layout.simple_spinner_item,rQ1);
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
            String k = keys.get(pos);
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_Quest2);
            new FirebaseDatabaseHelper().readHistory2s(k,new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<History1> history1s, List<String> keys) {

                }

                @Override
                public void DataIsLoaded2(List<History2> history2s, List<String> keys) {
                    new RecyclerView_Config_Quest1().setConfig(mRecyclerView, rQuestPage.this,
                            history2s, keys);
                }

                @Override
                public void DataIsLoadedCom(List<Com> coms, List<String> keys) {

                }

                @Override
                public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {

                }
            });
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}