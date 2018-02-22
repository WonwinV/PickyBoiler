package pickyboiler.pickyboiler;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import java.util.HashMap;


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


        }

    };


    @Override
    public void onResume() {
        super.onResume();

    }

}
