package pickyboiler.pickyboiler.utilities.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager extends Application{
    private static SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public SharedPreferencesManager() {
    }

    private static SharedPreferences getPrefs(Context context) {
        return sharedPreferences;
    }

    @Deprecated
    public static void putStringSharedPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, value).apply();
    }

    public static String getValueFromKey(Context context, String key) {
        return getPrefs(context).getString(key, null);
    }

    public static void addOrAppendStringToSharedPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        String oldValue = getPrefs(context).getString(key, null);

        if(oldValue == null) {
            editor.putString(key, value).apply();
        }
        else {
            editor.putString(key, oldValue + ',' + value).apply();
        }
    }
}
