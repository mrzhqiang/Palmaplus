package com.palmaplus.nagrand.demo.activity;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.demo.widget.Mark;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/10/22.
 * 演示overlay的添加与删除
 */
public class MapOverlayActivity extends MapAbstractActivity implements View.OnClickListener{
  private TextView mRemoveFirst;
  private TextView mRemoveLast;
  private TextView mRemoveAll;
  private TextView mHideFirst;
  private TextView mShowFirst;
  private int iconId = R.drawable.red;
  private RadioGroup mRadioGroup;

  private List<Mark> mMarkList;
  private int mNum = 0;

  @Override
  protected void initData() {
    super.initData();

    mMarkList = new ArrayList<Mark>();
  }

  @Override
  protected void initMap() {
    super.initMap();

    mRemoveFirst = (TextView) findViewById(R.id.map_overlay_first);
    mRemoveFirst.setOnClickListener(this);
    mRemoveLast = (TextView) findViewById(R.id.map_overlay_last);
    mRemoveLast.setOnClickListener(this);
    mRemoveAll = (TextView) findViewById(R.id.map_overlay_all);
    mRemoveAll.setOnClickListener(this);
    mHideFirst = (TextView) findViewById(R.id.map_overlay_hide);
    mHideFirst.setOnClickListener(this);
    mShowFirst = (TextView) findViewById(R.id.map_overlay_show);
    mShowFirst.setOnClickListener(this);
    mRadioGroup = (RadioGroup)findViewById(R.id.change_icon);
    mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
          case R.id.red:
            iconId = R.drawable.red;
            break;
          case R.id.green:
            iconId = R.drawable.green;
            break;
          case R.id.blue:
            iconId = R.drawable.blue;
            break;
        }
      }
    });
  }

  @Override
  protected void setupListener() {
    super.setupListener();

    // 地图点击事件监听
    mMapView.setOnSingleTapListener(new OnSingleTapListener() {
      @Override
      public void onSingleTap(MapView mapView, float x, float y) {
        Types.Point point = mMapView.converToWorldCoordinate(x, y);
        Mark mark = new Mark(getApplicationContext());
        mark.setMark(++mNum, x, y,iconId);
        mark.init(new double[]{point.x, point.y});
        mapView.addOverlay(mark);
        mMarkList.add(mark);
      }
    });

  }

  @Override
  int createViewByXML() {
    return R.layout.activity_map_overlay;
  }

  @Override
  public void onClick(View view) {
    Log.e("exp", "mMarkList.size() = " + mMarkList.size());
    switch (view.getId()){
      case R.id.map_overlay_first:
        if (mMarkList != null && mMarkList.size() > 0){
          mMapView.removeOverlay(mMarkList.get(0));
          mMarkList.remove(0);
        } else {
          DialogUtils.showShortToast("当前没有覆盖物");
        }
        break;
      case R.id.map_overlay_last:
        if (mMarkList != null && mMarkList.size() > 0){
          Log.e("exp", "index: " + (mMarkList.size() - 1));
          mMapView.removeOverlay(mMarkList.get(mMarkList.size() - 1));
          mMarkList.remove(mMarkList.size() - 1);
        } else {
          DialogUtils.showShortToast("当前没有覆盖物");
        }
        break;
      case R.id.map_overlay_all:
        mMapView.removeAllOverlay();
        if (mMarkList != null && mMarkList.size() > 0){
          mMarkList.clear();
          mNum=0;
        }
        break;
      case R.id.map_overlay_hide:
        if (mMarkList != null && mMarkList.size() > 0) {

        }
        break;
      case R.id.map_overlay_show:
        if (mMarkList != null && mMarkList.size() > 0){

        }
        break;
    }
  }
}
