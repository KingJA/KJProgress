package com.kingja.progress;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int currentProgress = progress.getProgress();
            if (currentProgress < 100) {
                progress.setProgress(++currentProgress);
                handler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };
    private KJProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (KJProgress) findViewById(R.id.progress);
        handler.sendEmptyMessageDelayed(0, 100);
    }
}
