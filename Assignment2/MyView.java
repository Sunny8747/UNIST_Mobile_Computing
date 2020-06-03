package com.example.a106_2nd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyView extends View {

    DisplayMetrics display = this.getResources().getDisplayMetrics();
    int w = display.widthPixels;
    int h = display.heightPixels;
    private Paint mPaint;
    private Paint mLinePaint;
    private Drawable mdrawable = getResources().getDrawable(R.drawable.convert_bf4);
    private Bitmap oldBitmap = RotateBitmap(((BitmapDrawable)mdrawable).getBitmap(),90);
    private Bitmap cBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
    double cBitmapRatio = ((double) cBitmap.getWidth()) / (double) cBitmap.getHeight();
    int targetWidth = (int) ((h - 250) * cBitmapRatio);
    private Bitmap mBitmap = Bitmap.createScaledBitmap(cBitmap, targetWidth, h-250, false);
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private Canvas mCanvas;
    long unixtime = System.currentTimeMillis()/1000 - 1573990000;

    //new
    private float mPosX = 0;
    private float mPosY = 0;
    float mCircleX = 0;
    float mCircleY = 0;

    public MyView(Context context) {
        this(context, null, 0);
        setupDrawing();
        mCanvas = new Canvas(mBitmap);
    }

    public MyView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public MyView(Context context, AttributeSet attr, int ref) {
        super(context, attr, ref);
    }

    public void setupDrawing() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(12);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(12);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
    }
    String Stime = null;
    int checkbit1 = 0,checkbit2 = 0,checkbit3 = 0,checkbit4 = 0,checkbit5 = 0 ,checkbit6 = 0 ,checkbit7 = 0 ,checkbit8 = 0 ,checkbit9 = 0 ,checkbit10 = 0 ,checkbit11 = 0 ,checkbit12 = 0 ,checkbit13 = 0 ,checkbit14 = 0 ;
    public void pointPos(float x, float y) {
        if(x > 560 && x <661) {//1st line
            if(y > 0 && y <150) {//y 607
                y = 63;
                x = 607;
                if(checkbit1 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit1 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit1 = 0;
                }
            }
            else if(y > 300 && y <465) {
                y = 420;
                x = 607;
                if(checkbit2 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit2 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit2 = 0;
                }
            }
            else if(y > 465 && y <650) {
                y = 533;
                x = 607;
                if(checkbit3 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit3 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit3 = 0;
                }
            }
            else if(y > 1050 && y <1400) {
                y = 1212;
                x = 607;
                if(checkbit4 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit4 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit4 = 0;
                }
            }
            else if(y > 1600 && y <1900) {
                y = 1721;
                x = 607;
                if(checkbit5 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit5 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit5 = 0;
                }
            }
            else if(y > 2100 && y <2310) {
                y = 2245;
                x = 607;
                if(checkbit6 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit6 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit6 = 0;
                }
            }
        }
        else if(x > 464 && x <560) {//entrance
            if(y > 300 && y <465) {
                y = 420;
                x = 520;
                if(checkbit7 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit7 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit7 = 0;
                }
            }
            else if(y > 465 && y <650) {
                y = 533;
                x = 520;
                if(checkbit8 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit8 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit8 = 0;
                }
            }
        }
        else if(x > 229 && x <368) {
            if(y > 0 && y <150) {//y 303
                y = 63;
                x = 303;
                if(checkbit9 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit9 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit9 = 0;
                }
            }
            else if(y > 300 && y <465) {
                y = 420;
                x = 303;
                if(checkbit10 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit10 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit10 = 0;
                }
            }
            else if(y > 465 && y <650) {
                y = 533;
                x = 303;
                if(checkbit11 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit11 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit11 = 0;
                }
            }
            else if(y > 1050 && y <1400) {
                y = 1212;
                x = 303;
                if(checkbit12 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit12 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit12 = 0;
                }
            }
            else if(y > 1600 && y <1900) {
                y = 1721;
                x = 303;
                if(checkbit13 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit13 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit13 = 0;
                }
            }
            else if(y > 2100 && y <2310) {
                y = 2245;
                x = 303;
                if(checkbit14 == 0) {
                    mCanvas.drawCircle(x, y, 3,mPaint);
                    checkbit14 = 1;
                }
                else {
                    mCanvas.drawCircle(x, y, 3,mLinePaint);
                    checkbit14 = 0;
                }
            }
        }

        Long Ltime = System.currentTimeMillis()/1000;
        Stime =  y + "," + x + "," + Ltime.toString();
        try {
            String filename = "experiment" + unixtime;
            BufferedWriter bfw = new BufferedWriter(new FileWriter("/mnt/sdcard/Download/" + filename + ".csv", true));
            Log.d("DEBUG", "path : " + this.getContext().getFilesDir().getPath());
            bfw.write(Stime);
            bfw.newLine();
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        Log.d("DEBUG", "mposX : "+mPosX+" mposY: "+mPosY);
        canvas.translate(mPosX, mPosY);
        canvas.drawBitmap(mBitmap,mPosX,mPosY,mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX(); //현재의 화면 좌표
                final float y = event.getY();
                //Log.d("DEBUG", "1x : "+x+" 1y: "+y);
                mCircleX = x;
                mCircleY = y;
                mCircleX = mCircleX - mPosX*2;
                mCircleY = mCircleY - mPosY*2;
                pointPos(mCircleX, mCircleY);
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                invalidate();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                break;
            }
        }
        return true;
    }
}
