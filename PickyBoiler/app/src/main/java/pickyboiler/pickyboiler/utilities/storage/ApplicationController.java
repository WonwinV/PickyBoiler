package pickyboiler.pickyboiler.Utilities.Storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationController extends Application {
    private static ApplicationController mController;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        mController = this;
        mSharedPreferences = getSharedPreferences("OasisLite", Context.MODE_PRIVATE);
    }

    /**
     * @return Singleton controller instance
     */
    public static synchronized ApplicationController getInstance(){
        return mController;
    }

    public static synchronized SharedPreferences getSharedPreferences(){
        return mSharedPreferences;
    }

    public static synchronized void putStringSharedPreferences(String key, String value){
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public Context getContext() {
        return getApplicationContext();
    }

}
