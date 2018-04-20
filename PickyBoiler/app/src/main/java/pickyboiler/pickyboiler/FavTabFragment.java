package pickyboiler.pickyboiler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Collections;

import pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;

import static pickyboiler.pickyboiler.R.id.autoCompleteTextView;
import static pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager.getAllDislikeItem;
import static pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager.getAllFavoriteItem;

/**
 * Created by Prin on 2/15/18.
 */

public class FavTabFragment extends Fragment {
  private static final String TAG = "FavTabFragment";

    public static Toast toastShow;
    private SwipeMenuListView mListView;
    ArrayList<String> favItemsList;
    ArrayList<String> dislike;
    ListView listView;
    ArrayAdapter adapter;
    ArrayAdapter<String> ada;
    String[] favorites = SharedPreferencesManager.getArrayOfDiningCourtMenu();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("header_fofygg", "TEST 1");
        final View view = inflater.inflate(R.layout.fav_tab_fragment,container,false);
        Log.d("header_fofygg", "TEST 2");
        mListView = (SwipeMenuListView) view.findViewById(R.id.favfood_lv);
        Log.d("header_fofygg", "TEST 3");
        listView = (ListView) view.findViewById(R.id.favfood_lv);

        favItemsList = SharedPreferencesManager.getPrefFavListtFofy();
        for (int i = 0; i < favItemsList.size(); i++) {
            if(favItemsList.get(i).trim().length() == 0)
                favItemsList.remove(i);
        }

        Log.d("header_fofygg", "TEST 4");
        String forbg = "";
        for (String x:
             favItemsList) {
            forbg += x + ",";
        }
        Log.d("header_fofygg", "TEST 5");
        Log.d("favlist_fofygg", forbg);
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,favItemsList);

        //Create instance to check autofill
        ada = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, favorites);
        AutoCompleteTextView actv = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1); //will start working from first character
        actv.setAdapter(ada);

        view.findViewById(R.id.addfood_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String favItems = ((AutoCompleteTextView) view.findViewById(autoCompleteTextView)).getText().toString();
                //System.out.println(favItems);
                if (favItems.trim().length() <= 0) {
                    //Toast.makeText(getActivity(), "Input should not be empty.", Toast.LENGTH_SHORT).show();
                    SharedPreferencesManager.showToast("Input should not be empty.");
                    return;
                }
                if (!favItems.matches("[a-zA-Z ]+")) {
                    SharedPreferencesManager.showToast("Input should not contain invalid character.");
                    return;
                }
                if (favItemsList.contains(favItems.trim().toLowerCase())) {
                    SharedPreferencesManager.showToast("Item already added.");
                    return;
                }

                /*SharedPref*/
                dislike = getAllDislikeItem();
                if(dislike.contains(favItems.toLowerCase().trim())) {
                    //remove from fav list then add
                    Log.d("CONFLICT", "onClick: found conflict");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setTitle("Food Preference Conflict");
                    builder.setMessage("The item that is being added will be added to Favorites page but will be removed from Diets page as the item already exists in the Diets page.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                Log.d("CONFLICT", "onClick: no conflict");
                SharedPreferencesManager.addFavoriteItem(getActivity().getApplicationContext(), favItems.trim());
                Log.d("what_added_fofygg", "||"+ favItems.trim());
                Log.d("testSharedPref_fofygg", ">>>" + SharedPreferencesManager.getValueFromKey(getResources().getString(R.string.favoriteFood)));

                favItemsList.add(favItems.trim());
                SharedPreferencesManager.showToast("Item added.");
                Collections.sort(favItemsList);
                //ListView listView = (ListView) view.findViewById(R.id.favfood_lv);
                //ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,favItemsList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                ((AutoCompleteTextView) view.findViewById(autoCompleteTextView)).setText("");
            }
        });


        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
      
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

        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        SharedPreferencesManager.removeFavoriteItem(favItemsList.get(position));
                        favItemsList.remove(position);
                        adapter.notifyDataSetChanged();
                        SharedPreferencesManager.showToast("Item deleted.");
                        break;
                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //SharedPreferencesManager.showToast("FAV onRESUME");

        favItemsList.clear();
        favItemsList = getAllFavoriteItem();
        for (int i = 0; i < favItemsList.size(); i++) {
            if(favItemsList.get(i).trim().length() == 0)
                favItemsList.remove(i);
        }
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,favItemsList);
        listView.setAdapter(adapter);

        //FOR DIETS
        dislike = getAllDislikeItem();
        for (int i = 0; i < dislike.size(); i++) {
            if(dislike.get(i).trim().length() == 0)
                dislike.remove(i);
        }
        //adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,dislike);
        //listView.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            //SharedPreferencesManager.showToast("FAV VISIBLE");

            //DietTabFragment dtf = new DietTabFragment();
            //dtf.onResume();

            //favItemsList.clear();
            if (favItemsList != null) {
                favItemsList = getAllFavoriteItem();
                for (int i = 0; i < favItemsList.size(); i++) {
                    if(favItemsList.get(i).trim().length() == 0)
                        favItemsList.remove(i);
                }
                adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,favItemsList);
                listView.setAdapter(adapter);
            }

            //FOR DIETS
            dislike = getAllDislikeItem();
            for (int i = 0; i < dislike.size(); i++) {
                if(dislike.get(i).trim().length() == 0)
                    dislike.remove(i);
            }
            //adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,dislike);
            //listView.setAdapter(adapter);
        }
    }

}
