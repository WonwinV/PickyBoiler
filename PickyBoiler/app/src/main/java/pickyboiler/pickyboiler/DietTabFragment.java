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
                        drawable = getResources().getDrawable(R.drawable.vegan);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenFish), "true");
                    }
                    else {
                        text = "Removed vegan from diets.";
                        drawable = getResources().getDrawable(R.drawable.vegan_bw);
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


                case R.id.nobeef_btn:
                    if (pressed) {
                        text = "Added no beef to diets.";
                        drawable = getResources().getDrawable(R.drawable.nobeef);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenMilk), "true");
                    }
                    else {
                        text = "Removed no beef from diets.";
                        drawable = getResources().getDrawable(R.drawable.nobeef_bw);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenMilk), "false");
                        view.setBackgroundDrawable(drawable);
                    }
                    break;


                case R.id.lowsodium_btn:
                    if (pressed) {
                        text = "Added low sodium to diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsodium);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenNut), "true");
                    }
                    else {
                        text = "Removed low sodium from diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsodium_bw);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenNut), "false");
                    }
                    break;


                case R.id.lowsugar_btn:
                    if (pressed) {
                        text = "Added low sugar to diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsugar);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenPeanut), "true");
                    }
                    else {
                        text = "Removed low sugar from diets.";
                        drawable = getResources().getDrawable(R.drawable.lowsugar_bw);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenPeanut), "false");
                    }
                    break;


                case R.id.lowcalories_btn:
                    if (pressed) {
                        text = "Added low calories to diets.";
                        drawable = getResources().getDrawable(R.drawable.lowcalories);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenShellfish), "true");
                    }
                    else {
                        text = "Removed low calories from diets.";
                        drawable = getResources().getDrawable(R.drawable.lowcalories_bw);
                        view.setBackgroundDrawable(drawable);
                        //SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenShellfish), "false");
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
