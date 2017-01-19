package com.palmaplus.nagrand.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.demo.constance.Constant;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.demo.tools.FileUtilsTools;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.navigate.NavigateManager;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.adapter.DataListAdapter;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;
import com.palmaplus.nagrand.view.layer.FeatureLayer;

/**
 * Created by Overu on 2015/10/13.
 * 演示导航功能
 * 需要设置三个方面：
 *   1）NavigateManager对象：请求导航线，请求后的回调监听
 *   2）mMapView.setOnSingleTapListener： 获取导航起始点坐标
 *   3）mMapView.setOnChangePlanarGraph：添加导航层
 */
public class NavigateActivity extends MapAbstractActivity {
  private NavigateManager navigateManager;
  private FeatureLayer navigateLayer;

  private double startX;
  private double startY;
  private long startId;
  private double toX;
  private double toY;
  private long toId;
  private long floorId;
  private boolean isNavigating; // 决定什么时候显示“没有导航数据”提示语

  @Override
  protected void initData() {
    super.initData();

    mMallId = Constant.DEFAULT_MALL_ID; // 该地图上有导航线
  }

  @Override
  protected void initMap() {
    super.initMap();

    navigateManager = new NavigateManager(Constant.SERVER_URL); // http://120.55.179.150/nagrand-service/
    navigateManager.setOnNavigateComplete(new NavigateManager.OnNavigateComplete() {
      @Override
      public void onNavigateComplete(NavigateManager.NavigateState navigateState, FeatureCollection featureCollection) {
        closeProgress();
        if (navigateLayer != null && navigateState == NavigateManager.NavigateState.ok) { // TODO 成功返回导航线，先清除旧的导航线，再添加新的导航线
          Log.w("NavigateActivity", "onNavigateComplete->ResourceState.ok");
          startX = 0;
          navigateLayer.clearFeatures();
          navigateLayer.addFeatures(featureCollection);
        } else {
          if (isNavigating) {
            DialogUtils.showLongToast("无导航数据");
          }
        }
        isNavigating = false;
        Log.w("NavigateActivity", "导航线总长" + String.valueOf(navigateManager.getTotalLineLength())+" "+featureCollection.getSize());
        Log.w("NavigateActivity", "导航线本层长度" + String.valueOf(navigateManager.getFloorLineLength(floorId)));
      }
    });

  }

  @Override
  protected void setupListener() {
    super.setupListener();

    // 设置点击事件监听器 TODO 这里获取导航起始点坐标
    mMapView.setOnSingleTapListener(new OnSingleTapListener() {
      @Override
      public void onSingleTap(MapView mapView, float x, float y) {
        Feature feature = mapView.selectFeature(x, y);
        if (feature == null)
          return;
        Types.Point point = mapView.converToWorldCoordinate(x, y);
        if (startX == 0) {
          Log.w("NavigateActivity->onSingleTap","设置起点");
          DialogUtils.showShortToast(mHandler,"已设置起点("+String.valueOf(point.x)+","+String.valueOf(point.y)+")");
          startX = point.x;
          startY = point.y;
          startId = floorId;
        } else {
          Log.w("NavigateActivity->onSingleTap","设置终点");
          DialogUtils.showShortToast(mHandler, "已设置终点("+String.valueOf(point.x)+","+String.valueOf(point.y)+")");
          toX = point.x;
          toY = point.y;
          toId = floorId;
          isNavigating = true;
          showProgress(mHandler, "提示", "请求导航线...");
          navigateManager.navigation(startX, startY, startId, toX, toY, toId); // TODO 请求导航线
        }
      }
    });

    /*
    *设置更换楼层监听器 TODO 每次切换楼层需要重新添加导航层
    * */
    mMapView.setOnChangePlanarGraph(new MapView.OnChangePlanarGraph() {
      @Override
      public void onChangePlanarGraph(PlanarGraph oldPlanarGraph, PlanarGraph newPlanarGraph, long oldPlanarGraphId, long newPlanarGraphId) {
        Log.w("NavigateActivity", "oldPlanarGraphId = " + oldPlanarGraphId + "; newPlanarGraphId = " + newPlanarGraphId);
        floorId = newPlanarGraphId;
        navigateLayer = new FeatureLayer("navigate");
        mMapView.addLayer(navigateLayer);
        mMapView.setLayerOffset(navigateLayer);
        if (navigateManager != null) {
          navigateManager.switchPlanarGraph(newPlanarGraphId);
        }
      }
    });

  }

  @Override
  int createViewByXML() {
    return R.layout.navigate_activity;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    navigateManager.drop(); // TODO 销毁导航管理对象
  }
}
