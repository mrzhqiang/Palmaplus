package com.palmaplus.nagrand.quickstart;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.io.CacheAsyncHttpClient;
import com.palmaplus.nagrand.io.FileCacheMethod;
import com.palmaplus.nagrand.position.Location;

import java.io.File;

/**
 * Created by Overu on 2015/5/14.
 */
public class DataSourceDemo extends Activity {

  private static String TAG = DataSource.class.getName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Engine engine = Engine.getInstance();
    engine.startWithLicense(Constant.APP_KEY, this); //license

    //添加带缓存的DataSource
//    CacheAsyncHttpClient cacheAsyncHttpClient = new CacheAsyncHttpClient("http://api.ipalmap.com/");
//    FileCacheMethod fileCacheMethod = new FileCacheMethod(Environment.getExternalStorageDirectory() + File.separator +  "Nagrand1/cache1/");
//    cacheAsyncHttpClient.reset(fileCacheMethod);
    DataSource dataSource = new DataSource("http://api.ipalmap.com/");

    //获取一个平面的渲染数据
    dataSource.requestPlanarGraph(
            1672,
            new DataSource.OnRequestDataEventListener<PlanarGraph>() {
              @Override
              public void onRequestDataEvent(DataSource.ResourceState state, PlanarGraph data) {
                if (state != DataSource.ResourceState.ok) {
                  return;
                }
                Log.w(TAG, "requestPlanarGraph " + data.getLayerCount());

                data.drop(); //如果返回的数据不需要持久保存，请用这种方式释放内存
              }
            });

    //获取到当前所有可用的地图列表
    dataSource.requestMaps(new DataSource.OnRequestDataEventListener<DataList<MapModel>>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, DataList<MapModel> data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0)
          return;
        MapModel map = data.getPOI(0);
        Log.w(TAG, "requestMaps " + LocationModel.id.get(map));

        data.drop();
      }
    });

    //根据floorId获取所有的category
    dataSource.requestMap(1, new DataSource.OnRequestDataEventListener<MapModel>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, MapModel data) {
        if (state != DataSource.ResourceState.ok)
          return;
        Log.w(TAG, "requestMap " + MapModel.id.get(data));
        Log.w(TAG, "requestMap region name " + RegionModel.name.get(MapModel.region.get(data)));

        data.drop();
      }
    });

    //根据floorId获取所有的shop
    dataSource.requestCategory(15000000, new DataSource.OnRequestDataEventListener<CategoryModel>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, CategoryModel data) {
        if (state != DataSource.ResourceState.ok)
          return;
        Log.w(TAG, "requestCategory " + CategoryModel.id.get(data));

        data.drop();
      }
    });

    dataSource.requestCategories(1671, 1672, new DataSource.OnRequestDataEventListener<DataList<CategoryModel>>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, DataList<CategoryModel> data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0)
          return;
        CategoryModel poi = data.getPOI(0);
        Log.w(TAG, "requestCategories " + CategoryModel.id.get(poi));

        data.drop();
      }
    });

    //根据mapId获取所有的category
    dataSource.requestPOI(26796, new DataSource.OnRequestDataEventListener<LocationModel>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, LocationModel data) {
        if (state != DataSource.ResourceState.ok)
          return;
        Log.w(TAG, "requestPOI " + LocationModel.id.get(data));
        Log.w(TAG, "requestPOI Category " + LocationModel.category.get(data));

        data.drop();
      }
    });

    //根据一个poiId获取POI详情
    dataSource.requestPOIChildren(1672, new DataSource.OnRequestDataEventListener<LocationList>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, LocationList data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0)
          return;

        LocationModel poi = data.getPOI(0);
        Log.w(TAG, "requestPOIChildren " + LocationModel.type.get(poi));

        data.drop();
      }
    });

    //搜索POI
    dataSource.search("上海图聚", 1, 10, new long[] {}, new long[] {}, new DataSource.OnRequestDataEventListener<LocationPagingList>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, LocationPagingList data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0)
          return;
        LocationModel poi = data.getPOI(0);
        Log.w(TAG, "search " + LocationModel.id.get(poi));

        data.drop();
      }
    });

    //根据一个坐标查找包含它的所有POI信息
    dataSource.requestCoordBeContained(13523521.0922, 3663496, 1, 10, new long[]{163, 2}, null, new DataSource.OnRequestDataEventListener<LocationPagingList>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, LocationPagingList data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0)
          return;
        LocationModel poi = data.getPOI(0);
        Log.w(TAG, "requestCoordBeContained " +  LocationModel.id.get(poi));

        data.drop();
      }
    });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
