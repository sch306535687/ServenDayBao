package sun.ch.servendaybao;

import android.app.Activity;
import android.os.Bundle;

import sun.ch.servendaybao.utils.StatusBarUtils;

/**
 * Created by sunch on 2017/4/6.
 */

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏颜色
        StatusBarUtils.setWindowStatusBarColor(this,R.color.bar_defaule);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_register);
    }
}
