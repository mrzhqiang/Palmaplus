package com.palmaplus.nagrand.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.palmaplus.nagrand.core.Value;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.layer.FeatureLayer;
import com.palmaplus.nagrand.view.layer.Layer;

/**
 * Created by zhang on 2015/10/21.
 */
public class MapPublicServiceActivity extends MapAbstractActivity2 {

  @Override
  protected void initMap() {
    super.initMap();

    mRadio1.setText("ALL");
    mRadio2.setText("洗手间");
    mRadio3.setText("楼梯");
    mDesc.setText("请选择要显示的公共设施！");

  }

  @Override
  protected void setupListener() {
    super.setupListener();

  }

  @Override
  protected int createViewByXML() {
    return R.layout.activity_map_function;
  }

  @Override
  protected String getLurConfigName() {
    return "publicService";
  }

  @Override
  protected String getLurName(RadioGroup radioGroup, int index) {
    String name = "publicService";
    switch (index){
      case R.id.radio_btn_1:
        mMapView.visibleLayerAllFeature("Facility", true);
        break;
      case R.id.radio_btn_2:
        mMapView.visibleLayerAllFeature("Facility", false);
        mMapView.visibleLayerFeature("Facility", "category", new Value(23024000L), true);
        mMapView.visibleLayerFeature("Facility", "category", new Value(23025000L), true);
        break;
      case R.id.radio_btn_3:
        mMapView.visibleLayerAllFeature("Facility", false);
        mMapView.visibleLayerFeature("Facility", "category", new Value(24091000L),true);
        break;
    }
    return null;
  }

}
