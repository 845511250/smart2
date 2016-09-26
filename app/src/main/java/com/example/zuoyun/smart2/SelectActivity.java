package com.example.zuoyun.smart2;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_suiji,iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8,iv9,iv10,iv11,iv12,iv13,iv14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        iv_suiji = (ImageView) findViewById(R.id.iv_suiji);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        iv4 = (ImageView) findViewById(R.id.iv_4);
        iv5 = (ImageView) findViewById(R.id.iv_5);
        iv6 = (ImageView) findViewById(R.id.iv_6);
        iv7 = (ImageView) findViewById(R.id.iv_7);
        iv8 = (ImageView) findViewById(R.id.iv_8);
        iv9 = (ImageView) findViewById(R.id.iv_9);
        iv10 = (ImageView) findViewById(R.id.iv_10);
        iv11 = (ImageView) findViewById(R.id.iv_11);
        iv12 = (ImageView) findViewById(R.id.iv_12);
        iv13 = (ImageView) findViewById(R.id.iv_13);
        iv14 = (ImageView) findViewById(R.id.iv_14);

        iv_suiji.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        iv7.setOnClickListener(this);
        iv8.setOnClickListener(this);
        iv9.setOnClickListener(this);
        iv10.setOnClickListener(this);
        iv11.setOnClickListener(this);
        iv12.setOnClickListener(this);
        iv13.setOnClickListener(this);
        iv14.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_suiji:
                Service1.instance.et_code.setText("0");
                finish();
                break;
            case R.id.iv_1:
                Service1.instance.et_code.setText("1");
                finish();
                break;
            case R.id.iv_2:
                Service1.instance.et_code.setText("2");
                finish();
                break;
            case R.id.iv_3:
                Service1.instance.et_code.setText("3");
                finish();
                break;
            case R.id.iv_4:
                Service1.instance.et_code.setText("4");
                finish();
                break;
            case R.id.iv_5:
                Service1.instance.et_code.setText("5");
                finish();
                break;
            case R.id.iv_6:
                Service1.instance.et_code.setText("6");
                finish();
                break;
            case R.id.iv_7:
                Service1.instance.et_code.setText("7");
                finish();
                break;
            case R.id.iv_8:
                Service1.instance.et_code.setText("8");
                finish();
                break;
            case R.id.iv_9:
                Service1.instance.et_code.setText("9");
                finish();
                break;
            case R.id.iv_10:
                Service1.instance.et_code.setText("10");
                finish();
                break;
            case R.id.iv_11:
                Service1.instance.et_code.setText("11");
                finish();
                break;
            case R.id.iv_12:
                Service1.instance.et_code.setText("12");
                finish();
                break;
            case R.id.iv_13:
                Service1.instance.et_code.setText("13");
                finish();
                break;
            case R.id.iv_14:
                Service1.instance.et_code.setText("14");
                finish();
                break;
        }
    }
}
