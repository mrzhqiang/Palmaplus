package com.palmaplus.nagrand.demo.activity;

import android.widget.RadioGroup;
import android.widget.TextView;
import com.palmaplus.nagrand.core.Value;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.layer.FeatureLayer;
import com.palmaplus.nagrand.view.layer.Layer;

/**
 * Created by zhang on 2015/10/22.
 */
public class MapLayerActivity extends MapAbstractActivity2 {


  @Override
  protected void initMap() {
    super.initMap();

    mRadio1.setText("商铺");
    mRadio2.setText("文字");
    mRadio3.setText("公共设施");
    mDesc.setText("请选择要显示的图层！");

  }

  @Override
  protected void setupListener() {
    super.setupListener();

    /*
    * 可以监听每一层绘制时情况
    * */
    mMapView.setOnDrawingLayer(new MapView.OnDrawingLayer() {
      @Override
      public void OnDrawingLayer(MapView mapView, Layer layer, MapView.LayerDrawingStatus layerDrawingStatus) {
        if (layer.getName().equals("Area_text") && layerDrawingStatus == MapView.LayerDrawingStatus.DataComplete) {
          ((FeatureLayer) layer).visibleAllRenderable(false); // 隐藏文字
        }
        if (layer.getName().equals("Facility") && layerDrawingStatus == MapView.LayerDrawingStatus.DataComplete){
          ((FeatureLayer) layer).visibleAllRenderable(false); // 隐藏公共设施
        }
      }
    });
  }

  @Override
  protected int createViewByXML() {
    return R.layout.activity_map_function;
  }

  @Override
  protected String getLurConfigName() {
    return "default";
  }

  @Override
  protected String getLurName(RadioGroup radioGroup, int index) {
    switch (index){
      case R.id.radio_btn_1:
        mMapView.visibleLayerAllFeature("Area", true);
        mMapView.visibleLayerAllFeature("Area_text", false);
        mMapView.visibleLayerAllFeature("Facility", false);
        break;
      case R.id.radio_btn_2:
        mMapView.visibleLayerAllFeature("Area", false);
        mMapView.visibleLayerAllFeature("Area_text", true);
        mMapView.visibleLayerAllFeature("Facility", false);
        break;
      case R.id.radio_btn_3:
        mMapView.visibleLayerAllFeature("Area", false);
        mMapView.visibleLayerAllFeature("Area_text", false);
        mMapView.visibleLayerAllFeature("Facility", true);
        break;
    }
    return null;
  }

}
