package com.example.app01;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.List;

public class SearchscorePage extends AppCompatActivity {
    private Spinner spinner,spinner2;
    DatabaseReference databaseReference;
    List<String> months1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchscore_page);

        //顯示spinner
        spinner=findViewById(R.id.spmonth1);
        spinner2=findViewById(R.id.spmonth2);
        months1=new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("PHYD");
        databaseReference.child("騎士").child(uid).child("危險駕駛次數").child("本月")
                .addValueEventListener(listener_Month_key);
        databaseReference.child("騎士").child(uid).child("危險駕駛次數").child("本月")
                .addValueEventListener(listener_Month_key2);


//        /*按一下 sresultlist*/
//        sresultlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GlobalVariable gv = (GlobalVariable)getApplicationContext();
//                gv.setAll(1);
//                Intent intent = new Intent();
//                intent.setClass(SearchscorePage.this, rScorePage.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        /*按一下 查詢btn*/
        Button searchsbtn = (Button) findViewById(R.id.searchsbtn);
        searchsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                int intSearchScore1,intSearchScore2,startSearch,endSearch;
                String SearchScore1,SearchScore2;
                SearchScore1 = gv.getSearchScore1().substring(5, 7);
                SearchScore2 = gv.getSearchScore2().substring(5, 7);
                intSearchScore1 = Integer.parseInt(SearchScore1);
                intSearchScore2 = Integer.parseInt(SearchScore2);
                if(intSearchScore1 > intSearchScore2){
                    startSearch = intSearchScore2;
                    endSearch = intSearchScore1;
                }else {
                    startSearch = intSearchScore1;
                    endSearch = intSearchScore2;
                }
                String startString = "2020-"+startSearch;
                String endString = "2020-"+endSearch;

                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_SearchScore);
                new FirebaseDatabaseHelper().readSearchScore(startString,endString,new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<History1> history1s, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoaded2(List<History2> history2s, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedCom(List<Com> coms, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {
                        new RecyclerView_Config_SearchS().setConfig(mRecyclerView, SearchscorePage.this,
                                searchs1s, keys);
                    }
                });

//                int intA,intB,start,end;
//                String a = "2020-06";
//                a = a.substring(5, 7);
//                intA = Integer.parseInt(a);
//                String b = "2020-06";
//                b = b.substring(5, 7);
//                intB = Integer.parseInt(b);
//
//                if(intA > intB){
//                    end = intA;
//                    start = intB;
//                }else{
//                    end = intB;
//                    start = intA;
//                }
//



            }
        });

        /*按一下跳到歷史紀錄介面*/
        Button historybtn = (Button) findViewById(R.id.historybtn);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SearchscorePage.this, rHistoryPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到行車紀錄介面*/
        Button recordbtn = (Button) findViewById(R.id.recordbtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SearchscorePage.this, rRecordPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到首頁介面*/
        Button main1btn = (Button) findViewById(R.id.main1btn);
        main1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SearchscorePage.this, rMainPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到申訴介面*/
        Button questbtn = (Button) findViewById(R.id.questbtn);
        questbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SearchscorePage.this, rQuestPage.class);
                startActivity(intent);
            }
        });

        /*按一下回到本日危險駕駛統計(HistoryPage)介面*/
        Button sscorebackbtn = (Button) findViewById(R.id.scorebackbtn);
        sscorebackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SearchscorePage.this, rScorePage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    ValueEventListener listener_Month_key = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            months1.clear();
            for (DataSnapshot chilSnapshot:dataSnapshot.getChildren()){
                String spinnerMonthkey = chilSnapshot.getKey();
                months1.add(spinnerMonthkey);}
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(SearchscorePage.this,android.R.layout.simple_spinner_item,months1);
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
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setSearchScore1(spinner.getSelectedItem().toString());
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    ValueEventListener listener_Month_key2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            months1.clear();
            for (DataSnapshot chilSnapshot:dataSnapshot.getChildren()){
                String spinnerMonthkey = chilSnapshot.getKey();
                months1.add(spinnerMonthkey);}
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(SearchscorePage.this,android.R.layout.simple_spinner_item,months1);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner2.setAdapter(arrayAdapter);
            spinner2.setOnItemSelectedListener(spnOnItemSelected2);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private AdapterView.OnItemSelectedListener spnOnItemSelected2
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setSearchScore2(spinner2.getSelectedItem().toString());
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}