package tn.rnu.enis.myprojectmanager.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import tn.rnu.enis.myprojectmanager.MainActivity;
import tn.rnu.enis.myprojectmanager.TasksActivity;
import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.task.ShowTask;
import tn.rnu.enis.myprojectmanager.task.TasksFragment;

/**
 * Created by Mohamed on 04/05/2015.
 */
public class Notify extends IntentService {

    private final static int TASK_NOTIF = 1020 ;
    private final static String TASK_NOTIF_TITLE = "Task Notification" ;

    private String mTitle = "Waiting Tasks" ;
    private String mStatus;

    public Notify() {
        super("notify");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mStatus = intent.getStringExtra(Contract.NAME);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_notify_more)
                        .setContentTitle(TASK_NOTIF_TITLE)
                        .setContentText(mTitle);

        Intent resultIntent = new Intent(this, TasksActivity.class);

        resultIntent.putExtra(Contract.NAME, mStatus);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder.setAutoCancel(true);

        mNotificationManager.notify(TASK_NOTIF, mBuilder.build());

    }
}
