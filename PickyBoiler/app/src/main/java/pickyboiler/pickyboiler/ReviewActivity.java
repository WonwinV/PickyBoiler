package pickyboiler.pickyboiler;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import pickyboiler.pickyboiler.Utilities.Adapters.DiningCourtAdapter;



public class ReviewActivity extends AppCompatActivity {

    //create constructors for database objects
    @IgnoreExtraProperties
    public static class diningCourts {
        public int pointsForQuantity;
        public int pointsForQuality;
        public int pointsForService;
        public int pointsForSeating;
        public int totalVoters;

        public diningCourts() {

        }

        public diningCourts(int pointsForQuality, int pointsForQuantity, int pointsForSeating,
                           int pointsForService, int totalVoters) {
            this.pointsForQuantity = pointsForQuantity;
            this.pointsForQuality = pointsForQuality;
            this.pointsForService = pointsForService;
            this.pointsForSeating = pointsForSeating;
            this.totalVoters = totalVoters;
        }
    }





    //declaration to database
    public static DatabaseReference mDatabase;
    public String diningCourtNam;
    public ArrayList<String> menuItems;
    public static float quanChange;
    public static float qualChange;
    public static float servChange;
    public static float seatChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        final Intent intent = getIntent();
        diningCourtNam = MenuDisplayPage.diningCourtName;
        menuItems = intent.getStringArrayListExtra(DiningCourtAdapter.InfoViewHolder.sendMenuItems);


        ImageView menuIMG = (ImageView) findViewById(R.id.menuimg);

        //prepare database for communications
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //diningCourtName = "Ford";

        int menuID = R.drawable.default_menu_logo;


        switch (diningCourtNam){
            case "Wiley":
                //setContentView(R.layout.activity_review);
                menuID = R.drawable.wiley_menu_logo;
                mDatabase.child("diningCourts").child("Wiley").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get values of Wiley
                        diningCourts wiley = dataSnapshot.getValue(diningCourts.class);

                        //get totalvoters
                        int totalVoters = wiley.totalVoters;

                        //declare bars
                        RatingBar quanibar = findViewById(R.id.varietybar);
                        RatingBar qualibar = findViewById(R.id.qualitybar);
                        RatingBar servibar = findViewById(R.id.servicebar);
                        RatingBar seatibar = findViewById(R.id.seatingebar);
                        RatingBar overbar = findViewById(R.id.overallbar);

                        //handle case when zero votes in
                        if (wiley.totalVoters == 0) {
                            quanibar.setRating(0);
                            qualibar.setRating(0);
                            servibar.setRating(0);
                            seatibar.setRating(0);
                            overbar.setRating(0);
                            return;
                        }

                        int totPoints = wiley.pointsForQuality + wiley.pointsForSeating +
                                wiley.pointsForService + wiley.pointsForQuantity;
                        int avg = (int)totPoints / 4;

                        //assign to variables
                        quanibar.setRating(wiley.pointsForQuantity/totalVoters);
                        qualibar.setRating(wiley.pointsForQuality/totalVoters);
                        servibar.setRating(wiley.pointsForService/totalVoters);
                        seatibar.setRating(wiley.pointsForService/totalVoters);
                        overbar.setRating(avg);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case "Ford":
                //setContentView(R.layout.activity_review);
                menuID = R.drawable.ford_menu_logo;
                mDatabase.child("diningCourts").child("Ford").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get values of Ford
                        diningCourts ford = dataSnapshot.getValue(diningCourts.class);

                        //get totalvoters
                        int totalVoters = ford.totalVoters;

                        //declare bars
                        RatingBar quanibar = findViewById(R.id.varietybar);
                        RatingBar qualibar = findViewById(R.id.qualitybar);
                        RatingBar servibar = findViewById(R.id.servicebar);
                        RatingBar seatibar = findViewById(R.id.seatingebar);
                        RatingBar overbar = findViewById(R.id.overallbar);

                        //handle case when zero votes in
                        if (ford.totalVoters == 0) {
                            quanibar.setRating(0);
                            qualibar.setRating(0);
                            servibar.setRating(0);
                            seatibar.setRating(0);
                            overbar.setRating(0);
                            return;
                        }

                        int totPoints = ford.pointsForQuality + ford.pointsForSeating +
                                ford.pointsForService + ford.pointsForQuantity;
                        int avg = (int)totPoints / 4;

                        //assign to variables
                        quanibar.setRating(ford.pointsForQuantity/totalVoters);
                        qualibar.setRating(ford.pointsForQuality/totalVoters);
                        servibar.setRating(ford.pointsForService/totalVoters);
                        seatibar.setRating(ford.pointsForService/totalVoters);
                        overbar.setRating(avg);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case "Hillenbrand":
                //setContentView(R.layout.activity_review);
                menuID = R.drawable.hillenbrand_menu_logo;
                mDatabase.child("diningCourts").child("Hillenbrand").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get values of Hillenbrand
                        diningCourts hilli = dataSnapshot.getValue(diningCourts.class);

                        //get totalvoters
                        int totalVoters = hilli.totalVoters;

                        //declare bars
                        RatingBar quanibar = findViewById(R.id.varietybar);
                        RatingBar qualibar = findViewById(R.id.qualitybar);
                        RatingBar servibar = findViewById(R.id.servicebar);
                        RatingBar seatibar = findViewById(R.id.seatingebar);
                        RatingBar overbar = findViewById(R.id.overallbar);

                        //handle case when zero votes in
                        if (hilli.totalVoters == 0) {
                            quanibar.setRating(0);
                            qualibar.setRating(0);
                            servibar.setRating(0);
                            seatibar.setRating(0);
                            overbar.setRating(0);
                            return;
                        }

                        int totPoints = hilli.pointsForQuality + hilli.pointsForSeating +
                                hilli.pointsForService + hilli.pointsForQuantity;
                        int avg = (int)totPoints / 4;

                        //assign to variables
                        quanibar.setRating(hilli.pointsForQuantity/totalVoters);
                        qualibar.setRating(hilli.pointsForQuality/totalVoters);
                        servibar.setRating(hilli.pointsForService/totalVoters);
                        seatibar.setRating(hilli.pointsForService/totalVoters);
                        overbar.setRating(avg);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case "Earhart":
                //setContentView(R.layout.activity_review);
                menuID = R.drawable.earhart_menu_logo;
                mDatabase.child("diningCourts").child("Earhart").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get values of Earhart
                        diningCourts earhart = dataSnapshot.getValue(diningCourts.class);

                        //get totalvoters
                        int totalVoters = earhart.totalVoters;

                        //declare bars
                        RatingBar quanibar = findViewById(R.id.varietybar);
                        RatingBar qualibar = findViewById(R.id.qualitybar);
                        RatingBar servibar = findViewById(R.id.servicebar);
                        RatingBar seatibar = findViewById(R.id.seatingebar);
                        RatingBar overbar = findViewById(R.id.overallbar);

                        //handle case when zero votes in
                        if (earhart.totalVoters == 0) {
                            quanibar.setRating(0);
                            qualibar.setRating(0);
                            servibar.setRating(0);
                            seatibar.setRating(0);
                            overbar.setRating(0);
                            return;
                        }

                        int totPoints = earhart.pointsForQuality + earhart.pointsForSeating +
                                earhart.pointsForService + earhart.pointsForQuantity;
                        int avg = (int)totPoints / 4;

                        //assign to variables
                        quanibar.setRating(earhart.pointsForQuantity/totalVoters);
                        qualibar.setRating(earhart.pointsForQuality/totalVoters);
                        servibar.setRating(earhart.pointsForService/totalVoters);
                        seatibar.setRating(earhart.pointsForService/totalVoters);
                        overbar.setRating(avg);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case "Windsor":
                //setContentView(R.layout.activity_review);
                menuID = R.drawable.windsor_menu_logo;
                mDatabase.child("diningCourts").child("Windsor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get values of Windsor
                        diningCourts windsor = dataSnapshot.getValue(diningCourts.class);

                        //get totalvoters
                        int totalVoters = windsor.totalVoters;

                        //declare bars
                        RatingBar quanibar = findViewById(R.id.varietybar);
                        RatingBar qualibar = findViewById(R.id.qualitybar);
                        RatingBar servibar = findViewById(R.id.servicebar);
                        RatingBar seatibar = findViewById(R.id.seatingebar);
                        RatingBar overbar = findViewById(R.id.overallbar);

                        //handle case when zero votes in
                        if (windsor.totalVoters == 0) {
                            quanibar.setRating(0);
                            qualibar.setRating(0);
                            servibar.setRating(0);
                            seatibar.setRating(0);
                            overbar.setRating(0);
                            return;
                        }

                        int totPoints = windsor.pointsForQuality + windsor.pointsForSeating +
                                windsor.pointsForService + windsor.pointsForQuantity;
                        int avg = (int)totPoints / 4;

                        //assign to variables
                        quanibar.setRating(windsor.pointsForQuantity/totalVoters);
                        qualibar.setRating(windsor.pointsForQuality/totalVoters);
                        servibar.setRating(windsor.pointsForService/totalVoters);
                        seatibar.setRating(windsor.pointsForService/totalVoters);
                        overbar.setRating(avg);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
        }

        //top now matches the dining court selected
        menuIMG.setImageDrawable(getResources().getDrawable(menuID));

        //Display popup for user to leave reviews on press
        Button reviewButton = findViewById(R.id.leaveReview);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buildpopup(ReviewActivity.this);
            }
        });
    }

    private void buildpopup (final Activity context) {
        Intent intent = new Intent(this, reviewSeparatePopup.class);
        startActivity(intent);

    }
}

