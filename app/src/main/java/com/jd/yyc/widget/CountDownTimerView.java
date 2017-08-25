package com.jd.yyc.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.yyc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/5/19.
 * <p>
 * 倒计时
 */

public class CountDownTimerView extends LinearLayout {
    @InjectView(R.id.hours)
    TextView mHourTextView;
    @InjectView(R.id.minutes)
    TextView mMinTextView;
    @InjectView(R.id.seconds)
    TextView mSecondTextView;


    private CountDownTimer mCountDownTimer;
    private long mMillis;
    private OnCountDownTimerListener OnCountDownTimerListener;


    public CountDownTimerView(Context context) {
        super(context);
        init();
    }

    public CountDownTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountDownTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_counttime, this);
        ButterKnife.inject(this, this);
    }

    /**
     * 创建倒计时
     */
    private void createCountDownTimer() {
        mCountDownTimer = new CountDownTimer(mMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                setSecond(millisUntilFinished);// 设置秒
            }

            @Override
            public void onFinish() {
                OnCountDownTimerListener.onFinish();
            }
        };
    }

    /**
     * 设置秒
     *
     * @param millis
     */
    private void setSecond(long millis) {
        long totalSeconds = millis / 1000;
        String second = (int) (totalSeconds % 60) + "";// 秒
        long totalMinutes = totalSeconds / 60;
        String minute = (int) (totalMinutes % 60) + "";// 分
        long totalHours = totalMinutes / 60;
        String hour = (int) (totalHours) + "";// 时
//        Logger.i("TAG", "hour:" + hour);
//        Logger.i("TAG", "minute:" + minute);
//        Logger.i("TAG", "second:" + second);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        if (second.length() == 1) {
            second = "0" + second;
        }
        mHourTextView.setText(hour);
        mMinTextView.setText(minute);
        mSecondTextView.setText(second);
    }

    /**
     * 设置监听事件
     *
     * @param listener
     */
    public void setDownTimerListener(OnCountDownTimerListener listener) {
        this.OnCountDownTimerListener = listener;
    }

    /**
     * 设置时间值
     *
     * @param millis
     */
    public void setDownTime(long millis) {
        this.mMillis = millis;
    }

    /**
     * 开始倒计时
     */
    public void startDownTimer() {
        createCountDownTimer();// 创建倒计时
        mCountDownTimer.start();
    }

    public void cancelDownTimer() {
        mCountDownTimer.cancel();
    }

    public interface OnCountDownTimerListener {

        void onFinish();

    }
}
