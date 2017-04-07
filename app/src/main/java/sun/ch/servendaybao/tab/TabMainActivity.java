package sun.ch.servendaybao.tab;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sun.ch.servendaybao.R;
import sun.ch.servendaybao.base.BaseActivity;

/**
 * Created by sunch on 2017/3/28.
 */

public class TabMainActivity extends BaseActivity {
    public View mRootView;
    private ViewPager mViewPager;
    private LinearLayout pointContain;
    private int pointPosition;
    public int[] ImagesArray = new int[]{R.mipmap.scanner1, R.mipmap.scanner2, R.mipmap.scanner3, R.mipmap.scanner4};
    private boolean isAdd = true;

    public TabMainActivity(Activity activity) {
        super(activity);
    }

    /**
     * 初始化视图
     */
    @Override
    public View initView(){
        mRootView = View.inflate(activity, R.layout.tab_main, null);
        return mRootView;
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData(){
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyViewPager());
        mViewPager.setCurrentItem(ImagesArray.length * 10000);

        //监听页面改变事件
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position %= ImagesArray.length;
                pointContain.getChildAt(position).setEnabled(true);
                pointContain.getChildAt(pointPosition).setEnabled(false);
                pointPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 当手指放上时，停止自动轮播
         */
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);//null表示移除全部消息和回调
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0, 3000);//发送延迟空消息
                        break;
                }

                return false;//这里不能设为true，因为会把默认的ontouch事件给捕获掉
            }
        });

        //初始化圆点
        pointContain = (LinearLayout) mRootView.findViewById(R.id.point_container);
        if(isAdd){
            isAdd = false;
            for (int i = 0; i < ImagesArray.length; i++) {
                ImageView point = new ImageView(activity);
                point.setBackgroundResource(R.drawable.point_select);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i != 0) {
                    point.setEnabled(false);
                    params.leftMargin = 6;
                }
                point.setLayoutParams(params);
                pointContain.addView(point);
            }
        }

        mHandler.sendEmptyMessageDelayed(0, 3000);//发送延迟空消息
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            mHandler.sendEmptyMessageDelayed(0, 3000);//发送延迟空消息
        }
    };

    /**
     * ViewPager适配器
     */
    private class MyViewPager extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//定义可以滑动的最大次数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= ImagesArray.length;
            ImageView imageView = new ImageView(activity);
            imageView.setBackgroundResource(ImagesArray[position]);
            container.addView(imageView);//把ImageView添加到ViewGroup中
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
