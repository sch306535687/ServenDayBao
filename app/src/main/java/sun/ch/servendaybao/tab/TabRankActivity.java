package sun.ch.servendaybao.tab;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import sun.ch.servendaybao.base.BaseActivity;

/**
 * Created by sunch on 2017/3/29.
 */

public class TabRankActivity extends BaseActivity {

    public TabRankActivity(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(activity);
        textView.setText("我是排行榜");
        return textView;
    }

    @Override
    public void initData() {

    }
}
