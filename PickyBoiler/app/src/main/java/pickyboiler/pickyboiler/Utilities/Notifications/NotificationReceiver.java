package pickyboiler.pickyboiler.Utilities.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jackson Sorrells on 3/25/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try{
            NotificationUtils.generateNotification(context);
        }catch(Exception e){
            e.printStackTrace();
        }
    }





}
