package com.palmaplus.nagrand.demo.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import com.palmaplus.nagrand.demo.module.ActivityModule;
import com.palmaplus.nagrand.demo.widget.MyProgressDialog;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Overu on 2015/8/10.
 */
public class NagrandBaseActivity extends Activity {


  private MyProgressDialog progressDialog = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    NagrandApplication application = (NagrandApplication) getApplication();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }


  protected List<Object> getModules() {
    return Arrays.<Object>asList(
            new ActivityModule(this)
    );
  }

  /*
  *  显示进度条
  * */
  protected void showProgress(String title, String msg) {
    if (progressDialog == null) {
      progressDialog = new MyProgressDialog(this);
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      progressDialog.setCanceledOnTouchOutside(false);
      progressDialog.setIndeterminate(false);
      progressDialog.setCancelable(true);
    }

    progressDialog.setTitle(title);
    progressDialog.setMessage(msg);

    if (!progressDialog.isShowing()){
      progressDialog.show();
    }
  }

  protected void showProgress(Handler handler,  String title, String msg) {
    if (progressDialog == null) {
      progressDialog = new MyProgressDialog(this);
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      progressDialog.setCanceledOnTouchOutside(false);
      progressDialog.setIndeterminate(false);
      progressDialog.setCancelable(true);
    }

    progressDialog.setTitle(title);
    progressDialog.setMessage(msg);

    if (!progressDialog.isShowing()){
      handler.post(new Runnable() {
        @Override
        public void run() {
          progressDialog.show();
        }
      });
    }
  }

  /*
  *  关闭进度条
  * */
  protected void closeProgress(){
    if (progressDialog != null && progressDialog.isShowing()){
      progressDialog.dismiss();
    }
  }

  protected void closeProgress(Handler handler){
    if (progressDialog != null && progressDialog.isShowing()){
      handler.post(new Runnable() {
        @Override
        public void run() {
          progressDialog.dismiss();
        }
      });
    }
  }

}
