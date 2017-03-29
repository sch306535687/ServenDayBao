package sun.ch.servendaybao.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by sunch on 2017/3/29.
 */

public abstract class BaseActivity  {
    public Activity activity;
    public View mRootView;

    public BaseActivity(Activity activity){
        this.activity = activity;
        mRootView = initView();
    }
    /**
     * 初始化
     */
    public abstract View initView();

    /**
     * 初始化数据
     */
    public abstract void initData();


}
