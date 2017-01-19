package com.palmaplus.nagrand.demo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.view.overlay.OverlayCell;

/**
 * Created by androidlzj on 2015/6/24.
 */
public class LogoMark2 extends ImageView implements OverlayCell {
  private double[] geoCoordinate;

  public LogoMark2(Context context) {
    super(context);
  }

  public LogoMark2(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public LogoMark2(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }


  @Override
  public void init(double[] doubles) {
    geoCoordinate = doubles;
  }

  @Override
  public double[] getGeoCoordinate() {
    return geoCoordinate;
  }


  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void position(double[] doubles) {

    setX((float) doubles[0] - getWidth() / 2);
    setY((float) doubles[1]);
  }
}
