package sun.ch.servendaybao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sunch on 2017/3/28.
 */

public class SharedPreferencesUtil {
    private static Context context;
    public SharedPreferencesUtil(Context context){
        this.context = context;
    }
    public static SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences("config", MODE_PRIVATE);
    }
}
