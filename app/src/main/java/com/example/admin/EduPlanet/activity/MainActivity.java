package com.example.admin.EduPlanet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.EduPlanet.Model.UserModel;
import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.fragments.FindPageFragment;
import com.example.admin.EduPlanet.fragments.MainPageFragment;
import com.example.admin.EduPlanet.fragments.SortPageFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> mDatas;

    private TextView mFindTextView;
    private TextView mMainPageTextView;
    private TextView mSortTextView;

    private TextView tv_intro;
    private TextView tv_name;
    private ImageView iv_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initData() {
        tv_intro.setText("");
        tv_name.setText(UserModel.getInstance().getCurrentUser().getUsername());
        Picasso.with(MainActivity.this).load(UserModel.getInstance().getCurrentUser().getAvatorurl()).resize(200, 200).into(iv_avatar);
//        Toast.makeText(MainActivity.this, UserModel.getInstance().getCurrentUser().getUsername(), Toast.LENGTH_LONG);
    }

    public class MyOnClickListener implements View.OnClickListener{

        private  int index = 0;
        public  MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View view) {
            mViewPager.setCurrentItem(index);
        }
    }

    private void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, sendMessageActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headview = navigationView.getHeaderView(0);
        tv_name = headview.findViewById(R.id.tv_name);
        tv_intro = headview.findViewById(R.id.tv_intro);
        iv_avatar = headview.findViewById(R.id.iv_avator);

        mViewPager = (ViewPager) findViewById(R.id.vp_fragments);
        mDatas = new ArrayList<Fragment>();

        mFindTextView = (TextView) findViewById(R.id.tv_findpage);
        mMainPageTextView = (TextView) findViewById(R.id.tv_mainpage);
        mSortTextView = (TextView) findViewById(R.id.tv_sortpage);

        mFindTextView.setOnClickListener(new MyOnClickListener(2));
        mMainPageTextView.setOnClickListener(new MyOnClickListener(0));
        mSortTextView.setOnClickListener(new MyOnClickListener(1));

        MainPageFragment mainPageFragment = new MainPageFragment();
        FindPageFragment findPageFragment = new FindPageFragment();
        SortPageFragment sortPageFragment = new SortPageFragment();

        mDatas.add(mainPageFragment);
        mDatas.add(sortPageFragment);
        mDatas.add(findPageFragment);


        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };

        mViewPager.setAdapter(fragmentPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();

                switch (position)
                {
                    case 0:
                        mMainPageTextView.setTextColor(Color.GREEN);
                        break;
                    case 1:
                        mSortTextView.setTextColor(Color.GREEN);
                        break;
                    case 2:
                        mFindTextView.setTextColor(Color.GREEN);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {
        mFindTextView.setTextColor(Color.BLACK);
        mSortTextView.setTextColor(Color.BLACK);
        mMainPageTextView.setTextColor(Color.BLACK);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recent) {
            startActivity(new Intent(MainActivity.this, ItemListActivity.class));
        } else if (id == R.id.nav_logout) {
            UserModel.getInstance().logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
