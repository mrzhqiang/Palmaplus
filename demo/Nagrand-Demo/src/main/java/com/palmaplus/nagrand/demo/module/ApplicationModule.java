package com.palmaplus.nagrand.demo.module;

import android.app.Application;
import android.content.Context;
import com.palmaplus.nagrand.demo.base.NagApplication;

/**
 * Created by Overu on 2015/8/7.
 */

public class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @NagApplication
  Context provideApplicationContext() {
    return this.application;
  }

}
