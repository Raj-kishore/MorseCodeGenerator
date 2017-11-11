package com.nexforttorch.flashclub;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.nexforttorch.flashclub.R.id;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public class alarm extends ActionBarActivity implements OnSeekBarChangeListener {
	TextView mTextField;
	private ImageButton btnSwitch;
	RelativeLayout RLa1;
	RelativeLayout RLa;
	RelativeLayout RLa3;
	int ok;
	int okk;
    private Camera camera;
	private boolean isFlashOn  ;
	private boolean hasFlash;
	private Parameters params;
	private MediaPlayer mp;
	 int sound ;
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
	Camera mCamera;
	Camera.Parameters mParameters;
	int delay = 100; // in ms
	public static Camera cam = null;
	Thread tu;
	private int choice =3;
	boolean separateOnClickActive;
	int i = 0;
	LinearLayout lay1;
	LinearLayout lay2;
	LinearLayout lay3;
	ImageView img2;
	ImageView img3;
	ImageView img4;
	Button btnSubmitdok;
	private TextView batteryTxt;
	FrameLayout frame;
	LinearLayout White;
	LinearLayout Black;
	Spinner spinner1;
	
	  private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
	    @Override
	    public void onReceive(Context ctxt, Intent intent) {
	      int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
	      batteryTxt.setText(String.valueOf(level) + "%");
	    }
	  };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.strobe);
		
		

	    
	    
	    
	    
		
	    batteryTxt = (TextView) this.findViewById(R.id.xView1);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        

		  genmorse();
		
		
		
		 lay1 = (LinearLayout) findViewById(R.id.lay1);
		 lay2 = (LinearLayout) findViewById(R.id.lay2);
		 lay3 = (LinearLayout) findViewById(R.id.lay3);
		 
		 img2 = (ImageView) findViewById(R.id.imageView2);

		 img3 = (ImageView) findViewById(R.id.imageView3);

		 img4 = (ImageView) findViewById(R.id.imageView4);
		 
		 forlay2();
		 forlay3();
		 img5();
		 img7();
		 
		 img8();
		 img9();
		 img11();
		 fronts2();
		
		
		 img12();
		
		  spinner1 = (Spinner) findViewById(R.id.spin);
	      List<String> list = new ArrayList<String>();
	      
	      list.add("Cam1");
	      list.add("Cam2");
	      list.add("Morse");
	    
	      
	      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
	      (this, android.R.layout.simple_spinner_item,list);
	       
	dataAdapter.setDropDownViewResource
	      (android.R.layout.simple_spinner_dropdown_item);
	       
	spinner1.setAdapter(dataAdapter);

    
    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
            // Your code here
        	
        	int spinposition=spinner1.getSelectedItemPosition();
        		
        	    String spins = Integer.toString(spinposition);

        	//   Toast.makeText(alarm.this,spins, Toast.LENGTH_SHORT).show();
        	   if(spins.equals("0")){
        		   
        		  sound = R.raw.cam1;

      			seekBar2.setMax(550);
      			seekBar2.setProgress(150);
        	   }else if(spins.equals("1")){
        		   

        		   sound = R.raw.cam2;

        		   seekBar2.setMax(550);
         			seekBar2.setProgress(150);
        	   }else if(spins.equals("2")){
        		    sound = R.raw.morse;
        		    

        			seekBar2.setMax(100);
        			seekBar2.setProgress(60);
        		   
        	   }else{

         		  sound = R.raw.shotgun;
        		   seekBar2.setMax(550);
         			seekBar2.setProgress(150);
        		    
        		   
        	   }
        	
        } 

        public void onNothingSelected(AdapterView<?> adapterView) {
            return;
        } 
    }); 


		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		seekBar1=(SeekBar)findViewById(R.id.seekBar1); 
		// before calling (this) implement it in mainactivity 
	    seekBar1.setOnSeekBarChangeListener(this);  

  		seekBar1.setProgress(0);
 	   seekBar1.incrementProgressBy(199); 
 	   
 	   
 	   
		seekBar2=(SeekBar)findViewById(R.id.seekBar1m); 
		// before calling (this) implement it in mainactivity 
	    seekBar2.setOnSeekBarChangeListener(this);  

 		seekBar2.setProgress(0);
	   seekBar2.incrementProgressBy(199); 

		
		
		// get the camera
		getCamera();
		sos();
		aboutmorse();
		   
		//Animation while activity open
	     overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	   
	     
	     Switch list_toggle=(Switch)findViewById(R.id.switch1);
	     list_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	        @Override
	        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	           if(isChecked) {
	             
	              Log.d("You are :", "Checked");
	          
	            //  Toast.makeText(alarm.this, "checked", Toast.LENGTH_SHORT).show();
	              
	          
	                
	              /**  if(isFlashon)
      	        {
    	            //do Flash off
    	            isFlashon=false;
    	        }
    	        else
    	        {
    	            //do Flash on
    	            isFlashon=true;
    	        }
    	        handler.postDelayed(this, 1000 * 60);
	              
	             **/ 
	              
	              choice=3;

	              Thread t = new Thread() {
	            	    public void run() {
	            	        try {
	            	            // Switch on the cam for app's life
	            	            if (mCamera == null) {
	            	                // Turn on Cam
	            	                mCamera = Camera.open();
	            	                try {
	            	                    mCamera.setPreviewDisplay(null);
	            	                } catch (IOException e) {
	            	                    e.printStackTrace();
	            	                }
	            	                mCamera.startPreview();
	            	            }
	            	            
	            	            
while(choice==3){
							//	for (int i=0; i < times*2; i++) {
	            	                toggleFlashLight();
	            	                sleep(ok);
	            	                
	            	                if(choice==1){
	            	                	
	            	                	break;
	            	                
	            	                }
	            	            }

	            	            if (mCamera != null) {
	            	                mCamera.stopPreview();
	            	                mCamera.release();
	            	                mCamera = null;
	            	            }
	            	        } catch (Exception e){ 
	            	            e.printStackTrace(); 
	            	        }
	            	    }
	            	};

	            	t.start();
	        
	         
	           }
	           else {
	           
	              Log.d("You are :", " Not Checked");
	              choice =1;
	          //    TextView tv = (TextView) findViewById(R.id.textView1);
            //    	  tv.setText("Back Srobe");
	           //   Toast.makeText(alarm.this, "unchecked", Toast.LENGTH_SHORT).show();
	           }
	        }       
	      });
	    
	     
	     
	     
	     
	     
	     Switch list_togglem=(Switch)findViewById(R.id.switch1m);
	     list_togglem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	        @Override
	        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	           if(isChecked) {
	             
	              Log.d("You are :", "Checked");
	          
	            //  Toast.makeText(alarm.this, "checked", Toast.LENGTH_SHORT).show();
	              
	          
	                
	              /**  if(isFlashon)
      	        {
    	            //do Flash off
    	            isFlashon=false;
    	        }
    	        else
    	        {
    	            //do Flash on
    	            isFlashon=true;
    	        }
    	        handler.postDelayed(this, 1000 * 60);
	              
	             **/ 
	        	  if(newstring==null){
	    			  
	    			  Toast.makeText(getBaseContext(), "Whoa! Generate a morse code",
	    	                    Toast.LENGTH_SHORT).show();

	    			     Switch list_togglem=(Switch)findViewById(R.id.switch1m);
	    			  list_togglem.setChecked(false);
	    		  }else{
	              choice=3;

	              Thread t = new Thread() {
	            	    public void run() {
	            	        try {
	            	            // Switch on the cam for app's life
	            	            if (mCamera == null) {
	            	                // Turn on Cam
	            	                mCamera = Camera.open();
	            	                try {
	            	                    mCamera.setPreviewDisplay(null);
	            	                } catch (IOException e) {
	            	                    e.printStackTrace();
	            	                }
	            	                mCamera.startPreview();
	            	            }
	            	            
	            	            
	            	            
	            	            while(choice==3){
	            	            	// Apply 00 before any code to delay two off
	                	          String myString = newstring;
	                	        
	                	        //on /off/ on /off/ on /off/ off /off/ off /off/ off /off/ on /off/ on /off/ on /off/
	                	        

	                	          for (int x = 0; x < myString.length(); x++) {
	      						//	for (int i=0; i < times*2; i++) {
	                	            if ((myString.charAt(x) == '1') ) { // Off, turn it on
	                	  	        turnOn();
	                	  
	                	 	        
	                	  	    } else { // On, turn it off
	                	  	        turnOff();
	                	  	    }
	                	            sleep(okk);
	                	                if(choice==1){
		            	                	
		            	                	break;
		            	                
		            	                }
		            	            
	                	         
	                	            }
							//	for (int i=0; i < times*2; i++) {
	                	         
	            	            }
	            	               

	            	            if (mCamera != null) {
	            	                mCamera.stopPreview();
	            	                mCamera.release();
	            	                mCamera = null;
	            	            }
	            	        } catch (Exception e){ 
	            	            e.printStackTrace(); 
	            	        }
	            	    }
	            	};

	            	t.start();
	        
	    		  }
	           }
	           else {
	           
	              Log.d("You are :", " Not Checked");
	              choice =1;
	          //    TextView tv = (TextView) findViewById(R.id.textView1);
            //    	  tv.setText("Back Srobe");
	           //   Toast.makeText(alarm.this, "unchecked", Toast.LENGTH_SHORT).show();
	           }
	        }       
	      });
	    
	     
	     fronts();
	     
	     
	     


	}

	
	
	    
	    
	    
	
	
	



	private void img11() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView11);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	onBackPressed();
		   
		   
	          
		     
	            }
	        
	 
	});
	}

	
	
	private void img12() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView12);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
		   
		   
	            	  

		         	   Intent intent = new Intent(getBaseContext(), toucher.class);
		               startActivity(intent);
		          
		     
	            }
	        
	 
	});
	}

	private void img8() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView8);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	 lay2.setVisibility(View.GONE);

	 lay3.setVisibility(View.GONE);

	 lay1.setVisibility(View.VISIBLE);
		seekBar1.setProgress(0);
		   seekBar1.incrementProgressBy(199); 
		   
		   
		   
		   
		   Switch list_toggle=(Switch)findViewById(R.id.switch1m);
	  	     list_toggle.setChecked(false);
		   choice=1;
		   
	          
		     
	            }
	        
	 
	});
		
	}
	
	private void img9() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView9);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	 lay1.setVisibility(View.GONE);

	 lay3.setVisibility(View.GONE);

	 lay2.setVisibility(View.VISIBLE);
	          
		     
	            }
	        
	 
	});
		
	}

	private void img5() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView5);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	 lay2.setVisibility(View.GONE);

	 lay3.setVisibility(View.GONE);

	 lay1.setVisibility(View.VISIBLE);
		seekBar1.setProgress(0);
		   seekBar1.incrementProgressBy(199); 
		   
		   
	          
		     
	            }
	        
	 
	});
		
	}
	private void img7() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView7);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	 lay2.setVisibility(View.GONE);

	 lay1.setVisibility(View.GONE);

	 lay3.setVisibility(View.VISIBLE);
		seekBar1.setProgress(0);
		   seekBar1.incrementProgressBy(199); 
		   
		   
	          
		     
	            }
	        
	 
	});
		
	}

	private void forlay2() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView3);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	 lay1.setVisibility(View.GONE);

	 lay2.setVisibility(View.VISIBLE);

	 lay3.setVisibility(View.GONE);
	          
		     
	            }
	        
	 
	});
	    
		
	}

	private void forlay3() {
		// TODO Auto-generated method stub
		   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView4);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	 lay2.setVisibility(View.GONE);
	 lay1.setVisibility(View.GONE);
	 lay3.setVisibility(View.VISIBLE);
		seekBar1.setProgress(0);
		   seekBar1.incrementProgressBy(199); 

           choice=1;

  	     Switch list_toggle=(Switch)findViewById(R.id.switch1);
  	     list_toggle.setChecked(false);
		   
		   
	          
		     
	            }
	        
	 
	});
	    
		
	}

	private void genmorse() {
		// TODO Auto-generated method stub
		
		
		   btnSubmitdok = (Button) findViewById(R.id.morsebtn);
			 
	        btnSubmitdok.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	             

	        		EditText Et = (EditText) findViewById(R.id.editText1);
	         String myString= Et.getText().toString();
	          newstring= "00";
	         
	         
	         
	     //    String myString = "SOS";
 	        
 	        //on /off/ on /off/ on /off/ off /off/ off /off/ off /off/ on /off/ on /off/ on /off/
 	        

 	          for (int x = 0; x < myString.length(); x++) {
				//	for (int i=0; i < times*2; i++) {
 	        	  
 	        	 String stringValueOf = String.valueOf(myString.charAt(x));

 	 	 	     //  speakOut(stringValueOf);
 	
 	        //	 Toast.makeText(getBaseContext(), stringValueOf,
 		      //              Toast.LENGTH_SHORT).show();
 	 	          String partcode=  transfermorse(stringValueOf);
 	 	         newstring = newstring +  partcode;
 	 	         
 	 	         
 	   
 	            }
 	          
 	         
	           Toast.makeText(getBaseContext(), "Morse Code Generated.",
	                    Toast.LENGTH_SHORT).show();
	           
	           Switch list_togglem=(Switch)findViewById(R.id.switch1m);
	  	     list_togglem.setChecked(false);
	  	  
	           

	            Thread t = new Thread() {
	          	    public void run() {
	          	        try {
	          	            // Switch on the cam for app's life
	          	            if (mCamera == null) {
	          	                // Turn on Cam
	          	                mCamera = Camera.open();
	          	                try {
	          	                    mCamera.setPreviewDisplay(null);
	          	                } catch (IOException e) {
	          	                    e.printStackTrace();
	          	                }
	          	                mCamera.startPreview();
	          	            }
	          	            
	          	            // 7 gaps between each word 
	          	          String myString = newstring;
	          	        
	          	        //on /off/ on /off/ on /off/ off /off/ off /off/ off /off/ on /off/ on /off/ on /off/
	          	        

	          	          for (int x = 0; x < myString.length(); x++) {
							//	for (int i=0; i < times*2; i++) {
	          	            if ((myString.charAt(x) == '1') ) { // Off, turn it on
	          	  	        turnOn();
	          	  	    } else { // On, turn it off
	          	  	        turnOff();
	          	  	    }
	          	               
	          	          sleep(okk); 
	          	         
	          	            }

	          	            if (mCamera != null) {
	          	                mCamera.stopPreview();
	          	                mCamera.release();
	          	                mCamera = null;
	          	            }
	          	        } catch (Exception e){ 
	          	            e.printStackTrace(); 
	          	        }
	          	    }
	          	};

	          	t.start();
	      
	            
	         
		     
	            }

				private String transfermorse(String charAt) {
					
					// TODO Auto-generated method stub
					
					


					// Morse code
					
					String value = charAt;
					if ("A".equals(value) || "a".equals(value)) {
					    code = "00010111";
					}
					if ("B".equals(value) || "b".equals(value)) {
					    code = "000111010101";
					}
					if ("C".equals(value) || "c".equals(value)) {
					  code = "00011101011101";
					}
					if ("D".equals(value) || "d".equals(value)) {
					  code = "0001110101";
					}
					if ("E".equals(value) || "e".equals(value)) {
				 code = "0001";
					}
					if ("F".equals(value) || "f".equals(value)) {
					     code = "000101011101";
					}
					if ("G".equals(value) || "g".equals(value)) {
					     code = "000111011101";
					}
					if ("H".equals(value) || "h".equals(value)) {
					  code = "0001010101";
					}
					if ("I".equals(value) || "i".equals(value)) {
					     code = "000101";
					}
					if ("J".equals(value) || "j".equals(value)) {
					    code = "0001011101110111";
					}
					if ("K".equals(value) || "k".equals(value)) {
					code = "000111010111";
					}
					if ("L".equals(value) || "l".equals(value)) {
					 code = "000101110101";
					}
					if ("M".equals(value) || "m".equals(value)) {
					    code = "0001110111";
					}
					if ("N".equals(value) || "n".equals(value)) {
					   code = "00011101";
					}
					if ("O".equals(value) || "o".equals(value)) {
					    code = "00011101110111";
					}
					if ("P".equals(value) || "p".equals(value)) {
					  code = "00010111011101";
					}
					if ("Q".equals(value) || "q".equals(value)) {
					   code = "0001110111010111";
					}
					if ("R".equals(value) || "r".equals(value)) {
					   code = "0001011101";
					}
					if ("S".equals(value) || "s".equals(value)) {
				 code = "00010101";
					}
					if ("T".equals(value) || "t".equals(value)) {
					 code = "000111";
					}
					if ("U".equals(value) || "u".equals(value)) {
					   code = "0001010111";
					}
					if ("V".equals(value) || "v".equals(value)) {
					    code = "000101010111";
					}
					if ("W".equals(value) || "w".equals(value)) {
					    code = "000101110111";
					}
					if ("X".equals(value) || "x".equals(value)) {
					   code = "00011101010111";
					}
					if ("Y".equals(value) || "y".equals(value)) {
					  code = "0001110101110111";
					}
					if ("Z".equals(value) || "z".equals(value)) {
					    code = "00011101110101";
					}
					if ("1".equals(value)) {
					 code = "00010111011101110111";
					}
					if ("2".equals(value)) {
					  code = "000101011101110111";
					}
					if ("3".equals(value)) {
					    code = "0001010101110111";
					}
					if ("4".equals(value)) {
					   code = "00010101010111";
					}
					if ("5".equals(value)) {
					    code = "000101010101";
					}
					if ("6".equals(value)) {
					   code = "00011101010101";
					}
					if ("7".equals(value)) {
					  code = "0001110111010101";
					}
					if ("8".equals(value)) {
					  code = "000111011101110101";
					}
					if ("9".equals(value)) {
					    code = "00011101110111011101";
					}
					if ("0".equals(value)) {
					   code = "0001110111011101110111";
					}
					if (" ".equals(value)) {
						   code = "0000000";
						}

					
					return code;
				}
	        
	 
	});
	        
		
	}

	
	
	



public void onInit(int status) {

if (status == TextToSpeech.SUCCESS) {

    int result = tts.setLanguage(Locale.US);

    if (result == TextToSpeech.LANG_MISSING_DATA
            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        Log.e("TTS", "This Language is not supported");
    } else {

		   Button btnSubmitdok = (Button) findViewById(R.id.morsebtn);
		   btnSubmitdok.setEnabled(true);
     //   speakOut(String kkk);
    }

} else {
    Log.e("TTS", "Initilization Failed!");
}

}

private void speakOut(String kkk) {
tts.speak(kkk, TextToSpeech.QUEUE_FLUSH, null);
}


	
	
	
	
	
	
	
	
	
	
	

	
	private void aboutmorse() {
		// TODO Auto-generated method stub

			   ImageView btnSubmitdok = (ImageView) findViewById(R.id.imageView1);
				 
		        btnSubmitdok.setOnClickListener(new OnClickListener() {
		 
		            @Override
		            public void onClick(View v) {
		             

		         	   Intent intent = new Intent(getBaseContext(), morse.class);
		               startActivity(intent);
		          
			     
		            }
		        
		 
		});
		        
	}
		        
		        
		    	private void fronts() {
		    		// TODO Auto-generated method stub

		    			  Button btnSubmitdok1 = (Button) findViewById(R.id.switch2);
		    				 
		    		        btnSubmitdok1.setOnClickListener(new OnClickListener() {
		    		 
		    		            @Override
		    		            public void onClick(View v) {
		    		             

		    				       Intent intent = new Intent(getBaseContext(), frontstrobe.class);

		    				       String sp = Integer.toString(okk);
					               intent.putExtra("Speed", sp);
		    				       
		    				       startActivity(intent);
		    		                
		    			     
		    		            }
		    		        
		    		 
		    		});
		    		        
		    		 
	              
	            



		
	}
		    	
		        
		       	private void fronts2() {
		    		// TODO Auto-generated method stub

		    			  Button btnSubmitdok1 = (Button) findViewById(R.id.switch3);
		    				 
		    		        btnSubmitdok1.setOnClickListener(new OnClickListener() {
		    		        	
		    		        	
		    		 
		    		            @Override
		    		            public void onClick(View v) {
		    		            	
		    		  if(newstring==null){
		    			  
		    			  Toast.makeText(getBaseContext(), "Whoa! Generate a morse code",
		    	                    Toast.LENGTH_SHORT).show();
		    		  }else{
		    		 
		    		      
		    		  	  

			         	   Intent intent = new Intent(getBaseContext(), frontmorse.class);

    				       String sp = Integer.toString(okk);
			               intent.putExtra("Speed", sp);
			               intent.putExtra("MCode", newstring);
			               startActivity(intent);
		    		
		    		  }
		    			     
		    		            }
		    		        
		    		 
		    		});
    
		       	}
    

	private void sos() {
     
            
            

            Thread t = new Thread() {
          	    public void run() {
          	        try {
          	            // Switch on the cam for app's life
          	            if (mCamera == null) {
          	                // Turn on Cam
          	                mCamera = Camera.open();
          	                try {
          	                    mCamera.setPreviewDisplay(null);
          	                } catch (IOException e) {
          	                    e.printStackTrace();
          	                }
          	                mCamera.startPreview();
          	            }
          	            
          	            
          	          String myString = "101010000000101010";
          	        
          	        //on /off/ on /off/ on /off/ off /off/ off /off/ off /off/ on /off/ on /off/ on /off/
          	        

          	          for (int x = 0; x < myString.length(); x++) {
						//	for (int i=0; i < times*2; i++) {
          	            if ((myString.charAt(x) == '1') ) { // Off, turn it on
          	  	        turnOn();
          	  	    } else { // On, turn it off
          	  	        turnOff();
          	  	    }
          	                sleep(okk);
          	                
          	         
          	            }

          	            if (mCamera != null) {
          	                mCamera.stopPreview();
          	                mCamera.release();
          	                mCamera = null;
          	            }
          	        } catch (Exception e){ 
          	            e.printStackTrace(); 
          	        }
          	    }
          	};

          	t.start();
      

}


	public void SetAlarm()
    {
        final Button button = (Button) findViewById(R.id.xViewjj);// replace with a button from your own UI
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive( Context context, Intent _ )
            {
                button.setBackgroundColor( Color.RED );
                context.unregisterReceiver( this ); // this == BroadcastReceiver, not Activity
            }
        };

        this.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );

        PendingIntent pintent = PendingIntent.getBroadcast( this, 0, new Intent("com.blah.blah.somemessage"), 0 );
        AlarmManager manager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000*5, pintent );
    }
	/*
	 * Get the camera
	 */
	private void getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open();
				try{
					
					camera.setPreviewDisplay(null);
					
				}catch( IOException e){
					
					e.printStackTrace();
				}
				params = camera.getParameters();
			} catch (RuntimeException e) {
				Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
			}
		}
	}
	



	private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }
    
    
    public void flashLightOn() {

        try {
            if (getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam = Camera.open();
                Parameters p = cam.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void flashLightOff() {
        try {
            if (getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOff",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
    	
    	if (tts != null) {
    	    tts.stop();
    	    tts.shutdown();
    	}
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
        if(hasFlash)
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

		EditText Et = (EditText) findViewById(R.id.editText1);
		Et.setText("SOS");

		
		//How to set focus to right of text in EditText for android
		Et.setSelection(Et.getText().length());
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
       	     overridePendingTransition(R.anim.slidebackin, R.anim.slidebackout);

			   Intent intent = new Intent(getBaseContext(), MainActivity.class);
                         startActivity(intent);  
                      
                   choice=1;
                  

	 //   return;
	}


	
	
	///extra
	/** Turn the devices FlashLight on */
	public void turnOn() {
	    if (mCamera != null) {
	    // Turn on LED
	    playSound();

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

	/*
	 * Playing sound will play button toggle sound on flash on / off
	 */
	private void playSound() {
		if (isFlashOn) {
		} else {
			mp = MediaPlayer.create(alarm.this, sound);
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

	
	public void onProgressChanged(SeekBar seekBar, int progress,  
            boolean fromUser) {  
     //   Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();  
       ok = progress +40;
       okk = progress +40;
    //   tex.setText(String.valueOf(ok)); 
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
        	
            LayoutInflater inflater = alarm.this.getLayoutInflater();
        				new AlertDialog.Builder(alarm.this)
        	

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
    	
    	

        AlertDialog alertDialog = new AlertDialog.Builder(alarm.this).create(); //Read Update
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
    
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        return false;
    }
 

	
}
