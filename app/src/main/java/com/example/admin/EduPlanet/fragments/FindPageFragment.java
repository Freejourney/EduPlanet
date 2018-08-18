package com.example.admin.EduPlanet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.EduPlanet.Adapter.ItemRecyclerAdapter;
import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.bean.Item;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindPageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ItemRecyclerAdapter mRecyclerAdapter;
    private List<Item> mList = new ArrayList<>();

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_find_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RV_item);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerAdapter = new ItemRecyclerAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mList.clear();
        BmobQuery<Item> msgQuery = new BmobQuery<>();
        msgQuery.order("-createdAt");
        msgQuery.include("owner");
        msgQuery.findObjects(new FindListener<Item>() {
            @Override
            public void done(List<Item> list, BmobException e) {
                if(e == null) {
                    mList.addAll(list);
                    mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
