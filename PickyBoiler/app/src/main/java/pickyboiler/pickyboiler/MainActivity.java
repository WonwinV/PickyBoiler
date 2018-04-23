package pickyboiler.pickyboiler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pickyboiler.pickyboiler.Utilities.Adapters.DiningCourtAdapter;
import pickyboiler.pickyboiler.Utilities.Network.DiningCourtParser;
import pickyboiler.pickyboiler.Utilities.Network.JSONFetcher;
import pickyboiler.pickyboiler.Utilities.Notifications.NotificationReceiver;
import pickyboiler.pickyboiler.Utilities.Sorter.Sorter;
import pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;

import static pickyboiler.pickyboiler.R.id.swipelayout;
import static pickyboiler.pickyboiler.R.id.mainlayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, JSONFetcher.AsyncResponse {

    JSONArray allCurrentMealJSONArr = new JSONArray();
    int completedAsyncTask;
    boolean finishedAllAsync;
    final int numberOfCourts = 5;
    int counter = 0;

    DrawerLayout drawer;
    Button sidebarbtn;
    NavigationView navigationView;

    SwipeRefreshLayout swipeRefreshLayout;

    PendingIntent pIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recycle View
        setContentView(R.layout.activity_main);
        recycleDiningCourtsList = (RecyclerView) findViewById(R.id.rv_home_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleDiningCourtsList.setLayoutManager(layoutManager);
        diningAdapter = new DiningCourtAdapter();
        recycleDiningCourtsList.setAdapter(diningAdapter);

        //allCurrentMeal = new HashMap<>();
        allCurrentMealJSONArr = new JSONArray();
        completedAsyncTask = 0;
        finishedAllAsync = false;
        counter = 0;

        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        String checkCacheDate = new SimpleDateFormat("M/d/yyyy").format(cDate);

        //check cache first
        String[] diningCourtList = {"Ford", "Earhart", "Windsor", "Hillenbrand", "Wiley"};
        boolean validCache = false;
        DiningCourtParser parser = new DiningCourtParser();
        try {
            for (String diningCourtName : diningCourtList) {
                JSONObject parsedCache = new JSONObject(SharedPreferencesManager.getValueFromKey(diningCourtName + "_cache"));
                if (parsedCache.get("Date").equals(checkCacheDate)) {
                    //log.d("^^^CACHE^^^", "validCache");
                    validCache = true;
                    JSONObject mealObj = parser.getCurrentMealJSON(parsedCache);
                    //log.d("P==CACHE==Parsed_" + parsedCache.getString("Location"), "processFinish: " + parsedCache.toString());
                    if (mealObj != null) {
                        counter++;
                        allCurrentMealJSONArr.put(mealObj);
                    } else {
                        //log.d("HomeActivity-parsed", "Not serving @ " + parsedCache.getString("Location") + " : " + parser.findCurrentMeal(parsedCache));
                    }
                }
                else {
                    //log.d("^^^CACHE^^^", "NOT validCache");
                    break;
                }
            }

            if(validCache) {
                if (counter == 0) {
                    //no dining court open
                    //log.d("ADDDDSS", "processFinish: ");
                    JSONObject ads = new JSONObject();
                    try {
                        ads.put("Location", "Local Restaurant Ads Here");
                        ads.put("favoriteCounts", "Contact developers");
                        ads.put("AllergenCount", new JSONObject().put("Contact developers", 0));
                        ads.put("URL", "https://hungryboiler.com/");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<JSONObject> adsArr = new ArrayList<>();
                    adsArr.add(ads);
                    diningAdapter.addData(adsArr, getApplicationContext());

                    Drawable drawable;
                    drawable = getDrawable(R.drawable.background_closed);
                    RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                    mlayout.setBackground(drawable);
                }
                if (counter > 0) {
                    Drawable drawable2 = getDrawable(R.drawable.background);
                    RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                    mlayout.setBackground(drawable2);
                    //log.d("ADDDATA", "onRefresh: menu");
                    diningAdapter.addData(Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr), getApplicationContext());
                }
            }
        } catch (JSONException e) {
            //invalid cache handler here
        } catch (Exception e) {
            //something happened boom boom
            System.out.println("onCreate crashes");
            e.printStackTrace();
        }

        //hard coded for now
        if(!validCache) {
            //log.d("CALL TO FETCHHHHH", "fetch everythingggggggg");
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Ford/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Earhart/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Windsor/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Hillenbrand/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Wiley/" + fDate);
        }

        final JSONFetcher.AsyncResponse dd = this;

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(swipelayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                diningAdapter = new DiningCourtAdapter();
                recycleDiningCourtsList.setAdapter(diningAdapter);

                allCurrentMealJSONArr = new JSONArray();
                completedAsyncTask = 0;
                finishedAllAsync = false;
                counter = 0;

                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                String checkCacheDate = new SimpleDateFormat("M/d/yyyy").format(cDate);

                //check cache first
                String[] diningCourtList = {"Ford", "Earhart", "Windsor", "Hillenbrand", "Wiley"};
                boolean validCache = false;
                DiningCourtParser parser = new DiningCourtParser();
                try {
                    for (String diningCourtName : diningCourtList) {
                        JSONObject parsedCache = new JSONObject(SharedPreferencesManager.getValueFromKey(diningCourtName + "_cache"));
                        if (parsedCache.get("Date").equals(checkCacheDate)) {
                            //log.d("^^^CACHE^^^", "validCache");
                            validCache = true;
                            JSONObject mealObj = parser.getCurrentMealJSON(parsedCache);
                            //log.d("P==CACHE==Parsed_" + parsedCache.getString("Location"), "processFinish: " + parsedCache.toString());
                            if (mealObj != null) {
                                counter++;
                                allCurrentMealJSONArr.put(mealObj);
                            } else {
                                //log.d("HomeActivity-parsed", "Not serving @ " + parsedCache.getString("Location") + " : " + parser.findCurrentMeal(parsedCache));
                            }
                        }
                        else {
                            //log.d("^^^CACHE^^^", "NOT validCache");
                            break;
                        }
                    }

                    if(validCache) {
                        if (counter == 0) {
                            //no dining court open
                            //log.d("ADDDDSS", "processFinish: ");
                            JSONObject ads = new JSONObject();
                            try {
                                ads.put("Location", "Local Restaurant Ads Here");
                                ads.put("favoriteCounts", "Contact developers");
                                ads.put("AllergenCount", new JSONObject().put("Contact developers", 0));
                                ads.put("URL", "https://hungryboiler.com/");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ArrayList<JSONObject> adsArr = new ArrayList<>();
                            adsArr.add(ads);
                            diningAdapter.addData(adsArr,getApplicationContext());

                            Drawable drawable;
                            drawable = getDrawable(R.drawable.background_closed);
                            RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                            mlayout.setBackground(drawable);
                        }
                        if (counter > 0) {
                            Drawable drawable2 = getDrawable(R.drawable.background);
                            RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                            mlayout.setBackground(drawable2);
                            //log.d("ADDDATA", "onRefresh: menu");
                            diningAdapter.addData(Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr),getApplicationContext());
                        }
                    }
                } catch (JSONException e) {
                    //invalid cache handler here
                } catch (Exception e) {
                    //something happened boom boom
                    e.printStackTrace();
                }

                //hard coded for now
                if(!validCache) {
                    //log.d("CALL TO FETCHHHHH", "fetch everythingggggggg");
                    new JSONFetcher(dd).execute("https://api.hfs.purdue.edu/menus/v2/locations/Ford/" + fDate);
                    new JSONFetcher(dd).execute("https://api.hfs.purdue.edu/menus/v2/locations/Earhart/" + fDate);
                    new JSONFetcher(dd).execute("https://api.hfs.purdue.edu/menus/v2/locations/Windsor/" + fDate);
                    new JSONFetcher(dd).execute("https://api.hfs.purdue.edu/menus/v2/locations/Hillenbrand/" + fDate);
                    new JSONFetcher(dd).execute("https://api.hfs.purdue.edu/menus/v2/locations/Wiley/" + fDate);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 158, 138), Color.rgb(254, 193, 136), Color.rgb(255, 233, 135));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sidebarbtn = (Button) findViewById(R.id.sidebarbutton);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        sidebarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Notifications

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        pIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pIntent);



    }

    @Override
    public void processFinish(String output) {
        if(output == null) {
            Log.e("HomeActivity-fetched", "network error");
            //handle network error here
            if(++completedAsyncTask == numberOfCourts) {
                finishedAllAsync = true;
                //ArrayList<JSONObject> arr = Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr);
                ////log.d("Sorting====", ">>" + arr.toString());
                if(counter > 0)
                    diningAdapter.addData(Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr), getApplicationContext());

                if(counter == 0) {
                    //no dining court open
                    JSONObject ads = new JSONObject();
                    try {
                        ads.put("Location", "Local Restaurant ads here");
                        ads.put("favoriteCounts", "contact developer for ads");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<JSONObject> adsArr = new ArrayList<>();
                    adsArr.add(ads);
                    diningAdapter.addData(adsArr, getApplicationContext());

                    Drawable drawable;
                    drawable = getDrawable(R.drawable.background_nointernet);
                    RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                    mlayout.setBackground(drawable);
                }
            }
            return;
        }
        JSONObject jsonobj = null;
        try {
            //log.d("~~~~~~", "finished fetching " + new JSONObject(output).getString("Location"));
            //do operation on the fetched JSON
            jsonobj = new JSONObject(output);
            DiningCourtParser parser = new DiningCourtParser();
            JSONObject parsed = parser.parseDiningCourtJSON(jsonobj);
            //cached parsed data
            SharedPreferencesManager.putStringSharedPreferences(parsed.getString("Location")+"_cache", parsed.toString());
            //log.d("^^^CACHE^^^", "cached to>>>"+parsed.getString("Location")+"_cache"+" : "+SharedPreferencesManager.getValueFromKey(parsed.getString("Location")+"_cache"));

            JSONObject mealObj = parser.getCurrentMealJSON(parsed);
            //log.d("===Parsed_" + parsed.getString("Location"), "processFinish: " + parsed.toString());
            ////log.d("===ParsedLunch", ">" + parsed.getJSONObject("MealDetails").getJSONObject("Lunch").toString());
            if (mealObj != null) {
                counter++;
                allCurrentMealJSONArr.put(mealObj);
                //allCurrentMeal.put(jsonobj.getString("Location"), mealObj);
                ////log.d("HomeActivity-parsed", "Serving @  " + jsonobj.getString("Location") + " : " + allCurrentMeal.get(jsonobj.getString("Location")).toString());
                ////log.d("URLLLLLL", allCurrentMeal.get(jsonobj.getString("Location")).getString("URL"));
            } else {
                //log.d("HomeActivity-parsed", "Not serving @ " + jsonobj.getString("Location") + " : " + parser.findCurrentMeal(parsed));
            }

        } catch (JSONException e) {
            //TODO: handle network error here. Incomplete request response.
            Log.e("JSON fetch error", "parse JSON error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            //crash and burn
            Log.e("Crash!!! ", e.getMessage());
            e.printStackTrace();
        }

        if(++completedAsyncTask == numberOfCourts) {

            if(counter == 0) {
                //no dining court open
                //log.d("ADDDDSS", "processFinish: ");
                JSONObject ads = new JSONObject();
                try {
                    ads.put("Location", "Local Restaurant Ads Here");
                    ads.put("favoriteCounts", "Contact developers");
                    ads.put("AllergenCount", new JSONObject().put("Contact developers", 0));
                    ads.put("URL", "https://hungryboiler.com/");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList<JSONObject> adsArr = new ArrayList<>();
                adsArr.add(ads);
                diningAdapter.addData(adsArr, getApplicationContext());

                Drawable drawable;
                drawable = getDrawable(R.drawable.background_closed);
                RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                mlayout.setBackground(drawable);
            }
            if(counter > 0) {
                Drawable drawable2 = getDrawable(R.drawable.background);
                RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                mlayout.setBackground(drawable2);

                //ArrayList<JSONObject> arr = Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr);
                diningAdapter.addData(Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr),getApplicationContext());
            }

            finishedAllAsync = true;
        }
    }

    private RecyclerView recycleDiningCourtsList;
    private DiningCourtAdapter diningAdapter;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_oncampus) {
            Intent navoncampus = new Intent(MainActivity.this, MainActivity.class);
            startActivity(navoncampus);
        } else if (id == R.id.nav_offcampus) {
            Intent navoffcampus = new Intent(MainActivity.this, OffCampusDining.class);
            startActivity(navoffcampus);
        } else if (id == R.id.nav_foodpref) {
            Intent navfoodpref = new Intent(MainActivity.this, FoodPreferences.class);
            startActivity(navfoodpref);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*buttonpref = (Button) findViewById(R.id.prefbutton);
        buttonpref.setPressed(false);
        boolean pressed = buttonpref.isPressed();
        if (!pressed) {
            drawable = getDrawable(R.drawable.pref_btn);
            buttonpref.setBackground(drawable);
        }*/

        diningAdapter = new DiningCourtAdapter();
        recycleDiningCourtsList.setAdapter(diningAdapter);

        //allCurrentMeal = new HashMap<>();
        allCurrentMealJSONArr = new JSONArray();
        completedAsyncTask = 0;
        finishedAllAsync = false;
        counter = 0;

        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        String checkCacheDate = new SimpleDateFormat("M/d/yyyy").format(cDate);

        //check cache first
        String[] diningCourtList = {"Ford", "Earhart", "Windsor", "Hillenbrand", "Wiley"};
        boolean validCache = false;
        DiningCourtParser parser = new DiningCourtParser();
        try {
            for (String diningCourtName : diningCourtList) {
                JSONObject parsedCache = new JSONObject(SharedPreferencesManager.getValueFromKey(diningCourtName + "_cache"));
                if (parsedCache.get("Date").equals(checkCacheDate)) {
                    //log.d("^^^CACHE^^^", "validCache");
                    validCache = true;
                    JSONObject mealObj = parser.getCurrentMealJSON(parsedCache);
                    //log.d("resumePCACHE==Parsed_" + parsedCache.getString("Location"), parsedCache.toString());
                    if (mealObj != null) {
                        counter++;
                        allCurrentMealJSONArr.put(mealObj);
                    } else {
                        //log.d("HomeActivity-parsed", "Not serving @ " + parsedCache.getString("Location") + " : " + parser.findCurrentMeal(parsedCache));
                    }
                }
                else {
                    //log.d("^^^CACHE^^^", "NOT validCache,"+parsedCache.get("Date")+" vs "+checkCacheDate);
                    break;
                }
            }

            if(validCache) {
                if (counter == 0) {
                    //no dining court open
                    //log.d("ADDDDSS", "processFinish: ");
                    JSONObject ads = new JSONObject();
                    try {
                        ads.put("Location", "Local Restaurant Ads Here");
                        ads.put("favoriteCounts", "Contact developers");
                        ads.put("AllergenCount", new JSONObject().put("Contact developers", 0));
                        ads.put("URL", "https://hungryboiler.com/");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<JSONObject> adsArr = new ArrayList<>();
                    adsArr.add(ads);
                    //log.d("ADDDATA", "onResume: ads");
                    diningAdapter.addData(adsArr,getApplicationContext());

                    Drawable drawable;
                    drawable = getDrawable(R.drawable.background_closed);
                    RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                    mlayout.setBackground(drawable);
                }
                if (counter > 0) {
                    Drawable drawable2 = getDrawable(R.drawable.background);
                    RelativeLayout mlayout = (RelativeLayout) findViewById(mainlayout);
                    mlayout.setBackground(drawable2);
                    //log.d("ADDDATA", "onResume: menu");
                    diningAdapter.addData(Sorter.sortDiningCourt(getApplicationContext(), allCurrentMealJSONArr),getApplicationContext());
                }
            }
        } catch (JSONException e) {
            //invalid cache handler here
        } catch (Exception e) {
            //something happened boom boom
            e.printStackTrace();
        }

        //hard coded for now
        if(!validCache) {
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Ford/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Earhart/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Windsor/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Hillenbrand/" + fDate);
            new JSONFetcher(this).execute("https://api.hfs.purdue.edu/menus/v2/locations/Wiley/" + fDate);
        }
    }
}
