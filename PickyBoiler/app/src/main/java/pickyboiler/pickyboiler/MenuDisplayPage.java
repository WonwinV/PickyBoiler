package pickyboiler.pickyboiler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pickyboiler.pickyboiler.Utilities.Adapters.DiningCourtAdapter;

/**
 * Created by Jackson Sorrells on 3/2/2018.
 */

public class MenuDisplayPage extends AppCompatActivity {

    public String diningCourtName;
    public ArrayList<String> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Intent intent = getIntent();
        diningCourtName = intent.getStringExtra(DiningCourtAdapter.InfoViewHolder.sendDiningCourt);
        menuItems = intent.getStringArrayListExtra(DiningCourtAdapter.InfoViewHolder.sendMenuItems);

        ImageView menuIMG = (ImageView) findViewById(R.id.menuimg);
        TextView menuText = (TextView) findViewById(R.id.MenuText);

        menuText.setMovementMethod(new ScrollingMovementMethod());

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


        String menuFinal = "";
        for (int i = 0; i < menuItems.size(); i++){
            menuFinal += menuItems.get(i) + "\n";
        }

        menuText.setText(menuFinal);


    }



}
