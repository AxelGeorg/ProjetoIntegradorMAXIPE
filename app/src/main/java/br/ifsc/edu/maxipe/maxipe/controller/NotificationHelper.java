package br.ifsc.edu.maxipe.maxipe.controller;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import br.ifsc.edu.maxipe.maxipe.R;
import br.ifsc.edu.maxipe.maxipe.view.MainDesligaAlarme;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Intent intent = new Intent(this, MainDesligaAlarme.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("MAXIPE")
                .setContentText("ALARME!!! CLIQUE PARA CANCELAR")
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
}
