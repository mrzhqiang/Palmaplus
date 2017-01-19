package com.palmaplus.nagrand.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.palmaplus.nagrand.core.Value;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.demo.R;
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
 * 演示了地图的创建 -- new MapView对象
 * 至少包含三个控件:
 *     RelativeLayout(R.id.map_view_container) - 地图视图的父容器
 *     mOverlayContainer(R.id.map_overlay_container) - 地图覆盖物父容器
 *     Spinner(R.id.map_overlay_container) - 楼层列表
 */
public abstract class MapAbstractActivity2 extends NagrandBaseActivity {
  protected RelativeLayout mOverlayContainer;
  protected MapView mMapView;
  protected RelativeLayout mMapContainer;

  protected DataSource mDataSource;
  protected MapOptions mMapOptions;
  protected long mMallId = -1;

  protected Handler mHandler;

  // 下面控件不是加载地图所需
  protected RadioGroup mRadioGroup;
  protected RadioButton mRadio1;
  protected RadioButton mRadio2;
  protected RadioButton mRadio3;
  protected TextView mDesc;
  protected PlanarGraph mPlanarGraph;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(createViewByXML());

    // 创建MapView对象
    mMapContainer = (RelativeLayout) findViewById(R.id.map_view_container);
    String name = getLurConfigName();
    if (name == null){
      name = "default";
    }
    mMapView = new MapView(name, this); // "default" 与lua中配置一直，设置使用哪一种配置
    mMapContainer.addView(mMapView, RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);

    initData(); // 初始化数据

    initMap();

    setupListener();

    initLoadMap();
  }

  /*
  * 初始化数据
  * 包括：传递参数、地图配置数据等
  * */
  protected void initData(){
    // 商场id
    mMallId = Constant.DEFAULT_MALL_ID;
  }

  /*
  * 初始化地图
  * 包括：view控件、地图相关对象、类变量等
  * */
  protected void initMap(){
    // view控件
    mOverlayContainer = (RelativeLayout) findViewById(R.id.map_overlay_container);

    // 地图相关对象
    mDataSource = new DataSource(Constant.SERVER_URL); // 与地图数据服务器绑定
    mMapOptions = new MapOptions(); // 修改它，可以改变mapView交互的一些属性
    mMapView.setMapOptions(mMapOptions);
    mMapView.setOverlayContainer(mOverlayContainer);

    // 类变量
    mHandler = new Handler(getMainLooper());

    mRadioGroup = (RadioGroup) findViewById(R.id.map_function_radio_group);
    mRadio1 = (RadioButton) findViewById(R.id.radio_btn_1);
    mRadio2 = (RadioButton) findViewById(R.id.radio_btn_2);
    mRadio3 = (RadioButton) findViewById(R.id.radio_btn_3);
    mDesc = (TextView) findViewById(R.id.map_function_desc);

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

  /*
  * 第一次加载地图
  * */
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
              DataListAdapter<LocationModel> floorAdp = new DataListAdapter<LocationModel>(MapAbstractActivity2.this,
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
        Log.w("InteractionActivity", "position = " + position);
        LocationModel item = (LocationModel) parent.getAdapter().getItem(position);
        // TODO 根据floorId，加载地图数据
        showProgress(mHandler, "提示", "加载地图数据中...");
        mDataSource.requestPlanarGraph(LocationModel.id.get(item),
                new DataSource.OnRequestDataEventListener<PlanarGraph>() {
                  @Override
                  public void onRequestDataEvent(DataSource.ResourceState state, PlanarGraph planarGraph) {
                    Log.w("InteractionActivity", "state = " + state.toString());
                    closeProgress(mHandler);
                    if (state == DataSource.ResourceState.ok){
                      mPlanarGraph = planarGraph;
                      mMapView.drawPlanarGraph(planarGraph);
                      mMapView.start();
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

    mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int index) {
        if (mPlanarGraph == null){ // 无数据，直接返回
          return;
        }

        String name = getLurName(radioGroup, index);
        if (name == null){ // 不需要更换lur文件
          return;
        }

        mPlanarGraph.obtain(); // 先使计数器加载，避免调用mMapView.drop()时数据被清空
        mMapView.drop(); // 销毁MapView对象
        mMapContainer.removeView(mMapView);

        // 从新加载新的lue配置
        mMapView = new MapView(name, MapAbstractActivity2.this); // "default" 与lua中配置一直，设置使用哪一种配置
        mMapContainer.addView(mMapView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mMapView.drawPlanarGraph(mPlanarGraph);
        mMapView.start();

        resetMapView(); // 重设mapView相关属性
      }
    });

  }

  /*
  * 重新创建mapView对象时，对mapView的设置
  * */
  protected void resetMapView(){
    if (mMapView != null){
      mMapView.setMapOptions(mMapOptions);
      mMapView.setOverlayContainer(mOverlayContainer);

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
  }

  /*
  * activity的界面
  * */
  protected abstract int createViewByXML();

  protected abstract String getLurConfigName();

  protected abstract String getLurName(RadioGroup radioGroup, int index);

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mMapView != null){
      mMapView.drop();
    }
    mDataSource.drop();
  }

}
