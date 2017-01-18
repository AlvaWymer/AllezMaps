package com.helloworld.lyz.allezmap;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.helloworld.lyz.allezmap.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2017/1/11 20:11
 *
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: GuideActivity  导航页面
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加guide页面
        setContentView(R.layout.activity_guide);
        Toast.makeText(GuideActivity.this, "GuideActivity", Toast.LENGTH_LONG).show();
        // 初始化页面
        initViews();
        // 初始化底部小点
        initDots();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.activity_guide_show_pre, null));
        views.add(inflater.inflate(R.layout.activity_guide_show_deux, null));
        views.add(inflater.inflate(R.layout.activity_guide_show_tro, null));
        views.add(inflater.inflate(R.layout.activity_guide_show_qua, null));


        int d = Log.d("debug", "22222");
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);
Log.d("debug", "111111");
        vp = (ViewPager) findViewById(R.id.guide_viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
//      vp.setOnPageChangeListener(this);//已过时
        vp.addOnPageChangeListener(this);

    }

    //初始化底部小点 方法
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.guide_linearlayout);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }

}

