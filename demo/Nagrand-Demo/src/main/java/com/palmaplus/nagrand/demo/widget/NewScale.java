package com.palmaplus.nagrand.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.palmaplus.nagrand.view.MapView;


/**
 * Created by TYT on 2016/1/22.
 * 调用完setMapview之后就可以使用，刷新使用invalidate
 */
public class NewScale extends View {
    private Context mContext;
    private MapView mMapView=null;
    private int canvasHeight=0;
    private int canvasWidth=0;
    private Paint mPaint= new Paint();


    public NewScale(Context context){
        super(context);
        initView(context,null);
    }
    public NewScale(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        initView(context,attributeSet);
    }
    public NewScale(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initView(context, attrs);
    }
    /*public NewScale(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context,attrs,defStyleAttr,defStyleRes);
        initView(context,attrs);
    }*/
    private void initView(Context context,AttributeSet attributeSet){
        mContext=context;
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMapView!=null){
            canvasHeight = canvas.getHeight();
            canvasWidth = canvas.getWidth();
            float length =mMapView.getPixelLengthFromRealDistance(10);
            String meter="10";
            if (mMapView.getPixelLengthFromRealDistance(10)>canvasWidth) {
                meter = "5";
                length = mMapView.getPixelLengthFromRealDistance(5);
                if (mMapView.getPixelLengthFromRealDistance(5) > canvasWidth) {
                    meter = "2";
                    length = mMapView.getPixelLengthFromRealDistance(2);
                    if (mMapView.getPixelLengthFromRealDistance(2) > canvasWidth) {
                        meter = "1";
                        length = mMapView.getPixelLengthFromRealDistance(1);
                        if (mMapView.getPixelLengthFromRealDistance(1) > canvasWidth) {
                            meter = "0.5";
                            length = mMapView.getPixelLengthFromRealDistance(0.5f);
                            if (mMapView.getPixelLengthFromRealDistance(0.5f) > canvasWidth) {
                                meter = "0.2";
                                length = mMapView.getPixelLengthFromRealDistance(0.2f);
                            }
                        }
                    }
                }
            }
            drawScale(canvas,length);
            drawText(canvas,meter);
        }
    }

    private void drawScale(Canvas canvas,float length){
        canvas.drawLine(canvasWidth/2-0.5f*length,0.9f*canvasHeight,canvasWidth/2+0.5f*length,0.9f*canvasHeight,mPaint);
        canvas.drawLine(canvasWidth/2-0.5f*length,0.8f*canvasHeight,canvasWidth/2-0.5f*length,canvasHeight,mPaint);
        canvas.drawLine(canvasWidth/2+0.5f*length,0.8f*canvasHeight,canvasWidth/2+0.5f*length,canvasHeight,mPaint);
    }
    private void drawText(Canvas canvas,String  meter){
        float[] floats = new float[meter.length()];
        mPaint.setTextSize(canvasHeight/4);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        canvas.drawText(meter+"米",canvasWidth/2,canvasHeight/3-fontMetrics.ascent,mPaint);
    }

    public void setMapView(MapView mapView){
        mMapView=mapView;
    }
}
