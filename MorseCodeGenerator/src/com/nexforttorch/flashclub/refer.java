package com.nexforttorch.flashclub;

import java.util.Random;

// "#FFEB3B"
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

public class refer extends Activity implements OnSeekBarChangeListener {

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
	
	Thread tu;
	private int choice =3;
	   boolean separateOnClickActive;
	int i = 0;
	
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
	    setContentView(R.layout.activity_main);
	    
	    

	    
	    try {
		 curBrightnessValue = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		tex = (TextView) findViewById(R.id.textView2);
		RLa = (RelativeLayout) findViewById(R.id.lay2);
        RLa1 = (RelativeLayout) findViewById(R.id.lay1);
        RLa3 = (RelativeLayout) findViewById(R.id.lay3);
        

        batteryTxt = (TextView) this.findViewById(R.id.xView1);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
        
   //     tex1 = (TextView) findViewById(R.id.textView2444);
	//  text2= (TextView) findViewById(R.id.textView44);
	//  String oke =String.valueOf(choice);
	//  String okk = String.valueOf(choice);
	 // tex1.setText(oke);
	 // text2.setText(okk);
        
		RLa.setVisibility(View.GONE);

		RLa3.setVisibility(View.GONE);
		seekBar1=(SeekBar)findViewById(R.id.seekBar1); 
		// before calling (this) implement it in mainactivity 
	    seekBar1.setOnSeekBarChangeListener(this);  
	

		RelativeLayout lay12=(RelativeLayout) findViewById (R.id.lay1); 
	    lay12.setOnTouchListener(new OnSwipeTouchListener(refer.this) {
	        public void onSwipeTop() {
	        //    Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(getBaseContext(), alarm.class);
                startActivity(intent);
	        }
	        public void onSwipeRight() {
	         //   Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
	        }
	        public void onSwipeLeft() {
	           // Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
	        }
	        public void onSwipeBottom() {
	           // Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
	        }

	    });
	    
	    
	    
	    
	
		 
		   btnclick();
		   back();
		   front();
		   backfront();
		   
	


	 	 
	 	 
		// flash switch button
		btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);

		/*
		 * First check if device is supporting flashlight or not
		 */
		hasFlash = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

		if (!hasFlash) {
			// device doesn't support flash
			// Show alert message and close the application
			AlertDialog alert = new AlertDialog.Builder(refer.this)
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

		// displaying button image
		toggleButtonImage();
		
		

		/*
		 * Switch button click event to toggle flash on/off
		 */
		btnSwitch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFlashOn) {
					// turn off flash
					turnOffFlash();
				} else {
					// turn on flash
					turnOnFlash();
				}
			}
		});
	}

	
    
 




	@Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;                       
            }
        }, 2000);
    } 
    



	private void front() {
		// TODO Auto-generated method stub

	       ImageView btnSubmit3 = (ImageView) findViewById(R.id.imageView1);
	 
	        btnSubmit3.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	
	      	
	       		   RLa3.setVisibility(View.VISIBLE);
       	  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
  	    RLa3.startAnimation(animation2);
  	  WindowManager.LayoutParams layout = getWindow().getAttributes();
  	layout.screenBrightness = 1F;
  	getWindow().setAttributes(layout);
  		
	 	  
	            }

			
	 
	        });
	 
	}


	






	private void back() {
		// TODO Auto-generated method stub
		   RelativeLayout btnSubmit = (RelativeLayout) findViewById(R.id.lay2);
			 
	        btnSubmit.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                i++;
	                Handler handler = new Handler();
	                Runnable r = new Runnable() {

	                    @Override
	                    public void run() {
	                        i = 0;
	                    }
	                };

	                if (i == 1) {
	                    //Single click
	               	Toast.makeText(getApplicationContext(),"Double Tap To Close Disco.",Toast.LENGTH_SHORT).show();
	                    handler.postDelayed(r, 250);
	                } else if (i == 2) {
	                    //Double click
	                    i = 0;
	                    
	           		  
			       		  RelativeLayout RLa = (RelativeLayout) findViewById(R.id.lay1);
			       		   RLa.setVisibility(View.VISIBLE);
			       	       RelativeLayout RLa2 = (RelativeLayout) findViewById(R.id.lay2);
			       		   RLa2.setVisibility(View.GONE);
			       		  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movb);
			        	    RLa2.startAnimation(animation2);
			        		   
			        		   
				       		  WindowManager.LayoutParams layout = getWindow().getAttributes();
				       	  	layout.screenBrightness = curBrightnessValue/100.0f;
				       	  	getWindow().setAttributes(layout);
				       	  	
			choice =1;
	                }


		     
	            }
	        });
	 
	}
	
	
	
	

	private void backfront() {
		// TODO Auto-generated method stub
		   RelativeLayout btnSubmitd = (RelativeLayout) findViewById(R.id.lay3);
			 
	        btnSubmitd.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                i++;
	                Handler handler = new Handler();
	                Runnable r = new Runnable() {

	                    @Override
	                    public void run() {
	                        i = 0;
	                    }
	                };

	                if (i == 1) {
	                    //Single click
	               	Toast.makeText(getApplicationContext(),"Double Tap To Close Front Flash.",Toast.LENGTH_SHORT).show();
	                    handler.postDelayed(r, 250);
	                } else if (i == 2) {
	                    //Double click
	                    i = 0;
	                    
			       		   RLa3.setVisibility(View.GONE);
			       		   
			       		  WindowManager.LayoutParams layout = getWindow().getAttributes();
			       	  	layout.screenBrightness = curBrightnessValue/100.0f;
			       	  	getWindow().setAttributes(layout);
			       		
			     	
	                }


		     
	            }
	        });
	 
	}






	private void btnclick() {
		// TODO Auto-generated method stub

	       ImageButton btnSubmit = (ImageButton) findViewById(R.id.imageButton1);
	 
	        btnSubmit.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	
	            	
	      		  
	       		  RelativeLayout RLa = (RelativeLayout) findViewById(R.id.lay1);
	       		   RLa.setVisibility(View.GONE);
	       		final RelativeLayout RLa2 = (RelativeLayout) findViewById(R.id.lay2);
	       		   RLa2.setVisibility(View.VISIBLE);
       	  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
  	    RLa2.startAnimation(animation2);
  	  
 		  WindowManager.LayoutParams layout = getWindow().getAttributes();
 	  	layout.screenBrightness = 1f;
 	  	getWindow().setAttributes(layout);
 	  	
  	    
 //	  Thread t = new Thread(); 
  	//  t.start(); 
  	
  	//  if(tu.isAlive()){
  	  //  tu.interrupt(); 
  //	}
 	  	
 	  	choice =3;
  	
  	    
  		final Handler mHandler = new Handler();
  			tu = new Thread(new Runnable() {
  		   
  		        // stuff here
  		      public void run() {
		            // TODO Auto-generated method stub
		            while (true) {
		        
		                try {
		                    Thread.sleep(ok);
		                    mHandler.post(new Runnable() {
		                    	
		                    	
		                        @Override
		                        public void run() {
		                            // TODO Auto-generated method stub
		                            // Write your code here to update the UI.
		                        ////    number=number+1;
		                            //time_done=number-prev_time;
		                       //     System.out.println("timer" + time_done);
		                      	  Random rnd = new Random(); 
			   	        		  int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));   

			   		       		  RelativeLayout RLaq = (RelativeLayout) findViewById(R.id.lay2);
			   	        		  RLaq.setBackgroundColor(color);	
			   	        	  
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
	 
  
  	
		
		// These 2 code lines saves my life from hanging lay2
		seekBar1.setProgress(0);
	   seekBar1.incrementProgressBy(199); 
	   
	   
	   
  	    
  	    
	       //		Toast.makeText(getApplicationContext(),choice,Toast.LENGTH_SHORT).show();
	       		   
	       		
	       		  
	     //  		   final Handler ha=new Handler();
	   	   //     ha.postDelayed(new Runnable() {

	   	     //       @Override
	   	      //      public void run() {
	   	                //call function
	   	        	    // This generates random color
	   	        	
	   	        	 //  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
	   	  	      //RLa2.startAnimation(animation2);
	   	            	
	   	            	
	   	   //  ha.postDelayed(this, 100);
	   	     //       }
	   	       // }, 100); 
	       	
	 	  
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
			mp = MediaPlayer.create(refer.this, R.raw.light_switch_off);
		} else {
			mp = MediaPlayer.create(refer.this, R.raw.light_switch_on);
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
	 * *
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
	
	
	
	public void onProgressChanged(SeekBar seekBar, int progress,  
            boolean fromUser) {  
     //   Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();  
       ok = progress +1;
       tex.setText(String.valueOf(ok)); 
	}  
    public void onStartTrackingTouch(SeekBar seekBar) {  
     //   Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();  
    }  
    public void onStopTrackingTouch(SeekBar seekBar) {  
     //   Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();  
    }  
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main, menu); 
        
        
        
        return true;  
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
        	
            LayoutInflater inflater = refer.this.getLayoutInflater();
        				new AlertDialog.Builder(refer.this)
        	

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
    
    
    
    
    
    public static class AppRater {
    	private final static String APP_TITLE = "YOUR-APP-NAME";
    	private final static String APP_PNAME = "YOUR-PACKAGE-NAME";

    	private final static int DAYS_UNTIL_PROMPT = 3;
    	private final static int LAUNCHES_UNTIL_PROMPT = 7;

    	public static void app_launched(Context mContext) {
    	    SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
    	    if (prefs.getBoolean("dontshowagain", false)) { return ; }

    	    SharedPreferences.Editor editor = prefs.edit();

    	    // Increment launch counter
    	    long launch_count = prefs.getLong("launch_count", 0) + 1;
    	    editor.putLong("launch_count", launch_count);

    	    // Get date of first launch
    	    Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
    	    if (date_firstLaunch == 0) {
    	        date_firstLaunch = System.currentTimeMillis();
    	        editor.putLong("date_firstlaunch", date_firstLaunch);
    	    }

    	    // Wait at least n days before opening dialog
    	    if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
    	        if (System.currentTimeMillis() >= date_firstLaunch + 
    	                (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
    	            showRateDialog(mContext, editor);
    	        }
    	    }

    	    editor.commit();
    	}   

    	public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
    	    final Dialog dialog = new Dialog(mContext);
    	    dialog.setTitle("Rate " + APP_TITLE);

    	    LinearLayout ll = new LinearLayout(mContext);
    	    ll.setOrientation(LinearLayout.VERTICAL);

    	    TextView tv = new TextView(mContext);
    	    tv.setText("If you enjoy using " + APP_TITLE + ", please take a moment to rate it. Thanks for your support!");
    	    tv.setWidth(240);
    	    tv.setPadding(4, 0, 4, 10);
    	    ll.addView(tv);

    	    Button b1 = new Button(mContext);
    	    b1.setText("Rate " + APP_TITLE);
    	    b1.setOnClickListener(new OnClickListener() {
    	        public void onClick(View v) {
    	            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
    	            dialog.dismiss();
    	        }
    	    });        
    	    ll.addView(b1);

    	    Button b2 = new Button(mContext);
    	    b2.setText("Remind me later");
    	    b2.setOnClickListener(new OnClickListener() {
    	        public void onClick(View v) {
    	            dialog.dismiss();
    	        }
    	    });
    	    ll.addView(b2);

    	    Button b3 = new Button(mContext);
    	    b3.setText("No, thanks");
    	    b3.setOnClickListener(new OnClickListener() {
    	        public void onClick(View v) {
    	            if (editor != null) {
    	                editor.putBoolean("dontshowagain", true);
    	                editor.commit();
    	            }
    	            dialog.dismiss();
    	        }
    	    });
    	    ll.addView(b3);

    	    dialog.setContentView(ll);        
    	    dialog.show();        
    	    }
    	}
    

}
