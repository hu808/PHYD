package com.example.app01;

import android.text.format.DateFormat;
import android.util.Size;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import caffe_android_lib.CaffeMobile;

import static com.example.app01.R.id.result;


public class TrafficLightsDetectorActivity extends DetectorActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    //辨識紅綠燈 0是無燈號 1是紅燈 2是綠燈
    int trafficLightsDetectorResult = 0;

    //闖紅燈次數
    int overRedCount = 0;

    //這是跟辨識完紅燈跟加速度後結合的變數，false就沒有，true就有
    boolean overRed = false;

    //private static final Logger LOGGER = new Logger();

    private static Integer[] LIGHTS = new Integer[]{R.mipmap.none, R.mipmap.stop, R.mipmap.start};

    static {
        System.loadLibrary("caffe");
        System.loadLibrary("caffe_jni");
        System.loadLibrary("imageutils_jni");
    }

    private String modelProto = "deploy.prototxt";
    private String modelBinary = "train_squeezenet_scratch_trainval_manual_p2__iter_8000.caffemodel";

    private CaffeMobile caffeMobile;
    private ImageView resultView;

    @Override
    public void onPreviewSizeChosen(Size size, int rotation) {
        super.onPreviewSizeChosen(size, rotation);

        resultView = (ImageView) findViewById(result);
    }

    @Override
    public void onDetector(final String bitmap) {

        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {
                        //0沒，1停，2走
                        final int result = caffeMobile.predictImage(bitmap)[0];
                        //LOGGER.e("predictImage >> %d", result);
                        //抓出來方便給其他地方用
                        trafficLightsDetectorResult = result;
                        if(trafficLightsDetectorResult == 0){
                            Toast.makeText(com.example.app01.TrafficLightsDetectorActivity.this,"nope", Toast.LENGTH_SHORT).show();
                        }else if(trafficLightsDetectorResult == 1){
                            Toast.makeText(com.example.app01.TrafficLightsDetectorActivity.this,"red", Toast.LENGTH_SHORT).show();
                            final GlobalVariable gv = (GlobalVariable)getApplicationContext();
                            gv.setTrafficLightsDetectorResult(trafficLightsDetectorResult);
                            if(gv.getTrafficLightsDetectorResult() == 1 && gv.getSpeedChangeOrNot() == 1){//闖紅燈以及有加速度
                                overRed = true;
                                gv.setoverRed(overRed);
                                overRedCount++;
                                gv.setoverRedCount(overRedCount);
                                //firebase
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();
                                mDatabase = FirebaseDatabase.getInstance();
                                mReference = mDatabase.getReference("PHYD").child("騎士").child(uid);
                                String key=gv.getRmainKey();
                                Calendar cal = Calendar.getInstance();
                                final CharSequence overRedHappen = DateFormat.format("kk:mm", cal.getTime());
                                HashMap<String,Object> ifcatch = new HashMap<>();
                                ifcatch.put("危險項目","闖紅燈");
                                ifcatch.put("時間點",overRedHappen.toString());
                                String key2 = mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").push().getKey();
                                mReference.child("危險駕駛紀錄").child(key).child("危險紀錄").child(key2).setValue(ifcatch);
                            }else{
                                overRed = false;
                                gv.setoverRed(overRed);
                            }
                        }else{
                            Toast.makeText(com.example.app01.TrafficLightsDetectorActivity.this,"green", Toast.LENGTH_SHORT).show();
                        }



                        requestRender();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultView.setImageResource(LIGHTS[result]);
                                computing = false;
                            }
                        });

                    }
                });

    }

    @Override
    protected void setFragment() {
        super.setFragment();

        Utils.makeModelDir(getApplicationContext());
        Utils.copyFile(getApplicationContext(), modelProto);
        Utils.copyFile(getApplicationContext(), modelBinary);

        caffeMobile = new CaffeMobile();
        caffeMobile.setNumThreads(4);
        caffeMobile.loadModel(Utils.getModelFile(getApplicationContext(), modelProto).getAbsolutePath(),
                Utils.getModelFile(getApplicationContext(), modelBinary).getAbsolutePath());

        float[] meanValues = {104, 117, 123};
        caffeMobile.setMean(meanValues);
        caffeMobile.setScale(320f);
        caffeMobile.enableLog(false);
    }
}
