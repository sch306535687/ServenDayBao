package sun.ch.servendaybao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
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

import sun.ch.servendaybao.domain.User;
import sun.ch.servendaybao.global.GlobalData;
import sun.ch.servendaybao.utils.SharedPreferencesUtil;
import sun.ch.servendaybao.utils.StatusBarUtils;

/**
 * Created by sunch on 2017/4/6.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private EditText username;
    private EditText password;
    private boolean isLoginSuccessd = false;
    private int rcd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏颜色
        StatusBarUtils.setWindowStatusBarColor(this,R.color.bar_defaule);
        setContentView(R.layout.activity_login);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);//获取SharedPreferences工具类
        sharedPreferences = sharedPreferencesUtil.getSharedPreferences();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                //登录
                String loginUrl = GlobalData.loginUrl;
                String u = username.getText().toString();
                String p = password.getText().toString();

                if(TextUtils.isEmpty(u)||TextUtils.isEmpty(p)){
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    getDataFromServer(loginUrl,u,p);
                }
                break;
            case R.id.register:
                //注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    //从服务器获取json数据,并封装成对象
    private void getDataFromServer(final String url, final String user, final String pwd) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("AppId", "B207E275A16C41CB903A3E1CF1539995");
            obj.put("CCD", "1002");
            obj.put("LoginName", user);
            obj.put("Password", pwd);
            obj.put("DeciveID", user);
            obj.put("Sys_Type", pwd);
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
                            if(!TextUtils.isEmpty(result)){
                                Gson gson = new Gson();
                                User userInfo = gson.fromJson(result, User.class);
                                String token = userInfo.Data.Token;
                                String dayBao_url = userInfo.Data.UserInfo.getDayBao_Url();
                                sharedPreferences.edit().putString("dayBao_url",dayBao_url+"?token="+token).commit();
                                rcd = userInfo.RCD;
                                if(rcd==0){
                                    //登录成功
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                    sharedPreferences.edit().putBoolean("is_login_show",true).commit();
                                }else {
                                    //登录失败
                                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"获取不到数据",Toast.LENGTH_SHORT).show();
                            }
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
}
