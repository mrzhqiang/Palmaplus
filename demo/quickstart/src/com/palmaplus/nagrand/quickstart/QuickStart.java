package com.palmaplus.nagrand.quickstart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.geos.GeometryFactory;
import com.palmaplus.nagrand.geos.Point;
import com.palmaplus.nagrand.navigate.NavigateManager;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.layer.FeatureLayer;

import java.io.File;

/**
 * Created by Overu on 2015/4/29.
 */
public class QuickStart extends Activity {
  MapView mapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Engine engine = Engine.getInstance(); //初始化引擎
    engine.startWithLicense(Constant.APP_KEY, this); //设置验证lincense，可以通过开发者平台去查找自己的lincense

    final DataSource dataSource = new DataSource("http://api.ipalmap.com/"); //填写服务器的URL
    mapView = new MapView("default", this);
    setContentView(mapView);

    dataSource.requestMaps(new DataSource.OnRequestDataEventListener<DataList<MapModel>>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, DataList<MapModel> data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0) //如果列表中的地图数量是0，请去开发者平台添加一些地图
          return;
        dataSource.requestPOIChildren(MapModel.POI.get(data.getPOI(0)), new DataSource.OnRequestDataEventListener<LocationList>() {
          @Override
          public void onRequestDataEvent(DataSource.ResourceState state, LocationList data) {
            if (state != DataSource.ResourceState.ok)
              return;
            if (data.getSize() == 0) //如果是0说明这套图没有楼层，请反馈给我们
              return;
            dataSource.requestPlanarGraph(
                    LocationModel.id.get(data.getPOI(0)), //获取这套图的默认楼层id
                    new DataSource.OnRequestDataEventListener<PlanarGraph>() { //发起获取一个平面图的请求
                      @Override
                      public void onRequestDataEvent(DataSource.ResourceState state, PlanarGraph data) {
                        if (state == DataSource.ResourceState.ok) {
                          mapView.drawPlanarGraph(data);  //加载平面图
                          mapView.start(); //开始绘制地图

                        } else {
                          //error
                        }
                      }
                    });
          }
        });
      }
    });


  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.drop();
  }
}
