package com.palmaplus.nagrand.demo.activity;

import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.palmaplus.nagrand.demo.R;

/**
 * Created by zhang on 2015/10/21.
 */
public class MapTextureActivity extends MapAbstractActivity2 {

  @Override
  protected void initMap() {
    super.initMap();

    mRadio1.setText("地毯");
    mRadio2.setText("木质");
    mRadio3.setText("草皮");
    mDesc.setText("请为图聚(F15)装饰个地面！");

  }

  @Override
  protected int createViewByXML() {
    return R.layout.activity_map_function;
  }

  @Override
  protected String getLurConfigName() {
    return "texture_1";
  }

  @Override
  protected String getLurName(RadioGroup radioGroup, int index) {
    String name = "texture_1";
    switch (index){
      case R.id.radio_btn_1:
        break;
      case R.id.radio_btn_2:
        name = "texture_2";
        break;
      case R.id.radio_btn_3:
        name = "texture_3";
        break;
    }
    return name;
  }

}
