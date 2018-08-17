package com.example.curveview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zengwei on 2018/8/14.
 */

public class CurveView extends View {
    private Paint paint;   //画笔
    private int width;     //宽度
    private int height;    //高度
    private  double[] x,y;    //贝塞尔曲线的坐标
    private boolean isbut=false;    //是按在按钮中
    private int id=0;               //显示滑动的位置  再坐标里面比对
    private double downX;           //按下的X轴  用来判断左右滑动
    private int sum;                //值   显示的值
    private double sumz=0;          //跨距   最大值与最小值的区间
    private double MinNum=0,MaxNum=100,bwidth;   //最小值 最大值  宽度
    private int size,Radii,Image;   //字体大小    图片大小   图片资源
    private int color1,color2,color3;
    private boolean Is;
    private TypedArray array;

    private CurveViewEvent curveViewEvent;
    public CurveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint=new Paint();
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        array= context.obtainStyledAttributes(attrs, R.styleable.CurveView);
        /**
         * 设置最大最小数值
         */
        MaxNum=array.getInteger(R.styleable.CurveView_MaxNum,100);  //最大值
        MinNum=array.getInteger(R.styleable.CurveView_MinNum,0);  //最小值
        size=array.getInteger(R.styleable.CurveView_TextSize,90);  //子体大小
        Radii=array.getInteger(R.styleable.CurveView_Radii,0);  //圆半径大小
        Image=array.getResourceId(R.styleable.CurveView_Image,R.mipmap.but_g1);  //图片
        color1=array.getColor(R.styleable.CurveView_color1,Color.parseColor("#cccccc"));  //图片
        color2=array.getColor(R.styleable.CurveView_color2,Color.parseColor("#76D6F1"));  //图片
        color3=array.getColor(R.styleable.CurveView_color3,Color.parseColor("#3C3F41"));  //图片
        bwidth=array.getInteger(R.styleable.CurveView_width,80);  //最大值
        Is=array.getBoolean(R.styleable.CurveView_Is,true);  //最大值
       //如果超过这个区间
       if((MaxNum>100||MaxNum<1)||(MinNum>100||MinNum<0)){
           //设置默认值
           MinNum=0;
           MaxNum=100;
       }
        sumz=MaxNum-MinNum;   //跨距  最大值和最小值的区间
        sum=(int)MinNum;  //初始值
        //关闭
        array.recycle();
    }

    /**
     * 回调方法
     * @param curveViewEvent
     */
    public void setCurveViewEvent(CurveViewEvent curveViewEvent) {
        this.curveViewEvent = curveViewEvent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        /**
         * 控件的宽高
         */
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if(Radii==0){
            Radii=width/7;
        }
        /**
         * 计算贝塞尔曲线的100个位置   0为初始位置
         */
        x=new double[101];
        y=new double[101];
        x[0]=bwidth;
        y[0]=height-50;
        for(int z=1;z<=100;z++){
            double i;
            if(z>=10&&z<100){
                i=Double.parseDouble("0."+z);
            }else if(z==100){
                i=1;
            }else{
                i=Double.parseDouble("0.0"+z);
            }
            //三阶公式
            x[z] = (1-i)*(1-i)*bwidth+2*i*(1-i)*(width/2)+i*i*(width-bwidth);
            if(Is){
                y[z] = (1-i)*(1-i)*(height-50)+2*i*(1-i)*0+i*i*(height-50);
            }else{
                y[z] = (1-i)*(1-i)*(height-50)+2*i*(1-i)*(height-50)+i*i*(height-50);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(color1);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo((float) bwidth,height-50);
        if(Is){
            path.quadTo(width/2,0, width-(float)bwidth,height-50);
        }else{
            path.quadTo(width/2,height-50, width-(float)bwidth,height-50);
        }
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(color3);
        //文字大小及位置
        paint.setTextSize(size);
        canvas.drawText(sum+"",width/2-size/2,height-65,paint);

        paint.setColor(color2);
        for(int iz=0;iz<id;iz++){
            canvas.drawLine((float) x[iz],(float)y[iz],(float)x[iz+1],(float)y[iz+1],paint);
        }
        //按钮图片
        canvas.drawBitmap(loadImage(getContext(),Radii,Image),(int)x[id]-Radii/2,(int)y[id]-Radii/2,paint);
    }

    /**
     * 滑动控制逻辑
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()>x[id]-Radii/2&&event.getX()<x[id]+Radii/2&&event.getY()>y[id]-Radii/2&&event.getY()<y[id]+Radii/2){
                    isbut=true;
                }
                if(curveViewEvent!=null){
                    curveViewEvent.Down(sum);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isbut){
                    if(event.getX()>downX){
                        if(id!=100){
                            if(event.getX()>x[id+1]){
                                id++;
                            }
                        }
                    }else if(event.getX()<downX){
                        if(id!=0){
                            if(event.getX()<x[id-1]){
                                id--;
                            }
                        }
                    }
                    sum=(int)MinNum+(int)(id/(100/sumz));
                    if(id==100){
                        sum=(int)MinNum+(int)sumz;
                    }
                    downX=event.getX();
                    invalidate();
                    if(curveViewEvent!=null){
                        curveViewEvent.Move(sum);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isbut=false;
                if(curveViewEvent!=null) {
                    curveViewEvent.Up(sum);
                }
                break;
        }
        return true;
    }

    /**
     * 获取图片并设置大小
     * @param context
     * @param width
     * @param image
     * @return
     */
    private static Bitmap loadImage(Context context,int width,int image){
        Resources res =context.getResources();
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,image), width, width, true);
    }

    /**
     * 获取当前数值
     * @return
     */
    public int getSum(){
        return sum;
    }
}
