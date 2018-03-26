package pickyboiler.pickyboiler.Utilities.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import pickyboiler.pickyboiler.MainActivity;
import pickyboiler.pickyboiler.R;

/**
 * Created by Jackson Sorrells on 3/25/2018.
 */

public class NotificationUtils {

    public static NotificationManager mManager;


    @SuppressWarnings("static-access")
    public static void generateNotification(Context context) {

        android.support.v7.app.NotificationCompat.Builder nb = new android.support.v7.app.NotificationCompat.Builder(context);
        nb.setSmallIcon(R.drawable.logo);
        nb.setContentTitle("Test 1");
        nb.setContentText("Test 2");
        nb.setTicker("Test 3");

        nb.setAutoCancel(true);


        Bitmap bitmap_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        android.support.v7.app.NotificationCompat.BigPictureStyle s = new android.support.v7.app.NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText("Test 4");
        nb.setStyle(s);


        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder TSB = TaskStackBuilder.create(context);
        TSB.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        TSB.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                TSB.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        nb.setContentIntent(resultPendingIntent);
        nb.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11221, nb.build());

    }
}