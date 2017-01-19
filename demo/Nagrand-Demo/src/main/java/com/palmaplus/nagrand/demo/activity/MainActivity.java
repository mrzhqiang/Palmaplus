package com.palmaplus.nagrand.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.palmaplus.nagrand.demo.base.BaseListFeatureGenerator;
import com.palmaplus.nagrand.demo.base.FunctionNode;
import com.palmaplus.nagrand.demo.base.NagApplication;

import java.util.ArrayList;

/**
 * Created by Overu on 2015/8/10.
 */
public class MainActivity extends ListBaseActivity {

  @NagApplication
  Context application;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ArrayList<FunctionNode> root = new ArrayList<FunctionNode>() {
      {
        FunctionNode[] subRoot1 = new FunctionNode[]{
                new FunctionNode("颜色", MapColorActivity.class.getName(), null),
                new FunctionNode("贴图", MapTextureActivity.class.getName(), null),
                new FunctionNode("字体", MapTypefaceActivity.class.getName(), null),
                new FunctionNode("公共设施", MapPublicServiceActivity.class.getName(), null),
                new FunctionNode("地图图层", MapLayerActivity.class.getName(), null),
                new FunctionNode("居中变色", MapPOICenterActivity.class.getName(), null),
        };
        FunctionNode[] subRoot2 = new FunctionNode[]{
                new FunctionNode("WIFI", WIFIPositionActivity.class.getName(), null),
                new FunctionNode("Beacon", BlePositionActivity.class.getName(), null),
        };

        add(new FunctionNode("交互", InteractionActivity.class.getName(), null));
        add(new FunctionNode("地图功能", ListBaseActivity.class.getName(), subRoot1));
        add(new FunctionNode("Overlay",MapOverlayActivity.class.getName(), null));
        add(new FunctionNode("定位", ListBaseActivity.class.getName(), subRoot2));
        add(new FunctionNode("导航", NavigateActivity.class.getName(), null));
          add(new FunctionNode("Datasource",DatasourceActivity.class.getName(),null));
      }
    };
      ArrayList<FunctionNode> new_root = new ArrayList<FunctionNode>(){

      };
    Intent intent = getIntent();
    intent.putParcelableArrayListExtra(BaseListFeatureGenerator.ROOT, root);
    super.onCreate(savedInstanceState);
  }


}
