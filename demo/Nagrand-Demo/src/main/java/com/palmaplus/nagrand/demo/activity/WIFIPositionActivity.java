package com.palmaplus.nagrand.demo.activity;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

import com.palmaplus.nagrand.position.PositioningManager;
import com.palmaplus.nagrand.position.wifi.SinglePositioningManager;

/**
 * Created by Overu on 2015/10/13.
 * 演示WiFi定位
 */
public class WIFIPositionActivity extends PositionActivity {

  @Override
  public PositioningManager createPositionManager() {
    WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
    WifiInfo info = wifi.getConnectionInfo();

    Log.w("TAG", "mac = " + info.getMacAddress());
    return new SinglePositioningManager(info.getMacAddress(), "http://location.palmap.cn/ws/comet/"); // 返回WiFi定位管理对象
  }

}
