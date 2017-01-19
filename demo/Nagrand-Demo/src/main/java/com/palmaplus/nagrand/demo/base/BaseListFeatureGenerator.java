package com.palmaplus.nagrand.demo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by Overu on 2015/8/11.
 */
public class BaseListFeatureGenerator implements ListFeatureGenerator {

  public final static String ROOT = "root";

  @Override
  public ListAdapter genListAdapter(Context context, Intent intent) {
    ArrayList<FunctionNode> nodeList = intent.getParcelableArrayListExtra(ROOT);
    if (nodeList == null)
      throw new RuntimeException("nodeList == null");
    String[] titles = new String[nodeList.size()];
    int index = 0;
    for (FunctionNode node : nodeList) {
      titles[index++] = node.getTitle();
    }
    return new ArrayAdapter<String>(context,
            android.R.layout.simple_list_item_1, titles);
  }

  @Override
  public void selectItemEvent(Context content, Intent intent, int i, long l) {
    ArrayList<FunctionNode> nodeList = intent.getParcelableArrayListExtra(ROOT);
    FunctionNode node = nodeList.get(i);
    try {
      Intent newIntent = new Intent(content, Class.forName(node.getTarget()));
      if (node.getChilds() != null) {
        ArrayList<Parcelable> parcelables = new ArrayList<Parcelable>();
        for (Parcelable parcelable : node.getChilds()) {
          parcelables.add(parcelable);
        }
        newIntent.putExtra(ROOT, parcelables);
      }
      content.startActivity(newIntent);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
