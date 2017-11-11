package com.nexforttorch.flashclub;

import java.io.IOException;
import java.util.Random;

// "#FFEB3B"
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.SettingNotFoundException;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class toucher extends Activity  {

	private ImageButton btnSwitch;
	RelativeLayout RLa1;
	RelativeLayout RLa;
	RelativeLayout RLa3;
	int ok;
    private Camera camera;
	private boolean isFlashOn;
	private boolean hasFlash;
	private Parameters params;
	private MediaPlayer mp;
	boolean status = false;
	TextView tex;
	TextView tex1;
	TextView text2;
	Switch  mySwitch;
	SeekBar seekBar1;
	int curBrightnessValue;
	boolean doubleBackToExitPressedOnce = false;
	
	
	
	
	
	Camera mCamera;
	Camera.Parameters mParameters;
	int delay = 100; // in ms
	
	
	Thread tu;
	private int choice =3;
	   boolean separateOnClickActive;
	int i = 0;
	myDBClass myDb ;
	
	private TextView batteryTxt;
	
	  private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
	    @Override
	    public void onReceive(Context ctxt, Intent intent) {
	      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
	      batteryTxt.setText(String.valueOf(level) + "%");
	    }
	  };
	
	
	
	
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.toucher);
	   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	   isFlashOn=false;

	 	 

		/*
		 * First check if device is supporting flashlight or not
		 */
		hasFlash = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

		if (!hasFlash) {
			// device doesn't support flash
			// Show alert message and close the application
			AlertDialog alert = new AlertDialog.Builder(toucher.this)
					.create();
			alert.setTitle("Error");
			alert.setMessage("Sorry, your device doesn't support flash light!");
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// closing the application
					finish();
				}
			});
			alert.show();
			return;
		}

		// get the camera
		getCamera();

		
		

		
		bt();
	
	}

	
    
 





	






	




























	private void bt() {
		// TODO Auto-generated method stub

		// flash switch button
		LinearLayout btnSwitchx = (LinearLayout) findViewById(R.id.lay1);
		btnSwitchx.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_DOWN) {
	                // Pressed
	            	
						turnOnFlash();
					
	        	         

			    		
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	                // Released
						turnOffFlash();
					
	            }
	            return true;
	        }
	    });
	}













































	/*
	 * Get the camera
	 */
	private void getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open();
				params = camera.getParameters();
			} catch (RuntimeException e) {
				Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
			}
		}
	}
	
	
	
	/*
	 * Turning On flash
	 */
	private void turnOnFlash() {
		if (!isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			// play sound
			// play sound


			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
			isFlashOn = true;

		}

	}



	/*
	 * Turning Off flash
	 */
	private void turnOffFlash() {
		if (isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			// play sound
			// play sound
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			isFlashOn = false;

			// changing button/switch
		}
	}


	
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// on pause turn off the flash
		turnOffFlash();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// on resume turn on the flash
	//	if (hasFlash)
		//	turnOnFlash();
	}

	@Override
	protected void onStart() {
		super.onStart();

		// on starting the app get the camera params
		getCamera();
		
		
		

        Toast.makeText(this, "Touch", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// on stop release the camera
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	///extra
	/** Turn the devices FlashLight on */
	public void turnOn() {
	    if (mCamera != null) {
	    // Turn on LED
	    

	    params = mCamera.getParameters();
	    params.setFlashMode(Parameters.FLASH_MODE_TORCH);
	    mCamera.setParameters(params);

	    isFlashOn = true;
	}
	}

	/** Turn the devices FlashLight off */
	public void turnOff() {
	    // Turn off flashlight
	    if (mCamera != null) {
	    

	        params = mCamera.getParameters();
	        if (params.getFlashMode().equals(Parameters.FLASH_MODE_TORCH)) {
	            params.setFlashMode(Parameters.FLASH_MODE_OFF);
	            mCamera.setParameters(params);
	        }
	    }
	    isFlashOn  = false;
	}


    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
           // Toast.makeText(this, "Rate This App", Toast.LENGTH_LONG).show();

			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nexforttorch.flashclub")));
            return true;
        }
        if (id == R.id.action_settingsok) {
        	
            LayoutInflater inflater = toucher.this.getLayoutInflater();
        				new AlertDialog.Builder(toucher.this)
        	

        			    // Inflate and set the layout for the dialog
        			    // Pass null as the parent view because its going in the dialog layout
        			    .setView(inflater.inflate(R.layout.dialog_signin, null))
        			//	  .setTitle("About The App")
        		//.setMessage(Html.fromHtml("<b><font color=#303f9f>Send Mail</font></b>"))
        		.setPositiveButton("Proceed to mail", new DialogInterface.OnClickListener() {
        				        public void onClick(DialogInterface dialog, int which) { 
        				            // continue with closing
        				        	
        				        	Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        				        	emailIntent.setData(Uri.parse("mailto: quickroutes@gmail.com"));
        				        	startActivity(Intent.createChooser(emailIntent, "Send in Gmail(Recommended)"));        	
        				        	
        				        	
        				        	
        				        	
        				 }
        				     })
        				
        				    .setIcon(android.R.drawable.ic_dialog_info)
        				     .show();
        	
         }

        if (id == R.id.action_settingsok1) {
           // Toast.makeText(this, "Rate This App", Toast.LENGTH_LONG).show();
        	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
        	sharingIntent.setType("text/plain");
        	String shareBody = "Here is the share content body";
        	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        	startActivity(Intent.createChooser(sharingIntent, "Share via"));
			
        }
        if (id == R.id.action_settingsok2) {
            // Toast.makeText(this, "Rate This App", Toast.LENGTH_LONG).show();
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nexforttorch.flashclubpro")));
            return true;
 			
         }
        
    if (id == R.id.action_settingsok3) {
    	
    	

        AlertDialog alertDialog = new AlertDialog.Builder(toucher.this).create(); //Read Update
      //  alertDialog.setTitle("Live Wallpaper");
		alertDialog.setMessage(Html.fromHtml("<font color=#303f9f>To set livewallpaper go to <br/><b>Launcher > Menu > Home Screen Wallpaper > Live Wallpaper > Flash Club's Grisma Live </b><br/>Or,<br/><b>Launcher > Menu > Home Screen Wallpaper > Live Wallpaper > Flash Club's Glowing Purple </b> <br/>and press set live wallpaper.</font>"));
        alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
              // here you can add functions
           }
        });

        alertDialog.show();  //<-- See This!
    
        
        	
         }

        return super.onOptionsItemSelected(item);
    }
    
    
    
   
}
