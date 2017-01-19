package com.palmaplus.nagrand.demo.activity;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.palmaplus.nagrand.demo.R;

/**
 * Created by zhang on 2015/10/21.
 */
public class MapTypefaceActivity extends MapAbstractActivity2 {

  @Override
  protected void initMap() {
    super.initMap();

    mRadio1.setText("android默认字体");
    mRadio2.setText("仿宋");
    mRadio3.setVisibility(View.GONE);
    mDesc.setText("这里可以选择两种不同的字体！");

  }

  @Override
  protected int createViewByXML() {
    return R.layout.activity_map_function;
  }

  @Override
  protected String getLurConfigName() {
    return "typeface_1";
  }

  @Override
  protected String getLurName(RadioGroup radioGroup, int index) {
    String name = "typeface_1";
    switch (index){
      case R.id.radio_btn_1:
        break;
      case R.id.radio_btn_2:
        name = "typeface_2";
        break;
      case R.id.radio_btn_3:
        break;
    }
    return name;
  }

}
