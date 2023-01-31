package com.example.app01;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class cCustomerPage extends AppCompatActivity {

    private RecyclerView mRecyclerViewT,mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_customer_page);
        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        String Companyuser = gv.getCompanyuser();

        //顯示list_readComsT
        mRecyclerViewT = (RecyclerView) findViewById(R.id.recyclerview_comTitle);
        new FirebaseDatabaseHelper_C().readComTs(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<History1> history1s, List<String> keys) {

            }

            @Override
            public void DataIsLoaded2(List<History2> history2s, List<String> keys) {

            }

            @Override
            public void DataIsLoadedCom(List<Com> coms, List<String> keys) {
                new RecyclerView_Config_ComT().setConfig(mRecyclerViewT, cCustomerPage.this,
                        coms, keys);
            }

            @Override
            public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {

            }
        });


        //顯示list_readComs
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_com);
        new FirebaseDatabaseHelper_C().readComs(Companyuser,new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<History1> history1s, List<String> keys) {

            }

            @Override
            public void DataIsLoaded2(List<History2> history2s, List<String> keys) {

            }

            @Override
            public void DataIsLoadedCom(List<Com> coms, List<String> keys) {
                new RecyclerView_Config_Com().setConfig(mRecyclerView, cCustomerPage.this,
                        coms, keys);
            }

            @Override
            public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {

            }
        });


        /*按一下返回回到首頁*/
        Button backbtn = (Button) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cCustomerPage.this, cMainPage.class);
                startActivity(intent);
                finish();
            }
        });
        /*按一下登出回到首頁介面*/
        Button clogoutbtn = (Button) findViewById(R.id.clogoutbtn);
        clogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(cCustomerPage.this, MainActivity.class);
                startActivity(intent);
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