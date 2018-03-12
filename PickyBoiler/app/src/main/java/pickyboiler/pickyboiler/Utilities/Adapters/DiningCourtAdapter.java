package pickyboiler.pickyboiler.Utilities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import pickyboiler.pickyboiler.MenuDisplayPage;
import pickyboiler.pickyboiler.R;
import pickyboiler.pickyboiler.MainActivity;
import pickyboiler.pickyboiler.Utilities.Storage.ApplicationController;

import static android.support.v4.content.ContextCompat.startActivity;

public class DiningCourtAdapter extends RecyclerView.Adapter<DiningCourtAdapter.InfoViewHolder> {
    private String[] diningCourtNames;// = {"Ford", "Erhart", "Wiley", "Hillenbrand", "Windsor"};
    private String[] numsVeggies;// = {"3", "2", "3", "2", "100"};
    private String[] listAllergens;// = {"GG", "RIP", "F", "DEATH", "Shrimp"};
    private String[] urlList;// = {"http://www.google.com", "http://www.youtube.com", "http://www.apple.com", "http://www.slickdeals.com", "http://www.purdue.com"};


    private ArrayList<JSONObject> diningCourtMenus;

    private Context mainContext;

//    public void addData(HashMap<String, JSONObject> dataSet, Context context) {
//        String[] keys = dataSet.keySet().toArray(new String[dataSet.size()]);
//        diningCourtNames = new String[dataSet.size()];
//        numsVeggies = new String[dataSet.size()];
//        listAllergens = new String[dataSet.size()];
//        urlList = new String[dataSet.size()];
//        for (int i = 0; i < keys.length; i++) {
//            diningCourtNames[i] = keys[i];
//            try {
//                numsVeggies[i] = dataSet.get(keys[i]).getString(context.getString(R.string.VegetarianCount));
//                JSONArray arr = dataSet.get(keys[i]).getJSONObject(context.getString(R.string.AllergenCount)).names();
//                StringBuilder listAllergen = new StringBuilder();
//                for (int j = 0; j < arr.length(); j++) {
//                    if(j == arr.length() - 1) {
//                        listAllergen.append(arr.get(j));
//                    } else {
//                        listAllergen.append(arr.get(j));
//                        listAllergen.append(", ");
//                    }
//
//                }
//                listAllergens[i] = listAllergen.toString();
//                urlList[i] = dataSet.get(keys[i]).getString(context.getString(R.string.URL));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        notifyDataSetChanged();
//    }

    public void addData(ArrayList<JSONObject> allCurrentMeal, Context context) {
        diningCourtNames = new String[allCurrentMeal.size()];
        numsVeggies = new String[allCurrentMeal.size()];
        listAllergens = new String[allCurrentMeal.size()];
        urlList = new String[allCurrentMeal.size()];

        diningCourtMenus = allCurrentMeal;

        mainContext = context;

        try {
            for (int i = 0; i < allCurrentMeal.size(); i++) {


                diningCourtNames[i] = allCurrentMeal.get(i).getString("Location");
                //Log.d("DUPLICAEBUGGGGGG", "addData: " + diningCourtNames[i]);
                //TODO: change back to numVeggies after debugging
                //numsVeggies[i] = allCurrentMeal.get(i).getString("computedRanking");
                numsVeggies[i] = allCurrentMeal.get(i).getString("favoriteCounts");
                //numsVeggies[i] = allCurrentMeal.get(i).getString(context.getString(R.string.VegetarianCount));

                StringBuilder listAllergen = new StringBuilder();
                JSONArray arr = allCurrentMeal.get(i).getJSONObject(context.getString(R.string.AllergenCount)).names();
                if(arr != null && arr.length() > 0) {
                    for (int j = 0; j < arr.length(); j++) {
                        if (arr.get(j).equals("Vegetarian"))
                            continue;
                        if (j == arr.length() - 1) {
                            listAllergen.append(arr.get(j));
                        } else {
                            listAllergen.append(arr.get(j));
                            listAllergen.append(", ");
                        }
                    }
                }
                listAllergens[i] = listAllergen.toString();
                urlList[i] = allCurrentMeal.get(i).getString(context.getString(R.string.URL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }


    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.places_list_item, parent, false);
        return new InfoViewHolder(view,mainContext);
    }
    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.diningCourtNameText.setText(holder.diningCourtNameText.getText().toString()+diningCourtNames[position]);
        holder.allergenListText.setText(holder.allergenListText.getText().toString()+listAllergens[position]);
        holder.countVeggiesText.setText(holder.countVeggiesText.getText().toString()+numsVeggies[position]);
    }

    @Override
    public int getItemCount() {
        if(diningCourtNames == null) return 0;
        else {
            return diningCourtNames.length;
        }
    }
    public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final static String sendDiningCourt = "SEND_DINING_COURT_NAME";
        public final static String sendMenuItems = "SEND_MENU_ITEMS";

        TextView diningCourtNameText;
        TextView allergenListText;
        TextView countVeggiesText;

        Context context;

        public InfoViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            diningCourtNameText = (TextView) itemView.findViewById(R.id.tv_place_name);
            allergenListText = (TextView) itemView.findViewById(R.id.tv_allergens);
            countVeggiesText = (TextView) itemView.findViewById(R.id.tv_veggies_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //get the current dining court

            String diningCourt = diningCourtNames[getAdapterPosition()];


            Iterator<String> menuIterator = null;
            ArrayList<String> menuItem = new ArrayList<>();
            try {
                for (int i = 0; i < diningCourtMenus.size(); i++) {
                    String tempCourt = diningCourtMenus.get(i).getString("Location");
                    if (tempCourt == diningCourt){
                        menuIterator = diningCourtMenus.get(i).getJSONObject("AllMeal").keys();
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            if (menuIterator != null){
                while (menuIterator.hasNext()){
                    menuItem.add(menuIterator.next());
                }
            }else{
                menuItem.add("NO DINING COURT SELECTED");
            }

            Intent menuPage = new Intent(context,MenuDisplayPage.class);
            menuPage.putExtra(sendDiningCourt,diningCourt);
            menuPage.putExtra(sendMenuItems,menuItem);

            context.startActivity(menuPage);



        }
    }
}