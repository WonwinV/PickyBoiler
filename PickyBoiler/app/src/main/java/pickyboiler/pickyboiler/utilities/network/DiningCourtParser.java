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
    /**
     * superseded by parseDiningCourtJSON
     * return -1 if not open, 0 if no veggie menu
     */
    @Deprecated
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
     * @return JSONObject with "Location", "Date", and "MealDetails".
     *         Each "MealDetails" object has "Breakfast/Luch/.." key mapped to "Status", "VegetarianCount", "AllergenCount" JSONObject, 'StartTime", "endTime"
     *         Each AllergenCount object has "Nut/Shrimp/..." key mapped to count of that allergens
     *
     *         Ex:
     *         {
     *             Location : Earhart
     *             Date : 10/19/2017
     *             MealDetails : {
     *                 Breakfast : {
     *                     Status : Open
     *                     AllMeal : {
     *                         Steak,
     *                         ...
     *                     }
     *                     VegetarianCount : 3,
     *                     AllergenCount : {
     *                         Gluten : 2,
     *                         Eggs : 3,
     *                         ...
     *                     }
     *                     StartTime : 06:30:00
     *                     EndTime : 10:00:00
     *                     URL : https://...
     *                 }
     *
     *                 Lunch : {
     *                      ....
     *                 }
     *
     *                 Dinner : {
     *                     ....
     *                 }
     *             }
     *         }
     */
    public JSONObject parseDiningCourtJSON(JSONObject diningCourtJSON) {
        JSONObject result = new JSONObject();
        try {
            Log.d("--------", "counting for " + diningCourtJSON.getString("Location"));
            result.put("Location", diningCourtJSON.getString("Location"));
            result.put("Date", diningCourtJSON.getString("Date"));
            JSONObject allMealDetails = new JSONObject();

            for (int i = 0; i < diningCourtJSON.getJSONArray("Meals").length(); i++) {
                //details of each meal
                JSONObject mealDetail = new JSONObject();
                int counter = 0;
                //all meal objects
                JSONObject meal = diningCourtJSON.getJSONArray("Meals").getJSONObject(i);

                if(meal.getString("Status").equals("Open")) {
                    Map<String, Integer> countAllergens = new HashMap<>();
                    JSONArray allStations = meal.getJSONArray("Stations");
                    JSONObject allMenu = new JSONObject();

                    for (int j = 0; j < allStations.length(); j++) {
                        JSONArray allItems = allStations.getJSONObject(j).getJSONArray("Items");
                        for (int k = 0; k < allItems.length(); k++) {
                            //JSONArray of present allergens for a single item
                            JSONArray menuAllergens = new JSONArray();

                            //count Veggies
                            String isVeggie = allItems.getJSONObject(k).getString("IsVegetarian");
                            if (isVeggie != null && isVeggie.equals("true")) {
                                //menuAllergens.put("isVegeterian");
                                counter++;
                            }
                            //count Allergies
                            JSONArray allAllergens;
                            try {
                                allAllergens = allItems.getJSONObject(k).getJSONArray("Allergens");
                            } catch (JSONException e) {
                                allAllergens = null;
                            }

                            if (allAllergens != null) {
                                for (int l = 0; l < allAllergens.length(); l++) {
                                    JSONObject obj = allAllergens.getJSONObject(l);
                                    if(obj.getBoolean("Value")) {
                                        menuAllergens.put(obj.getString("Name"));
                                        if (countAllergens.containsKey(obj.getString("Name"))) {
                                            countAllergens.put(obj.getString("Name"), ((Integer) countAllergens.get(obj.getString("Name"))) + 1);
                                        } else {
                                            countAllergens.put(obj.getString("Name"), 1);
                                        }
                                    }
                                }
                            }

                            //list of all menu items, each item with name and allergens list
                            allMenu.put(allItems.getJSONObject(k).getString("Name"), menuAllergens);
                            //Log.d("....menuAllergen", ">" + allItems.getJSONObject(k).getString("Name") + " : " + menuAllergens.toString());
                        }
                    }
                    Log.d("****" + meal.getString("Name") + " @ " + diningCourtJSON.getString("Location") + " VegieCount", "parseDiningCourtJSON: " + counter);
                    Log.d("****" + meal.getString("Name") + " @ " + diningCourtJSON.getString("Location") + " AllergenMap", "parseDiningCourtJSON: " + countAllergens.toString());
                    mealDetail.put("Location", diningCourtJSON.getString("Location"));
                    mealDetail.put("Status", meal.getString("Status"));
                    mealDetail.put("AllMeal", allMenu);
                    mealDetail.put("VegetarianCount", counter);
                    mealDetail.put("AllergenCount", new JSONObject(countAllergens));
                    mealDetail.put("StartTime", meal.getJSONObject("Hours").getString("StartTime"));
                    mealDetail.put("EndTime", meal.getJSONObject("Hours").getString("EndTime"));
                    mealDetail.put("URL", "https://dining.purdue.edu/menus/" + diningCourtJSON.getString("Location") + "/");
                    allMealDetails.put(meal.getString("Name"), mealDetail);
                } else {
                    mealDetail.put("Status", meal.getString("Status"));
                    result.put(meal.getString("Name"), mealDetail);
                }
            }
            result.put("MealDetails", allMealDetails);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "YOU SHALL NOT CRASH!: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * accept output JSON from parseDiningCourtJSON only
     * @param parsedDiningCourtJSON
     * @return null if not open
     */
    public String findCurrentMeal(JSONObject parsedDiningCourtJSON) {
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        try {
            JSONObject allMeals = parsedDiningCourtJSON.getJSONObject("MealDetails");
            Iterator<String> keysIterator = allMeals.keys();
            while (keysIterator.hasNext()) {
                String mealName = keysIterator.next();
                if(betweenTime(currentTime, allMeals.getJSONObject(mealName).getString("StartTime"), allMeals.getJSONObject(mealName).getString("EndTime"))){
                    return mealName;
                }
            }
            return "No meal served";
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * accept output JSON from parseDiningCourtJSON only
     * @param parsedDiningCourtJSON
     * @return null if not open, with URL attribute added
     *
     * example JSON:
     *      see Breakfast object above.
     */
    public JSONObject getCurrentMealJSON(JSONObject parsedDiningCourtJSON) {
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String currentMeal = null;
        try {
            JSONObject allMeals = parsedDiningCourtJSON.getJSONObject("MealDetails");
            Iterator<String> keysIterator = allMeals.keys();
            while (keysIterator.hasNext()) {
                String mealName = keysIterator.next();
                if(betweenTime(currentTime, allMeals.getJSONObject(mealName).getString("StartTime"), allMeals.getJSONObject(mealName).getString("EndTime"))) {
                    currentMeal = mealName;
                    break;
                }
            }
            if(currentMeal == null)
                return null;
            return parsedDiningCourtJSON.getJSONObject("MealDetails").getJSONObject(currentMeal);
        } catch (JSONException e) {
            return null;
        }
    }

    private boolean betweenTime(String current, String start, String end) {
        Log.d("^^^^^^betweenTime", current + " from " + start + " to " + end);
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
            Date userDate = parser.parse(current);
            Date begin = parser.parse(start);
            Date finish = parser.parse(end);
            if (userDate.equals(begin) || userDate.equals(finish) || (userDate.after(begin) && userDate.before(finish))) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }
}