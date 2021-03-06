package pickyboiler.pickyboiler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import  pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;
/**
 * Created by Jackson
 */



public class RestaurantMapFragment extends Fragment {


    ImageButton viewMapButton;

    private Context mapPageContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_tab_fragment,container,false);

        mapPageContext = getContext();

        viewMapButton = (ImageButton)view.findViewById(R.id.viewMap);
        viewMapButton.setOnClickListener(handleClick);

        return view;
    }


    private View.OnClickListener handleClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            CharSequence text = "Opening Map...";
            SharedPreferencesManager.showToast((String) text);
            Intent mapIntent = new Intent(mapPageContext,MapsActivity.class);
            mapPageContext.startActivity(mapIntent);
        }
    };

    @Override
    public void onResume() {

        super.onResume();
    }
}
