package com.palmaplus.nagrand.quickstart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.geos.GeometryFactory;
import com.palmaplus.nagrand.geos.Point;
import com.palmaplus.nagrand.position.Location;
import com.palmaplus.nagrand.position.PositioningManager;
import com.palmaplus.nagrand.position.ble.BeaconPositioningManager;
import com.palmaplus.nagrand.position.ble.Region;
import com.palmaplus.nagrand.position.util.PositioningUtil;
import com.palmaplus.nagrand.position.wifi.SinglePositioningManager;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.layer.FeatureLayer;

/**
 * Created by Overu on 2015/5/14.
 */
public class PositioningDemo extends Activity {

  MapView mapView;
  PositioningManager positioningManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Engine engine = Engine.getInstance();
    engine.startWithLicense(Constant.APP_KEY, this);

    mapView = new MapView("default", this);
    setContentView(mapView);

    //新建一个定位对象，并指定一个mac地址
//    positioningManager =
//            new SinglePositioningManager("58:44:98:DD:7C:4F");

    //新建一个蓝牙定位
    positioningManager = new BeaconPositioningManager( // 蓝牙定位管理对象
            this,
            6,
            "AppKey"
    );

    final DataSource dataSource = new DataSource("http://api.ipalmap.com/");

    dataSource.requestPlanarGraph(
            1672, //请先确认你所需要的地图是否包含了定位的权限
            new DataSource.OnRequestDataEventListener<PlanarGraph>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, PlanarGraph data) {
        if (state == DataSource.ResourceState.ok) {
          mapView.drawPlanarGraph(data);

          final FeatureLayer positioningLayer = new FeatureLayer("positioning"); //新建一个放置定位点的图层
          mapView.addLayer(positioningLayer);  //把这个图层添加至MapView中
          mapView.setLayerOffset(positioningLayer); //让这个图层获取到当前地图的坐标偏移

          //添加一个Feature，用于展现定位点
          Point point = GeometryFactory.createPoint(new Coordinate(0, 0));//添加一个定位点
          MapElement mapElement = new MapElement(); //设置它的属性
          mapElement.addElement("id", new BasicElement(1L)); //设置id
          Feature feature = new Feature(point, mapElement); //创建Feature
          positioningLayer.addFeature(feature); //把这个Feature添加到FeatureLayer中

          positioningManager.setOnLocationChangeListener(new PositioningManager.OnLocationChangeListener<Location>() { //定位监听的事件，如果得到了新的位置数据，就会调用这个方法
            @Override
            public void onLocationChange(PositioningManager.LocationStatus status, Location oldLocation, Location newLocation) {  //分别代表着上一个位置点和新位置点
              switch (status) {
                case MOVE:
                PositioningUtil.positionLocation(1, positioningLayer, newLocation); //这里的id就是上面设置的id，这个接口的意思是把id为1的定位点移动到newLocation的位置上
                  break;
              }
            }
          });
          positioningManager.start(); //开启定位
          mapView.start();
        } else {
          //error
        }

      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.drop();
    positioningManager.stop();
    positioningManager.close();
  }
}
