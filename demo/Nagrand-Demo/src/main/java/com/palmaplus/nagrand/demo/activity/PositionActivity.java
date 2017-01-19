package com.palmaplus.nagrand.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.demo.constance.Constant;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.demo.tools.FileUtilsTools;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.geos.GeometryFactory;
import com.palmaplus.nagrand.geos.Point;
import com.palmaplus.nagrand.position.Location;
import com.palmaplus.nagrand.position.PositioningManager;
import com.palmaplus.nagrand.position.util.PositioningUtil;
import com.palmaplus.nagrand.view.layer.FeatureLayer;

/**
 * Created by Overu on 2015/10/12.
 * 演示定位功能
 * 需要注意的地方：
 * 1）PositioningManager对象：
 * 位置管理，通过设置PositioningManager.OnLocationChangeListener<Location>，监听位置坐标变化;
 * 调用start() - 开始定位。
 * 2）FeatureLayer对象：
 * 定位点显示在该层上 - 在afterDrawPlanarGraph()中添加。
 */
public abstract class PositionActivity extends MapAbstractActivity {
  protected PositioningManager positioningManager;

  FeatureLayer positioningLayer;

  @Override
  protected void initMap() {
    super.initMap();

    positioningManager = createPositionManager(); // TODO 创建定位管理对象
  }

  @Override
  protected void setupListener() {
    super.setupListener();

    findViewById(R.id.btnLocation).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mMapView.checkInitializing()) {
          return;
        }
        positioningManager.start(); // TODO 开始定位
      }
    });

    positioningManager.setOnLocationChangeListener(new PositioningManager.OnLocationChangeListener<Location>() { // TODO 定位监听的事件，如果得到了新的位置数据，就会调用这个方法
      @Override
      public void onLocationChange(PositioningManager.LocationStatus status, final Location oldLocation,
                                   final Location newLocation) {  // 分别代表着上一个位置点和新位置点
        Log.w("TAG", "onLocationChange");
        switch (status){
          case MOVE:
            Coordinate coordinate = newLocation.getPoint().getCoordinate();
            Log.w("onLocationChange", "x = " + coordinate.getX() + ", y = " + coordinate.getY());
            PositioningUtil.positionLocation(1L, positioningLayer, newLocation); // TODO 当第二次返回点位点时，我们就可以让这个定位点开始移动了
            break;
        }


      }
    });
  }

  @Override
  protected void afterDrawPlanarGraph() { // TODO 每次切换楼层，地图绘制前需要添加位置层
    super.afterDrawPlanarGraph();

    positioningLayer = new FeatureLayer("positioning"); //新建一个放置定位点的图层
    mMapView.addLayer(positioningLayer);  // 把这个图层添加至MapView中
    mMapView.setLayerOffset(positioningLayer); // 让这个图层获取到当前地图的坐标偏移

    // 在PositionLayer上添加一个特征点，用于显示定位点
    Point point = GeometryFactory.createPoint(new Coordinate(0, 0));
    MapElement mapElement = new MapElement();
    mapElement.addElement("id", new BasicElement(1L)); // 1L为特征点ID，下面需要
    Feature feature = new Feature(point, mapElement);
    positioningLayer.addFeature(feature);


  }

  public abstract PositioningManager createPositionManager();

  @Override
  int createViewByXML() {
    return R.layout.position_activity;
  }

  @Override
  protected void onDestroy() {
    positioningManager.stop();
    positioningManager.close();
    super.onDestroy();
  }
}
