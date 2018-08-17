package com.example.curveview;

/**
 * Created by zengwei on 2018/8/17.
 */

public interface CurveViewEvent {
    void Down(int sum);
    void Move(int sum);
    void Up(int sum);
}
