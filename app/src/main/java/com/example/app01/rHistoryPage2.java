package com.example.app01;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

public class rHistoryPage2 extends AppCompatActivity {

    //宣告 長條圖 BarChart barChart
    BarChart barChart;
    ArrayList<String> labels = new ArrayList<>();
    String[] Dangerous = { "闖紅燈", "重油", "急煞", "夜騎", "雨騎" };
    private DatabaseReference reference,reference_video;
    private String key;
    private TextView t_start_time_data,t_km_data;
    private RecyclerView mRecyclerView;
    private int color = R.color.gray1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_history_page2);


        t_start_time_data = findViewById(R.id.t_start_time_data);
        t_km_data = findViewById(R.id.t_km_data);

        reference_video = FirebaseDatabase.getInstance()
                .getReference("Videos");

        //讀取在His1點擊的項目是哪個
        key = getIntent().getStringExtra("key");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance()
                .getReference("PHYD");
        reference.child("騎士").child(uid).child("危險駕駛紀錄").child(key)
                .addListenerForSingleValueEvent(listener_DAYTime_StartTimeKM);

        //顯示list_history2
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_history2);
        new FirebaseDatabaseHelper().readHistory2s(key,new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<History1> history1s, List<String> keys) {

            }

            @Override
            public void DataIsLoaded2(List<History2> history2s, List<String> keys) {
                new RecyclerView_Config_History2().setConfig(mRecyclerView, rHistoryPage2.this,
                        history2s, keys);
            }

            @Override
            public void DataIsLoadedCom(List<Com> coms, List<String> keys) {

            }

            @Override
            public void DataIsLoadedSrarchS(List<SearchS1> searchs1s, List<String> keys) {

            }
        });

        /*按一下video_btn*/
        Button videobtn = (Button) findViewById(R.id.video_btn);
        videobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GlobalVariable gv = (GlobalVariable)getApplicationContext();
                    String url = gv.getURL();
                    Uri uri=Uri.parse(url);
                    Intent i=new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                }catch (Exception e){Toast.makeText(rHistoryPage2.this, "影片不存在!!!", Toast.LENGTH_SHORT).show();}
            }
        });

        /*按一下跳到行車紀錄介面*/
        Button recordbtn = (Button) findViewById(R.id.recordbtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage2.this, rRecordPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到首頁介面*/
        Button main1btn = (Button) findViewById(R.id.main1btn);
        main1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage2.this, rMainPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到安全分數介面*/
        Button scorebtn = (Button) findViewById(R.id.scorebtn);
        scorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage2.this, rScorePage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到申訴介面*/
        Button questbtn = (Button) findViewById(R.id.questbtn);
        questbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage2.this, rQuestPage.class);
                startActivity(intent);
            }
        });

        //宣告 barChart -> id.mp_BarChart
        barChart=findViewById(R.id.mp_BarChart2);

        /*按一下回到本日危險駕駛統計(HistoryPage)介面*/
        Button his2backbtn = (Button) findViewById(R.id.hbackbtn);
        his2backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rHistoryPage2.this, rHistoryPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            try{
            String url = dataSnapshot.child("videoUrl")
                    .getValue(String.class);
            //初始保費全額保費時程 顯示
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setURL(url);}
            catch(Exception e){
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                gv.setURL(null);}
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rHistoryPage2.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listener_DAYTime_StartTimeKM = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String 年月日 = dataSnapshot.child("年月日").getValue(String.class);
            String 開始時間 = dataSnapshot.child("開始時間").getValue(String.class);
            double 里程數 = dataSnapshot.child("里程數").getValue(double.class);
            int 雨騎 = dataSnapshot.child("雨騎").getValue(int.class);
            t_start_time_data.setText(開始時間);
            t_km_data.setText(里程數+"");

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            reference.child("騎士").child(uid).child("危險駕駛次數").child("本次").child(年月日+" "+開始時間)
                    .addListenerForSingleValueEvent(listener_DAYTime_RFHNW);

            reference_video.child(年月日+" "+開始時間).addListenerForSingleValueEvent(listener);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rHistoryPage2.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener listener_DAYTime_RFHNW = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            int R = dataSnapshot.child("闖紅燈").getValue(int.class);
            int F = dataSnapshot.child("重油").getValue(int.class);
            int H = dataSnapshot.child("急煞").getValue(int.class);
            int N = dataSnapshot.child("夜騎").getValue(int.class);
            int W = dataSnapshot.child("雨騎").getValue(int.class);
            chart(R,F,H,N,W);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(rHistoryPage2.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void chart(int R,int F,int H,int N,int W){
        //宣告 長條圖 barDataSet1 -> DataSet 1
        BarDataSet barDataSet2 = new BarDataSet(dataValues2(R,F,H,N,W),"DataSet 2");
        /*chart整體的顏色 部分【內訂好的】*/
        /*  MPAndroidChart程式已經自訂好的顏色【ex: barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);】
            ColorTemplate.LIBERTY_COLORS (藍色系列)
            ColorTemplate.COLORFUL_COLORS (彩色:暗紅色、橘色、黃色、墨綠色、淺棕色)
            ColorTemplate.JOYFUL_COLORS (彩色:淺粉色、淺橘色、淺黃色、綠色、淺藍色)
            ColorTemplate.PASTEL_COLORS (彩色:深藍色、墨綠色、深膚色、深粉色、暗紅色)
            ColorTemplate.VORDIPLOM_COLORS (彩色:亮綠色、亮黃色、亮橘色、亮藍色、亮粉色) */
        //宣告 長條圖 barDataSet1顏色
        barDataSet2.setColors(ColorTemplate.VORDIPLOM_COLORS);

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
        barChart.setGridBackgroundColor(getResources().getColor(color));

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
        barDataSet2.setDrawValues(true);
        barDataSet2.setValueTextColor(Color.BLACK);
        barDataSet2.setValueTextSize(12f);
        barDataSet2.setValueFormatter(new rHistoryPage2.AxisFormatter2());

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
        barData.addDataSet(barDataSet2);
        barData.setBarWidth((float) 0.7);

        //顯示圖表
        barChart.setData(barData);
        //在chart中呼叫會使其重新整理重繪
        barChart.invalidate();
    }

    //宣告 長條圖1 各項數值 ArrayList<Entry> dataValues1()
    private ArrayList<BarEntry> dataValues2(int R,int F,int H,int N,int W){

        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0,R));
        dataVals.add(new BarEntry(1,F));
        dataVals.add(new BarEntry(2,H));
        dataVals.add(new BarEntry(3,N));
        dataVals.add(new BarEntry(4,W));
        return dataVals;
    }

    //宣告 各項數值格式 = 數值
    private class AxisFormatter2 extends ValueFormatter {
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