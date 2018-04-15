package pickyboiler.pickyboiler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.PopupWindow;
import android.widget.Button;
import android.text.method.LinkMovementMethod;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;

import pickyboiler.pickyboiler.Utilities.Adapters.DiningCourtAdapter;
import pickyboiler.pickyboiler.Utilities.Storage.SharedPreferencesManager;


/**
 * Created by Jackson Sorrells on 3/2/2018.
 */

public class MenuDisplayPage extends AppCompatActivity {

    public String diningCourtName;
    public ArrayList<String> menuItems;
    String menuFinal;
    int ini;
    int fin;
    ArrayList<String> favItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //retrieve favorites list for potential updating
        favItemsList = SharedPreferencesManager.getPrefFavListtFofy();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Intent intent = getIntent();
        diningCourtName = intent.getStringExtra(DiningCourtAdapter.InfoViewHolder.sendDiningCourt);
        menuItems = intent.getStringArrayListExtra(DiningCourtAdapter.InfoViewHolder.sendMenuItems);

        ImageView menuIMG = (ImageView) findViewById(R.id.menuimg);
        TextView menuText = (TextView) findViewById(R.id.MenuText);
        menuText.setMovementMethod(LinkMovementMethod.getInstance());


        int menuID = R.drawable.default_menu_logo;
        switch (diningCourtName){
            case "Wiley":
                menuID = R.drawable.wiley_menu_logo;
                break;
            case "Ford":
                menuID = R.drawable.ford_menu_logo;
                break;
            case "Hillenbrand":
                menuID = R.drawable.hillenbrand_menu_logo;
                break;
            case "Earhart":
                menuID = R.drawable.earhart_menu_logo;
                break;
            case "Windsor":
                menuID = R.drawable.windsor_menu_logo;
                break;
        }

        menuIMG.setImageDrawable(getResources().getDrawable(menuID));


        menuFinal = "";
        for (int i = 0; i < menuItems.size(); i++){
            menuFinal += menuItems.get(i) + "\n";
        }

        //define spannable string and clickable span functionality
        SpannableString ss = new SpannableString(menuFinal);

        //mechanism for making each line of span clickable
        int currPos;
        for(currPos = 0; currPos < menuFinal.length(); currPos++) {
            ini = currPos;
            while(menuFinal.charAt(currPos + 1) !=  '\n') {
                if(currPos >= menuFinal.length()) {
                    break;
                }
                currPos++;
            }
            fin = currPos;

            //define new clickable item
            ClickableSpan newclickableSpan = new ClickableSpan() {
                int curr1 = ini;
                int curr2 = fin;
                @Override
                public void onClick(View view) {
                    showPopup(MenuDisplayPage.this, curr1, curr2);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };

            //add to span
            ss.setSpan(newclickableSpan, ini, fin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //to newline; increment puts us on new line past the newline char
            currPos++;
        }
        menuText.setText(ss);
    }


    //method to display popup
    private void showPopup(final Activity context, int first, int last) {
        int width = 1000;
        int height = 950;
        final int fir = first;
        final int las = last;

        //inflate popup_layout
        LinearLayout viewGroup= (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.menu_popup, viewGroup);

        //create the popup
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(width);
        popup.setHeight(height);
        popup.setFocusable(true);

        //clear default background
        popup.setBackgroundDrawable(new BitmapDrawable());

        //popup appears at center screen
        popup.showAtLocation(layout, Gravity.CENTER, 0,0);

        //Get a reference to close button, and close the popup when clicked
        Button close = (Button) layout.findViewById(R.id.cbutton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });


        // Getting a reference to left button, and add item when clicked.
        Button left = (Button) layout.findViewById(R.id.lbutton);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favItem = menuFinal.substring(fir,las+1);
                if (favItemsList.contains(favItem.trim())) {
                    SharedPreferencesManager.showToast("Item already added.");
                    popup.dismiss();
                    return;
                }
                SharedPreferencesManager.addFavoriteItem(getApplicationContext(), favItem.trim());
                favItemsList.add(favItem.trim());
                SharedPreferencesManager.showToast("Item added.");
                Collections.sort(favItemsList);
                popup.dismiss();
            }
        });

        // Getting a reference to right button, and close the popup when clicked.
        Button right = (Button) layout.findViewById(R.id.rbutton);
        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://net.housing.purdue.edu/NetNutrition/1"));
                startActivity(viewIntent);
            }
        });

    }
}
