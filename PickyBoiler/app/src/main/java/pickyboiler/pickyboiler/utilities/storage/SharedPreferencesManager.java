package pickyboiler.pickyboiler.Utilities.Storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


import pickyboiler.pickyboiler.R;

public class SharedPreferencesManager extends Application{
    private static SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";
    private static Context context;
    public static Toast toastShow;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        context = getApplicationContext();
        toastShow = null;
    }
    // Code for toast singleTron
    public static void showToast(String text) {

        if (toastShow == null || toastShow.getView().getWindowVisibility() != View.VISIBLE) {
            toastShow = Toast.makeText(SharedPreferencesManager.getContext(), text, Toast.LENGTH_SHORT);
            toastShow.show();
            Log.v("Toasty", "== null or it is not visible");
            return;
        }
        if (toastShow != null) {
            toastShow.setText(text);
            toastShow.show();
            Log.v("Toasty", "!= null");
        }
    }

    public static SharedPreferences getPrefs() {

        return sharedPreferences;
    }

    public static Context getContext() {
        return context;
    }

    public static void putStringSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }

    public static String getValueFromKey(String key) {
        return sharedPreferences.getString(key,null);
    }

    public static void addOrAppendStringToSharedPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String oldValue = sharedPreferences.getString(key, null);

        if(oldValue == null) {
            editor.putString(key, value).apply();
        }
        else {
            editor.putString(key, oldValue + ',' + value).apply();
        }
    }

    public static void addFavoriteItem(Context context, String item) {
        item = item.toLowerCase().trim();
        //check if already existed
        ArrayList<String> fav = getAllFavoriteItem();
        if(fav.contains(item)) {
            return;
        }

        //check if conflict with dislike
        ArrayList<String> dislike = getAllDislikeItem();
        if(dislike.contains(item.trim())) {
            //removed first then add
            removeDislikeItem(item.trim());
            addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.favoriteFood), item);
        }
        else {
            //add normally
            addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.favoriteFood), item);
        }
    }

    public static void forceAddFavoriteItem(Context context, String item) {
        item = item.toLowerCase();
        // add without checking. Only use in special cases
        addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.favoriteFood),item);
    }

    public static void removeFavoriteItem(String itemToRemoved) {
        itemToRemoved = itemToRemoved.toLowerCase().trim();
        ArrayList<String> favoriteList = getAllFavoriteItem();
        if(favoriteList.size() == 0) {
            return;
        }
        String newList = "";
        for (String iterator: favoriteList) {
            Log.d("ITERAATORRRR", ">>" + iterator);
            if(!iterator.equals(itemToRemoved))
                newList += iterator + ',';
        }
        if(newList.length() > 0)
            newList = newList.substring(0, newList.length()-1);

        Log.d("REMOVEFAV_fofygg", "old: " + sharedPreferences.getString(context.getResources().getString(R.string.favoriteFood), null) + " new: " + newList);
        putStringSharedPreferences(context.getResources().getString(R.string.favoriteFood), newList);
    }

    public static ArrayList<String> getAllFavoriteItem() {
        String favorite = sharedPreferences.getString(context.getResources().getString(R.string.favoriteFood), null);
        if(favorite == null) {
            return new ArrayList<String>();
        }
        favorite = favorite.trim();


        return new ArrayList<String>(Arrays.asList(favorite.trim().split(",")));
    }
    public static ArrayList<String> getPrefFavListtFofy() {
        String favorite = sharedPreferences.getString(context.getResources().getString(R.string.favoriteFood), null);
        if(favorite == null || favorite.trim().equals("")) {
            return new ArrayList<String>();
        }
        favorite = favorite.trim();
        if(favorite.length() >= 2) {
            // favorite = favorite.substring(1);
        }
        Log.d("fromHIGH_fofygg", favorite);
        return new ArrayList<String>(Arrays.asList(favorite.trim().split(",")));
    }
    public static boolean isVeggie() {
        return Boolean.parseBoolean(SharedPreferencesManager.getValueFromKey("isVeggie"));
    }

    public static ArrayList<String> getAllAllergens() {
        String[] allergensKey = {context.getResources().getString(R.string.AllergenEgg), context.getResources().getString(R.string.AllergenFish), context.getResources().getString(R.string.AllergenGluten)
                , context.getResources().getString(R.string.AllergenMilk), context.getResources().getString(R.string.AllergenNut), context.getResources().getString(R.string.AllergenPeanut)
                , context.getResources().getString(R.string.AllergenShellfish), context.getResources().getString(R.string.AllergenSoy), context.getResources().getString(R.string.AllergenWheat)};

        ArrayList<String> allergensList = new ArrayList<>();
        for (String allergenKey : allergensKey) {
            if(getValueFromKey(allergenKey) != null && getValueFromKey(allergenKey).equals("true")) {
                allergensList.add(allergenKey);
            }
        }

        return allergensList;
    }

    public static ArrayList<String> getAllDiets() {
        String[] dietsKey = {context.getResources().getString(R.string.isVeggie), context.getResources().getString(R.string.isVegan), context.getResources().getString(R.string.noPork)
                , context.getResources().getString(R.string.noBeef), context.getResources().getString(R.string.lowSodium), context.getResources().getString(R.string.lowSugar)
                , context.getResources().getString(R.string.lowCalories)};

        ArrayList<String> dietsList = new ArrayList<>();
        for (String dietKey : dietsKey) {
            if(getValueFromKey(dietKey) != null && getValueFromKey(dietKey).equals("true")) {
                dietsList.add(dietKey);
            }
        }

        return dietsList;
    }

    public static ArrayList<String> getAllDislikeItem() {
        String dislike = sharedPreferences.getString(context.getResources().getString(R.string.dislikeFood), null);
        if(dislike == null) {
            return new ArrayList<String>();
        }
        dislike = dislike.trim();

        return new ArrayList<String>(Arrays.asList(dislike.trim().split(",")));
    }
    public static ArrayList<String> getPrefDislikeList() {
        String dislike = sharedPreferences.getString(context.getResources().getString(R.string.dislikeFood), null);
        if(dislike == null || dislike.trim().equals("")) {
            return new ArrayList<String>();
        }
        dislike = dislike.trim();
        if(dislike.length() >= 2) {
            // favorite = favorite.substring(1);
        }
        Log.d("getPrefDislikeList", dislike);
        return new ArrayList<String>(Arrays.asList(dislike.trim().split(",")));
    }

    public static void forceAddDislikeItem(Context context, String item) {
        item = item.toLowerCase();
        addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.dislikeFood),item);
    }

    public static void addDislikeItem(String item) {
        item = item.toLowerCase().trim();
        //check if already existed
        ArrayList<String> dis = getAllDislikeItem();
        if(dis.contains(item)) {
            return;
        }

        ArrayList<String> favList = getPrefFavListtFofy();
        if(favList.contains(item.trim())) {
            //remove from fav list then add
            removeFavoriteItem(item.trim());
            addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.dislikeFood), item.trim());
        }
        else {
            //add normally
            addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.dislikeFood), item.trim());
        }
    }

    public static void removeDislikeItem(String itemToRemoved) {
        itemToRemoved = itemToRemoved.toLowerCase().trim();
        ArrayList<String> dislikeList = getAllDislikeItem();
        if(dislikeList.size() == 0) {
            return;
        }
        String newList = "";
        for (String iterator: dislikeList) {
            //Log.d("ITERAATORRRR", ">>" + iterator);
            if(!iterator.equals(itemToRemoved))
                newList += iterator + ',';
        }
        if(newList.length() > 0)
            newList = newList.substring(0, newList.length()-1);

        Log.d("REMOVEFAV_fofygg", "old: " + sharedPreferences.getString(context.getResources().getString(R.string.dislikeFood), null) + " new: " + newList);
        putStringSharedPreferences(context.getResources().getString(R.string.dislikeFood), newList);
    }
}
