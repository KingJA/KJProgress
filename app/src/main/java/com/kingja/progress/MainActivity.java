package com.kingja.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.lib.kingja.progress.R;

import lib.kingja.progress.BaseProgress;
import lib.kingja.progress.NormalProgress;
import lib.kingja.progress.RoundProgress;
import lib.kingja.progress.ScaleProgress;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentProgress = progress1.getProgress();
            int currentProgress2 = progress2.getProgress();
            int currentProgressRound3 = progressRound1.getProgress();
            int currentProgressRound4 = progressRound2.getProgress();
            int currentProgressRound5 = progressRound3.getProgress();
//            int currentProgress6 = progressDot1.getProgress();
            if (currentProgress < 100) {
                progress1.setProgress(++currentProgress);
                progress2.setProgress(++currentProgress2);
                progressRound1.setProgress(++currentProgressRound3);
                progressRound2.setProgress(++currentProgressRound4);
                progressRound3.setProgress(++currentProgressRound5);
//                progressDot1.setProgress(++currentProgress6);
                handler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };
    private NormalProgress progress1;
    private NormalProgress progress2;
    private RoundProgress progressRound1;
    private RoundProgress progressRound2;
    private RoundProgress progressRound3;
    private ScaleProgress progressDot1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress1 = (NormalProgress) findViewById(R.id.progress1);
        progress2 = (NormalProgress) findViewById(R.id.progress2);
        progressRound1 = (RoundProgress) findViewById(R.id.progressRound1);
        progressRound2 = (RoundProgress) findViewById(R.id.progressRound2);
        progressRound3 = (RoundProgress) findViewById(R.id.progressRound3);
        progressDot1 = (ScaleProgress) findViewById(R.id.progressDot1);
        handler.sendEmptyMessageDelayed(0, 100);
        progressRound3.setOnProgressFinsihedListener(new BaseProgress.OnProgressFinsihedListener() {
            @Override
            public void onFinished() {
//                Toast.makeText(MainActivity.this,"完成",Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "完成: ");
            }
        });
    }
}
