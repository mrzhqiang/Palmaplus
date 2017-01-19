package com.palmaplus.nagrand.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.data.Feature;
import com.palmaplus.nagrand.data.LocationModel;
import com.palmaplus.nagrand.data.PlanarGraph;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.demo.widget.Mark;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by zhang on 2015/10/21.
 */
public class MapColorActivity extends MapAbstractActivity  {
  private RadioGroup mRadioGroup;
  private LinearLayout bottomLayout;
  private TextView words;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void initData() {
    super.initData();

  }

  @Override
  protected void initMap() {
    super.initMap();

    bottomLayout = (LinearLayout) findViewById(R.id.map_overlay_bottom);
    bottomLayout.setVisibility(View.GONE);
    words = (TextView)findViewById(R.id.words);
    words.setText("请为图聚选择地板颜色");
    mRadioGroup = (RadioGroup)findViewById(R.id.change_icon);
    mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.red:
            mMapView.setRenderableColor("Area", 953562, 0xff0000);//暂时只支持颜色的动态改变，改变贴图以及字体仍然需要在lua中预先配置。在将来版本中会支持动态改变其他属性。
            break;
          case R.id.green:
            mMapView.setRenderableColor("Area", 953562, 0x00ff00);
            break;
          case R.id.blue:
            mMapView.setRenderableColor("Area", 953562, 0x0000ff);
            break;
        }
      }
    });
  }

  @Override
  protected void setupListener() {
    super.setupListener();
    mMapView.setOnChangePlanarGraph(new MapView.OnChangePlanarGraph() {
      @Override
      public void onChangePlanarGraph(PlanarGraph planarGraph, PlanarGraph planarGraph1, long l, long l1) {
        mMapView.setRenderableColor("Area",953562,0xff0000);
      }
    });

  }

  @Override
  int createViewByXML() {
    return R.layout.activity_map_overlay;
  }

}
