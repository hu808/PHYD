package com.example.app01;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

public class rHistoryPage extends AppCompatActivity {

    //宣告 長條圖 BarChart barChart
    BarChart barChart;
    ArrayList<String> labels = new ArrayList<>();
    String[] Dangerous = { "闖紅燈", "重油", "急煞", "夜騎", "雨騎" };
    private int mYear, mMonth, mDay;
    private RecyclerView mRecyclerView;
    private DatabaseReference reference;
    private int colorG = R.color.gray1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_history_page);

        //顯示list_history1
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_history1);
        new FirebaseDatabaseHelper().readHistory1s(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<History1> history1s, List<String> keys) {
                new RecyclerView_Config_History1().setConfig(mRecyclerView, rHistoryPage.this,
                        history1s, keys);
            }

            @Override
            public void DataIsLoaded2(List<History2> history2s, List<String> keys) {

            }

            @Override
            public void DataIsLoadedCom(List<Com> coms, List<String> keys) {

            }

            @Override
            public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {

            }
        });

        /*按一下 顯示行事曆*/
        final TextView ymddate = (TextView)findViewById(R.id.ymddate);
        Calendar cal = Calendar.getInstance();
        final CharSequence ymd = DateFormat.format("yyyy-MM-d", cal.getTime());
        ymddate.setText(ymd);
        ymddate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(rHistoryPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        ymddate.setText(format);
                        GlobalVariable gv = (GlobalVariable)getApplicationContext();
                        gv.setHis1Y(year);
                        gv.setHis1M(month);
                        gv.setHis1D(day);
                    }
                }, mYear,mMonth, mDay).show();
            }
        });

        //監聽本日次數
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");
        reference.child("騎士").child(uid).child("危險駕駛次數").child("本日").child(ymd.toString())
                .addListenerForSingleValueEvent(listener_DAY_num);

        /*按一下send*/
        Button sendbtn = (Button) findViewById(R.id.sendbtn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
//                int gg=1;
//                gv.setGg(gg);
//                BarDataSet barDataSet2 = new BarDataSet(dataValues2(),"DataSet 2");
//                BarData barData = new BarData();
//                //宣告 barDataSet1 的 addDataSet
//                barData.addDataSet(barDataSet2);
//                barData.setBarWidth((float) 0.7);
//
//                //顯示圖表
//                barChart.setData(barData);
//                //在chart中呼叫會使其重新整理重繪
//                barChart.invalidate();

                mYear = gv.getHis1Y();
                mMonth=gv.getHis1M()+1;
                mDay=gv.getHis1D();
                //測試是否讀到Calendar的資料
//                final TextView datetxt = (TextView)findViewById(R.id.datetxt);
//                datetxt.setText(String.valueOf(mYear+"-"+mMonth+"-"+mDay));
                String a = String.valueOf(mYear+"-"+mMonth+"-"+mDay);
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_history1);
                new FirebaseDatabaseHelper().readHistory1scatch(a,new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<History1> history1s, List<String> keys) {
                        new RecyclerView_Config_History1().setConfig(mRecyclerView, rHistoryPage.this,
                                history1s, keys);
                    }

                    @Override
                    public void DataIsLoaded2(List<History2> history2s, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedCom(List<Com> coms, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {

                    }
                });
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                reference = FirebaseDatabase.getInstance()
                        .getReference("PHYD");
                reference.child("騎士").child(uid).child("危險駕駛次數").child("本日").child(a)
                        .addListenerForSingleValueEvent(listener_DAY_num);
            }
        });

        /*按一下跳到行車紀錄介面*/
        Button recordbtn = (Button) findViewById(R.id.recordbtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage.this, rRecordPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到首頁介面*/
        Button main1btn = (Button) findViewById(R.id.main1btn);
        main1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage.this, rMainPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到安全分數介面*/
        Button scorebtn = (Button) findViewById(R.id.scorebtn);
        scorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage.this, rScorePage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到申訴介面*/
        Button questbtn = (Button) findViewById(R.id.questbtn);
        questbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage.this, rQuestPage.class);
                startActivity(intent);
            }
        });

        //宣告 barChart -> id.mp_BarChart
        barChart=findViewById(R.id.mp_BarChart);

    }
    ValueEventListener listener_DAY_num = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            try {
                int R = dataSnapshot.child("闖紅燈").getValue(int.class);
                int F = dataSnapshot.child("重油").getValue(int.class);
                int H = dataSnapshot.child("急煞").getValue(int.class);
                int N = dataSnapshot.child("夜騎").getValue(int.class);
                int W = dataSnapshot.child("雨騎").getValue(int.class);

                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                gv.setHis1_R(R);
                gv.setHis1_F(F);
                gv.setHis1_H(H);
                gv.setHis1_N(N);
                gv.setHis1_W(W);
                chart(R,F,H,N,W);
            }catch(Exception e){
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                chart(0,0,0,0,0);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rHistoryPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void chart(int R,int F,int H,int N,int W){
        //宣告 長條圖 barDataSet1 -> DataSet 1
        BarDataSet barDataSet1 = new BarDataSet(dataValues1(R,F,H,N,W),"DataSet 1");
        /*chart整體的顏色 部分【內訂好的】*/
        /*  MPAndroidChart程式已經自訂好的顏色【ex: barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);】
            ColorTemplate.LIBERTY_COLORS (藍色系列)
            ColorTemplate.COLORFUL_COLORS (彩色:暗紅色、橘色、黃色、墨綠色、淺棕色)
            ColorTemplate.JOYFUL_COLORS (彩色:淺粉色、淺橘色、淺黃色、綠色、淺藍色)
            ColorTemplate.PASTEL_COLORS (彩色:深藍色、墨綠色、深膚色、深粉色、暗紅色)
            ColorTemplate.VORDIPLOM_COLORS (彩色:亮綠色、亮黃色、亮橘色、亮藍色、亮粉色) */
        //宣告 長條圖 barDataSet1顏色
        barDataSet1.setColors(ColorTemplate.LIBERTY_COLORS);

        //設定圖表的描述文字，會顯示在圖表的右下角
        barChart.setDescription(null);
        //barChart.setDescriptionColor(int color); 設定描述文字的顏色
        //barChart.setDescriptionPosition(float x, float y); 自定義描述文字在螢幕上的位置（單位是畫素）
        //barChart.setDescriptionTypeface(Typeface t); 設定描述文字的 Typeface
        //barChart.setDescriptionTextSize(float size); 設定以畫素為單位的描述文字，最小6f，最大16f

        /*chart為空時，顯示的文字 部分*/
        //設定當chart為空時，顯示的描述文字
        barChart.setNoDataText("No Data");
        //描述文字的顏色
        barChart.setNoDataTextColor(Color.BLUE);

        /*網格背景 部分*/
        //如果啟用，chart繪圖區後面的背景矩形將繪製(網格背景)
        barChart.setDrawGridBackground(true);
        //設定網格背景應與繪製的顏色(自訂在colors.xml裡)
        barChart.setGridBackgroundColor(getResources().getColor(colorG));

        /*圖表邊框 部分*/
        // 啟用/禁用繪製圖表邊框（chart周圍的線）
        barChart.setDrawBorders(true);
        //邊框線的顏色
        barChart.setBorderColor(Color.GRAY);
        //邊界線的寬度，單位 dp。 barChart.setBorderWidth(2);

        /*圖表互動 部分*/
        // 啟用/禁用與圖表的所有可能的觸控互動
        barChart.setTouchEnabled(false);
        // 啟用/禁用拖動（平移）圖表
        barChart.setDragEnabled(false);
        // 啟用/禁用縮放圖表上的兩個軸
        barChart.setScaleEnabled(false);
        // 啟用/禁用縮放在x軸上 ; setScaleYEnabled(false); 啟用/禁用縮放在y軸。
        barChart.setScaleXEnabled(false);
        //如果設定為true，捏縮放功能。 如果false，x軸和y軸可分別放大
        barChart.setPinchZoom(false);
        //設定為false以禁止通過在其上雙擊縮放圖表
        barChart.setDoubleTapToZoomEnabled(false);
        //設定為true，允許每個圖表表面拖過，當它完全縮小突出。 barChart.setHighlightPerDragEnabled(false);
        //設定為false，以防止值由敲擊姿態被突出顯示。值仍然可以通過拖動或程式設計方式突出顯示。 barChart.setHighlightPerTapEnabled(false);

        //設定X軸
        XAxis xAxis = barChart.getXAxis();
        //設定X軸出現的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //設定字型 xAxis.setTypeface(mTf);
        //顯示X軸線條【會影響:X軸(AxisLine)、圖表內豎的線(GridLine) 的繪製】
        xAxis.setEnabled(true);
        //繪製X軸 (AxisLine)
        xAxis.setDrawAxisLine(true);
        //設定軸線的軸的顏色 xAxis.setAxisLineColor(Color.GREEN);
        //設定該軸軸行的寬度(float width) xAxis.setAxisLineWidth(5f);
        //繪製圖表內"豎的線"（與X軸有關）
        xAxis.setDrawGridLines(false);
        //設定該軸的網格線顏色 xAxis.setGridColor(Color.RED);
        //設定該軸網格線的寬度(float width) xAxis.setGridLineWidth(10f);
        // 啟用/禁用繪製軸的標籤
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Dangerous));
        //設定軸標籤的顏色
        xAxis.setTextColor(Color.BLACK);
        //設定軸標籤的文字大小
        xAxis.setTextSize(11f);
        //設定標籤字元間的空隙，預設間隔是4(float size) xAxis.setSpaceMin(3);
        //設定x軸間隔最小刻度，避免標籤的迅速增多
        xAxis.setGranularity(1f);
        //設定標籤居中 xAxis.setCenterAxisLabels(true);

        //設定y軸 【左:leftAxis ;右:rightAxis】
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        //從y軸發出橫向直線
        leftAxis.setDrawGridLines(true);
        //顯示y軸線條 【左:leftAxis ;右:rightAxis(不顯示)】
        leftAxis.setEnabled(true);
        rightAxis.setEnabled(false);
        //y軸最小值是0
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextSize(12f);
        //設定圖表中的最高值的頂部間距 佔最高值的值的百分比（設定的百分比 = 最高柱頂部間距/最高柱的值）。預設值是10f，即10% 。
        //leftAxis.setSpaceTop(float percent);
        //在執行時，使用 public AxisDependency getAxisDependency() 方法以確定此軸表示圖表的側面。
        //YAxis leftAxis = barChart.getAxis(YAxis.AxisDependency.LEFT);

        //是否在點上繪製 Value
        barDataSet1.setDrawValues(true);
        barDataSet1.setValueTextColor(Color.BLACK);
        barDataSet1.setValueTextSize(12f);
        barDataSet1.setValueFormatter(new AxisFormatter());

        //是否繪製 圖例
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        //設定圖例標籤的顏色 legend.setTextColor(int color); 。
        //設定圖例標籤的文字大小 legend.setTextSize(float size);

        //啟用網格線的虛線模式中得出，比如像這樣 “ - - - - - - ”【“lineLength”控制虛線段的長度;“spaceLength”控制線之間的空間;“phase”controls the starting point】
        //xAxis.enableGridDashedLine(float lineLength, float spaceLength, float phase);

        //給該軸新增一個新的限制線 xAxis.addLimitLine(LimitLine l);
        //從該軸刪除指定限制線 xAxis.removeLimitLine(LimitLine l);
        /*限制線範例:
        設定x軸的限制線，index是從0開始的
        LimitLine xLimitLine = new LimitLine(4f,"xL 測試");
        xLimitLine.setLineColor(Color.GREEN);
        xLimitLine.setTextColor(Color.GREEN);
        xAxis.addLimitLine(xLimitLine);
        設定y軸的限制線
        LimitLine yLimitLine = new LimitLine(50f,"yLimit 測試");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setTextColor(Color.RED);
        獲得左側側座標軸
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.addLimitLine(yLimitLine); */

        BarData barData = new BarData();
        //宣告 barDataSet1 的 addDataSet
        barData.addDataSet(barDataSet1);
        barData.setBarWidth((float) 0.7);

        //顯示圖表
        barChart.setData(barData);
        //在chart中呼叫會使其重新整理重繪
        barChart.invalidate();
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    //宣告 長條圖1 各項數值 ArrayList<Entry> dataValues1()
    private ArrayList<BarEntry> dataValues1(int R,int F,int H,int N,int W){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
//        Calendar cal = Calendar.getInstance();
//        final CharSequence ymd = DateFormat.format("yyyy-MM-d", cal.getTime());
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
//        reference = FirebaseDatabase.getInstance()
//                .getReference("PHYD");
//        reference.child("保險公司").child("騎士").child(uid).child("危險駕駛次數").child("本日").child(ymd.toString())
//                .addListenerForSingleValueEvent(listener_DAY_num);
//        GlobalVariable gv = (GlobalVariable)getApplicationContext();
//        int y1=gv.getHis1_R();
//        int y2=gv.getHis1_F();
//        int y3=gv.getHis1_H();
//        int y4=gv.getHis1_N();
//        int y5=gv.getHis1_W();
//        dataVals.add(new BarEntry(0,y1));
//        dataVals.add(new BarEntry(1,y2));
//        dataVals.add(new BarEntry(2,y3));
//        dataVals.add(new BarEntry(3,y4));
//        dataVals.add(new BarEntry(4,y5));
        dataVals.add(new BarEntry(0,R));
        dataVals.add(new BarEntry(1,F));
        dataVals.add(new BarEntry(2,H));
        dataVals.add(new BarEntry(3,N));
        dataVals.add(new BarEntry(4,W));
        return dataVals;
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
