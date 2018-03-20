package com.clb.commonlibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.clb.commonlibrary.R;
import com.clb.commonlibrary.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by DonMoses on 2015/9/23
 */
public class WatchView extends View {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    private int viewW;
    private Paint mPaint;
    private Path path;
    private PointF cenP;
    //半径
    private int radius;
    //时针角度
    private float angelH;
    //分针角度
    private float angelM;
    //秒针角度
    private float angelS;
    //初始位置
    private float angelStartS;
    private float angelStartM;
    private float angelStartH;
    //日期、星期
    private int dateOfMonth;
    private int dateOfWeek;

    //表盘的颜色
    private int clockDialColor;
    //表盘的宽度
    private float clockDialWidth;

    //时针颜色
    private int clockWiseColor;

    private float clockWiseWidth;

    //分针颜色
    private int minuteColor;

    private float minuteWidth;

    //秒针颜色
    private int secondsColor;

    private float secondsWidth;

    private String time,t;

    private String df = "yyyy/MM/dd HH:mm:ss";



    public WatchView(Context context) {
        super(context);
        init();
    }

    @SuppressLint("CustomViewStyleable")
    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.clockColor);
        if(ta!=null){
            clockDialColor = ta.getColor(R.styleable.clockColor_clockDialColor,getResources().getColor(R.color.colorBlack));
            clockWiseColor = ta.getColor(R.styleable.clockColor_clockWiseColor,getResources().getColor(R.color.colorRed));
            minuteColor = ta.getColor(R.styleable.clockColor_minuteColor,getResources().getColor(R.color.colorBlue));
            secondsColor = ta.getColor(R.styleable.clockColor_secondsColor,getResources().getColor(R.color.colorWite));

            clockDialWidth = ta.getDimension(R.styleable.clockColor_clockDialWidth,3.0f);
            //默认20f
            clockWiseWidth = ta.getDimension(R.styleable.clockColor_clockWiseWidth,15f);
            minuteWidth = ta.getDimension(R.styleable.clockColor_minuteWidth,10f);
            secondsWidth = ta.getDimension(R.styleable.clockColor_secondsWidth,5f);
            ta.recycle();
        }
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDimension(DEFAULT_WIDTH, widthMeasureSpec);
        int height = getDimension(DEFAULT_HEIGHT, heightMeasureSpec);
        viewW = width;
        cenP.x = viewW / 2;
        cenP.y = height / 2;
        radius = Math.min(viewW, height) / 2;
        setMeasuredDimension(width, height);
    }

    private int getDimension(int defaultSize, int measureSpec) {
        int result;
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        if (measureMode == MeasureSpec.EXACTLY) {
            result = measureSize;
        } else if (measureMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, measureSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    private void init() {
        //src = BitmapFactory.decodeResource(getResources(), R.drawable.circle_bg);
        //获取系统时间
        getTime();
        //初始化绘画相关
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        cenP = new PointF();
        path = new Path();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                float mAngelS = 24*60*360*interpolatedTime;
                angelS = angelStartS + mAngelS;
                angelM = angelStartM + mAngelS / 60f;
                angelH = angelStartH + mAngelS / 3600f;
                getDate();
                invalidate();
            }
        };
        animation.setDuration(24 * 60 * 60 * 1000);//一天24个小时为一个周期
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new LinearInterpolator());
        startAnimation(animation);
    }

    private void getDate(){
        Date date = new Date(System.currentTimeMillis());
        time = DateUtil.getDateToString(date,df);
        if(time!=t){
            Log.i("TAG",time);
            t = time;
        }
    }

    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        if (hour > 12) {
            hour = hour - 12;
        }
        angelS = 360 * sec / 60f;
        angelM = 360 * min / 60f + sec / 60f;
        angelH = 360 * hour / 12f + 360 * min / (60 * 12f) + 360 * sec / (60 * 60 * 12f);
        angelStartS = angelS;
        angelStartM = angelM;
        angelStartH = angelH;
        dateOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        //表盘背景
        path.reset();
        path.addCircle(cenP.x, cenP.y, radius, Path.Direction.CW);
        canvas.clipPath(path);
        //canvas.drawBitmap(src, 0, 0, mPaint);
        path.close();
        //表盘边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(clockDialWidth);
        mPaint.setColor(clockDialColor);
        canvas.drawCircle(cenP.x, cenP.y, radius, mPaint);

        //日期
        /*mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(44);
        canvas.drawText(String.valueOf(dateOfMonth), cenP.x + radius / 4, cenP.y + 22, mPaint);
        //星期
        mPaint.setTextSize(44);
        String weekDay = null;
        switch (dateOfWeek) {
            case 1:
                weekDay = "SUN";
                break;
            case 2:
                weekDay = "MON";
                break;
            case 3:
                weekDay = "TUE";
                break;
            case 4:
                weekDay = "WED";
                break;
            case 5:
                weekDay = "THU";
                break;
            case 6:
                weekDay = "FRI";
                break;
            case 7:
                weekDay = "SAT";
                break;
            default:
                break;
        }
        assert weekDay != null;
        canvas.drawText(weekDay, cenP.x + radius / 2, cenP.y + 22, mPaint);*/

        //绘制时针
        mPaint.setColor(clockWiseColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(clockWiseWidth);
        float hLen = radius / 2f;
        canvas.drawLine(cenP.x, cenP.y, (float) (viewW / 2f + hLen * Math.sin(angelH * Math.PI / 180f)),
                (float) (radius - hLen * Math.cos(angelH * Math.PI / 180f)), mPaint);

        //绘制分针
        mPaint.setColor(minuteColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(minuteWidth);
        float mLen = 2 * radius / 3f;
        canvas.drawLine(cenP.x, cenP.y, (float) (viewW / 2f + mLen * Math.sin(angelM * Math.PI / 180f)),
                (float) (radius - mLen * Math.cos(angelM * Math.PI / 180f)), mPaint);

        //绘制秒针
        mPaint.setColor(secondsColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(secondsWidth);
        float sLen = 5 * radius / 6f;
        canvas.drawLine(cenP.x, cenP.y, (float) (viewW / 2f + sLen * Math.sin(angelS * Math.PI / 180f)),
                (float) (radius - sLen * Math.cos(angelS * Math.PI / 180f)), mPaint);

        //中心点
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(cenP.x, cenP.y, radius / 30, mPaint);
        //12个点
        float txtSize = 35f;
        mPaint.setTextSize(txtSize);
        mPaint.setStrokeWidth(3.09f);
        float rR = radius - 35f;
        for (int i = 0; i < 12; i++) {
            String txt;
            if (i == 0) {
                txt = "12";
            } else {
                txt = String.valueOf(i);
            }
            canvas.drawText(
                    txt
                    , (float) (viewW / 2f - txtSize / 2f + rR * Math.sin(i * 30 * Math.PI / 180f))
                    , (float) (radius + txtSize / 2f - rR * Math.cos(i * 30 * Math.PI / 180f))
                    , mPaint);

        }

    }


    public interface OnCurrentTime{
        void getCurrentTime(String date);
    }


}
