package com.zet.demo2;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText mEdit;
    private ScrollView mScroll;
    private Button mBtn;
    private TextView mTxt;

    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setUpViews();
        viewAction();
    }

    private void setUpViews() {
        initTextView();
    }

    private void initTextView() {
        try {
            InputStream inputStream = this.getAssets().open("random.txt", AssetManager.ACCESS_BUFFER);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }
            String result = stringBuilder.toString();
            mTxt.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewAction() {
        mEdit.setText("500");
    }

    public void mBtnClick(View view) {
        Log.e(TAG, "mBtnClick: click ... ");
        final int anInt = Integer.parseInt(mEdit.getText().toString());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mScroll.smoothScrollBy(0, anInt);
            }
        });

//        mScroll.post(new Runnable() {
//            @Override
//            public void run() {
//                mScroll.smoothScrollBy(0, anInt);
//            }
//        });

    }

    private void initView() {
        mEdit = (EditText) findViewById(R.id.mEdit);
        mScroll = (ScrollView) findViewById(R.id.mScroll);
        mBtn = (Button) findViewById(R.id.mBtn);
        mBtn.setOnClickListener(this);
        mTxt = (TextView) findViewById(R.id.mTxt);
        mTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn:
                mBtnClick(v);
                break;
        }
    }
}
