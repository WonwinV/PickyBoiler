package pickyboiler.pickyboiler;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button sidebar_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSidebar(View view) {
        sidebar_btn = (Button) findViewById(R.id.sidebarbutton);
        Intent intent = new Intent(this, Sidebar.class);
        startActivity(intent);
    }
}
