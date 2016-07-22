package com.kingja.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int currentProgress = progress.getProgress();
            int currentProgress2 = progress2.getProgress();
            int currentProgress3 = progressRound1.getProgress();
            int currentProgress4 = progressRound1.getProgress();
            if (currentProgress < 100) {
                progress.setProgress(++currentProgress);
                progress2.setProgress(++currentProgress2);
                progressRound1.setProgress(++currentProgress3);
                progressRound2.setProgress(++currentProgress4);
                handler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };
    private KJProgress progress;
    private KJProgress progress2;
    private KJProgressRound progressRound1;
    private KJProgressRound progressRound2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (KJProgress) findViewById(R.id.progress);
        progress2 = (KJProgress) findViewById(R.id.progress2);
        progressRound1 = (KJProgressRound) findViewById(R.id.progressRound1);
        progressRound2 = (KJProgressRound) findViewById(R.id.progressRound2);
        handler.sendEmptyMessageDelayed(0, 100);
    }
}
