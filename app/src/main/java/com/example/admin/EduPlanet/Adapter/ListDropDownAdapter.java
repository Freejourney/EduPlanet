package com.example.admin.EduPlanet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.EduPlanet.R;

import java.util.List;

public class ListDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<String> mList;
    private int checkItemPosition = -1;

    public void setCheckItemPosition(int checkItemPosition){
        this.checkItemPosition = checkItemPosition;
    }

    public ListDropDownAdapter(Context context, List<String> list){
        this.context = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
          convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down, null);
          viewHolder = new ViewHolder();
          viewHolder.tv = convertView.findViewById(R.id.tv);
          convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.tv.setText(mList.get(position));
        if (checkItemPosition != -1){
            if (checkItemPosition == position){
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.tv.setBackgroundResource(R.color.check_bg);
            } else {
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.tv.setBackgroundResource(R.color.white);
            }
        }
    }

    private class ViewHolder {
        TextView tv;
    }
}
