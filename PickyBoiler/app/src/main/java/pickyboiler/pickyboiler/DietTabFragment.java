package pickyboiler.pickyboiler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

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


        return view;
    }


}
