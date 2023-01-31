package com.example.app01;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper_C {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceComs,mReferenceComTs;
    private List<Com> coms = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoadedCom(List<Com> coms, List<String> keys);
    }

    public FirebaseDatabaseHelper_C() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceComs = mDatabase.getReference("PHYD").child("保險公司");
        mReferenceComTs = mDatabase.getReference("Title").child("cComPage");
    }

    public void readComTs(final FirebaseDatabaseHelper.DataStatus dataStatus){

        mReferenceComTs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
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

    public void readComs(String Companyuser,final FirebaseDatabaseHelper.DataStatus dataStatus){

        mReferenceComs.child(Companyuser).child("洽詢").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
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
}
