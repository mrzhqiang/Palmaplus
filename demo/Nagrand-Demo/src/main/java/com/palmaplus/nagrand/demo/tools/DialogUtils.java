package com.palmaplus.nagrand.demo.tools;

import android.os.Handler;
import android.widget.Toast;
import com.palmaplus.nagrand.demo.base.NagrandApplication;

/**
 * Created by zhang on 2015/4/14.
 * 弹出框工具类
 *  包括 Toast，Dialog等
 */
public class DialogUtils {

  public static void showShortToast(String msg){
    Toast.makeText(NagrandApplication.instance, msg, Toast.LENGTH_SHORT).show();
  }

  public static void showShortToast(Handler handler, final String msg){
    handler.post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(NagrandApplication.instance, msg, Toast.LENGTH_SHORT).show();
      }
    });
  }

  public static void showLongToast(String msg){
    Toast.makeText(NagrandApplication.instance, msg, Toast.LENGTH_LONG).show();
  }

}
