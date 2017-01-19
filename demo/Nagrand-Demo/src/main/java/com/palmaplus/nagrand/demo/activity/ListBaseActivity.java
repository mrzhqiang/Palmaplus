package com.palmaplus.nagrand.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.palmaplus.nagrand.demo.R;

import com.palmaplus.nagrand.demo.base.BaseListFeatureGenerator;
import com.palmaplus.nagrand.demo.base.ListFeatureGenerator;
import com.palmaplus.nagrand.demo.base.NagrandBaseActivity;



/**
 * Created by Overu on 2015/8/11.
 */
public class ListBaseActivity extends NagrandBaseActivity {
  private BaseListFeatureGenerator blfg = new BaseListFeatureGenerator();


  ListFeatureGenerator listFeatureGenerator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_list);

    Intent intent = getIntent();
    ListView listView = (ListView) findViewById(R.id.listView);

    listView.setAdapter(blfg.genListAdapter(this, intent));
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        blfg.selectItemEvent(ListBaseActivity.this, ListBaseActivity.this.getIntent(), i, l);
      }
    });
  }

}
