package com.example.admin.EduPlanet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.EduPlanet.Adapter.*;
import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.bean.Item;
import com.example.admin.EduPlanet.bean.Require;
import com.example.admin.EduPlanet.util.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SortPageFragment extends Fragment implements AdapterView.OnItemClickListener {

    DropDownMenu dropDownMenu;
    private String headers[] = {"学院", "类别", "综合"};
    private List<View> popViews = new ArrayList<View>();

    private static int id1 = 1;
    private static int id2 = 2;
    private static int id4 = 3;

    private String Institute[] = {"不限", "计算机学院", "土建学院", "艺术设计学院", "生工学院"};
    private String ContentType[] = {"类别", "课本教材", "试卷解析", "大赛解析", "科研成果", "技术发展"};
    private String Priority[] = {"综合", "最近发布", "最高好评"};

    private GridDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter, sexAdapter;
    private ItemRecyclerAdapter itemRecyclerAdapter;

    private Context context;
    private View view;
    private List<Item> mList = new ArrayList<>();
    private Require require = new Require();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != view){
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent){
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.tab_sorts_fragment, container, false);
            dropDownMenu = view.findViewById(R.id.dropDownMenu);
            context = getActivity();
            initDatas();
            initViews();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        mList.clear();
        BmobQuery<Item> msgQuery = new BmobQuery<>();
        msgQuery.order("-createAt");
        msgQuery.findObjects(new FindListener<Item>() {
            @Override
            public void done(List<Item> list, BmobException e) {
                if(e == null) {
                    mList.addAll(list);
                    itemRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initViews() {
        ListView lvCity = new ListView(context);
        cityAdapter = new GridDropDownAdapter(context, Arrays.asList(Institute));
        lvCity.setDividerHeight(0);
        lvCity.setId(0);
        lvCity.setAdapter(cityAdapter);

        ListView lvAge = new ListView(context);
        ageAdapter = new ListDropDownAdapter(context, Arrays.asList(ContentType));
        lvAge.setDividerHeight(0);
        lvAge.setId(id1);
        lvAge.setAdapter(ageAdapter);

        ListView lvSex = new ListView(context);
        sexAdapter = new ListDropDownAdapter(context, Arrays.asList(Priority));
        lvSex.setDividerHeight(0);
        lvSex.setId(id2);
        lvSex.setAdapter(sexAdapter);

        RecyclerView rvItem = new RecyclerView(context);
        itemRecyclerAdapter = new ItemRecyclerAdapter(mList, context);
        rvItem.setHasFixedSize(true);
        rvItem.setId(id4);
        rvItem.setAdapter(itemRecyclerAdapter);

        lvCity.setOnItemClickListener(this);
        lvAge.setOnItemClickListener(this);
        lvSex.setOnItemClickListener(this);

        popViews.add(lvCity);
        popViews.add(lvAge);
        popViews.add(lvSex);

        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popViews, rvItem);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case 0:
                cityAdapter.setCheckItemPostion(position);
                dropDownMenu.setTabText(position == 0?headers[0]: Institute[position], 0);
                dropDownMenu.closeMenu();
                require.setInstitute(Institute[position]);
                querybyrequire();
                break;
            case 1:
                ageAdapter.setCheckItemPosition(position);
                dropDownMenu.setTabText(position == 0?headers[1]: ContentType[position], 1);
                dropDownMenu.closeMenu();
                require.setType(ContentType[position]);
                querybyrequire();
                break;
            case 2:
                sexAdapter.setCheckItemPosition(position);
                dropDownMenu.setTabText(position == 0?headers[2]: Priority[position], 2);
                dropDownMenu.closeMenu();
                require.setPriority(Priority[position]);
                querybyrequire();
                break;
        }
    }

    private void querybyrequire() {
        mList.clear();
        BmobQuery<Item> msgQuery = new BmobQuery<>();
        if (require.getPriority().equals(Priority[1]))
            msgQuery.order("-createdAt");
        msgQuery.addWhereEqualTo("instituteType", require.getInstitute());
        msgQuery.addWhereEqualTo("contentType", require.getType());
        msgQuery.setLimit(50);
        msgQuery.findObjects(new FindListener<Item>() {
            @Override
            public void done(List<Item> list, BmobException e) {
                if(e == null) {
                    mList.addAll(list);
                    itemRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
//        if (mList.size() == 0) {
//            Toast.makeText(context, "没有找到", Toast.LENGTH_LONG);
//            initDatas();
//        }
    }

}
