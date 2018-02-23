package pickyboiler.pickyboiler.utilities.Sorter;

/**
 * Created by HP on 2/22/2018.
 */

public class Sorter {
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
