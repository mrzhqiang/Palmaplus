package com.palmaplus.nagrand.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.demo.R;
import com.palmaplus.nagrand.demo.constance.Constant;
import com.palmaplus.nagrand.position.Location;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Overu on 2015/5/14.
 */
public class DatasourceActivity extends Activity {
    private static String TAG = DataSource.class.getName();
    private ListView listView;
    private ArrayAdapter<String > arrayAdapter;
    private ArrayList<String> arrayList=new ArrayList<String>();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasource);
        listView = (ListView) findViewById(R.id.datasource_list);
        mHandler = new Handler(getMainLooper());


        DataSource dataSource = new DataSource(Constant.SERVER_URL);


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
                        arrayList.add("requestPlanarGraph " + data.getLayerCount());

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
                arrayList.add("requestMaps " + LocationModel.id.get(map));

                data.drop();
            }
        });


        //根据floorId获取所有的category
        dataSource.requestMap(25, new DataSource.OnRequestDataEventListener<MapModel>() {
            @Override
            public void onRequestDataEvent(DataSource.ResourceState state, MapModel data) {
                if (state != DataSource.ResourceState.ok)
                    return;
                Log.w(TAG, "requestMap " + MapModel.id.get(data));
                arrayList.add("requestMap " + MapModel.id.get(data));

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
                arrayList.add("requestCategory " + CategoryModel.id.get(data));

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
                arrayList.add("requestCategories " + CategoryModel.id.get(poi));
                data.drop();
            }
        });

        //根据mapId获取所有的category
        dataSource.requestPOI(1672, new DataSource.OnRequestDataEventListener<LocationModel>() {
            @Override
            public void onRequestDataEvent(DataSource.ResourceState state, LocationModel data) {
                if (state != DataSource.ResourceState.ok)
                    return;
                Log.w(TAG, "requestPOI " + LocationModel.id.get(data));
                arrayList.add("requestPOI " + LocationModel.id.get(data));

                data.drop();
            }
        });

        //根据一个poiId获取POI详情
        dataSource.requestPOIChildren(25960, new DataSource.OnRequestDataEventListener<LocationList>() {
            @Override
            public void onRequestDataEvent(DataSource.ResourceState state, LocationList data) {
                if (state != DataSource.ResourceState.ok)
                    return;
                if (data.getSize() == 0)
                    return;
                LocationModel poi = data.getPOI(0);
                Log.w(TAG, "requestPOIChildren " + LocationModel.type.get(poi));
                arrayList.add("requestPOIChildren " + LocationModel.type.get(poi));

                data.drop();
            }
        });

        //搜索POI
        dataSource.search("徐", 1, 1000, new long[]{1672L,1752L}, new long[]{}, new DataSource.OnRequestDataEventListener<LocationPagingList>() {
            @Override
            public void onRequestDataEvent(DataSource.ResourceState state, LocationPagingList data) {
                if (state != DataSource.ResourceState.ok)
                    return;
                if (data.getSize() == 0)
                    return;
                for (int i =0;i<data.getSize();i++){
                    Log.w("test",LocationModel.name.get(data.getPOI(i)));
                }
                LocationModel poi = data.getPOI(0);
                Log.w(TAG, "search " + LocationModel.id.get(poi));
                arrayList.add("search " + LocationModel.id.get(poi));

                data.drop();
            }
        });

        //根据楼层ID获取Locantionlist，再根据Locationlist获取所有的Locationmodel，就可以得到ID,NAME,CATEGORY等信息
        dataSource.requestPOIChildren(1672, new DataSource.OnRequestDataEventListener<LocationList>() {
            @Override
            public void onRequestDataEvent(DataSource.ResourceState resourceState, LocationList locationList) {
                if (resourceState == DataSource.ResourceState.ok) {
                    Log.w("test", "总计" + String.valueOf(locationList.getSize()));
                    for (int i = 0; i < locationList.getSize(); i++) {
                        LocationModel locationModel = locationList.getPOI(i);
                        Log.w(TAG, "id:" + String.valueOf(LocationModel.id.get(locationModel)) + " " + "name:" + LocationModel.name.get(locationModel));
                        arrayList.add("id:" + String.valueOf(LocationModel.id.get(locationModel)) + " " + "name:" + LocationModel.name.get(locationModel));
                    }
                    locationList.drop();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setListView();
                        }
                    });
                }
            }
        });


    }
    private void setListView(){
        String[] strings = new String[arrayList.size()];
        for(int i =0;i<arrayList.size();i++){
            strings[i] = arrayList.get(i);
        }
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
