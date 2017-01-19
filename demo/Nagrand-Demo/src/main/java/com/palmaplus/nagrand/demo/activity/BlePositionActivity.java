package com.palmaplus.nagrand.demo.activity;

import com.palmaplus.nagrand.position.PositioningManager;
import com.palmaplus.nagrand.position.ble.BeaconPositioningManager;
import com.palmaplus.nagrand.position.ble.Region;

/**
 * Created by Overu on 2015/10/13.
 * 演示蓝牙定位
 */
public class BlePositionActivity extends PositionActivity {

  @Override
  public PositioningManager createPositionManager() {
    BeaconPositioningManager beaconPositioningManager = new BeaconPositioningManager( // 蓝牙定位管理对象
            this,
            6,
            "4da2b7f1e5114ba4977e5b165289bc30");
    return beaconPositioningManager;
  }
}


