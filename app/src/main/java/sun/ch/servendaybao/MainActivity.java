package sun.ch.servendaybao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import sun.ch.servendaybao.base.BaseActivity;
import sun.ch.servendaybao.tab.TabCountActivity;
import sun.ch.servendaybao.tab.TabMainActivity;
import sun.ch.servendaybao.tab.TabRankActivity;
import sun.ch.servendaybao.tab.TabSevenActivity;
import sun.ch.servendaybao.utils.StatusBarUtils;

public class MainActivity extends Activity {


    private ViewPager mViewPager;
    private List<BaseActivity> viewList;
    private RelativeLayout radioLayout;
    private int mScreenHeight;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏颜色
        StatusBarUtils.setWindowStatusBarColor(this,R.color.maintop);
        //隐藏导航栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();//获取屏幕高度
        radioLayout = (RelativeLayout) findViewById(R.id.radioLayout);
        /*radioLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                radioLayout.measure(0,0);
                int height = radioLayout.getMeasuredHeight();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScreenHeight - height);
                mViewPager.setLayoutParams(params);
            }
        });*/

        viewList = new ArrayList<BaseActivity>();
        //主界面
        TabMainActivity tabMainActivity = new TabMainActivity(this);
        viewList.add(tabMainActivity);
        //排行榜
        TabRankActivity tabRankActivity = new TabRankActivity(this);
        viewList.add(tabRankActivity);
        //7天宝
        TabSevenActivity tabSevenActivity = new TabSevenActivity(this);
        viewList.add(tabSevenActivity);
        //账户
        TabCountActivity tabCountActivity = new TabCountActivity(this);
        viewList.add(tabCountActivity);

        mViewPager = (ViewPager) findViewById(R.id.mainviewpager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //监听按钮状态改变事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.tab_main:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_rank:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_money:
                        mViewPager.setCurrentItem(2, false);//添加false表示禁止页面切换效果
                        break;
                    case R.id.tab_count:
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });
        //viewpager页面改变事件
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println(position);
                if(position==0){
                    StatusBarUtils.setWindowStatusBarColor(MainActivity.this,R.color.maintop);
                } else if(position==1){
                    StatusBarUtils.setWindowStatusBarColor(MainActivity.this,R.color.blue);
                }else if(position==2){
                    StatusBarUtils.setWindowStatusBarColor(MainActivity.this,R.color.orange);
                }else if(position==3){
                    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new MyViewPager());

    }

    /**
     * ViewPager适配器
     */
    private class MyViewPager extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();//定义可以滑动的最大次数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseActivity baseActivity = viewList.get(position);
            baseActivity.initData();
            container.addView(baseActivity.mRootView);
            return baseActivity.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
