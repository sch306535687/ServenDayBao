package sun.ch.servendaybao.tab;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.ArrayList;

import sun.ch.servendaybao.R;
import sun.ch.servendaybao.base.BaseActivity;
import sun.ch.servendaybao.domain.RankListData;
import sun.ch.servendaybao.global.GlobalData;

/**
 * Created by sunch on 2017/3/29.
 */

public class TabRankActivity extends BaseActivity {

    public View mRootView;
    private ListView mListView;
    private String resultString;
    private ArrayList<RankListData.DataList> dataLists;
    private TextView myName;
    private TextView myIncome;
    private TextView myRank;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            resultString = (String) msg.obj;
            if(!TextUtils.isEmpty(resultString)){
                Gson gson = new Gson();
                RankListData rankListData = gson.fromJson(resultString, RankListData.class);
                dataLists = rankListData.Data.list;
                RankListData.MyData myTincome = rankListData.Data.myTincome;
                myName.setText(myTincome.getRealname());
                myIncome.setText("￥"+myTincome.getTINCOME());
                myRank.setText(myTincome.getRanking());
                mListView.setAdapter(new MyAdapter());
            }else {
                Toast.makeText(activity,"获取不到数据",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private RadioGroup mRadioGroup;


    public TabRankActivity(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        mRootView = View.inflate(activity, R.layout.tab_rank, null);
        mListView = (ListView) mRootView.findViewById(R.id.listview);
        myName = (TextView) mRootView.findViewById(R.id.name);
        myIncome = (TextView) mRootView.findViewById(R.id.income);
        myRank = (TextView) mRootView.findViewById(R.id.rank);
        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radioGroup);
        return mRootView;
    }

    @Override
    public void initData() {
        final String rankUrl = GlobalData.rankUrl;
        final String totalRankUrl = GlobalData.totalRankUrl;
        getDataFromServer(rankUrl);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.myrank:
                        getDataFromServer(rankUrl);
                        break;
                    case R.id.totalrank:
                        getDataFromServer(totalRankUrl);
                        break;
                }
            }
        });

    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataLists.size();
        }

        @Override
        public Object getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(activity, R.layout.rank_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.realname = (TextView) view.findViewById(R.id.name);
                viewHolder.TINCOME = (TextView) view.findViewById(R.id.income);
                viewHolder.rank = (TextView) view.findViewById(R.id.rank);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            RankListData.DataList dataList = dataLists.get(position);
            viewHolder.realname.setText(dataList.c_name+"-"+dataList.realname);
            viewHolder.TINCOME.setText("总收益-￥"+dataList.TINCOME);
            if(position==0){

                view.setBackgroundResource(R.mipmap.rankonebg);
                viewHolder.rank.setTextColor(activity.getResources().getColor(R.color.white));
                viewHolder.rank.setTextSize(20);
                viewHolder.rank.getPaint().setFakeBoldText(true);
                viewHolder.rank.setText("NO."+dataList.ranking);
                viewHolder.realname.setTextColor(activity.getResources().getColor(R.color.white));
                viewHolder.TINCOME.setTextColor(activity.getResources().getColor(R.color.white));
            } else if(position==1){
                viewHolder.rank.setTextColor(activity.getResources().getColor(R.color.two));
                viewHolder.rank.setTextSize(20);
                viewHolder.rank.getPaint().setFakeBoldText(true);
                viewHolder.rank.setText("NO."+dataList.ranking);
            }else if(position==2){
                viewHolder.rank.setTextColor(activity.getResources().getColor(R.color.three));
                viewHolder.rank.setTextSize(20);
                viewHolder.rank.getPaint().setFakeBoldText(true);
                viewHolder.rank.setText("NO."+dataList.ranking);
            }else {
                viewHolder.rank.setText(dataList.ranking);
            }

            return view;
        }
    }

    private class ViewHolder {
        public TextView realname;
        public TextView rank;
        public TextView TINCOME;
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
                                    Message message = handler.obtainMessage(1, result);
                                    handler.sendMessage(message);
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
