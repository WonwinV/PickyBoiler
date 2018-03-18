package pickyboiler.pickyboiler;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;


/**
 * Created by Prin on 2/15/18.
 * Implemented by Jackson 2/22/18
 */

public class DietTabFragment extends Fragment {

    private static final String TAG = "DietTabFragment";

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

        View view = inflater.inflate(R.layout.diet_tab_fragment,container,false);

        vegetarian = (ToggleButton) view.findViewById(R.id.vegetarian_btn);
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
        lowcalories.setOnClickListener(handleDietClick);

        vegetarian.setOnClickListener(new View.OnClickListener() {

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
        }

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
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenEgg), "true");
                    }
                    else {
                        text = "Removed vegetarian from diets.";
                        drawable = getResources().getDrawable(R.drawable.vegetarian_bw);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenEgg), "false");
                        view.setBackgroundDrawable(drawable);
                    }
                    break;


                case R.id.vegan_btn:
                    if (pressed) {
                        text = "Added vegan to diets.";
                        drawable = getResources().getDrawable(R.drawable.fish);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenFish), "true");
                    }
                    else {
                        text = "Removed vegan from diets.";
                        drawable = getResources().getDrawable(R.drawable.fish_bw);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenFish), "false");
                    }
                    break;


                case R.id.nopork_btn:
                    if (pressed) {
                        text = "Added no pork to diets.";
                        drawable = getResources().getDrawable(R.drawable.nopork);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenGluten), "true");
                    }
                    else {
                        text = "Removed no pork from diets.";
                        drawable = getResources().getDrawable(R.drawable.nopork_bw);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenGluten), "false");
                    }
                    break;


                case R.id.milk_btn:
                    if (pressed) {
                        text = "Added milk to allergens.";
                        drawable = getResources().getDrawable(R.drawable.milk);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenMilk), "true");
                    }
                    else {
                        text = "Removed milk from allergens.";
                        drawable = getResources().getDrawable(R.drawable.milk_bw);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenMilk), "false");
                        view.setBackgroundDrawable(drawable);
                    }
                    break;


                case R.id.nut_btn:
                    if (pressed) {
                        text = "Added nut to allergens.";
                        drawable = getResources().getDrawable(R.drawable.nut);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenNut), "true");
                    }
                    else {
                        text = "Removed nut from allergens.";
                        drawable = getResources().getDrawable(R.drawable.nut_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenNut), "false");
                    }
                    break;


                case R.id.peanut_btn:
                    if (pressed) {
                        text = "Added peanut to allergens.";
                        drawable = getResources().getDrawable(R.drawable.peanut);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenPeanut), "true");
                    }
                    else {
                        text = "Removed peanut from allergens.";
                        drawable = getResources().getDrawable(R.drawable.peanut_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenPeanut), "false");
                    }
                    break;


                case R.id.shellfish_btn:
                    if (pressed) {
                        text = "Added shellfish to allergens.";
                        drawable = getResources().getDrawable(R.drawable.shellfish);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenShellfish), "true");
                    }
                    else {
                        text = "Removed shellfish from allergens.";
                        drawable = getResources().getDrawable(R.drawable.shellfish_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenShellfish), "false");
                    }
                    break;


                case R.id.soy_btn:
                    if (pressed) {
                        text = "Added soy to allergens.";
                        drawable = getResources().getDrawable(R.drawable.soy);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenSoy), "true");
                    }
                    else {
                        text = "Removed soy from allergens.";
                        drawable = getResources().getDrawable(R.drawable.soy_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenSoy), "false");
                    }
                    break;


                case R.id.wheat_btn:
                    if (pressed) {
                        text = "Added wheat to allergens.";
                        drawable = getResources().getDrawable(R.drawable.wheat);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenWheat), "true");
                    }
                    else {
                        text = "Removed wheat from allergens.";
                        drawable = getResources().getDrawable(R.drawable.wheat_bw);
                        view.setBackgroundDrawable(drawable);
                        SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenWheat), "false");
                    }
                    break;
            }

            SharedPreferencesManager.showToast((String) text);

        }

    };


    @Override
    public void onResume(){
        super.onResume();

        if(SharedPreferencesManager.isVeggie()) {
            getView().findViewById(R.id.vegetarian_btn).setBackgroundDrawable(getResources().getDrawable(R.drawable.vegetarian));
        }

    }

}
