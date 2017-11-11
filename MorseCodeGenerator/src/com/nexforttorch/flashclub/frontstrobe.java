package com.nexforttorch.flashclub;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Random;

import com.nexforttorch.flashclub.R.id;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class frontstrobe extends ActionBarActivity  {
	TextView mTextField;
	private ImageButton btnSwitch;
	RelativeLayout RLa1;
	RelativeLayout RLa;
	RelativeLayout RLa3;
	int ok;
    private Camera camera;
	private boolean isFlashOn  ;
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
	

      boolean isOn;
	
	public static Camera cam = null;
	
	Thread tu;
	private int choice =3;
	   boolean separateOnClickActive;
	int i = 0;
	
	private TextView batteryTxt;
	String Speed;
	int speedit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frontstrobe);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

      	Speed = getIntent().getStringExtra("Speed");
     
    	speedit = Integer.parseInt(Speed);
    	
    	
		getCamera();
		//Animation while activity open
	     overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

	     choice =3;
 	    
 		final Handler mHandler = new Handler();
 			tu = new Thread(new Runnable() {
 		     
 				// stuff here
 		      public void run() {
		            // TODO Auto-generated method stub

 		          while(choice==3) {
		        
		                try {
		                    Thread.sleep(speedit);
		                    mHandler.post(new Runnable() {
		                    	
		                    	
		                        @Override
		                        public void run() {
		                          if(isFlashOn)
		  		      	        {
		  		    	            //do Flash off
		                        	  
		 LinearLayout lay1 = (LinearLayout) findViewById(R.id.lay1);     

		  lay1.setVisibility(View.GONE);
		 LinearLayout lay2 = (LinearLayout) findViewById(R.id.lay2);     

		  lay2.setVisibility(View.VISIBLE);
		  WindowManager.LayoutParams layout = getWindow().getAttributes();
	  	layout.screenBrightness = 1F;
	  	getWindow().setAttributes(layout);
		  		                
		  		           
		  		                	  isFlashOn=false;
		  		    	        }
		  		    	        else
		  		    	        {
		  		    	            //do Flash on
		 LinearLayout lay1 = (LinearLayout) findViewById(R.id.lay1);     

		  lay1.setVisibility(View.VISIBLE);	
		  		       		  
		  		       		  
		  LinearLayout lay2 = (LinearLayout) findViewById(R.id.lay2);     

		lay2.setVisibility(View.GONE);
			       	  WindowManager.LayoutParams layout = getWindow().getAttributes();
			      	layout.screenBrightness = 1F;
			      	getWindow().setAttributes(layout);
	  		             
		  		    	            isFlashOn=true;
		  		    	        }        
			   	       
			   	        	  
		                        }
		                    });
		                } catch (Exception e) {
		                    // TODO: handle exception
		                }
		                
		                if(choice==1){
		                break;
		             // return; //optional in this case since the loop will exit anyways
		                  }
		               
		            }
 		    }
 		});
 		tu.start();
	         
	     
	    /**

	        		  FrameLayout btnSubmitd = (FrameLayout) findViewById(R.id.layu);
	     			 
	      	        btnSubmitd.setOnTouchListener(new OnSwipeTouchListener(frontstrobe.this) {
	        	        public void onSwipeTop() {
	        	        //    Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
	        	   
LinearLayout layyy = (LinearLayout) findViewById(R.id.Bar2);
layyy.setVisibility(View.GONE);
  		               	  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.go);
  		               	layyy.startAnimation(animation2);
	        	        }
	        	        public void onSwipeRight() {
	        	         //   Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
	        	        }
	        	        public void onSwipeLeft() {
	        	           // Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
	        	        }
	        	        public void onSwipeBottom() {
	        	           // Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();

	     	        	   
	        	        	LinearLayout layyy = (LinearLayout) findViewById(R.id.Bar2);
	        	        	layyy.setVisibility(View.VISIBLE);
	        	        	  		               	  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.come);
	        	        	  		               	layyy.startAnimation(animation2);
	        	        }

	        	    });
	       
	
	
	    
	    
	    **/
	

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
	        	
	            LayoutInflater inflater = frontstrobe.this.getLayoutInflater();
	        				new AlertDialog.Builder(frontstrobe.this)
	        	

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
	        	
	                  return true;
	         }

	        return super.onOptionsItemSelected(item);
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
							playSound();


				params = camera.getParameters();
				params.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(params);
				camera.startPreview();
				isFlashOn = true;

				// changing button/switch image
				toggleButtonImage();
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
							playSound();


				params = camera.getParameters();
				params.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(params);
				camera.stopPreview();
				isFlashOn = false;

				// changing button/switch image
				toggleButtonImage();
			}
		}


		/*
		 * Playing sound will play button toggle sound on flash on / off
		 */
		private void playSound() {
			if (isFlashOn) {
				mp = MediaPlayer.create(frontstrobe.this, R.raw.light_switch_off);
			} else {
				mp = MediaPlayer.create(frontstrobe.this, R.raw.light_switch_on);
			}
			mp.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mp.release();
				}
			});
			mp.start();
		}
		
		
		
		/*
		 * *Button about me
	    */


		/*
		 * Toggle switch button images changing image states to on / off
		 */
		private void toggleButtonImage() {
			if (isFlashOn) {
				btnSwitch.setImageResource(R.drawable.toggle_on);
			} else {
				btnSwitch.setImageResource(R.drawable.toggle_off);
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
			//turnOffFlash();
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
		
	
	
	
}
