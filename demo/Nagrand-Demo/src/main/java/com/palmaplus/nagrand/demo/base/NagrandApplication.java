package com.palmaplus.nagrand.demo.base;

import android.app.Application;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.demo.constance.Constant;
import com.palmaplus.nagrand.demo.module.ApplicationModule;
import com.palmaplus.nagrand.demo.tools.DialogUtils;
import com.palmaplus.nagrand.demo.tools.FileUtilsTools;


import java.util.Arrays;
import java.util.List;

/**
 * Created by Overu on 2015/8/7.
 */
public class NagrandApplication extends Application {

  public static NagrandApplication instance;
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

    Log.w("test",Environment.getExternalStorageDirectory().getPath());


    if (FileUtilsTools.checkoutSDCard()) {
      FileUtilsTools.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, "font");
      FileUtilsTools.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, Constant.LUR_NAME);
    } else {
      DialogUtils.showShortToast("未找到SDCard");
    }

    Engine engine = Engine.getInstance(); //初始化引擎
    engine.startWithLicense(Constant.APP_KEY, this); //设置验证lincense，可以通过开发者平台去查找自己的lincense
  }

  protected List<Object> getModules() {
    return Arrays.<Object>asList(
            new ApplicationModule(this)
    );
  }
}
