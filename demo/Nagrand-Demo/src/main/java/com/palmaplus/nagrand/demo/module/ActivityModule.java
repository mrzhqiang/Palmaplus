package com.palmaplus.nagrand.demo.module;

import android.content.Context;

import com.palmaplus.nagrand.demo.activity.*;
import com.palmaplus.nagrand.demo.base.BaseListFeatureGenerator;
import com.palmaplus.nagrand.demo.base.ListFeatureGenerator;
import com.palmaplus.nagrand.demo.base.NagActivity;
import com.palmaplus.nagrand.demo.base.NagrandBaseActivity;


/**
 * Created by Overu on 2015/8/10.
 */

public class ActivityModule {

  private final NagrandBaseActivity activity;

  public ActivityModule(NagrandBaseActivity activity) {
    this.activity = activity;
  }

  @NagActivity
  Context provideActivityContext() {
    return activity;
  }

  String provideTag() {
    return activity.getClass().getSimpleName();
  }


  ListFeatureGenerator provideListAdapterProvider() {
    return new BaseListFeatureGenerator();
  }

}
