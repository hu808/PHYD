package com.example.app01;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class rMainPage extends AppCompatActivity implements LocationListener {
    //ok的 = 重油 急煞 夜騎 雨騎 // 闖紅燈 里程數
    //按下開始後，背景變化 由 @drawable/bg2 --> 變成 @drawable/bg3 騎士主畫面背景顏色:深藍色+下方四格(淺-->深)
    private TextView startbtn,endbtn,透明屏障;
    private int number_r=0,number_f=0,number_h=0,number_n=0,number_w=0;
    private int past_all_day_time,past_all_month_time;
    private String video="http://";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private static final int VIDEO_PICK_CAMERA_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private String[] cameraPermissions;
    private ProgressDialog progressDialog;
    private VideoView videoView;
    private Uri videoUri = null;
    private int startTime,endTime;
    //數值抓出來在這邊
    //重油急煞 0是無 1是重油 2是急煞
    int oilHeavy = 0,oilLoss = 0;
    //是否有加速度 0無 1加速 2減速
    int speedChangeOrNot = 0;
    //速度 km/hr
    double speed = 0;
    //總路徑長(里程數)
    double totalMileage = 0;



    static final int MIN_TIME = 1000;
    static final float MIN_DIST = 11;
    LocationManager mgr;
    Timer timer;
    double latitude,longitude; //緯度,經度
    double firstLatitude,firstLongitude; //初始位置
    double secondLatitude,secondLongitude; //1秒後位置
    double lastLatitude,lastLongitude; //2秒後位置

    boolean isGPSEnabled,isNetworkEnabled;
    private final double EARTH_RADIUS = 6378137.0;


    String provider;



    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_main_page);

        startbtn = findViewById(R.id.r_startbtn_txt);
        endbtn = findViewById(R.id.r_stopbtn_txt);
        透明屏障 = findViewById(R.id.透明屏障);
        videoView = findViewById(R.id.videoView);
        timer = new Timer();
        mgr = (LocationManager)getSystemService(LOCATION_SERVICE);
        provider = mgr.getBestProvider(new Criteria(), false);




        checkPermission();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(rMainPage.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        Location location = mgr.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG","No Location");


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Uploading Video");
        progressDialog.setCanceledOnTouchOutside(false);

        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        final GlobalVariable gv = (GlobalVariable)getApplicationContext();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("PHYD").child("騎士").child(uid);

        Timer timer = new Timer(true);

        TimerTask tasks = new TimerTask() {
            int time = 0;
            public void run() {
                //每次需要執行的程式碼放到這裡面。
                time ++;
                if(time == 1){
                    firstLatitude = latitude;
                    firstLongitude = longitude;

                    Log.e("firstLatitude ", Double.toString(firstLatitude));
                    Log.e("firstLongitude ", Double.toString(firstLongitude));
                }else if(time == 2){
                    secondLatitude = latitude;
                    secondLongitude = longitude;

                    Log.e("secondLatitude ", Double.toString(secondLatitude));
                    Log.e("secondLongitude ", Double.toString(secondLongitude));
                }else if(time == 3){
                    time = 0;
                    lastLatitude = latitude;
                    lastLongitude = longitude;

                    //重油&急煞
                    oilAndBrakeHard(firstLatitude,firstLongitude,secondLatitude,secondLongitude,lastLatitude,lastLongitude);
                    Log.e("lastLatitude ", Double.toString(lastLatitude));
                    Log.e("lastLongitude ", Double.toString(lastLongitude));
                }
            }
        };

        timer.schedule(tasks, 0, 1000);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbtn.setVisibility(View.INVISIBLE);
                endbtn.setVisibility(View.VISIBLE);
                透明屏障.setVisibility(View.INVISIBLE);
                Calendar cal = Calendar.getInstance();
                CharSequence year_start = DateFormat.format("yyyy", cal.getTime());
                CharSequence month_start = DateFormat.format("MM", cal.getTime());
                final CharSequence ymd_start = DateFormat.format("yyyy-MM-d", cal.getTime());
                final CharSequence time_start = DateFormat.format("kk:mm", cal.getTime());
                final CharSequence s = DateFormat.format("yyyy-MM-d kk:mm", cal.getTime());

                Toast.makeText(rMainPage.this, s+" 開始", Toast.LENGTH_SHORT).show();

                String y=year_start.toString();
                String m=month_start.toString();
                String ymd = ymd_start.toString();
                String time = time_start.toString();
                gv.setStart_year(y);
                gv.setStart_month(m);
                gv.setStart_ymd(ymd);
                gv.setStart_time(time);

                startTime = Integer.parseInt(time.substring(0,2));

                HashMap<String,Object> first = new HashMap<>();
                first.put("年月",y+"-"+m);
                first.put("年月日",ymd);
                first.put("開始時間",time);
                String key = mReference.push().getKey();
                gv.setRmainKey(key);
                mReference.child("危險駕駛紀錄").child(key).setValue(first)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(rMainPage.this, s+" 開始", Toast.LENGTH_SHORT).show();
                            }
                        });
                //本月危險駕駛紀錄
                ValueEventListener listener2 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            past_all_month_time = dataSnapshot.child("總次數").getValue(int.class);
                            int pastR = dataSnapshot.child("闖紅燈").getValue(int.class);
                            double pastKM = dataSnapshot.child("里程數").getValue(double.class);
                            int pastF = dataSnapshot.child("重油").getValue(int.class);
                            int pastH = dataSnapshot.child("急煞").getValue(int.class);
                            int pastN = dataSnapshot.child("夜騎").getValue(int.class);
                            int pastW = dataSnapshot.child("雨騎").getValue(int.class);
                            GlobalVariable gv = (GlobalVariable)getApplicationContext();
                            gv.setAllmonth(past_all_month_time);
                            gv.setAllmonthr(pastR);
                            gv.setAllmonthkm(pastKM);
                            gv.setAllmonthf(pastF);
                            gv.setAllmonthh(pastH);
                            gv.setAllmonthn(pastN);
                            gv.setAllmonthw(pastW);}}
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(rMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).addListenerForSingleValueEvent(listener2);
                //本日危險駕駛紀錄
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                        past_all_day_time = dataSnapshot.child("總次數").getValue(int.class);
                            int pastR = dataSnapshot.child("闖紅燈").getValue(int.class);
                            double pastKM = dataSnapshot.child("里程數").getValue(double.class);
                            int pastF = dataSnapshot.child("重油").getValue(int.class);
                            int pastH = dataSnapshot.child("急煞").getValue(int.class);
                            int pastN = dataSnapshot.child("夜騎").getValue(int.class);
                            int pastW = dataSnapshot.child("雨騎").getValue(int.class);
                            GlobalVariable gv = (GlobalVariable)getApplicationContext();
                            gv.setAllday(past_all_day_time);
                            gv.setAlldayr(pastR);
                            gv.setAlldaykm(pastKM);
                            gv.setAlldayf(pastF);
                            gv.setAlldayh(pastH);
                            gv.setAlldayn(pastN);
                            gv.setAlldayw(pastW);}}
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(rMainPage.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                mReference.child("危險駕駛次數").child("本日").child(ymd).addListenerForSingleValueEvent(listener);

                //啟動相機
                if(!checkCameraPerMission()){
                    requestCameraPerMission();
                }else{
                    videoPickCamera();
                   // endbtn.callOnClick();
                }
            }
        });

        endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbtn.setVisibility(View.VISIBLE);
                endbtn.setVisibility(View.INVISIBLE);
                透明屏障.setVisibility(View.VISIBLE);
                Calendar cal = Calendar.getInstance();
                CharSequence time_end = DateFormat.format("kk:mm", cal.getTime());
                final CharSequence s = DateFormat.format("yyyy-MM-d kk:mm", cal.getTime());
                Toast.makeText(rMainPage.this, s+" 結束", Toast.LENGTH_SHORT).show();

                String y=gv.getStart_year();
                String m=gv.getStart_month();
                String ymd=gv.getStart_ymd();
                String key=gv.getRmainKey();
                String time=time_end.toString();
                gv.setEndTime(time_end.toString());
                gv.setTotalMileage(totalMileage);

                //重油急煞&總里程數
                double totalMile = gv.getTotalMileage()/1000;
                int oilHeavyCount = gv.getoilHeavy(), oilLossCount = gv.getoilLoss();

                endTime = Integer.parseInt(time.substring(0,2));

                if(startTime >= 22 || startTime <= 2 || endTime >= 22 || endTime <= 2){
                    number_n=1;
                    HashMap<String,Object> ifcatch = new HashMap<>();
                    ifcatch.put("危險項目","夜騎");
                    ifcatch.put("時間點",time_end);
                    String key2 = mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").push().getKey();
                    mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").child(key2).setValue(ifcatch);
                }

                int number_all = number_r+oilHeavyCount+oilLossCount+number_n+number_w;

                HashMap<String,Object> first = new HashMap<>();
                first.put("結束時間",time_end);
                first.put("本次危險次數",number_all);
                first.put("雨騎",number_w);
                first.put("里程數",totalMile);
                mReference.child("危險駕駛紀錄").child(key).updateChildren(first)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(rMainPage.this, s+" 開始", Toast.LENGTH_SHORT).show();
                            }
                        });
                //如果抓到危險行為
                for(int i=0;i<number_r;i++) {
                    HashMap<String,Object> ifcatch = new HashMap<>();
                    ifcatch.put("危險項目","闖紅燈");
                    ifcatch.put("時間點",time_end);
                    String key2 = mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").push().getKey();
                    mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").child(key2).setValue(ifcatch);
                }

                for(int i=0;i<number_w;i++) {
                    HashMap<String,Object> ifcatch = new HashMap<>();
                    ifcatch.put("危險項目","雨騎");
                    ifcatch.put("時間點",time_end);
                    String key2 = mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").push().getKey();
                    mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").child(key2).setValue(ifcatch);
                }

                //更新本次危險次數
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("闖紅燈").setValue(number_r);
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("里程數").setValue(totalMile);
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("重油").setValue(oilHeavy);
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("急煞").setValue(oilLoss);
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("夜騎").setValue(number_n);
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("雨騎").setValue(number_w);
                mReference.child("危險駕駛次數").child("本次").child(ymd+" "+gv.getStart_time()).child("總次數").setValue(number_all);
                //更新本月危險次數
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("闖紅燈").setValue(gv.getAllmonthr()+number_r);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("里程數").setValue(gv.getAllmonthkm()+totalMile);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("重油").setValue(gv.getAllmonthf()+oilHeavyCount);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("急煞").setValue(gv.getAllmonthh()+oilLossCount);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("夜騎").setValue(gv.getAllmonthn()+number_n);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("雨騎").setValue(gv.getAllmonthw()+number_w);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("總次數").setValue(gv.getAllmonth()+number_all);
                mReference.child("危險駕駛次數").child("本月").child(y+"-"+m).child("月份").setValue(y+"-"+m);
                gv.setAllmonth(0);
                gv.setAllmonthr(0);
                gv.setAllmonthkm(0);
                gv.setAllmonthf(0);
                gv.setAllmonthh(0);
                gv.setAllmonthn(0);
                gv.setAllmonthw(0);
                //更新本日危險次數
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("闖紅燈").setValue(gv.getAlldayr()+number_r);
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("里程數").setValue(gv.getAlldaykm()+totalMile);
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("重油").setValue(gv.getAlldayf()+oilHeavyCount);
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("急煞").setValue(gv.getAlldayh()+oilLossCount);
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("夜騎").setValue(gv.getAlldayn()+number_n);
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("雨騎").setValue(gv.getAlldayw()+number_w);
                mReference.child("危險駕駛次數").child("本日").child(ymd).child("總次數").setValue(gv.getAllday()+number_all);
                gv.setAllday(0);
                gv.setAlldayr(0);
                gv.setAlldaykm(0);
                gv.setAlldayf(0);
                gv.setAlldayh(0);
                gv.setAlldayn(0);
                gv.setAlldayw(0);
            }
        });

        /*按一下跳到歷史紀錄介面*/
        Button historybtn = (Button) findViewById(R.id.historybtn);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rMainPage.this, rHistoryPage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到行車紀錄介面*/
        Button recordbtn = findViewById(R.id.recordbtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(rMainPage.this,AddVideoActivity.class));
            }
        });

        /*按一下跳到安全分數介面*/
        Button scorebtn = (Button) findViewById(R.id.scorebtn);
        scorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rMainPage.this, rScorePage.class);
                startActivity(intent);
            }
        });

        /*按一下跳到申訴介面*/
        Button questbtn = (Button) findViewById(R.id.questbtn);
        questbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(rMainPage.this, rQuestPage.class);
                startActivity(intent);
            }
        });
    }

    private void checkPermission() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        if(requestCode == 200){
            if(grantResults.length >= 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"程式需要定位權限才能運作",Toast.LENGTH_LONG).show();

            }
        }
    }

    private void uploadVideoFireBase() {
        progressDialog.show();

        //取時間,存影片進firebase的檔名
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d kk:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        String rt = sdf.format(calendar.getTime());

        //final String timestamp = ""+ System.currentTimeMillis();
        final String timestamp = rt;

        String filePathAndName = "Videos/" + "video_" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id",""+timestamp);
                            //hashMap.put("title",""+title);
                            hashMap.put("timestamp",""+timestamp);
                            hashMap.put("videoUrl",""+ downloadUri);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
                            reference.child(timestamp)
                                    .setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(rMainPage.this,"影片上傳雲端成功！",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(rMainPage.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(rMainPage.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
//        float[] results=new float[1];
//        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
//        return results[0];
        //計算2點距離 單位為M
        double radLat1 = (lat1 * Math.PI / 180.0);
        double radLat2 = (lat2 * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lon1 - lon2) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        speed = s*3.6;
        return s;
    }

    private void oilAndBrakeHard(double firstLatitude, double firstLongitude,double secondLatitude, double secondLongitude, double lastLatitude, double lastLongitude) {
        //0-1秒位移
        //double displacementOne = (secondLatitude - firstLatitude)*(secondLatitude - firstLatitude) + (secondLongitude - firstLongitude)*(secondLongitude - firstLongitude);
        //1-2秒位移
        //double displacementTwo = (lastLatitude - secondLatitude)*(lastLatitude - secondLatitude) + (lastLongitude - secondLongitude)*(lastLongitude - secondLongitude);

        double displacementOne = getDistance(firstLatitude,firstLongitude,secondLatitude,secondLongitude);
        double displacementTwo = getDistance(secondLatitude,secondLongitude,lastLatitude,lastLongitude);

        //加速度(m/s平方)
        double displacement = displacementTwo - displacementOne;
        final GlobalVariable gv = (GlobalVariable)getApplicationContext();

        if(displacement == 0){
            //靜止
            speedChangeOrNot = 0;
        }else if(displacement > 0){
            //加速
            speedChangeOrNot = 1;
            gv.setSpeedChangeOrNot(speedChangeOrNot);
        }else{
            //減速
            speedChangeOrNot = 2;
        }

        if(displacement > 3.26){
            //重油
            oilHeavy ++;
            gv.setoilHeavy(oilHeavy);
            //寫入firebase
            String key=gv.getRmainKey();
            Calendar cal = Calendar.getInstance();
            final CharSequence oilHeavyHappen = DateFormat.format("kk:mm", cal.getTime());
            HashMap<String,Object> ifcatchoilHeavy = new HashMap<>();
            ifcatchoilHeavy.put("危險項目","重油");
            ifcatchoilHeavy.put("時間點",oilHeavyHappen.toString());
            String key2 = mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").push().getKey();
            mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").child(key2).setValue(ifcatchoilHeavy);
        }else if(displacement < -3.26){
            //急煞
            oilLoss ++;
            gv.setoilLoss(oilLoss);
            //寫入firebase
            String key=gv.getRmainKey();
            Calendar cal = Calendar.getInstance();
            final CharSequence oilLossHappen = DateFormat.format("kk:mm", cal.getTime());
            HashMap<String,Object> ifcatchoilLoss = new HashMap<>();
            ifcatchoilLoss.put("危險項目","急煞");
            ifcatchoilLoss.put("時間點",oilLossHappen.toString());
            String key2 = mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").push().getKey();
            mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").child(key2).setValue(ifcatchoilLoss);
        }else{
            //正常
        }
        //Log.e("oilChangeResult ", Integer.toString(oilChangeResult));

        //因為每2秒算一次重油急煞時，有位移可用，就拿來用了
        totalMileage = totalMileage + displacementOne + displacementTwo;
    }

    @Override
    protected void onResume() {
        super.onResume();


        enableLocationUpdates(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(rMainPage.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        mgr.requestLocationUpdates(provider, 400, 1, this);
    }



    @Override
    protected void onPause() {
        super.onPause();

        enableLocationUpdates(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(rMainPage.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        mgr.removeUpdates(this);

    }

    private void requestCameraPerMission(){
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPerMission(){
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED;

        return result1 && result2;
    }

    private void videoPickCamera(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT,videoUri);
        intent.putExtra("android.intent.extra.quickCapture",true); //not working
        //intent.putExtra("return-data", true);
        //intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5); //最多幾秒，到秒數會自動停止  最後的數字是秒數，不是微秒
        startActivityForResult(intent,VIDEO_PICK_CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == VIDEO_PICK_CAMERA_CODE){
                videoUri = data.getData();
                setVideoToVideoView();
                uploadVideoFireBase();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setVideoToVideoView(){
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.pause();
            }
        });
        endbtn.callOnClick();////
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(rMainPage.this, "登出", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(rMainPage.this,MainActivity.class));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String str = "定位提供者:" + location.getProvider() + String.format("\n緯度:%.5f\n經度:%.5f\n高度:%.2f公尺",
                location.getLatitude(),
                location.getLongitude(),
                location.getAltitude());

        latitude = location.getLatitude();
        longitude = location.getLongitude();


//        System.out.println(Common.apiRequest(String.valueOf(latitude),String.valueOf(longitude)));
//        new GetWeather().execute(Common.apiRequest(String.valueOf(latitude),String.valueOf(longitude)));
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) { }

    @Override
    public void onProviderEnabled(String s) { }

    @Override
    public void onProviderDisabled(String s) { }





    public void setup(View v){
        Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(it);
    }



    private void enableLocationUpdates(boolean isTurnOn) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isTurnOn) {
                isGPSEnabled = mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);

                isNetworkEnabled = mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && isNetworkEnabled) {
                    Toast.makeText(this, "請確認已開啟定位功能!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "取得定位資訊中...", Toast.LENGTH_LONG).show();
                    if (isGPSEnabled)
                        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, this);

                    if (isNetworkEnabled)
                        mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, this);
                }
            } else {
                mgr.removeUpdates(this);
            }
        }
    }


}
