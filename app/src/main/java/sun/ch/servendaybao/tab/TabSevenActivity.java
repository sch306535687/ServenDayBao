package sun.ch.servendaybao.tab;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import sun.ch.servendaybao.R;
import sun.ch.servendaybao.base.BaseActivity;
import sun.ch.servendaybao.utils.SharedPreferencesUtil;

/**
 * Created by sunch on 2017/3/29.
 */

public class TabSevenActivity extends BaseActivity {
    private View mRootView;
    private WebView mWebView;
    private String servenDayUrl;
    private SharedPreferences sharedPreferences;

    public TabSevenActivity(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        mRootView = View.inflate(activity, R.layout.tab_serven, null);
        return mRootView;
    }

    @Override
    public void initData() {
        mWebView = (WebView) mRootView.findViewById(R.id.webview);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(activity);//获取SharedPreferences工具类
        sharedPreferences = sharedPreferencesUtil.getSharedPreferences();
        String dayBao_url = sharedPreferences.getString("dayBao_url", null);
        if(!TextUtils.isEmpty(dayBao_url)){
            mWebView.loadUrl(dayBao_url);
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }

    }
}
