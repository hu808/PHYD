package com.example.app01;

import android.text.format.DateFormat;

import androidx.annotation.NonNull;

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

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceHistory1s,mReferenceComs,mReferenceSearchScore;
    private List<History1> history1s = new ArrayList<>();
    private List<History2> history2s = new ArrayList<>();
    private List<Com> coms = new ArrayList<>();
    private List<SearchS1> searchs1s = new ArrayList<>();
    private List<History2> Qs = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<History1> history1s, List<String> keys);
        void DataIsLoaded2(List<History2> history2s, List<String> keys);
        void DataIsLoadedCom(List<Com> coms, List<String> keys);
        void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys);

    }
    public FirebaseDatabaseHelper() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceHistory1s = mDatabase.getReference("PHYD").child("騎士").child(uid).child("危險駕駛紀錄");
        mReferenceComs = mDatabase.getReference("PHYD").child("保險公司");
        mReferenceSearchScore = mDatabase.getReference("PHYD").child("騎士").child(uid).child("危險駕駛次數").child("本月");
    }

    public void readHistory1s(final DataStatus dataStatus){

        Calendar cal = Calendar.getInstance();
        final CharSequence ymd = DateFormat.format("yyyy-MM-d", cal.getTime());
        final CharSequence y = DateFormat.format("yyyy", cal.getTime());
        final CharSequence m = DateFormat.format("MM", cal.getTime());
        final CharSequence d = DateFormat.format("d", cal.getTime());
        mReferenceHistory1s.orderByChild("年月日").equalTo(ymd.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                history1s.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    History1 history1 = keyNode.getValue(History1.class);
                    history1s.add(history1);
                }
                dataStatus.DataIsLoaded(history1s,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readComs(final DataStatus dataStatus){

        mReferenceComs.child("00富邦產險").child("洽詢").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coms.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Com com = keyNode.getValue(Com.class);
                    coms.add(com);
                }
                dataStatus.DataIsLoadedCom(coms,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readHistory1scatch(String a,final DataStatus dataStatus){

        mReferenceHistory1s.orderByChild("年月日").equalTo(a).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                history1s.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    History1 history1 = keyNode.getValue(History1.class);
                    history1s.add(history1);
                }
                dataStatus.DataIsLoaded(history1s,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readSearchScore(String a,String b,final DataStatus dataStatus){

        mReferenceSearchScore.orderByChild("月份").startAt(a).endAt(b).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchs1s.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    SearchS1 searchs1 = keyNode.getValue(SearchS1.class);
                    searchs1s.add(searchs1);
                }
                dataStatus.DataIsLoadedSrarchS(searchs1s,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readHistory2s(final String key, final DataStatus dataStatus){

        mReferenceHistory1s.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                history2s.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.child("危險紀錄").getChildren()){
                    keys.add(keyNode.getKey());
                    History2 history2 = keyNode.getValue(History2.class);
                    history2s.add(history2);
                }
                dataStatus.DataIsLoaded2(history2s,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}