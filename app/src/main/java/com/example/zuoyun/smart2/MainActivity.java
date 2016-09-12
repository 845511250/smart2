package com.example.zuoyun.smart2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static MainActivity instance;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        instance = this;

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(context, Service1.class));
                finish();
            }
        });

    }

    public void toast(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
