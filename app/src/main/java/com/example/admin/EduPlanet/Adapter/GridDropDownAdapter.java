package com.example.admin.EduPlanet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.EduPlanet.R;

import java.util.List;

public class GridDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<String> mlist;
    private int checkItemPostion = -1;

    public void setCheckItemPostion(int postion){
        checkItemPostion = postion;
        notifyDataSetChanged();
    }

    public GridDropDownAdapter(Context context, List<String> list){
        this.context = context;
        this.mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
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
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_drop_down, null);
            viewHolder = new ViewHolder();
            viewHolder.tv = convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder){
        viewHolder.tv.setText(mlist.get(position));
        if (checkItemPostion != -1){
            if (checkItemPostion == position){
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.tv.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.drop_down_selected_icon), null);
            }else{
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    private class ViewHolder {
        TextView tv;
    }
}
