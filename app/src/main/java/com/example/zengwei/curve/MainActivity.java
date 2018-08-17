package com.example.zengwei.curve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CurveView curve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        curve=findViewById(R.id.curve);
        curve.setCurveViewEvent(new CurveViewEvent() {
            @Override
            public void Down(int sum) {
            }

            @Override
            public void Move(int sum) {
            }

            @Override
            public void Up(int sum) {
            }
        });
    }
}
