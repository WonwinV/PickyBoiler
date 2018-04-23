package pickyboiler.pickyboiler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import pickyboiler.pickyboiler.Utilities.Adapters.DiningCourtAdapter;

import static pickyboiler.pickyboiler.ReviewActivity.mDatabase;

public class reviewSeparatePopup extends AppCompatActivity {

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

    public String diningCourtNam = MenuDisplayPage.diningCourtName;
    public DatabaseReference nDatabase = mDatabase;
    public static float quanChange;
    public static float qualChange;
    public static float servChange;
    public static float seatChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review_separate_popup);

        //inflate popup_layout
        RelativeLayout viewGroup= (RelativeLayout) reviewSeparatePopup.this.findViewById(R.id.reviewPopup);
        LayoutInflater layoutInflater = (LayoutInflater) reviewSeparatePopup.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.review_popup, viewGroup);

        final RatingBar quanbar = (RatingBar) layout.findViewById(R.id.varibar);
        final RatingBar qualbar = (RatingBar) layout.findViewById(R.id.qualbar);
        final RatingBar servbar = (RatingBar) layout.findViewById(R.id.servbar);
        final RatingBar seatbar = (RatingBar) layout.findViewById(R.id.seatbar);

        quanbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                quanChange = quanbar.getRating();
            }
        });



        //qualChange = (int) qualbar.getRating();
        //quanChange = (int) quanbar.getRating();
        //seatChange = (int) seatbar.getRating();
        //servChange = (int) servbar.getRating();


        //Get a reference to close button, and close the popup when clicked

        Button close = layout.findViewById(R.id.closebutton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qualChange = (int) qualbar.getRating();
                quanChange = (int) quanbar.getRating();
                seatChange = (int) seatbar.getRating();
                servChange = (int) servbar.getRating();
                nDatabase = FirebaseDatabase.getInstance().getReference();

                nDatabase.child("diningCourts").child(diningCourtNam).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(quanChange);
                        ReviewActivity.diningCourts unknown = dataSnapshot.getValue(ReviewActivity.diningCourts.class);
                        nDatabase.child("diningCourts").child(diningCourtNam).child
                                ("pointsForQuality").setValue(qualChange + unknown.pointsForQuality);
                        nDatabase.child("diningCourts").child(diningCourtNam).child
                                ("pointsForQuantity").setValue(quanChange + unknown.pointsForQuantity);
                        nDatabase.child("diningCourts").child(diningCourtNam).child
                                ("pointsForService").setValue(servChange + unknown.pointsForService);
                        nDatabase.child("diningCourts").child(diningCourtNam).child
                                ("pointsForSeating").setValue(seatChange + unknown.pointsForSeating);
                        nDatabase.child("diningCourts").child(diningCourtNam).child
                                ("totalVoters").setValue(unknown.totalVoters + 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(getApplicationContext(),"Your review was saved!",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
        }

    @Override
    public void finish() {
        super.finish();
    }
}

