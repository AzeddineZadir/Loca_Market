package com.example.loca_market.ui.client.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.loca_market.R;
import com.example.loca_market.ui.client.Activities.ClientSearchSellerActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NotificationsService extends FirebaseMessagingService {
    public static final String TAG = "NotificationsService";
    private final int NOTIFICATION_ID = 007;
    private final String NOTIFICATION_TAG = "FIREBASEOC";


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "token NS"+s );

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

            // 8 - Show notification after received message
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.sendVisualNotification(remoteMessage);
                Log.e(TAG, "onMessageReceived:NotificationsService " );
            }

    }

    // ---

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendVisualNotification(RemoteMessage remoteMessage) {

        // 1 - Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(this, ClientSearchSellerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // 2 - Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        Map<String,String> data = remoteMessage.getData();
        String titre = data.get("title");
        String contenu = data.get("content");

        inboxStyle.setBigContentTitle(titre);
        inboxStyle.addLine(contenu);

        // 3 - Create a Channel (Android 8)
        String channelId = getString(R.string.channelID);

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_image_notification)
                        .setContentTitle(titre)
                        .setContentText(contenu)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setStyle(inboxStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message provenant de Firebase";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // 7 - Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    } 
}
