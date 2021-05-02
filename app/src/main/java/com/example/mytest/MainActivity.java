package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG="MyTest";
    ConstraintLayout constraintLayout;
    private int straight = 50;
    private int layOutWidth = 900;
    private int layOutHeight = 1200;
    private int startX = 50;
    private int startY = 50;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private BallView ballView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        constraintLayout = new ConstraintLayout(this);
        constraintLayout.setMaxWidth(layOutWidth);
        constraintLayout.setMaxHeight(layOutHeight);
        constraintLayout.setBackgroundColor(Color.BLACK);
        constraintLayout.setTranslationX(startX);
        constraintLayout.setTranslationY(startY);
        ballView = new BallView(this,straight,straight, layOutWidth, layOutHeight);
        constraintLayout.addView(ballView);

        setContentView(constraintLayout);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor =mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x11){
                    ballView.moveStep(msg.arg1,msg.arg2);
                }
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int deltax = 0;
                int deltay = 0;
                while (true) {
                    if (Math.sqrt(ballView.speedx * ballView.speedx + ballView.speedy * ballView.speedy) > 1) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        deltax = (int)ballView.speedx/3;
                        deltay = (int)ballView.speedy/3;


                    }else
                    {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        deltax = (int)ballView.speedx/3;
                        deltay = (int)ballView.speedy/3;
                    }
                    Message message = handler.obtainMessage(0x11, (int) deltax, (int) deltay);
                    handler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
//        Log.e(TAG, "onSensorChanged: "+x+" "+y+" "+z);
        ballView.speedx += x;
        ballView.speedy += y;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}