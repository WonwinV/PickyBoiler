package pickyboiler.pickyboiler.utilities.Sorter;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pickyboiler.pickyboiler.R;
import pickyboiler.pickyboiler.utilities.storage.SharedPreferencesManager;

public class Sorter {
    private static ArrayList<String> allergenList;
    private static ArrayList<String> favoriteList;
    private static final int veggieMultiplier = 7;
    private static final int favoriteMultiplier = 7;

    public static ArrayList<JSONObject> sortDiningCourt(Context context, JSONArray allCurrentMeal) {
        allergenList = new ArrayList<>();
        allergenList = SharedPreferencesManager.getAllAllergens();
        favoriteList = SharedPreferencesManager.getAllFavoriteItem();

        ArrayList<JSONObject> diningCourtWithScore = new ArrayList<>();
        try {
            //compute ranking score
            for (int i = 0; i < allCurrentMeal.length(); i++) {
                JSONObject item = allCurrentMeal.getJSONObject(i);
                JSONObject computed = computeScore(context, item);
                item.put("computedRanking", computed.getString("computedRanking"));
                String favCounts = "";
                JSONArray list = computed.getJSONArray("favoriteCounts");
                Log.d("SORTER", "sortDiningCourt: " + list.toString());
                //Log.d("SORTER_SCORE", "sortDiningCourt: " + computed.getString("computedRanking"));
                if(list != null && list.length() > 0) {
                    for (int j = 0; j < list.length(); j++) {
                        JSONObject entry = list.getJSONObject(j);
                        Iterator<String> keys = entry.keys();
                        String name = keys.next();
                        //Log.d("FAVCOUNTS", "sortDiningCourt: " + name);
                        if (name.trim().length() > 0) {
                            favCounts += name + "(" + entry.getInt(name) + ")" + ", ";
                        }
                    }
                    if (favCounts.length() > 2)
                        favCounts = favCounts.substring(0, favCounts.length() - 2);
                }
                if(favCounts.length() > 0)
                    item.put("favoriteCounts", favCounts);
                else
                    item.put("favoriteCounts", "No favorite menu found");
                diningCourtWithScore.add(item);
            }

            //sort by ranking score
            Collections.sort(diningCourtWithScore, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    Integer a1 = 0, a2 = 0;
                    try {
                        a1 = o1.getInt("computedRanking");
                        a2 = o2.getInt("computedRanking");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return a2.compareTo(a1);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return diningCourtWithScore;
    }

    private static JSONObject computeScore(Context context, JSONObject diningCourt) {
        Integer score = 0;
        HashMap<String, Integer> favoriteCounts = new HashMap<>();
        JSONObject result = new JSONObject();
        try {
            //check if veggie
            if(SharedPreferencesManager.isVeggie()) {
                score += diningCourt.getInt(context.getResources().getString(R.string.VegetarianCount)) * veggieMultiplier;
            }
            //check for favorites
            Iterator<String> keys = diningCourt.getJSONObject("AllMeal").keys();
            while (keys.hasNext()) {
                String menuName = keys.next();
                JSONArray itemAllergen = diningCourt.getJSONObject("AllMeal").getJSONArray(menuName);
                boolean safeForVegetarian = false;
                boolean allergicTo = false;
                boolean isFavorite = false;

                //do not check favorite if allergic
                for (int i = 0; i < itemAllergen.length(); i++) {
                    if (allergenList.contains(itemAllergen.getString(i))) {
                        allergicTo = true;
                        break;
                    }
                    if (itemAllergen.getString(i).equals("Vegetarian"))
                        safeForVegetarian = true;
                }
                if (allergicTo || (SharedPreferencesManager.isVeggie() && !safeForVegetarian))
                    continue;

                //check if favorite
                for (int j = 0; j < favoriteList.size(); j++) {
                    String[] keywords = favoriteList.get(j).split(" ");
                    String regex = "";
                    if (keywords.length > 0) {
                        for (String word : keywords) {
                            word = word.toLowerCase();
                            if (word.trim().length() > 0)
                                regex += ".*" + word;
                        }
                        regex += ".*";
                    }
                    //Log.d("computeSorter", "regex: >>" + regex + "<<" + "word: >>" + favoriteList.get(j));
                    boolean matchAllKeyword = menuName.toLowerCase().matches(regex);
                    if (matchAllKeyword) {
                        //counts favorite
                        Log.d("computeSorter", "found fav!: " + favoriteList.get(j));
                        if (!favoriteCounts.containsKey(favoriteList.get(j))) {
                            favoriteCounts.put(favoriteList.get(j), 1);
                        } else {
                            favoriteCounts.put(favoriteList.get(j), favoriteCounts.get(favoriteList.get(j)) + 1);
                        }
                        score += favoriteMultiplier;
                        isFavorite = true;
                        break;
                    }
                }
                //not favorite
                if (!isFavorite)
                    score++;
            }

            //only return top 5 favs
            //JSONArr format:
            //    0 : {"Pork" : 5},
            //    1 : {"Beef" : 3}, ...
            JSONArray sortedFav = new JSONArray();
            if(favoriteCounts.size() > 0) {
                //sort Map descending
                List<Map.Entry<String, Integer>> list = new LinkedList<>(favoriteCounts.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                //get top 5
                Map<String, Integer> res = new LinkedHashMap<String, Integer>();
                int numToPut = Math.min(favoriteCounts.size(), 5);
                int putCounter = 0;
                for(Map.Entry<String, Integer> entry : list) {
                    if(putCounter++ < numToPut)
                        sortedFav.put(new JSONObject().put(entry.getKey(), entry.getValue()));
                    else
                        break;
                }
            }

            result.put("computedRanking", score);
            result.put("favoriteCounts", sortedFav);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}