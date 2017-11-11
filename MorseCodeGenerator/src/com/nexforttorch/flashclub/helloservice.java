package com.nexforttorch.flashclub;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class helloservice extends Service {

    private static final String TAG = "helloservice";

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
// This class is called in main activity ...please check to believe
        isRunning = true;
        

    }
    
  

	public void createNotification(String fuck, String hms ) {
        // Prepare intent which is triggered if the
        // notification is selected
   //     Intent intent = new Intent(this, reminder.class);
     //   PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        // Build notification
        // Actions are just fake
    	
    	
    	
    	// Using helloservice for updating alarm notify bar
        int notifyID = 1;
        int numMessages = 0;
        Notification noti = new Notification.Builder(this)
                        .setContentTitle("Flash Club's Alarm")
                        .setContentText("Alarm Set For "+fuck)
                        .setSmallIcon(R.drawable.alarm)
                     //   .setContentIntent(pIntent)
                        .setPriority(Notification.PRIORITY_HIGH)
	 	                .setOngoing(true)
              //          .addAction(R.drawable.full, "Call", pIntent)
                //        .addAction(R.drawable.full, "More", pIntent)
                  //      .addAction(R.drawable.full, "And more", pIntent)
	 	                .build();
        
        

     // Start of a loop that processes data and then notifies the user
    
            // Because the ID remains unchanged, the existing notification is
            // updated.
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notifyID, noti);
     

}
    
 

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (int i = 0; i < 5; i++) {
                    try {
                    	
                        Thread.sleep(1000);
                        
                    } catch (Exception e) {
                    }

                    if(isRunning){
                        Log.i(TAG, "Service running");
                        
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}