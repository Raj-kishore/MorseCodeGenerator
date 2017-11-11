package com.nexforttorch.flashclub;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// "#FFEB3B"
import android.accounts.Account;
import android.accounts.AccountManager;
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

public class MainActivity extends Activity implements OnSeekBarChangeListener {

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
	    setContentView(R.layout.activity_main);
	    isFlashOn = false;
	     myDb = new myDBClass(this);

 		RelativeLayout RLa = (RelativeLayout) findViewById(R.id.lay1);
	    
 		ObjectAnimator anim = ObjectAnimator.ofInt(RLa, "backgroundColor", Color.rgb(253, 241, 133), Color.rgb(156, 39, 176));
	    
 		anim.setDuration(3000);
	    anim.setEvaluator(new ArgbEvaluator());
	    anim.setRepeatCount(ValueAnimator.INFINITE);
	    anim.setRepeatMode(ValueAnimator.REVERSE);
	    anim.start();

	    
	    

		  TextView ttt = (TextView) findViewById(R.id.textView1);
		    ObjectAnimator animt = ObjectAnimator.ofInt(ttt, "textColor",  Color.rgb(156, 39, 176),Color.rgb(253, 241, 133));
		    animt.setDuration(3000);
		    animt.setEvaluator(new ArgbEvaluator());
		    animt.setRepeatCount(ValueAnimator.INFINITE);
		    animt.setRepeatMode(ValueAnimator.REVERSE);
		    animt.start();
		    
		    
		    
		    
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
	    lay12.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
	        public void onSwipeTop() {
	        //    Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
	         
             
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
	    
	    
	    
	    
	    //String onme = getIntent().getStringExtra("n_data");
	    
	    Bundle extras = getIntent().getExtras(); 
	    if (extras != null) {
	    String name = extras.getString("n_data");
	        // and get whatever data you are adding
	    

	    if(name.equals("nexalarm")){
	    	   Intent intent = new Intent(getBaseContext(), show.class);
               startActivity(intent);
           //    finish();
	    	

	   //      Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
	    	
	    }else if(name.equals("alar")){
	    	  Intent intent = new Intent(getBaseContext(), reminder.class);
              startActivity(intent);
	    	
	    }else{
	    
	    
	    
	    	

	     //    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
	    }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
   //
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

		 
		   btnclick();
		   back();
		   front();
		   backfront();
		   left();
		   right();
		   
		   
		   
		
		   View kichi = (View) findViewById(R.id.imageView2);
		   Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.kichi);
		  // animation2.setRepeatCount(Animation.INFINITE);
		   kichi.startAnimation(animation2);
		   
		   
		   View kichi2 = (View) findViewById(R.id.imageView1);
		   Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.kichi2);
		  // animation2.setRepeatCount(Animation.INFINITE);
		   kichi2.startAnimation(animation3);
		   
		   
		   
		   
		   ImageButton dis = (ImageButton) findViewById(R.id.imageButton1);
		   Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boom);
		  // animation2.setRepeatCount(Animation.INFINITE);
		   dis.startAnimation(animation4);
		  // animation2.start();
	 	 
	 	 
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
			AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
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

	






	private void left() {
		// TODO Auto-generated method stub
		

	       View btnSubmit3 = (View) findViewById(R.id.imageView1);
	 
	        btnSubmit3.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	
	            	   Intent intent = new Intent(getBaseContext(), alarm.class);
	                   startActivity(intent);
	                   finish();
		
	 	  
	            }

			
	 
	        });
	 
		
	}
	private void right() {
		// TODO Auto-generated method stub
		

	       View btnSubmit3 = (View) findViewById(R.id.imageView2);
	 
	        btnSubmit3.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	
	      	
	                Intent intent = new Intent(getBaseContext(), reminder.class);
	                startActivity(intent);
	                finish();
	 	  
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
        Toast.makeText(this, "Please click BACK again to close the app", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;                       
            }
        }, 2000);
    } 
    



	private void front() {
		// TODO Auto-generated method stub

	       ImageView btnSubmit3 = (ImageView) findViewById(R.id.imageView1ko);
	 
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
			mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
		} else {
			mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_on);
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
			btnSwitch.setImageResource(R.drawable.toggle_onc);
		} else {
			btnSwitch.setImageResource(R.drawable.toggle_offc);
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
		
		if(isFlashOn==true){
			isFlashOn=false;
			
		}
		
		

String names = getUsername();
	  Toast.makeText(getApplicationContext(),"Welcome "+names, Toast.LENGTH_LONG).show(); 
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
        	
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        				new AlertDialog.Builder(MainActivity.this)
        	

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
    	
    	

    	 LayoutInflater inflater3 = MainActivity.this.getLayoutInflater();
			new AlertDialog.Builder(MainActivity.this)


		    // Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		    .setView(inflater3.inflate(R.layout.livewall, null))
		//	  .setTitle("About The App")
	//.setMessage(Html.fromHtml("<b><font color=#303f9f>Send Mail</font></b>"))
	.setPositiveButton("Continue...", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with closing
			        
			        	
			        	
			 }
			     })
			
			    .setIcon(android.R.drawable.ic_dialog_info)
			     .show();
        	
         }

        return super.onOptionsItemSelected(item);
    }
    
    
    
    public String getUsername() {
        AccountManager manager = AccountManager.get(this); 
        Account[] accounts = manager.getAccountsByType("com.google"); 
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
          // TODO: Check possibleEmail against an email regex or treat
          // account.name as an email address only for certain account.type values.
          possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }
}
