package com.nexforttorch.flashclub;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Random;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class strobe extends ActionBarActivity {
	TextView mTextField;
    WakeLock wl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strobe);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
	    wl.acquire();
	    // do your things, even when screen is off
		
		 mTextField = (TextView) findViewById(R.id.textView1);
		 new CountDownTimer(30000, 1000) {

			 public void onTick(long millisUntilFinished) {
			     mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
			 }

			 public void onFinish() {
			     mTextField.setText("done!");
		         Intent intent = new Intent(getBaseContext(), alarm.class);
	                startActivity(intent);   
			 }  
			 
		
			}.start();
		
		

	//	exportDatabse("jvh");    
	     
		
	//	File folder = new File(Environment.getExternalStorageDirectory() + "/map");
	//	boolean success = true;
	//	if (!folder.exists()) {
	//	    success = folder.mkdir();
	//	}
	//	if (success) {
	//	 	Toast.makeText(getApplicationContext(),"hogaya be",Toast.LENGTH_SHORT).show();
            
	//	} else {

		// 	Toast.makeText(getApplicationContext(),"nehi hua be",Toast.LENGTH_SHORT).show();
		//}

	}


	@Override
	protected void onDestroy() {
	
		wl.release();
	}

	public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
          
                String backupDBPath ="lol.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                
            }
        } catch (Exception e) {

        }
    }
	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
