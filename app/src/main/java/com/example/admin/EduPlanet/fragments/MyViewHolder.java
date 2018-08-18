package com.example.admin.EduPlanet.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.EduPlanet.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv;

    public MyViewHolder(View parent) {
        super(parent);
        tv = parent.findViewById(R.id.id_tv);
    }
}
