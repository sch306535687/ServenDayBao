package sun.ch.servendaybao.tab;

import android.app.Activity;
import android.view.View;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import sun.ch.servendaybao.R;
import sun.ch.servendaybao.base.BaseActivity;
import sun.ch.servendaybao.global.GlobalData;

/**
 * Created by sunch on 2017/3/29.
 */

public class TabRankActivity extends BaseActivity {

    public View mRootView;

    public TabRankActivity(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        mRootView = View.inflate(activity, R.layout.tab_rank, null);
        return mRootView;
    }

    @Override
    public void initData() {
        String rankUrl = GlobalData.rankUrl;
        getDataFromServer(rankUrl);
    }

    //从服务器获取json数据,并封装成对象
    public void getDataFromServer(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("uid", "75");
                    RequestParams params = new RequestParams();
                    params.setBodyEntity(new StringEntity(obj.toString(),"UTF-8"));
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            url,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {}

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {}

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String result = responseInfo.result;
                                    System.out.println(result);
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    System.out.println(error + ":" + msg);
                                }
                            });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
