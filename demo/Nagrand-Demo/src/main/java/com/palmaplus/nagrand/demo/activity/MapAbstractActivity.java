package com.palmaplus.nagrand.demo.activity;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.core.Value;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.demo.*;
import com.palmaplus.nagrand.demo.base.NagrandBaseActivity;
import com.palmaplus.nagrand.demo.constance.Constant;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.view.MapOptions;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.adapter.DataListAdapter;
import com.palmaplus.nagrand.view.layer.FeatureLayer;
import com.palmaplus.nagrand.view.layer.Layer;

/**
 * Created by Overu on 2015/10/12.
 * 演示了地图的创建 -- 由xml创建MapView对象
 * 至少包含三个控件:
 *     mMapView(R.id.mapview) - 地图视图
 *     mOverlayContainer(R.id.map_overlay_container) - 地图覆盖物父容器
 *     Spinner(R.id.map_overlay_container) - 楼层列表
 */
public abstract class MapAbstractActivity extends NagrandBaseActivity {
  protected RelativeLayout mOverlayContainer;
  protected MapView mMapView;

  protected DataSource mDataSource;
  protected MapOptions mMapOptions;
  protected long mMallId = -1;

  protected Handler mHandler;
  protected int screen_height;
  protected int screen_width;
  protected Types.Point map_center;
  protected Types.Point north_point;
  protected int statusBarHeight=0;
  protected LocationList mLocationList=null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle();
    setContentView(createViewByXML());

    initData(); // 初始化数据

    initMap();

    setupListener();

    initLoadMap();



  }
  protected void setTitle(){
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
  }

  /*
  * 初始化数据
  * 包括：传递参数、地图配置数据等
  * */
  protected void initData(){
    // 商场id
    mMallId = Constant.DEFAULT_MALL_ID;
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    screen_height = metrics.heightPixels;
    screen_width = metrics.widthPixels;
    Log.w("test",String.valueOf(screen_width)+" "+String.valueOf(screen_height));
  }

  /*
  * 初始化地图
  * 包括：view控件、地图相关对象、类变量等
  * */
  protected void initMap(){
    // view控件
    mMapView = (MapView) findViewById(R.id.mapview);
    mOverlayContainer = (RelativeLayout) findViewById(R.id.map_overlay_container);

    // 地图相关对象
    mDataSource = new DataSource(Constant.SERVER_URL); // 与地图数据服务器绑定
    mMapOptions = new MapOptions(); // 修改它，可以改变mapView交互的一些属性
    mMapView.setMapOptions(mMapOptions);
    mMapView.setOverlayContainer(mOverlayContainer);
    // 类变量
    mHandler = new Handler(getMainLooper());
  }

  /*
  * 设置监听器
  * 包括：地图相关监听器、view控件相关等
  * */
  protected void setupListener(){

    /*
    * 添加绘制层监听 -- TODO 这里可以设置哪些东西需要绘制或不绘制
    * */
    mMapView.setOnDrawingLayer(new MapView.OnDrawingLayer() {
      @Override
      public void OnDrawingLayer(MapView mapView, Layer layer, MapView.LayerDrawingStatus layerDrawingStatus) {
        if (layer.getName().equals("Area_text") && layerDrawingStatus == MapView.LayerDrawingStatus.DataComplete) {
          ((FeatureLayer) layer).visibleRenderable("category", new Value(24001000L), false); // 不绘制办公桌文字
          ((FeatureLayer) layer).visibleRenderable("category", new Value(24004000L), false); // 不绘制办公椅文字
          ((FeatureLayer) layer).visibleRenderable("category", new Value(24005000L), false); // 不绘制椅子文字
          ((FeatureLayer) layer).visibleRenderable("category", new Value(24002000L), false); // 不绘制桌子文字
        }
      }
    });

  }

  protected void initLoadMap(){
    final Spinner spinnerFloor = (Spinner) findViewById(R.id.spinner);

    // TODO 根据商场ID，请求商场数据
    showProgress("提示", "加载商场数据中...");
    mDataSource.requestPOIChildren(mMallId, new DataSource.OnRequestDataEventListener<LocationList>() {
      @Override
      public void onRequestDataEvent(final DataSource.ResourceState state, final LocationList data) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            closeProgress();
            if (state == DataSource.ResourceState.ok){
              mLocationList =data;
              DataListAdapter<LocationModel> floorAdp = new DataListAdapter<LocationModel>(MapAbstractActivity.this,
                      android.R.layout.simple_spinner_item, data, Constant.FLOOR_SHOW_FIELD);
              floorAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spinnerFloor.setAdapter(floorAdp);
            } else {
              DialogUtils.showShortToast("商场数据加载失败！");
            }
          }
        });
      }
    });

    spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.w("MapAbstractActivity", "position = " + position);
        LocationModel item = (LocationModel) parent.getAdapter().getItem(position);
        // TODO 根据floorId，加载地图数据
        showProgress(mHandler, "提示", "加载地图数据中...");
        mDataSource.requestPlanarGraph(LocationModel.id.get(item),
                new DataSource.OnRequestDataEventListener<PlanarGraph>() {
                  @Override
                  public void onRequestDataEvent(DataSource.ResourceState state, PlanarGraph planarGraph) {
                    Log.w("MapAbstractActivity",  "state = " + state.toString());
                    closeProgress(mHandler);
                    if (state == DataSource.ResourceState.ok){
                      mMapView.drawPlanarGraph(planarGraph);

                      afterDrawPlanarGraph();

                      mMapView.start();
                      mMapView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          map_center = mMapView.converToWorldCoordinate(screen_width / 2, (screen_height-statusBarHeight) / 2);
                          north_point = mMapView.converToWorldCoordinate(screen_width / 2, (screen_height-statusBarHeight) / 2 - 1000);
                        }
                      },2200L);

                    } else {
                      DialogUtils.showShortToast(mHandler, "地图数据加载失败！");
                    }
                  }
                });
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

  }

  abstract int createViewByXML();

  /*
  * 在mapView获取数据之后, 一些操作
  * 比如，设置定位层
  * */
  protected void afterDrawPlanarGraph(){

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mMapView.drop();
    mDataSource.drop();
  }

}
