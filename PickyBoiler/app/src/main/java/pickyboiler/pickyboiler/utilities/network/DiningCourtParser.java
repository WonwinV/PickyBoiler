package pickyboiler.pickyboiler.utilities.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class DiningCourtParser {
    /*
     * return -1 if not open, 0 if no veggie menu
     */
    public HashMap<String, Integer> countVegetarianMenu(JSONObject diningCourtJSON) {
        HashMap<String, Integer> counts = new HashMap<>();
        try {
            Log.d("--------", "counting for " + diningCourtJSON.getString("Location"));
            for (int i = 0; i < diningCourtJSON.getJSONArray("Meals").length(); i++) {
                int counter = 0;
                JSONObject meal = diningCourtJSON.getJSONArray("Meals").getJSONObject(i);
                if(meal.getString("Status").equals("Open")) {
                    JSONArray allStations = meal.getJSONArray("Stations");
                    for (int j = 0; j < allStations.length(); j++) {
                        JSONArray allItems = allStations.getJSONObject(j).getJSONArray("Items");
                        for (int k = 0; k < allItems.length(); k++) {
                            String isVeggie = allItems.getJSONObject(k).getString("IsVegetarian");
                            if (isVeggie != null && isVeggie.equals("true")) {
                                counter++;
                            }
                        }
                    }
                    counts.put(meal.getString("Name"), counter);
                } else {
                    counts.put(meal.getString("Name"), -1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return counts;
    }

    /**
     * @param diningCourtJSON
     * @return JSONObject with "Location", "Date", and "MealDetails". Each "MealDetails" has "Status", "VegetarianCount", "AllergenCount" JSONObject, 'StartTime", "endTime"
     */
    public JSONObject parseDiningCourtJSON(JSONObject diningCourtJSON) {
        JSONObject result = new JSONObject();
        try {
            Log.d("--------", "counting for " + diningCourtJSON.getString("Location"));
            result.put("Location", diningCourtJSON.getString("Location"));
            result.put("Date", diningCourtJSON.getString("Date"));
            for (int i = 0; i < diningCourtJSON.getJSONArray("Meals").length(); i++) {
                JSONObject mealObject = new JSONObject();
                int counter = 0;
                JSONObject meal = diningCourtJSON.getJSONArray("Meals").getJSONObject(i);
                JSONObject mealDetail = new JSONObject();

                if(meal.getString("Status").equals("Open")) {
                    Map<String, Integer> countAllergens = new HashMap<>();
                    JSONArray allStations = meal.getJSONArray("Stations");
                    for (int j = 0; j < allStations.length(); j++) {
                        JSONArray allItems = allStations.getJSONObject(j).getJSONArray("Items");
                        for (int k = 0; k < allItems.length(); k++) {
                            String isVeggie = allItems.getJSONObject(k).getString("IsVegetarian");
                            if (isVeggie != null && isVeggie.equals("true")) {
                                counter++;
                            }
                            JSONArray allAllergens = null;
                            try {
                                allAllergens = allItems.getJSONObject(k).getJSONArray("Allergens");
                            } catch (JSONException e) {
                                allAllergens = null;
                            }
                            if (allAllergens != null) {
                                for (int l = 0; l < allAllergens.length(); l++) {
                                    JSONObject obj = allAllergens.getJSONObject(l);
                                    if(obj.getBoolean("Value")) {
                                        if (countAllergens.containsKey(obj.getString("Name"))) {
                                            countAllergens.put(obj.getString("Name"), ((Integer) countAllergens.get(obj.getString("Name"))) + 1);
                                        } else {
                                            countAllergens.put(obj.getString("Name"), 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("****" + meal.getString("Name") + " @ " + diningCourtJSON.getString("Location") + " VegieCount", "parseDiningCourtJSON: " + counter);
                    Log.d("****" + meal.getString("Name") + " @ " + diningCourtJSON.getString("Location") + " AllergenMap", "parseDiningCourtJSON: " + countAllergens.toString());
                    mealDetail.put("Status", meal.getString("Status"));
                    mealDetail.put("VegetarianCount", counter);
                    mealDetail.put("AllergenCount", new JSONObject(countAllergens));
                    mealDetail.put("StartTime", meal.getJSONObject("Hours").getString("StartTime"));
                    mealDetail.put("EndTime", meal.getJSONObject("Hours").getString("EndTime"));
                    mealObject.put(meal.getString("Name"), mealDetail);
                    result.put("MealDetails", mealObject);
                } else {
                    mealDetail.put("Status", meal.getString("Status"));
                    result.put(meal.getString("Name"), mealDetail);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "YOU SHALL NOT CRASH!: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
