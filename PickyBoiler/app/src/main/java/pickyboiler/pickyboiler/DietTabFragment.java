package pickyboiler.pickyboiler;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;

import static pickyboiler.pickyboiler.R.id.autoCompleteDietTextView;
import static pickyboiler.pickyboiler.R.id.autoCompleteTextView;


/**
 * Created by Prin on 2/15/18.
 * Implemented by Jackson 2/22/18
 */

public class DietTabFragment extends Fragment {

    private static final String TAG = "DietTabFragment";

    private static HashMap<String, ToggleButton> hashMap;

    public static Toast toastShow;
    private SwipeMenuListView mListView;
    ArrayList<String> dietItemsList;
    ListView listView;
    ArrayAdapter adapter;
    ArrayAdapter<String> ada;
    String[] diets = SharedPreferencesManager.getArrayOfDiningCourtMenu();

    ToggleButton vegetarian;
    ToggleButton vegan;
    ToggleButton nopork;
    ToggleButton nobeef;
    ToggleButton lowsodium;
    ToggleButton lowsugar;
    ToggleButton lowcalories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.diet_tab_fragment,container,false);
        mListView = (SwipeMenuListView) view.findViewById(R.id.diet_lv);
        listView = (ListView) view.findViewById(R.id.diet_lv);

        dietItemsList = SharedPreferencesManager.getAllDislikeItem();
        String dietabc = "";
        for (String x:
                dietItemsList) {
            dietabc += x + ",";
        }
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,dietItemsList);
        ada = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, diets);
        AutoCompleteTextView actv = (AutoCompleteTextView) view.findViewById(autoCompleteDietTextView);
        actv.setThreshold(1);
        actv.setAdapter(ada);

        view.findViewById(R.id.adddiet_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dietItems = ((AutoCompleteTextView) view.findViewById(autoCompleteDietTextView)).getText().toString();

                if (dietItems.trim().length() <= 0) {
                    //Toast.makeText(getActivity(), "Input should not be empty.", Toast.LENGTH_SHORT).show();
                    SharedPreferencesManager.showToast("Input should not be empty.");
                    return;
                }
                if (!dietItems.matches("[a-zA-Z ]+")) {
                    SharedPreferencesManager.showToast("Input should not contain invalid character.");
                    return;
                }
                if (dietItemsList.contains(dietItems.trim().toLowerCase())) {
                    SharedPreferencesManager.showToast("Item already added.");
                    return;
                }

                //SharedPreferences
                SharedPreferencesManager.addDislikeItem(dietItems.trim());

                dietItemsList.add(dietItems.trim());
                SharedPreferencesManager.showToast("Item added.");
                Collections.sort(dietItemsList);
                listView.setAdapter(adapter);

                ((AutoCompleteTextView) view.findViewById(autoCompleteDietTextView)).setText("");
            }
        });

        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 149, 128)));  //rgb(237,70,47)
                // set item width
                deleteItem.setWidth(250);
                // set an icon
                //deleteItem.setIcon(R.drawable.ic_action_discard);
                // set text
                deleteItem.setTitle("Delete");
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(16);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        //SharedPreferencesManager.removeFavoriteItem(dietItemsList.get(position));
                        SharedPreferencesManager.removeDislikeItem(dietItemsList.get(position));
                        dietItemsList.remove(position);
                        adapter.notifyDataSetChanged();
                        SharedPreferencesManager.showToast("Item deleted.");
                        break;
                }
                return true;
            }
        });

        hashMap = new HashMap<>();

        vegetarian = (ToggleButton) view.findViewById(R.id.vegetarian_btn);
        vegetarian.setOnClickListener(handleDietClick);
        hashMap.put("isVeggie", vegetarian);

        /*vegetarian = (ToggleButton) view.findViewById(R.id.vegetarian_btn);
        vegan = (ToggleButton) view.findViewById(R.id.vegan_btn);
        nopork = (ToggleButton) view.findViewById(R.id.nopork_btn);
        nobeef = (ToggleButton) view.findViewById(R.id.nobeef_btn);
        lowsodium = (ToggleButton) view.findViewById(R.id.lowsodium_btn);
        lowsugar = (ToggleButton) view.findViewById(R.id.lowsugar_btn);
        lowcalories = (ToggleButton) view.findViewById(R.id.lowcalories_btn);

        vegetarian.setOnClickListener(handleDietClick);
        vegan.setOnClickListener(handleDietClick);
        nopork.setOnClickListener(handleDietClick);
        nobeef.setOnClickListener(handleDietClick);
        lowsodium.setOnClickListener(handleDietClick);
        lowsugar.setOnClickListener(handleDietClick);
        lowcalories.setOnClickListener(handleDietClick);*/

        /*hashMap.put("isVeggie", vegetarian);
        hashMap.put("isVegan", vegan);
        hashMap.put("noPork", nopork);
        hashMap.put("noBeef", nobeef);
        hashMap.put("lowSodium", lowsodium);
        hashMap.put("lowSugar", lowsugar);
        hashMap.put("lowCalories", lowcalories);*/

        /*vegetarian.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View v) {

                                              String text;
                                              Drawable drawable;

                                              if (((ToggleButton) v).isChecked()) {
                                                  text = "Added vegetarian to dietary needs.";
                                                  drawable = getResources().getDrawable(R.drawable.vegetarian);
                                                  v.setBackgroundDrawable(drawable);
                                                  SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVeggie), true).apply();
                                              } else {
                                                  text = "Removed vegetarian from dietary needs.";
                                                  drawable = getResources().getDrawable(R.drawable.vegetarian_bw);
                                                  SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVeggie), false).apply();
                                                  v.setBackgroundDrawable(drawable);
                                              }
                                              SharedPreferencesManager.showToast(text);
                                          }
                                      }
        );


        if(SharedPreferencesManager.isVeggie()) {
            view.findViewById(R.id.vegetarian_btn).setBackgroundDrawable(getResources().getDrawable(R.drawable.vegetarian));
        }*/

        return view;
    }

    private View.OnClickListener handleDietClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            boolean pressed = ((ToggleButton) view).isChecked();
            CharSequence text = "";
            Drawable drawable;

            switch (view.getId()) {

                //PLEASE DO NOT REMOVE MY COMMENTS -Prin

                case R.id.vegetarian_btn:
                    if (pressed) {
                        text = "Added vegetarian to diets.";
                        drawable = getResources().getDrawable(R.drawable.vegetarian);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.isVeggie), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVeggie), true).apply();
                    } else {
                        text = "Removed vegetarian from diets.";
                        drawable = getResources().getDrawable(R.drawable.vegetarian_bw);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.isVeggie), "false");
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVeggie), false).apply();
                    }
                    break;
            }

            SharedPreferencesManager.showToast((String) text);

        }
    };

    /*private View.OnClickListener handleDietClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            boolean pressed = ((ToggleButton) view).isChecked();
            CharSequence text = "";
            Drawable drawable;

            switch (view.getId()) {

                //PLEASE DO NOT REMOVE MY COMMENTS -Prin

                case R.id.vegetarian_btn:
                    if (pressed) {
                        text = "Added vegetarian to diets.";
                        drawable = getResources().getDrawable(R.drawable.vegetarian);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.isVeggie), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVeggie), true).apply();
                    }
                    else {
                        text = "Removed vegetarian from diets.";
                        drawable = getResources().getDrawable(R.drawable.vegetarian_bw);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.isVeggie), "false");
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVeggie), false).apply();
                    }
                    break;


                case R.id.vegan_btn:
                    if (pressed) {
                        text = "Added vegan to diets.";
                        drawable = getResources().getDrawable(R.drawable.vegan);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.isVegan), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVegan), true).apply();
                    }
                    else {
                        text = "Removed vegan from diets.";
                        drawable = getResources().getDrawable(R.drawable.vegan_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.isVegan), "false");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.isVegan), false).apply();
                    }
                    break;


                case R.id.nopork_btn:
                    if (pressed) {
                        text = "Added no pork to diets.";
                        drawable = getResources().getDrawable(R.drawable.nopork);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.noPork), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.noPork), true).apply();
                    }
                    else {
                        text = "Removed no pork from diets.";
                        drawable = getResources().getDrawable(R.drawable.nopork_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.noPork), "false");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.noPork), false).apply();
                    }
                    break;


                case R.id.nobeef_btn:
                    if (pressed) {
                        text = "Added no beef to diets.";
                        drawable = getResources().getDrawable(R.drawable.nobeef);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.noBeef), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.noBeef), true).apply();
                    }
                    else {
                        text = "Removed no beef from diets.";
                        drawable = getResources().getDrawable(R.drawable.nobeef_bw);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.noBeef), "false");
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.noBeef), false).apply();
                    }
                    break;


                case R.id.lowsodium_btn:
                    if (pressed) {
                        text = "Added low sodium to diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsodium);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.lowSodium), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.lowSodium), true).apply();
                    }
                    else {
                        text = "Removed low sodium from diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsodium_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.lowSodium), "false");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.lowSodium), false).apply();
                    }
                    break;


                case R.id.lowsugar_btn:
                    if (pressed) {
                        text = "Added low sugar to diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsugar);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.lowSugar), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.lowSugar), true).apply();
                    }
                    else {
                        text = "Removed low sugar from diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsugar_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.lowSugar), "false");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.lowSugar), false).apply();
                    }
                    break;


                case R.id.lowcalories_btn:
                    if (pressed) {
                        text = "Added low calories to diets.";
                        drawable = getResources().getDrawable(R.drawable.lowcalories);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.lowCalories), "true");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.lowCalories), true).apply();
                    }
                    else {
                        text = "Removed low calories from diets.";
                        drawable = getResources().getDrawable(R.drawable.lowcalories_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.lowCalories), "false");
                        //SharedPreferencesManager.getPrefs().edit().putBoolean(getString(R.string.lowCalories), false).apply();
                    }
                    break;
            }

            SharedPreferencesManager.showToast((String) text);

        }

    };*/


    @Override
    public void onResume(){
        /*super.onResume();

        if(SharedPreferencesManager.isVeggie()) {
            getView().findViewById(R.id.vegetarian_btn).setBackgroundDrawable(getResources().getDrawable(R.drawable.vegetarian));
        }*/

        super.onResume();

        //create array list to store preferences selected by user
        ArrayList<String> userSelected = SharedPreferencesManager.getAllDiets();

        for (String x: userSelected) {

            ToggleButton current = hashMap.get(x);
            current.setChecked(true);

            if (current.equals(vegetarian)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.vegetarian));
            }
        }

        //loop through array list
        /*for (String x: userSelected) {

            ToggleButton current = hashMap.get(x);
            current.setChecked(true);

            //if selected by user
            if(current.equals(vegetarian)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.vegetarian));
            }
            else if(current.equals(vegan)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.vegan));
            }
            else if(current.equals(nopork)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.nopork));
            }
            else if(current.equals(nobeef)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.nobeef));
            }
            else if(current.equals(lowsodium)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.lowsodium));
            }
            else if(current.equals(lowsugar)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.lowsugar));
            }
            else if(current.equals(lowcalories)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.lowcalories));
            }
        }*/

    }
}
