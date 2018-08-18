package com.example.admin.EduPlanet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.bean.Recent;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ItemListActivity extends Activity{
    private List<String> data = new ArrayList<String>();
    private List<String> idlist = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        mContext = ItemListActivity.this;

        initdata();
        ListView listView = findViewById(R.id.lv_items);
        adapter = new ArrayAdapter<String>(ItemListActivity.this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra("itemObjectId", idlist.get(i));
                intent.putExtra("title", data.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    public void initdata() {
        idlist.clear();
        data.clear();
        BmobQuery<Recent> bmobQuery = new BmobQuery<Recent>();
        bmobQuery.order("-createdAt");
        bmobQuery.setLimit(30);
        bmobQuery.findObjects(new FindListener<Recent>() {
            @Override
            public void done(List<Recent> list, BmobException e) {
                for (Recent recent : list) {
                    data.add(recent.getTitle());
                    idlist.add(recent.getItemObjectId());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
