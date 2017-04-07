package sun.ch.servendaybao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import sun.ch.servendaybao.utils.SharedPreferencesUtil;

/**
 * Created by sunch on 2017/3/28.
 */

public class WelcomeActivity extends Activity {

    private LinearLayout welcome;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏导航栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        welcome = (LinearLayout) findViewById(R.id.welcome);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);//获取SharedPreferences工具类
        sharedPreferences = sharedPreferencesUtil.getSharedPreferences();

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        welcome.startAnimation(scaleAnimation);

        //跳到主界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean is_guide_show = WelcomeActivity.this.sharedPreferences.getBoolean("is_guide_show", false);
                boolean is_login_show = WelcomeActivity.this.sharedPreferences.getBoolean("is_login_show", false);
                if (is_guide_show) {

                    if (is_login_show) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    }


                } else {
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                }
                finish();
            }
        }, 1000);
    }
}
