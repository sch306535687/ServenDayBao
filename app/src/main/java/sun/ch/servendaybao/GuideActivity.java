package sun.ch.servendaybao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sun.ch.servendaybao.utils.SharedPreferencesUtil;

/**
 * Created by sunch on 2017/3/28.
 */

public class GuideActivity extends Activity implements View.OnClickListener {

    private ViewPager mViewPager;
    private int[] mImgs = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private SharedPreferences sharedPreferences;
    private LinearLayout point_container;
    private int pointLong;
    private ImageView red_point;
    private TextView guide_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        red_point = (ImageView) findViewById(R.id.red_point);
        guide_btn = (TextView) findViewById(R.id.guide_btn);
        point_container = (LinearLayout) findViewById(R.id.point_container);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);//获取SharedPreferences工具类
        sharedPreferences = sharedPreferencesUtil.getSharedPreferences();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter());

        guide_btn.setOnClickListener(this);


        //初始化圆点
        for (int i = 0; i < mImgs.length; i++) {
            ImageView imageView = new ImageView(this);
            if (i != 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 5;
                imageView.setLayoutParams(params);
            }
            imageView.setBackgroundResource(R.drawable.point_default);
            point_container.addView(imageView);
        }

        //获取视图树，监听位置，获取两个圆点间的距离
        point_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointLong = point_container.getChildAt(1).getLeft() - point_container.getChildAt(0).getLeft();
            }
        });

        //mViewPager
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当滚动时
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
                params.leftMargin = ((int) (pointLong * positionOffset)) + position * pointLong;
                red_point.setLayoutParams(params);
                if (position == mImgs.length - 1) {
                    guide_btn.setVisibility(View.VISIBLE);
                } else {
                    guide_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.guide_btn:
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                sharedPreferences.edit().putBoolean("is_guide_show", true).commit();
                finish();
                break;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mImgs[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
