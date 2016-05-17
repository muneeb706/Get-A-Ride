package com.example.dell.google_maps_drive;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService
{
    public static int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private static final String LOG_TAG="GcmIntentService";
int count;
    int count_1;
    public GcmIntentService()
    {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) // has effect of unparcelling Bundle
        {
	    /*
	     * Filter messages based on message type. Since it is likely that
	     * GCM will be extended in the future with new message types, just
	     * ignore any message types you're not interested in, or that you
	     * don't recognize.
	     */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
            {
                sendNotification("Send error: " + extras.toString(), null);
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
            {
                sendNotification("Deleted messages on server: " + extras.toString(), null);
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                // Post notification of received message.
                String message = ((intent.getExtras() == null) ? "Empty Bundle" : intent.getExtras()
                        .getString("message"));
                String [] split=message.split(",");
                String int_msg=split[0];
                String sender_num=split[1];
                String number=split[2];
                String start=split[3];
                String end=split[4];
                String request=split[5];

                if (intent.getExtras() != null
                        && "com.example.dell.google_maps_drive.CLEAR_NOTIFICATION".equals(intent.getExtras()
                        .getString("action")))
                {
                    clearNotification();
                }
                else
                {
                    SharedPreferences sp = getSharedPreferences("login", 0);
                    String type = sp.getString("type", "");
                    String curr_phone = sp.getString("loginPhone", "");
                    if(int_msg.equals("Request For A Ride")) {
                        if (type.equals("Driver") && !curr_phone.equals(number)) {

                            //  String phone_number = sp.getString("loginPhone","");
                            //if(phone_number.equals(message)){

                            SharedPreferences request_sp = getSharedPreferences("Notification Request", Context.MODE_PRIVATE);
                            if ((request_sp.getString("count", "").equals(""))) {

                                count = 0;
                            } else {
                                count = Integer.parseInt(request_sp.getString("count", ""));
                            }
                            count++;
                            SharedPreferences.Editor editor = request_sp.edit();
                            editor.putString("Number" + count + "", number);
                            editor.putString("Start" + count + "", start);
                            editor.putString("End" + count + "", end);
                            editor.putString("RequestTime" + count + "", request);
                            editor.putString("count", count + "");
                            editor.commit();

                            NOTIFICATION_ID = count;

                            sendNotification(int_msg + System.getProperty("line.separator")
//                                    +"Number: "+number+System.getProperty("line.separator")+
//                                    "Start Location: "+start+System.getProperty("line.separator")+
//                                    "End Location: "+end+System.getProperty("line.separator")+
//                                    "Request Time: "+request+System.getProperty("line.separator")+"."
                                    , extras);
                            //}

                        }
                    }
                    else{
                        if (curr_phone.equals(number)) {
                            SharedPreferences confirmation_sp = getSharedPreferences("Driver Confirmation", Context.MODE_PRIVATE);
                            if ((confirmation_sp.getString("count", "").equals(""))) {

                                count_1 = 0;                            }
                            else {
                                count_1 = Integer.parseInt(confirmation_sp.getString("count", ""));
                            }
                            count_1++;
                            SharedPreferences.Editor editor = confirmation_sp.edit();
                            editor.putString("Number" + count_1 + "", number);
                            editor.putString("Start" + count_1 + "", start);
                            editor.putString("End" + count_1 + "", end);
                            editor.putString("AcceptTime" + count_1 + "", request);
                            editor.putString("SenderNumber" + count_1 + "", sender_num);

                            editor.putString("count", count_1 + "");
                            editor.commit();

                            NOTIFICATION_ID = count_1;

                            sendNotificationToCustomer(int_msg + System.getProperty("line.separator"), extras);
                        }



                        }

                }
                Log.i(LOG_TAG, "Received: " + message);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Put the message into a notification and post it.
     * This is just one simple example of what you might choose to do with
     * a GCM message.
     * @param msg
     * @param extras
     */
    private void sendNotification(String msg, Bundle extras)
    {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, UserConfirmationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("message", count+"");

        // If this is a notification type message include the data from the message
        // with the intent
        if (extras != null)
        {
            intent.putExtra("message", count + "");
            intent.setAction("com.example.dell.google_maps_drive.NOTIFICATION");
        }
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), requestID,
                intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Customer Notification "+count_1)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg)
                        .setTicker(msg)
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    private void sendNotificationToCustomer(String msg, Bundle extras)
    {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, DriverConfirmationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (extras != null)
        {
            intent.putExtra("message", count_1 + "");
            intent.setAction("com.example.dell.google_maps_drive.NOTIFICATION");
        }
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), requestID,
                intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Driver Notification "+count_1)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg)
                        .setTicker(msg)
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    /**
     * Remove the app's notification
     */
    private void clearNotification()
    {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

}
