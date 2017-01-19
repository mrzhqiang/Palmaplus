package com.palmaplus.nagrand.demo.activity;

import android.widget.TextView;
import com.palmaplus.nagrand.data.Feature;
import com.palmaplus.nagrand.data.LocationModel;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;

/**
 * Created by zhang on 2015/10/22.
 */
public class MapPOICenterActivity extends MapAbstractActivity {
  private TextView mPrompt;
  private Feature mFeature=null;

  @Override
  protected void initMap() {
    super.initMap();

    mPrompt = (TextView) findViewById(R.id.prompt);
    mPrompt.setText("单击屏幕上一点，可以使所在POI居中并且变色哦！");
  }

  @Override
  protected void setupListener() {
    super.setupListener();

    mMapView.setOnSingleTapListener(new OnSingleTapListener() {
      @Override
      public void onSingleTap(MapView mapView, float x, float y) {
        Feature feature = mapView.selectFeature(x, y);
        if(mFeature!=null) {
          mMapView.resetOriginStyle("Area",LocationModel.id.get(mFeature));
        }
        if (feature == null){
          mFeature=null;
          DialogUtils.showShortToast(mHandler, "点击位置在地图之外");
          return;
        }else{
          mFeature=feature;
          mMapView.setRenderableColor("Area",LocationModel.id.get(mFeature),0xffff5722);
        }
        mapView.moveToPoint(mFeature.getCentroid(),true,300);
      }
    });
  }

  @Override
  int createViewByXML() {
    return R.layout.activity_map_poi_center;
  }

}
