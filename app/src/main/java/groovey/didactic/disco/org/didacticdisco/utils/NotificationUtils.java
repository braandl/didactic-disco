package groovey.didactic.disco.org.didacticdisco.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import groovey.didactic.disco.org.didacticdisco.MainActivity;
import groovey.didactic.disco.org.didacticdisco.R;


public class NotificationUtils {

    private static final int NONE = 1;
    private static final int TRACKING_STOPPED = 2;
    public static final int TRACKING_RUNNING = 4;

    private static Notification currentTrackingNotification;
    private static NotificationManager mNotificationManager;

    public static int notify(Context context, int type) {
        initNotificationManager(context);
        switch (type) {
            case TRACKING_RUNNING:
                mNotificationManager.cancel(TRACKING_STOPPED);
                currentTrackingNotification = getTrackingRunningNotification(context);
                mNotificationManager.notify(TRACKING_RUNNING, currentTrackingNotification);
                return TRACKING_RUNNING;
            default:
                return NONE;
        }
    }

    public static void suppress(Context context) {
        initNotificationManager(context);
        mNotificationManager.cancelAll();
    }

    private static void initNotificationManager(Context context) {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public static Notification getCurrentTrackingNotification() {
        return currentTrackingNotification;
    }

    private static Notification getTrackingRunningNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Drawer");
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification notification = getNotification(context, intent, builder);
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        return notification;
    }

    private static Notification getNotification(Context context, Intent intent, NotificationCompat.Builder builder) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        return builder.build();
    }
}
