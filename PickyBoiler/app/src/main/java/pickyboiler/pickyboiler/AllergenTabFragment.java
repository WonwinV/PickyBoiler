package pickyboiler.pickyboiler;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.HashMap;


import android.widget.Toast;

import pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;


/**
 * Created by Prin on 2/15/18.
 * Implemented by Jackson 2/21/18
 */

public class AllergenTabFragment extends Fragment {

    private static final String TAG = "AllergenTabFragment";

    private static HashMap<String, ToggleButton> hashMap;

    // Sorted by name
    ToggleButton egg;
    ToggleButton fish;
    ToggleButton gluten;
    ToggleButton milk;
    ToggleButton nut;
    ToggleButton peanut;
    ToggleButton shellfish;
    ToggleButton soy;
    ToggleButton wheat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //need to implement allergen_tab_fragment xml
       View view = inflater.inflate(R.layout.allergen_tab_fragment,container,false);

        hashMap = new HashMap<>();

        //get ToggleButton through ID       name_btn
        egg = (ToggleButton) view.findViewById(R.id.egg_btn);
        fish = (ToggleButton) view.findViewById(R.id.fish_btn);
        gluten = (ToggleButton) view.findViewById(R.id.gluten_btn);
        milk = (ToggleButton) view.findViewById(R.id.milk_btn);
        nut = (ToggleButton) view.findViewById(R.id.nut_btn);
        peanut = (ToggleButton) view.findViewById(R.id.peanut_btn);
        shellfish = (ToggleButton) view.findViewById(R.id.shellfish_btn);
        soy = (ToggleButton) view.findViewById(R.id.soy_btn);
        wheat = (ToggleButton) view.findViewById(R.id.wheat_btn);

        //set click listeners
        //handleClick function to deal with button clicks
        egg.setOnClickListener(handleClick);
        fish.setOnClickListener(handleClick);
        gluten.setOnClickListener(handleClick);
        milk.setOnClickListener(handleClick);
        nut.setOnClickListener(handleClick);
        peanut.setOnClickListener(handleClick);
        shellfish.setOnClickListener(handleClick);
        soy.setOnClickListener(handleClick);
        wheat.setOnClickListener(handleClick);

        //add all buttons to hashMap
        hashMap.put("Egg", egg);
        hashMap.put("Fish", fish);
        hashMap.put("Gluten", gluten);
        hashMap.put("Milk", milk);
        hashMap.put("Nut", nut);
        hashMap.put("Peanut", peanut);
        hashMap.put("Shellfish", shellfish);
        hashMap.put("Soy", soy);
        hashMap.put("Wheat", wheat);


        return view;

    }

    //add handleClick listener
    private View.OnClickListener handleClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
          boolean pressed = ((ToggleButton) view).isChecked();
          CharSequence text = "";
          Drawable drawable;

          switch (view.getId()) {

              case R.id.egg_btn:
                  if (pressed) {
                      text = "Added egg to allergens.";
                      drawable = getResources().getDrawable(R.drawable.egg);
                      view.setBackgroundDrawable(drawable);
                      SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenEgg), "true");
                  }
                  else {
                      text = "Removed egg from allergens.";
                      drawable = getResources().getDrawable(R.drawable.egg_bw);
                      SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenEgg), "false");
                      view.setBackgroundDrawable(drawable);
                  }
                  break;


              case R.id.fish_btn:
                  if (pressed) {
                      text = "Added fish to allergens.";
                      drawable = getResources().getDrawable(R.drawable.fish);
                      view.setBackgroundDrawable(drawable);
                      SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenFish), "true");
                  }
                  else {
                      text = "Removed fish from allergens.";
                      drawable = getResources().getDrawable(R.drawable.fish_bw);
                      view.setBackgroundDrawable(drawable);
                      SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenFish), "false");
                  }
                  break;


              case R.id.gluten_btn:
                  if (pressed) {
                      text = "Added gluten to allergens.";
                      drawable = getResources().getDrawable(R.drawable.gluten);
                      view.setBackgroundDrawable(drawable);
                      SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenGluten), "true");
                  }
                  else {
                      text = "Removed gluten from allergens.";
                      drawable = getResources().getDrawable(R.drawable.gluten_bw);
                      view.setBackgroundDrawable(drawable);
                      SharedPreferencesManager.putStringSharedPreferences(getString(R.string.AllergenGluten), "false");
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
    public void onResume() {

        super.onResume();

        //create array list to store preferences selected by user
        ArrayList<String> userSelected = SharedPreferencesManager.getAllAllergens();

        //loop through array list
        for (String x: userSelected) {

            ToggleButton current = hashMap.get(x);
            current.setChecked(true);

            //if selected by user
            if(current.equals(egg)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.egg));
            }
            else if(current.equals(fish)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.fish));
            }
            else if(current.equals(gluten)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.gluten));
            }
            else if(current.equals(milk)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.milk));
            }
            else if(current.equals(nut)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.nut));
            }
            else if(current.equals(peanut)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.peanut));
            }
            else if(current.equals(shellfish)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.shellfish));
            }
            else if(current.equals(soy)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.soy));
            }
            else if(current.equals(wheat)) {
                current.setBackgroundDrawable(getResources().getDrawable(R.drawable.wheat));
            }
        }
    }

}
