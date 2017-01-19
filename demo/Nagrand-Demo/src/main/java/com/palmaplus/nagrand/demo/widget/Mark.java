package com.palmaplus.nagrand.demo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.view.overlay.OverlayCell;

public class Mark extends LinearLayout implements OverlayCell {
  private ImageView mIconView;
  private TextView mPosX;
  private TextView mPosY;
  private TextView mPosId;

  private double[] mGeoCoordinate;
  private int mId;

  public Mark(Context context) {
    super(context);

    initView();
  }

  public Mark(Context context,int id) {
    super(context);

    this.mId = id;
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.item_mark, this);
    mIconView = (ImageView) findViewById(R.id.mark_icon);
    mPosX = (TextView) findViewById(R.id.mark_x);
    mPosY = (TextView) findViewById(R.id.mark_y);
    mPosId = (TextView) findViewById(R.id.mark_id);
    mPosId.setText(String.valueOf(mId));
  }

  public void setMark(int id, double x, double y){
    mId = id;
    mPosId.setText(String.valueOf(id));
    mPosX.setText("x: " + x);
    mPosY.setText("y: " + y);
  }

  public void setMark(int id, double x, double y, int resId) {
    mId = id;
    mPosId.setText(String.valueOf(id));
    mPosX.setText("x: " + x);
    mPosY.setText("y: " + y);
    mIconView.setBackgroundResource(resId);
  }

  @Override
  public void init(double[] doubles) {
    mGeoCoordinate = doubles;
  }

  @Override
  public double[] getGeoCoordinate() {
    return mGeoCoordinate;
  }


  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void position(double[] doubles) {
    setX((float) doubles[0] - getWidth() / 2);
    setY((float) doubles[1] - getHeight() / 2);
  }

}
