package com.nexforttorch.flashclub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;




// "#FFEB3B"
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.SettingNotFoundException;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class reminder extends Activity implements OnSeekBarChangeListener {

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
	
	Button eReminderTime;
	 LinearLayout yhp;
	 LinearLayout ihp;
	 EditText et;
	 EditText ett;
	 EditText details;
	 
	 
	 long diff1;
	 
	 
	 

	 TextView tvDate;
		String s ;

	  private Spinner spinner1;
	    private Button btnSubmit;
	    
	    
	    
		private TextView tvDisplayDate;

		private TextView tvDisplayTime;
		private DatePicker dpResult;
		private Button btnChangeDate;

		private int year;
		private int month;
		private int day;
		
		private int year1;
		private int month1;
		private int day1;
		private int hour;
		private int min;
		private int hour1;
		private int min1;
		private int sec;
		String strr ;
		
		 int yo;

		static final int DATE_DIALOG_ID = 999;
		
	
	LinearLayout lay1 ;
	LinearLayout lay2 ;
	LinearLayout lay3 ;
	LinearLayout lay4 ;
	Button b1 ;
	EditText e1;
	 int notifyID = 1;
	 

	 myDBClass myDb;
	
	
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
	    setContentView(R.layout.activity_reminder);
	    batteryTxt = (TextView) this.findViewById(R.id.xView1);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    myDb = new myDBClass(this);

 		// myDb.InsertData("0", "n/a");
	    
	    int bb = myDb.SelectData2();
	    
	    
	    if(bb==0){
	    	
	    	
	    	
	    	Switch list_toggle2=(Switch)findViewById(R.id.switch1);
	    	list_toggle2.setChecked(false);
	    }else{
	    	
	   Cursor cursor  = myDb.getRow(bb);
	    String Spinpos = cursor.getString(cursor.getColumnIndex("Spinpos"));
	    String details =cursor.getString(cursor.getColumnIndex("Details"));
	    String setime=cursor.getString(cursor.getColumnIndex("Setfor"));
	    
	    
	    TextView t1 = (TextView) findViewById(R.id.detailstext);
	    t1.setText(details);
	  //  TextView t2 = (TextView) findViewById(R.id.alram_statustext);
	   // t2.setText(Spinpos);
	    TextView t3 = (TextView) findViewById(R.id.time_settext);
	    t3.setText(setime);

Switch list_toggle2=(Switch)findViewById(R.id.switch1);
list_toggle2.setChecked(true);


		createNotification(setime);
	    
	    }
	    

	   //    Toast.makeText(reminder.this, bbs +" - " +Spinpos +"-"+details+"-"+setime, Toast.LENGTH_SHORT).show();

	    
	    
	    
	 
		

	    /***	   
		//Blinks on notification (optional)
		Notification.Builder builder = new Notification.Builder(this);
		builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
// Example 1
// Date then; // When the notification will occur
//Date now = new Date();
//String remaining1 = DateUtils.formatElapsedTime ((then.getTime() - now.getTime())/1000); // Remaining time to seconds
// remaining1: "MM:SS"

// Example 2
//String remaining2 = DateUtils.formatElapsedTime (60 * 64 + 8); // 64 minutes in seconds and 8 seconds
// remaining2: "64:08"
		   
 	  	builder.setSmallIcon(R.drawable.alarm)
	 	   .setPriority(Notification.PRIORITY_HIGH)
	 	   .setContentTitle("Alarm ft Flash Club")
	 	   .setContentText("Alarm set in")
	 	   .setOngoing(true);
	 	 builder.setLights(0xff00ff00, 300, 100);
	 	 NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	 	 manager.notify(1, builder.build()); 
	 //	builder.setAutoCancel(true);
	 	 
	 
	  
	 	  Context ctx = null;
		Intent intent = new Intent(ctx, reminder.class);
	 	    PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

	 	    NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

	 	    b.setAutoCancel(true)
	 	     .setDefaults(Notification.DEFAULT_ALL)
	 	     .setWhen(System.currentTimeMillis())         
	 	     .setSmallIcon(R.drawable.ic_launcher)
	 	     .setTicker("Hearty365")            
	 	     .setContentTitle("Default notification")
	 	     .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
	 	     .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
	 	     .setContentIntent(contentIntent)
	 	     .setContentInfo("Info");


	 	    NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	 	    notificationManager.notify(1, b.build());
 	***/ 
		//Animation while activity open

   	     overridePendingTransition(R.anim.slidebackin, R.anim.slidebackout);
	    
	    

	    tvDisplayDate = (TextView) findViewById(R.id.textView1f);

	    tvDisplayTime = (TextView) findViewById(R.id.textView1ff);
	    
	    
	    
	    
	    
	    setCurrentDateOnView();
	    addListenerOnButton2();
	    addListenerOnButton3();
	    timedioluge();
	    point();
	 
	    
	    
	    
	    
 lay1 = (LinearLayout) findViewById(R.id.lay1);
 lay2 = (LinearLayout) findViewById(R.id.lay2);
 lay3 = (LinearLayout) findViewById(R.id.lay3);
 lay4 = (LinearLayout) findViewById(R.id.lay4);
 b1 = (Button) findViewById(R.id.button3);
 e1 = (EditText) findViewById(R.id.textView3);
lay1.setVisibility(View.GONE);
lay2.setVisibility(View.GONE);
b1.setVisibility(View.GONE);
e1.setVisibility(View.GONE);


Switch list_toggle2=(Switch)findViewById(R.id.switch1);
list_toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
   @Override
   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      if(isChecked) {
        
         Log.d("You are :", "Checked");
     
 
         lay1.setVisibility(View.VISIBLE);
         lay2.setVisibility(View.VISIBLE);
         b1.setVisibility(View.VISIBLE);
        e1.setVisibility(View.VISIBLE);
         

        lay3.setVisibility(View.GONE);
        lay4.setVisibility(View.GONE);
        
        
        //how can i pause alarm manager broad cast reciever
 	 
      //  	TextClock tc = (TextClock) findViewById(R.id.textView1fm);       
 		 		 	      
 		//	 		 			tc.setText("00:00:00"); 
 			 		 			
 			 		 			

 			// 		        	EditText edi = (EditText) findViewById(R.id.textView3);       
 			 		 		 		 	      
 			 //		 		edi.setText(""); 
 			 		 			
 			 		 		 
 		//	TextClock details = (TextClock) findViewById(R.id.textView1f2mo);       
 			 		 		 		 	      
 			// details.setText("N/A");  		 		
 			 		 		
 			 		 			
 		
    
      }
      else {
      
         Log.d("You are :", " Not Checked");

         
         
         lay1.setVisibility(View.GONE);
         lay2.setVisibility(View.GONE);
         b1.setVisibility(View.GONE);
         e1.setVisibility(View.GONE);
         

         lay3.setVisibility(View.VISIBLE);
         lay4.setVisibility(View.VISIBLE);
         
      	TextView tr = (TextView) findViewById(R.id.textView1f2);       
	 		tr.setText("Current Time");  
	 		
	 // condition to set todays hour and time in textclock inside handler 		
	    yo = 1;
	 		
	
 	        
 	       Toast.makeText(reminder.this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
   
 	     AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);         
 	     Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);      
 	     PendingIntent pIntent = PendingIntent.getBroadcast(getBaseContext(), 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);         
 	     aManager.cancel(pIntent);
 	     
 	     
 	     
 	     NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
 	     nMgr.cancel(notifyID);
 	   
 	   
 	   //delete ltest row 

	 int bb = myDb.SelectData2();
	   String bbs = Integer.toString(bb);
	    myDb.deleterow(bbs);
 	   
 	   
	    TextView t31 = (TextView) findViewById(R.id.time_settext);
		    t31.setText("- - -");  
		    TextView t1 = (TextView) findViewById(R.id.detailstext);
    	    t1.setText("- - - ");
 	   
 	   
 	   
      }
   }       
 });
	
	}

    
    
    
    public void createNotification(String fuck ) {
        // Prepare intent which is triggered if the
        // notification is selected
      Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra("n_data", "alar"); 
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
    	
        // Build notification
        // Actions are just fake
    	
    	
    	
    	// Using helloservice for updating alarm notify bar
       
        int numMessages = 0;
        Notification noti = new Notification.Builder(this)
                        .setContentTitle("Le me Torch's Alarm")
                        .setContentText("Alarm Set For "+fuck)
                        .setSmallIcon(R.drawable.ico)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pIntent)
                        .setPriority(Notification.PRIORITY_HIGH)
	 	                .setOngoing(true)
                    //    .addAction(R.drawable.full, "Call", pIntent)
                     //   .addAction(R.drawable.full, "More", pIntent)
                      // .addAction(R.drawable.full, "And more", pIntent)
	 	                .build();
        
  

     // Start of a loop that processes data and then notifies the user
    
            // Because the ID remains unchanged, the existing notification is
            // updated.
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notifyID, noti);
     

}
    
 
	
    
    
	private void startalarm(Long diff1) {
		// TODO Auto-generated method stub
		
		long seconds = diff1 / 1000;
		Intent intent = new Intent(this, MyBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                      this.getApplicationContext(), 234324243, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                                      + (seconds * 1000), pendingIntent);
		Toast.makeText(this, "Alarm set in " + seconds + " seconds",Toast.LENGTH_LONG).show();
	 	TextClock tc = (TextClock) findViewById(R.id.textView1fm);     
	 	String ok = Long.toString(diff1);
	 	
	 	
		
	}




	private void timedioluge() {
	
		
		eReminderTime = (Button) findViewById(R.id.button2);
		// TODO Auto-generated method stub
		eReminderTime.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            Calendar mcurrentTime = Calendar.getInstance();
	            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            int minute = mcurrentTime.get(Calendar.MINUTE);
	            

	         
	            
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(reminder.this, new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	tvDisplayTime.setText( selectedHour + ":" + selectedMinute);
	                	
	                	
	                 hour1 = selectedHour;  
	                min1 = selectedMinute;
	                
	                
	                
	                	
	                	
	                }
	            }, hour, minute, true);//Yes 24 hour time
	            mTimePicker.setTitle("Select Time");
	            mTimePicker.show();

	            
	            
	            
	        
	        }
	    });
	}

	
	
	
    
	private void point() {
	
		
		eReminderTime = (Button) findViewById(R.id.button3);
		// TODO Auto-generated method stub
		eReminderTime.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	        //	EditText tex3 = (EditText) findViewById(R.id.textView3);
	       // 	tex3.setText("day=="+day+"..month=="+ month+" ..year=="+ year+" ..day1=="+ day1+" ..month1=="+ month1+" ..year1=="+ year1+"");
	        	

		      	  // Outside Handler without update
		         	String strToDay = ""+day1+"-"+month1+"-"+year1+" "+hour1+":"+min1+"";
	 				  SimpleDateFormat formatterr = new SimpleDateFormat("d-M-yyyy kk:mm");
	 					// String strToDay = formatterr.format(new Date());
	 				  Date da = null;
	 				  try {
	 				   da = formatterr.parse(strToDay);//catch exception
	 				  } catch (ParseException e) {
	 				   // TODO Auto-generated catch block
	 				   e.printStackTrace();
	 				  } 
	 				  Calendar toDay = Calendar.getInstance();
	 				  toDay.setTime(da);
	 				  
	 			      long diff = toDay.getTimeInMillis() -  System.currentTimeMillis(); //result in millis
	 			      String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
	 			            TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
	 			            TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));
	 			     //    System.out.println(hms);
		             //      	long startTime = calendar.getTimeInMillis();
	 			      String milisec = Long.toString(diff);
	 			     
	 			      
	        	
	        	
	        	if(diff<=0){
	        		LayoutInflater inflater = getLayoutInflater();
	        		View layout = inflater.inflate(R.layout.toast,
	        		                               (ViewGroup) findViewById(R.id.toast_layout_root));

	        	//	ImageView image = (ImageView) layout.findViewById(R.id.image);
	        	// put in toast.xml
	         //   <ImageView android:id="@+id/image"
	          //  android:layout_width="40dp"
	           // android:layout_height="40dp"
	           // android:layout_marginRight="10dp" />/
	        	//	image.setImageResource(R.drawable.arr);
	        		
	        		TextView text = (TextView) layout.findViewById(R.id.text);
	        		text.setText("Please Set Time To a Future Reference.");

	        		Toast toast = new Toast(getApplicationContext());
	        		toast.setGravity(Gravity.BOTTOM, 0, 20);
	        		toast.setDuration(Toast.LENGTH_LONG);
	        		toast.setView(layout);
	        		toast.show();
	        		//Toast.makeText(reminder.this, "Not a Valid Input, I accept future references only.", Toast.LENGTH_SHORT).show();
	        	ScrollView SLa = (ScrollView) findViewById(R.id.sv);
	        	    
	         		ObjectAnimator anim = ObjectAnimator.ofInt(SLa, "backgroundColor", Color.RED, Color.BLACK);
	        	    
	         		anim.setDuration(3000);
	        	    anim.setEvaluator(new ArgbEvaluator());
	        	  //  anim.setRepeatCount(ValueAnimator.INFINITE);
	        	  //  anim.setRepeatMode(ValueAnimator.REVERSE);
	        	    anim.start();
	        	}else{
	        	
	        	// Layouts
	                 lay1.setVisibility(View.GONE);
	                 lay2.setVisibility(View.GONE);
	                 b1.setVisibility(View.GONE);
	                 e1.setVisibility(View.GONE);
	                 lay3.setVisibility(View.VISIBLE);
	 	             lay4.setVisibility(View.VISIBLE);

	           
	        	
	        		
	        	
	        	
 			      
 			      //Alarm set
                  startalarm(diff);
                  // Time set in notification
                  String am_pm = (hour1 < 12) ? "AM" : "PM";
				  String strToDay1 = ""+day1+"-"+month1+"-"+year1+" at "+hour1+":"+min1+" "+am_pm+"";
		 		 		createNotification(strToDay1);
		 		 	   TextView t31 = (TextView) findViewById(R.id.time_settext);
		 			    t31.setText(strToDay1);  
		 		 		
		 		 		
	 	 	     // Handler that updates every second     
	 	 	 		final Handler someHandler = new Handler(getMainLooper());   
		 	        someHandler.postDelayed(new Runnable() {
		 	            @Override
		 	            public void run() {
		 	                  String strToDay = ""+day1+"-"+month1+"-"+year1+" "+hour1+":"+min1+"";
		 	  				  SimpleDateFormat formatterr = new SimpleDateFormat("d-M-yyyy kk:mm");
		 	  			   	// String strToDay = formatterr.format(new Date());
		 	  				  Date da = null;
		 	  				  try {
		 	  				   da = formatterr.parse(strToDay);//catch exception
		 	  				  } catch (ParseException e) {
		 	  				   // TODO Auto-generated catch block
		 	  				   e.printStackTrace();
		 	  				  } 
                              Calendar toDay = Calendar.getInstance();
		 	  				  toDay.setTime(da);
		 	  				  //  tvClock.setText(new SimpleDateFormat("kk:mm", Locale.US).format(new Date()));
		 	            	  long diff = toDay.getTimeInMillis() -  System.currentTimeMillis(); //result in millis
		 	   			   String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
		 	   			                TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
		 	   			                TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));
		 	                  String dayy = Long.toString(diff);
		 	             	TextClock tc = (TextClock) findViewById(R.id.textView1fm);       
		 		 	     	tc.setText(hms); 
		 		 		  if(diff <= 0){

		 			      //     Toast.makeText(reminder.this, "Alarm", Toast.LENGTH_SHORT).show();
		 		 			//	handler.removeMessages(0);
		 		 				 Calendar mcurrentTime2 = Calendar.getInstance();
				 		 	        int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
				 		 	         int minute2 = mcurrentTime2.get(Calendar.MINUTE);
				 		 	         String am_pm = (hour2 < 12) ? "AM" : "PM";
				 		 			tc.setText(hour2 +":"+ minute2 +am_pm); 
			 		 	}
		 		 		  if(yo == 1){
		 		 				
		 		 		
		 		 		
		 		 		//		TextClock tc1 = (TextClock) findViewById(R.id.textView1fm);   
		 		 		    	
		 		 		  //      Calendar mcurrentTime2 = Calendar.getInstance();
		 		 	       ///   int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
		 		 	        //    int minute2 = mcurrentTime2.get(Calendar.MINUTE);
		 		 				

							  //  String delegate = "kk:mm aaa";
							   // String okd = (String) DateFormat.format(delegate,Calendar.getInstance().getTime());
		 		 			 //		tc1.setText(hour2 +":"+ minute2); 
		 		 				
		 		 			}
		 		 			
		 		 			   
		 	            	
		 	            	someHandler.postDelayed(this, 1000);
		 	            }

						
						
		 	        }, 10);
		        	TextView tr = (TextView) findViewById(R.id.textView1f2);       
				 		 		tr.setText("Time remained");  
		 	     
		 	       //Extra 
		 	  	   diff1 = toDay.getTimeInMillis() - System.currentTimeMillis(); //result in millis
		 	  	   String hms1 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff1),
	   			            TimeUnit.MILLISECONDS.toMinutes(diff1) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff1)),
	   			            TimeUnit.MILLISECONDS.toSeconds(diff1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff1)));
	   
	        	

			 	 
			 // Details text 	 
	        EditText t3 = (EditText) findViewById(R.id.textView3);
	        String tex= t3.getText().toString();
	        if(tex.equals("")){
	        	 TextView t1 = (TextView) findViewById(R.id.detailstext);
		    	    t1.setText("No details set");
  	   
            myDb.InsertData("1", "No details set",strToDay1);
	        }else{
	        	  
	    	    TextView t1 = (TextView) findViewById(R.id.detailstext);
	    	    t1.setText(tex);
	    	  
             myDb.InsertData("1", tex, strToDay1);
	        }
	        
	            
	        	}
	        }

	
	    });
	}


	
	

	private void addListenerOnButton2() {
		//tvDisplayDate.setVisibility(View.GONE);
	//	dpResult = (DatePicker) findViewById(R.id.datePicker1);

		final Calendar c = Calendar.getInstance();
		
		
				year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		

		year1 = c.get(Calendar.YEAR);
month1 = c.get(Calendar.MONTH)+1;
day1 = c.get(Calendar.DAY_OF_MONTH);
		
		


		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));

		// set current date into datepicker
	//	dpResult.init(year, month, day, null);
		
	}
	
	
	private void addListenerOnButton3() {
		//tvDisplayDate.setVisibility(View.GONE);
	//	dpResult = (DatePicker) findViewById(R.id.datePicker1);

		final Calendar c = Calendar.getInstance();
		
		
		
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		sec = c.get(Calendar.SECOND);
		
		
		

		// set current date into textview
		tvDisplayTime.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(hour).append(":").append(min).append(" "));

		// set current date into datepicker
	//	dpResult.init(year, month, day, null);
		
	}

	

	private void setCurrentDateOnView() {
		// TODO Auto-generated method stub
	 btnChangeDate = (Button) findViewById(R.id.button1);

		btnChangeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog(DATE_DIALOG_ID);

			}

		});
		
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		//    set date picker as current date
		   return new DatePickerDialog(this, datePickerListener,
                       year, month,day);
		   
		//  DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener,
			//                      year, month,day);
          // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
          // return datePickerDialog;
		   
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener
                = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			
			year1 = selectedYear;
			month1 = selectedMonth+1;
			day1 = selectedDay;

			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(day).append("-").append(month + 1)
			   .append("-").append(year)
			   .append(" "));

			// set selected date into datepicker also
		//	dpResult.init(year, month, day, null);

		}
	};

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	            && keyCode == KeyEvent.KEYCODE_BACK
	            && event.getRepeatCount() == 0) {
	        // Take care of calling this method on earlier versions of
	        // the platform where it doesn't exist.
	        onBackPressed();
	    }

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
       	     overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			   Intent intent = new Intent(getBaseContext(), MainActivity.class);
                         startActivity(intent);   

	 //   return;
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
			mp = MediaPlayer.create(reminder.this, R.raw.light_switch_off);
		} else {
			mp = MediaPlayer.create(reminder.this, R.raw.light_switch_on);
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
       ok = progress +50;
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
        	
            LayoutInflater inflater = reminder.this.getLayoutInflater();
        				new AlertDialog.Builder(reminder.this)
        	

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
    	
    	

        AlertDialog alertDialog = new AlertDialog.Builder(reminder.this).create(); //Read Update
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
