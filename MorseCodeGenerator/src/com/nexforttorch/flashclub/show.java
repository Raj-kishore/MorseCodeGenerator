package com.nexforttorch.flashclub;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Random;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class show extends ActionBarActivity {
	TextView mTextField;
    WakeLock wl;
	private ImageButton btnSwitch;
	RelativeLayout RLa1;
	RelativeLayout RLa;
	RelativeLayout RLa3;
	int ok;
	int okk;
    private Camera camera;
	private boolean isFlashOn  ;
	
	
	 TextView btnSubmit3;

	private boolean isFlashOn1  ;
	private boolean hasFlash;
	private Parameters params;
	private MediaPlayer mp;
	boolean status = false;
	TextView tex;
	TextView tex1;
	TextView text2;
	Switch  mySwitch;
	SeekBar seekBar1;
	SeekBar seekBar2;
	int curBrightnessValue;
	boolean doubleBackToExitPressedOnce = false;
	 String newstring;
	  String code ;
    int times= 2000;
    

private TextToSpeech tts;
	
int notifyID=1;
	Camera mCamera;
	Camera.Parameters mParameters;
	int delay = 100; // in ms
	
	
	
	public static Camera cam = null;
	
	Thread tu;
	private int choice =3;
	private int choice1 =3;
	   boolean separateOnClickActive;
	int i = 0;
	
	
	
	LinearLayout lay1;
	LinearLayout lay2;
	LinearLayout lay3;
	ImageView img2;
	ImageView img3;
	ImageView img4;
	myDBClass myDb;
	
	String details;
	String setime;
	
	private TextView batteryTxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		
		
		   myDb = new myDBClass(this);
		   
		   
		   // max audio
		   AudioManager am = 
				    (AudioManager) getSystemService(Context.AUDIO_SERVICE);

				am.setStreamVolume(
				    AudioManager.STREAM_MUSIC,
				    am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
				    0);   
		   
		   
		   
		   //Cancel notification
	   	    NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
	      	 
			nMgr.cancel(notifyID);
	   

	 		// myDb.InsertData("0", "n/a");
		    
		    int bb = myDb.SelectData2();
		    
		    
		    if(bb==0){
		    }else{
		    	
		   Cursor cursor  = myDb.getRow(bb);
		     details =cursor.getString(cursor.getColumnIndex("Details"));
		     setime =cursor.getString(cursor.getColumnIndex("Setfor"));

front();

		    
		    }

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
front2();
getCamera();
if (isFlashOn1) {
	// turn off flash
	turnOffFlash();
} else {
	// turn on flash
	turnOnFlash();
}


	}
	
	
	
	private void front() {
		// TODO Auto-generated method stub

		 btnSubmit3 = (TextView) findViewById(R.id.show);
	 
	        btnSubmit3.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {

	    	  
	        	
	            	// ...Irrelevant code for customizing the buttons and title
	            	LayoutInflater inflater = getLayoutInflater();
	            	View layout = inflater.inflate(R.layout.showd, null);
	            
	            		
	            		TextView text2 = (TextView) layout.findViewById(R.id.textView2);
		        		text2.setText(setime);
		        		TextView text4 = (TextView) layout.findViewById(R.id.textView4);
		        		text4.setText(details);

       				new AlertDialog.Builder(show.this)
       	
       		
       			    // Inflate and set the layout for the dialog
       			    // Pass null as the parent view because its going in the dialog layout
       				.setView(layout)
       			//	  .setTitle("About The App")
       	//	.setMessage(Html.fromHtml("Set for<br/><br/><b/>"+ setime +"</b><br/><br/><i>Details</i><br/><br/>"+details))
       		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
       				        public void onClick(DialogInterface dialog, int which) { 
       				            // continue with closing
       				        	
       				        
       				        	
       				        	
       				 }
       				     })
       				
       				    .setIcon(android.R.drawable.ic_dialog_info)
       				     .show();
	            

	            }

			

			
	 
	        });
	 
	}


	private void front2() {
		// TODO Auto-generated method stub

	       ImageView btnSubmit3 = (ImageView) findViewById(R.id.imageView3);
	 
	        btnSubmit3.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {

	      	
	            	onBackPressed();
	                 finish();
	                 choice=1;
	                 choice1=1;
	                 mp.stop();
	                		 

	          	   
	          
				   //delete ltest row 

				 int bb = myDb.SelectData2();
				   String bbs = Integer.toString(bb);
				    myDb.deleterow(bbs);
			 	  
			 	   

	            }

			

			
	 
	        });
	 
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	            && keyCode == KeyEvent.KEYCODE_BACK
	            && event.getRepeatCount() == 0) {
	        // Take care of calling this method on earlier versions of
	        // the platform where it doesn't exist.
	        onBackPressed();
	    }
	    

		        if (keyCode == KeyEvent.KEYCODE_MENU)
		            return true;
		       


	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
	    // This will be called either automatically for you on 2.0
	    // or later, or by the code above on earlier versions of the
	    // platform.
		
          //     Toast.makeText(create.this,
            //        "On Button Click " ,
              //       Toast.LENGTH_LONG).show();

       		//Animation while activity open
		 super.onBackPressed();
       	
		 
		 

	      	
     //    Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
      //    startActivity(intent2);
       //   finish();
          choice=1;
          choice1=1;
          mp.stop();
         		 


		   //delete ltest row 

		 int bb = myDb.SelectData2();
		   String bbs = Integer.toString(bb);
		    myDb.deleterow(bbs);
	 	  
	 	   

	 //   return;
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

		/** Toggle the flashlight on/off status */
		public void toggleFlashLight() {
		    if (! isFlashOn ) { // Off, turn it on
		        turnOn();
		    } else { // On, turn it off
		        turnOff();
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
			if (hasFlash)
				turnOnFlash();
		}

		@Override
		protected void onStart() {
			super.onStart();

			
		   	mp=MediaPlayer.create(getBaseContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
    	    mp.start();
    	    choice1 =3;
    	    
    		final Handler mHandler1 = new Handler();
    			tu = new Thread(new Runnable() {
    		     
    				// stuff here
    		      public void run() {
  		            // TODO Auto-generated method stub

    		          while(choice1==3) {
  		        
  		                try {
  		                    Thread.sleep(200);
  		                    mHandler1.post(new Runnable() {
  		                    	
  		                    	
  		                        @Override
  		                        public void run() {
  		                        	
  		                        	
  		                        	
  		                          if(isFlashOn1)
  		  		      	        {
  		  		    	            //do Flash off
  		                        	  
  		 LinearLayout lay1 = (LinearLayout) findViewById(R.id.mlay);  
  		 lay1.setBackgroundColor(Color.BLACK);

   		  WindowManager.LayoutParams layout = getWindow().getAttributes();
   	  	layout.screenBrightness = 1F;
   	  	getWindow().setAttributes(layout);
  		  		                
  		  		           
  		  		                	  isFlashOn1=false;
  		  		    	        }
  		  		    	        else
  		  		    	        {
  		  		    	            //do Flash on
  		  		    	   	 LinearLayout lay1 = (LinearLayout) findViewById(R.id.mlay);  
  		  	      		 lay1.setBackgroundColor(Color.WHITE);
  			       	  WindowManager.LayoutParams layout = getWindow().getAttributes();
  			      	layout.screenBrightness = 1F;
  			      	getWindow().setAttributes(layout);
  	  		             
  		  		    	            isFlashOn1=true;
  		  		    	        }        
  			   	       
  			   	        	  
  		                        }
  		                    });
  		                } catch (Exception e) {
  		                    // TODO: handle exception
  		                }
  		                
  		                if(choice1==1){
  		                break;
  		             // return; //optional in this case since the loop will exit anyways
  		                  }
  		               
  		            }
    		    }
    		});
    		tu.start();
    	
  	 
    		new Handler().postDelayed(new Runnable() {
    		    @Override
    		    public void run() {
    		    	btnSubmit3.performClick();
    		    }
    		}, 1);
			
    	
        	
        		
     
  	

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
			if (!isFlashOn1) {
				if (camera == null || params == null) {
					return;
				}
				// play sound
				// play sound


				params = camera.getParameters();
				params.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(params);
				camera.startPreview();
				isFlashOn1 = true;

			}

		}



		/*
		 * Turning Off flash
		 */
		private void turnOffFlash() {
			if (isFlashOn1) {
				if (camera == null || params == null) {
					return;
				}
				// play sound
				// play sound
				params = camera.getParameters();
				params.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(params);
				camera.stopPreview();
				isFlashOn1 = false;

				// changing button/switch
			}
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
		
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return false;
		}
		return super.onOptionsItemSelected(item);
	}
	   @Override
	    public boolean onPrepareOptionsMenu (Menu menu) {
	        return false;
	    }
	 

}
