package com.palmaplus.nagrand.demo.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.palmaplus.nagrand.data.*;
import com.palmaplus.nagrand.demo.R;
import com.palmap.widget.Compass;
import com.palmap.widget.FloorSelector;
import com.palmap.widget.Scale;
import com.palmap.widget.ZoomController;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.gestures.OnDoubleTapListener;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;
import com.palmaplus.nagrand.view.gestures.OnZoomListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by androidlzj on 2015/8/11.
 */
public class InteractionActivity extends MapAbstractActivity implements View.OnClickListener{
  private CheckBox mClick;
  private CheckBox mZoomControl;
  private CheckBox mTranslationControl;
  private CheckBox mRotationControl;
  private CheckBox mAngleControl;


  private Button rotateLeft;
  private Button rotateRight;
  private Button get_zoomlevel;
  private Button restore_state;
  private Scale scale;
  private Compass newcompass;




  @Override
  int createViewByXML() {
    return R.layout.activity_map_interaction;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    mHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Rect outRect = new Rect();
        InteractionActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        statusBarHeight = outRect.top;
        Log.w("test", "top" + String.valueOf(outRect.top));
      }
    }, 2000L);
    //mMapView.SetMaxZoomLevel(7);
    //mMapView.SetMinZoomLevel(1);
  }

  @Override
  protected void initMap() {
    super.initMap();

    mClick = (CheckBox) findViewById(R.id.click);
    mZoomControl = (CheckBox) findViewById(R.id.zoom_control);
    mTranslationControl = (CheckBox) findViewById(R.id.translation_control);
    mRotationControl = (CheckBox) findViewById(R.id.rotation_control);
    mAngleControl = (CheckBox) findViewById(R.id.angle_control);

    rotateLeft = (Button)findViewById(R.id.rotate_left);
    rotateRight = (Button)findViewById(R.id.rotate_right);
    get_zoomlevel = (Button)findViewById(R.id.zoomlevel);
    restore_state = (Button)findViewById(R.id.restore_state);
    scale = (Scale)findViewById(R.id.scale);
    scale.setMapView(mMapView);
    newcompass = (Compass)findViewById(R.id.compass);
    newcompass.setMapView(mMapView);
    ZoomController zoomController = (ZoomController)findViewById(R.id.zoomcontroller);
    zoomController.setMapView(mMapView);
    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if (mLocationList!=null){
          mHandler.post(new Runnable() {
            @Override
            public void run() {
              FloorSelector floorSelector = (FloorSelector) InteractionActivity.this.findViewById(R.id.floorselector);
              floorSelector.init(mMapView,mLocationList);
            }
          });
          timer.cancel();
        }
      }
    },0,100L);
  }
  /*protected void setTitle(){
    InteractionActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
  }*/

  @Override
  protected void setupListener() {
    super.setupListener();

    get_zoomlevel.setOnClickListener(this);

    restore_state.setOnClickListener(this);


    rotateRight.setOnClickListener(this);

    rotateLeft.setOnClickListener(this);
    //开启或关闭地图旋转事件
    mRotationControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          mMapOptions.setRotateEnabled(true);
        } else {
          mMapOptions.setRotateEnabled(false);
        }

      }
    });

    //开启或关闭地图平移事件
    mTranslationControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          mMapOptions.setMoveEnabled(true);
        } else {
          mMapOptions.setMoveEnabled(false);
        }
      }
    });

    //开启或关闭地图缩放事件
    mZoomControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          mMapOptions.setZoomEnabled(true);
        } else {
          mMapOptions.setZoomEnabled(false);
        }
      }
    });
    //开启或关闭地图点击事件
    mClick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          mMapOptions.setSigleTapEnabled(true);
        } else {
          mMapOptions.setSigleTapEnabled(false);
        }
      }
    });

    mAngleControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          mMapOptions.setSkewEnabled(true);
        } else {
          mMapOptions.setSkewEnabled(false);
        }
      }
    });

    // 地图缩放监听
    mMapView.setOnZoomListener(new OnZoomListener() {

      @Override
      public void preZoom(MapView mapView, float v, float v2) {
        Log.w("InteractionActivity", "准备缩放");
      }

      @Override
      public void onZoom(MapView mapView, boolean b) {
        refreshCompass();
        Log.w("test",String.valueOf(mMapView.GetZoomLevel()));
        if (b) { // true：放大；false：缩小
          Log.w("InteractionActivity", "放大地图");
        } else {
          Log.w("InteractionActivity", "缩小地图");
        }
        Log.w("test","1m"+String.valueOf(mMapView.getPixelLengthFromRealDistance(1)));
        Log.w("test","2m"+String.valueOf(mMapView.getPixelLengthFromRealDistance(2)));
        Log.w("test","5m"+String.valueOf(mMapView.getPixelLengthFromRealDistance(5)));
      }

      @Override
      public void postZoom(MapView mapView, float v, float v2) {
        Log.w("InteractionActivity", "缩放结束");
        scale.invalidate();
      }
    });

    // 地图点击事件监听
    mMapView.setOnSingleTapListener(new OnSingleTapListener() {
      @Override
      public void onSingleTap(MapView mapView, float x, float y) {
        Log.w("InteractionActivity", "单击位置： x = " + x + "; y = " + y);
        Feature feature = mapView.selectFeature(x, y);
        if (feature == null){
          Log.w("InteractionActivity", "当前位置在地图之外");
          return;
        }
        Param<String> name = new Param<String>("name", String.class);
        Param<Long> id = new Param<Long>("id", Long.class);
        Param<Long> CATEGORY_ID = new Param<Long>("category", Long.class);
        String n = name.get(feature);
        Log.w("InteractionActivity", "name = " + n + "; id = " + id.get(feature)+"category"+ CATEGORY_ID.get(feature));
      }
    });
    mMapView.setOnDoubleTapListener(new OnDoubleTapListener() {
      @Override
      public boolean onDoubleTap(MapView mapView, float v, float v1) {
        mMapView.zoomIn();
        return false;
      }
    });

    //切换楼层触发
    mMapView.setOnChangePlanarGraph(new MapView.OnChangePlanarGraph() {
      @Override
      public void onChangePlanarGraph(PlanarGraph planarGraph, PlanarGraph planarGraph1, long l, long l1) {
        mMapView.setMaxZoom(220F);
        mMapView.setMinZoom(4F);
        mMapView.setMinAngle(65F);
        mMapView.postDelayed(new Runnable() {
          @Override
          public void run() {
            scale.invalidate();
          }
        },30L);

      }
    });
  }

  public void onClick(View v){
    switch (v.getId()){
      case R.id.zoomlevel:
        Log.w("zoomlevel", String.valueOf(mMapView.GetZoomLevel()));
        break;
      case R.id.restore_state:
        mMapView.rotateByNorth(mMapView.converToScreenCoordinate(map_center.x, map_center.y), 0);
        mMapView.moveToPoint(new Coordinate(map_center.x, map_center.y), true, 300);
        mMapView.postDelayed(new Runnable() {
          @Override
          public void run() {
            mMapView.getOverlayController().refresh();
            refreshCompass();
            scale.invalidate();
          }
        }, 350L);
        break;
      case R.id.rotate_right:
        double angle = mMapView.getRotate();
        angle-=30;
        mMapView.rotateByNorth(mMapView.converToScreenCoordinate(map_center.x, map_center.y), -angle);
        mMapView.postDelayed(new Runnable() {
          @Override
          public void run() {
            refreshCompass();
            scale.invalidate();
          }
        }, 100L);
        break;
      case R.id.rotate_left:
        double angle2 = mMapView.getRotate();
        angle2 +=30;
        mMapView.rotateByNorth(mMapView.converToScreenCoordinate(map_center.x, map_center.y), -angle2);
        mMapView.postDelayed(new Runnable() {
          @Override
          public void run() {
            refreshCompass();
            scale.invalidate();
          }
        }, 100L);
        break;
      default:
        break;
    }

  }

  //刷新指南针
  private void refreshCompass(){
    newcompass.invalidate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
