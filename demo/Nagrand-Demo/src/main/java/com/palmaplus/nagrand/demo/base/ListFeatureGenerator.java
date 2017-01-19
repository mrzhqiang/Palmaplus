package com.palmaplus.nagrand.demo.base;

import android.content.Context;
import android.content.Intent;
import android.widget.ListAdapter;

/**
 * Created by Overu on 2015/8/11.
 */
public interface ListFeatureGenerator {

  ListAdapter genListAdapter(Context context, Intent intent);

  void selectItemEvent(Context content, Intent intent, int i, long l);

}
