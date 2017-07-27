package com.zet.demoscrollview;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";

    private static final int progressBarMax = 10000;

    private TextView mTxt;
    private ScrollView mScroll;
    private ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initView();
        setupView();
    }

    private void setupView() {
        initScrollView();
        initTextView();
        initProgressBar();
    }

    private void initScrollView() {
        mScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float measuredHeight = mTxt.getMeasuredHeight();
                float scrollY = mScroll.getScrollY();
                float height = mScroll.getHeight();

                int progress = (int) ((scrollY + height) / measuredHeight * progressBarMax);
                Log.e(TAG, "onTouch: " + progress);
                mPb.setProgress(progress);
                return false;
            }
        });
    }

    private void initProgressBar() {
        mPb.setMax(progressBarMax);
    }

    private void initTextView() {
        mTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float measuredHeight = mTxt.getMeasuredHeight();
                float scrollY = mScroll.getScrollY();
                float height = mScroll.getHeight();

                if ((scrollY + height) == measuredHeight) {
                    Log.e(TAG, "onTouch: " + " jump ");
                    Intent intent = new Intent(FirstActivity.this, FourActivity.class);
                    FirstActivity.this.startActivity(intent);
//                    FirstActivity.this.finish();
                }

                return false;
            }
        });
        updateTextViewUI();
    }

    private void updateTextViewUI() {
        try {
            String fileName = "random.txt";
            InputStream inputStream = this.getAssets().open(fileName, AssetManager.ACCESS_BUFFER);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String temp = null;

            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }

            String text = stringBuilder.toString();
            mTxt.setText(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mTxt = (TextView) findViewById(R.id.mTxt);
        mScroll = (ScrollView) findViewById(R.id.mScroll);
        mPb = (ProgressBar) findViewById(R.id.mPb);
    }
}
