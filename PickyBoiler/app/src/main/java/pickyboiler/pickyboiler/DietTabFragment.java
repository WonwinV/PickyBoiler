package pickyboiler.pickyboiler;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import pickyboiler.pickyboiler.utilities.storage.SharedPreferencesManager;

/**
 * Created by Prin on 2/15/18.
 * Implemented by Jackson 2/22/18
 */

public class DietTabFragment extends Fragment {

    private static final String TAG = "DietTabFragment";

    ToggleButton vegetarian;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //TODO: create diet_tab_fragment
        View view = inflater.inflate(R.layout.allergen_tab_fragment,container,false);


        //TODO: implement vegetarian_btn
        vegetarian = (ToggleButton) view.findViewById(R.id.vegetarian_btn);
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


}
