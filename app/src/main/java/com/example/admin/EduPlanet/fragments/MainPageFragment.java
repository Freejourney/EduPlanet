package com.example.admin.EduPlanet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.activity.ItemDetailActivity;
import com.squareup.picasso.Picasso;

public class MainPageFragment extends Fragment implements View.OnClickListener{
    private ImageView[] imageViews;

    private View view;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != view){
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent){
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.tab_mainpage_fragment, container, false);
            context = getActivity();
            initViews();
        }

        return view;
    }

    private void initViews() {
        imageViews = new ImageView[9];
        imageViews[0] = view.findViewById(R.id.iv_main_pic);
        imageViews[1] = view.findViewById(R.id.iv_cs1);
        imageViews[2] = view.findViewById(R.id.iv_cs2);
        imageViews[3] = view.findViewById(R.id.iv_bs1);
        imageViews[4] = view.findViewById(R.id.iv_bs2);
        imageViews[5] = view.findViewById(R.id.iv_language1);
        imageViews[6] = view.findViewById(R.id.iv_language2);
        imageViews[7] = view.findViewById(R.id.iv_ad1);
        imageViews[8] = view.findViewById(R.id.iv_ad2);

        for (int i = 0; i < 9; i++) {
            imageViews[i].setOnClickListener(this);
        }

        initpics();

        imageViews[0].setContentDescription("f2a816920d"+"人体生物节律与周期与人类健康的研究探索");
        imageViews[1].setContentDescription("9fde688124"+"计算机教学改革");
        imageViews[2].setContentDescription("c183daf519"+"芯片技术发展及产业变迁");
        imageViews[3].setContentDescription("f01f6e46bd"+"生物工程新兴技术");
        imageViews[4].setContentDescription("a9b9caae15"+"生物工程制药发展");
        imageViews[5].setContentDescription("f5d49d97a1"+"中国城镇特色发展");
        imageViews[6].setContentDescription("01c2c3e064"+"空间句法研究");
        imageViews[7].setContentDescription("1dce967abe"+"青花在现代技术中的应用");
        imageViews[8].setContentDescription("e985eadce6"+"中国民俗发展");
    }

    private void initpics() {
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/958ba59640ae1e9e80edd9bd84354a2e.jpg").fit().into(imageViews[0]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/624fdce240075bbc8072afa271879d85.jpg").fit().into(imageViews[1]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/ed8f6334407bf1fa802d9cea17d5c841.jpg").fit().into(imageViews[2]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/8476b0cf40f9bdf8805cf024e6598607.jpg").fit().into(imageViews[3]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/053778a840d6d36f80c8f26ec70d74d3.jpg").fit().into(imageViews[4]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/043dae6a40b6c3b880398528135382be.jpg").fit().into(imageViews[5]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/dc7f5609406252368090a8d85bc711f9.jpg").fit().into(imageViews[6]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/c04783db407e99c680877de1d69b766e.jpg").fit().into(imageViews[7]);
        Picasso.with(context).load("http://bmob-cdn-18187.b0.upaiyun.com/2018/04/26/700225914026976d803e5d0c8d447a07.jpg").fit().into(imageViews[8]);
    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("itemObjectId", view.getContentDescription().toString().substring(0, 10));
        intent.putExtra("title", view.getContentDescription().toString().substring(10));
        context.startActivity(intent);
    }
}
