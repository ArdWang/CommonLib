package com.clb.commonlib;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.clb.commonlibrary.utils.DateUtil;
import com.clb.commonlibrary.utils.HandlerUtils;
import com.clb.commonlibrary.view.ClockView;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements HandlerUtils.OnMsgListener,ClockView.getCurrentDate{
    /**
     * 弱引用handler
     */
    private HandlerUtils.Holder handlerHolder;
    public static final String TAG= "MainActivity";

    private ClockView clockView;

    private TextView mytesttxt;

    private String df = "yyyy/MM/dd HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mytesttxt = findViewById(R.id.mytest_txt);

        initData();
    }

    private void initData() {

        clockView = new ClockView(this);
        clockView.setDateClick(this,df);
        handlerHolder = new HandlerUtils.Holder(this);
        handlerHolder.sendEmptyMessage(0);

        String currentDate = "yyyy-MM-dd HH:mm:ss";
        String a = DateUtil.getCurrentDate(currentDate);
        Log.i(TAG,a);

        Long time = 1555232343000L;
        String a1 = DateUtil.getLongToString(time,currentDate);
        Log.i(TAG,a1);

        String date = "2018-12-15 18:22:55";
        Long a2 = DateUtil.getStringToLong(date,currentDate);
        Log.i(TAG,a2+"");

        //显示当前的天数是星期几
        @SuppressLint({"NewApi", "LocalSuppress"})
        int week = DateUtil.getDayWeek(2018,3,20);
        Log.i(TAG,week+"");

        //显示3月份总共多少天
        int alldays = DateUtil.getMonthDays(2018,3);
        Log.i(TAG,alldays+"");


        String a3 ="20180320083034";
        try {
            String currentDate1 = "yyyy-MM-dd HH:mm:ss";
            Date dd = DateUtil.parseStringToDate(a3);
            String dateString = DateUtil.getDateToString(dd,currentDate1);
            Log.i(TAG,dateString+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                Toast.makeText(this,"获取得到",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void currentDate(String date) {
        mytesttxt.setText(date);
    }
}
