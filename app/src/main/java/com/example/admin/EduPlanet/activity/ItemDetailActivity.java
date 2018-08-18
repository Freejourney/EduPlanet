package com.example.admin.EduPlanet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.bean.Item;
import com.example.admin.EduPlanet.bean.Recent;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class ItemDetailActivity extends Activity {

    private TextView mName;
    private TextView mTime;
    private WebView webview;
    private ImageView mAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        mName = findViewById(R.id.tv_item_detail_username);
        mTime = findViewById(R.id.tv_item_detail_time);
        webview = findViewById(R.id.wv_content);

        mAvatar = findViewById(R.id.iv_item_detail_avatar);

        String goodsObjectId = getIntent().getStringExtra("itemObjectId");
        String title = getIntent().getStringExtra("title");
        Recent recent = new Recent();
        recent.setItemObjectId(goodsObjectId);
        recent.setTitle(title);
        recent.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    Toast.makeText(ItemDetailActivity.this, "最近浏览保存失败", Toast.LENGTH_LONG);
                }
            }
        });
        BmobQuery<Item> msgquery = new BmobQuery<Item>();
        msgquery.getObject(goodsObjectId, new QueryListener<Item>() {
            @Override
            public void done(Item item, BmobException e) {
                if (e == null) {
                    mName.setText(item.getUsername());
                    mTime.setText(item.getCreatedAt().toString());
                    webview.loadDataWithBaseURL(null,item.getContent(), "text/html", "utf-8", null);
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.setWebViewClient(new MyWebViewClient());
                    Picasso.with(ItemDetailActivity.this).load(item.getAvatar() == null ? "www" : item.getAvatar()).into(mAvatar);
                }
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();
        }

        private void imgReset() {
            webview.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    " img.style.maxWidth = '100%';img.style.height='auto';" +
                    "}" +
                    "})()");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
