package pickyboiler.pickyboiler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Prin on 2/15/18.
 */

public class FavTabFragment extends Fragment {
  private static final String TAG = "FavTabFragment";

    public static Toast toastShow;
    private SwipeMenuListView mListView;
    ArrayList<String> favItemsList;
    ListView listView;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("header_fofygg", "TEST 1");
        final View view = inflater.inflate(R.layout.fav_tab_fragment,container,false);
        Log.d("header_fofygg", "TEST 2");
        mListView = (SwipeMenuListView) view.findViewById(R.id.favfood_lv);
        Log.d("header_fofygg", "TEST 3");
        listView = (ListView) view.findViewById(R.id.favfood_lv);

        favItemsList = SharedPreferencesManager.getPrefFavListtFofy();
        Log.d("header_fofygg", "TEST 4");
        String forbg = "";
        for (String x:
             favItemsList) {
            forbg += x + ",";
        }
        Log.d("header_fofygg", "TEST 5");
        Log.d("favlist_fofygg", forbg);

        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,favItemsList);

        view.findViewById(R.id.addfood_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favItems = ((EditText) view.findViewById(editText)).getText().toString();
                if (favItems.trim().length() <= 0) {
                    //Toast.makeText(getActivity(), "Input should not be empty.", Toast.LENGTH_SHORT).show();
                    SharedPreferencesManager.showToast("Input should not be empty.");
                    return;
                }
                if (!favItems.matches("[a-zA-Z ]+")) {
                    SharedPreferencesManager.showToast("Input should not contain invalid character.");
                    return;
                }
                if (favItemsList.contains(favItems.trim())) {
                    SharedPreferencesManager.showToast("Item already added.");
                    return;
                }

                /*SharedPref*/
                SharedPreferencesManager.addFavoriteItem(getActivity().getApplicationContext(), favItems.trim());
                Log.d("what_added_fofygg", "||"+favItems.trim());
                Log.d("testSharedPref_fofygg", ">>>" + SharedPreferencesManager.getValueFromKey(getActivity().getApplicationContext(), getResources().getString(R.string.favoriteFood)));

                favItemsList.add(favItems.trim());
                SharedPreferencesManager.showToast("Item added.");
                Collections.sort(favItemsList);
                //ListView listView = (ListView) view.findViewById(R.id.favfood_lv);
                //ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,favItemsList);
                listView.setAdapter(adapter);

                ((EditText) view.findViewById(editText)).setText("");
            }
        });


        listView.setAdapter(adapter);
}
