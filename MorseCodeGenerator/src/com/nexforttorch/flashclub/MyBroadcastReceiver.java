package com.nexforttorch.flashclub;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
	MediaPlayer mp;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		mp=MediaPlayer.create(context, R.raw.cam1);
	    mp.start();
	    
		Toast.makeText(context, "Alarm Activated", Toast.LENGTH_LONG).show();
			PackageManager pm = context.getPackageManager();
		Intent launchIntent = pm.getLaunchIntentForPackage("com.nexforttorch.flashclub");
	launchIntent.putExtra("n_data", "nexalarm");
			context.startActivity(launchIntent);
		
		
	}
	
	
	
}
