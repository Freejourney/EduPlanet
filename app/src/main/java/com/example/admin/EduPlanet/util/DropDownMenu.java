package com.example.admin.EduPlanet.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.EduPlanet.R;

import java.util.List;

public class DropDownMenu extends LinearLayout {
    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器  包含内容区域  遮罩区域  菜单弹出区域
    private FrameLayout containerView;
    //内容区域
    private View mcontentView;
    //遮罩区域
    private View maskView;
    //菜单弹出区域
    private FrameLayout popupMenuViews;


    //分割线颜色
    private int dividerColor = 0xffcccccc;
    //文本选中颜色
    private int textSelectedColor = 0xff890c85;
    //文本未被选中颜色
    private int textUnSelectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;
    //菜单背景颜色
    private int menuBackgroundColor = 0xffffffff;
    //水平分割线颜色
    private int underlineColor = 0xffcccccc;

    //字体大小
    private int menuTextSize = 14;
    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnSelectedIcon;

    //菜单项被选中位置 初始没有菜单被选中记为-1
    private int currentTabPosition = -1;

    public DropDownMenu(Context context) {
        super(context, null);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

//        containerView = new FrameLayout(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.dropDownMenu);
        underlineColor = a.getColor(R.styleable.dropDownMenu_underlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.dropDownMenu_dividerColor, dividerColor);
        textSelectedColor = a.getColor(R.styleable.dropDownMenu_textSelectedColor, textSelectedColor);
        textUnSelectedColor = a.getColor(R.styleable.dropDownMenu_textUnSelectedColor, textUnSelectedColor);
        menuBackgroundColor = a.getColor(R.styleable.dropDownMenu_menuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.dropDownMenu_maskColor, maskColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.dropDownMenu_menuTextSize, menuTextSize);
        menuSelectedIcon = a.getResourceId(R.styleable.dropDownMenu_menuSelectedIcon, menuSelectedIcon);
        menuUnSelectedIcon = a.getResourceId(R.styleable.dropDownMenu_menuUnSelectedIcon, menuUnSelectedIcon);
        a.recycle();

        initview(context);
    }

    private void initview(Context context) {

        //创建顶部菜单指示
        tabMenuView = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setLayoutParams(lp);
        addView(tabMenuView, 0);

        //创建下划线
        View underlineView = new View(context);
        underlineView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2Px(1.0f)));
        underlineView.setBackgroundColor(underlineColor);
        addView(underlineView, 1);

        //初始化containerView
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(containerView, 2);

        mcontentView = new FrameLayout(context);
        mcontentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    private int dp2Px(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

    /**
     * 初始化DropDownMenu显示具体的内容
     */
    public void setDropDownMenu(List<String> tabTexts, List<View> popuViews, RecyclerView contentView){
//        this.contentView = contentView;
        if (tabTexts.size()!=popuViews.size()){
            throw new IllegalArgumentException("tabTexts.size() should be equal to popuViews.size()"+tabTexts.size()+":"+popuViews.size());
        }
        
        for (int i = 0; i < tabTexts.size(); i++){
            addTab(tabTexts, i);
        }

        //view 加入 containerView 的顺序会影响它们的显示顺序*********************************
        containerView.addView(contentView, 0);

        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setAlpha(0.4f);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        maskView.setVisibility(View.GONE);
        containerView.addView(maskView, 1);

        contentView.setLayoutManager(new LinearLayoutManager(getContext()));

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setVisibility(GONE);
        for (int i = 0; i < popuViews.size(); i++){
            popuViews.get(i).setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            ));
            popupMenuViews.addView(popuViews.get(i), i);
        }
//        popupMenuViews.addView(contentView,3 );

        containerView.addView(popupMenuViews, 2);


//        containerView.addView(mainview, 2);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (currentTabPosition != -1){
            ((TextView)tabMenuView.getChildAt(currentTabPosition)).setTextColor(textUnSelectedColor);
            ((TextView)tabMenuView.getChildAt(currentTabPosition)).setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnSelectedIcon), null);
            popupMenuViews.setVisibility(GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            currentTabPosition = -1;
        }
    }

    private void addTab(List<String> text, int index) {
        final TextView tab = new TextView(getContext());
        //单行显示
        tab.setSingleLine();
        //文本过长后末尾省略号显示
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        //width为0，weight为1表示均分
        tab.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        tab.setTextColor(textUnSelectedColor);
        //在文字右边添加小图标
        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnSelectedIcon), null);
        tab.setText(text.get(index));
        tab.setPadding(dp2Px(5), dp2Px(12), dp2Px(5), dp2Px(12));
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
            }
        });
        tabMenuView.addView(tab);
        //添加分割线
        if (index < text.size()-1){
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(dp2Px(0.5f), LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }
    }

    /**
     * 切换菜单
     * @param targetView
     */
    private void switchMenu(View targetView) {
        //tab中文字、图标均占一个下标
        for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2 ){
            if (targetView == tabMenuView.getChildAt(i)) {
                if (currentTabPosition == i) {
                    //关闭菜单
                    closeMenu();
                } else {
                    //弹出菜单
                    if (currentTabPosition == -1) {  //初始状态
                        popupMenuViews.setVisibility(View.VISIBLE);
                        //-100%表示最上面，到0平移动画，0.25秒
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                        maskView.setVisibility(View.VISIBLE);
                        //从完全不可见到完全可见，0.25秒
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                        popupMenuViews.getChildAt(i / 2).setVisibility(VISIBLE);
                    } else {
                        popupMenuViews.getChildAt(i / 2).setVisibility(VISIBLE);
                    }

                    currentTabPosition = i;
                    ((TextView) tabMenuView.getChildAt(i)).setTextColor(textSelectedColor);
                    ((TextView) tabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
                }
            }else{
                ((TextView)tabMenuView.getChildAt(i)).setTextColor(textUnSelectedColor);
                ((TextView)tabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnSelectedIcon), null);
                popupMenuViews.getChildAt(i/2).setVisibility(GONE);
            }
        }
    }

    public void setTabText(String s, int i) {
        if(i == 0){
            ((TextView)tabMenuView.getChildAt(0)).setText(s);
        }else if(i == 1){
            ((TextView)tabMenuView.getChildAt(2)).setText(s);
        }else if(i == 2){
            ((TextView)tabMenuView.getChildAt(4)).setText(s);
        }else{
            ((TextView)tabMenuView.getChildAt(6)).setText(s);
        }
    }
}
