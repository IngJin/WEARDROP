package com.project.weardrop.Other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

// 비디오 화면 크기 변환시켜 화면 크기에 맞추기
public class MyVideoview extends VideoView {

    private int mForceHeight = 0;
    private int mForceWidth = 0;

    public MyVideoview(Context context) {
        super(context);
    }

    public MyVideoview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDimensions(int w, int h) {
        this.mForceHeight = h;
        this.mForceWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("Sub1Video", "onMeasure()");
        //DisplayMetrics displayMetrics = new DisplayMetrics();
        //((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
