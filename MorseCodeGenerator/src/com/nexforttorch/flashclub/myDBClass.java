package com.nexforttorch.flashclub;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class myDBClass extends SQLiteOpenHelper {
	//Helps
//String table = "table2";
//String[] columns = {"column1", "column3"};
//String selection = "column3 =?";
//String[] selectionArgs = {"apple"};
//String groupBy = null;
//String having = null;
//String orderBy = "column3 DESC";
//String limit = "10";
//Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

    // Database Version
    private static final int DATABASE_VERSION = 4;
    private SQLiteDatabase db;
    // Database Name
    private static final String DATABASE_NAME = "flashnexfort";
    // Table Name
    private static final String TABLE_COUNTRY = "alarm";
    public static final String[] ALL_KEYS = new String[] {"ID","Spinpos","Details","Setfor"};
  

    
	public myDBClass(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// Create Table Name
		//"I'm Not A Threat To You Unless You Are A Threat To Me"
	    db.execSQL("CREATE TABLE " + TABLE_COUNTRY +
		          "(ID INTEGER PRIMARY KEY," +
		        " Spinpos TEXT(100),"+ 
		        " Details TEXT(100),"+ 
	
		        " Setfor TEXT(100));");
	    
	    

	    //currency table
	
	   
	    Log.d("TABLE_COUNTRY","Create currency Table Successfully.");
	}
	
	// Insert Data
	public long InsertData(String spinp,String detaill, String setfor) {
		// TODO Auto-generated method stub
		
		
		
		 try {
			SQLiteDatabase db;
     		db = this.getWritableDatabase(); // Write Data
     		
     		/**
     		 *  for API 11 and above
			SQLiteStatement insertCmd;
			String strSQL = "INSERT INTO " + TABLE_MEMBER
					+ "(ID,Code,Country) VALUES (?,?,?)";
			
			insertCmd = db.compileStatement(strSQL);
			insertCmd.bindString(1, strMemberID);
			insertCmd.bindString(2, strName);
			insertCmd.bindString(3, strTel);
			return insertCmd.executeInsert();
			*/
			
     	   ContentValues Val = new ContentValues();
     	
     	 Val.put("Spinpos", spinp);
     	
     	 Val.put("Details", detaill);
     	 

     	 Val.put("Setfor", setfor);
     	  
     	  
		   long rows = db.insert(TABLE_COUNTRY, null, Val);

		   db.close();
		   return rows; // return rows inserted.
           
		 } catch (Exception e) {
		    return -1;
		 }

	}
	
	
		
		

	
	
	// Select Data by descending ID
	public Cursor SelectData() {
		// TODO Auto-generated method stub
		
		  try{
			
			 SQLiteDatabase db;
			 db = this.getReadableDatabase(); // Read Data
				
			 Cursor cursor = db.query(TABLE_COUNTRY, new String[] { "ID AS _id, *" },null, null, null, null, "ID DESC");
			 
			 	if(cursor != null)
			 	{
			 		return cursor;
			 	}
			 	else
			 	{
			 		return null;
			 	}
				
		     } catch (Exception e) {
		       return null;
		     }

	}
	

	// Select Data by descending Year
		public int SelectData2() {
			// TODO Auto-generated method stub
			String groupBy = "Month,Day,Year";
			
				 SQLiteDatabase db;
				 db = this.getReadableDatabase(); // Read Data
				 
			try{	 
				 
				 //Raw query
				// String query = "SELECT ID AS _id, Name, Day, Month, Year, GROUP_CONCAT(Name) AS Name FROM "+TABLE_COUNTRY+" GROUP BY Day, Month,Year ORDER BY Milisec ASC" ;
				 String query = "SELECT ID As _id from alarm order by ID DESC limit 1";

				    Cursor cursor = db.rawQuery(query, null);
				    

if (cursor != null) {
				    cursor.moveToFirst(); 
				    int i=cursor.getInt(0);

				    return i;
				    
}else{
	
	return 0;
}
				// DB query function	
			//	 Cursor cursor = db.query(TABLE_COUNTRY, new String[] { "ID AS _id, *" },null, null, groupBy, null,"Milisec ASC");
				 
		 } catch (Exception e) {
			    return 0;
			 }


		}
	
	   // Method is called during an upgrade of the database, e.g. if you increase
    // the database version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);

 
        
        // Re Create on method  onCreate
        onCreate(db);
	}


// Extreme Vengeance Of The Cause Behind My Goal Frustration 
	
//	public boolean deleteRow(long l) {
		
	//	return db.delete(TABLE_COUNTRY, "ID" + "=" + l, null) > 0;
	//}
	
	
	 public void deleterow(String title) {
	        //Open the database
	        SQLiteDatabase database = this.getWritableDatabase();

	        //Execute sql query to remove from database
	        //NOTE: When removing by String in SQL, value must be enclosed with ''
	        database.execSQL("DELETE FROM " + TABLE_COUNTRY + " WHERE " + "ID" + "= '" + title + "'");

	        //Close the database
	        database.close();
	    }
	
	 
	 
	 
	 
	 
	 
	 
	 
	
	
	// Get a specific row (by rowId)
	public Cursor getRow(int i) {
		String where =  "ID" + "=" + i;
	
		
		 try {
			
			 SQLiteDatabase db;
			 db = this.getReadableDatabase(); // Read Data
				
			 Cursor cursor = db.query(TABLE_COUNTRY, new String[] { "ID AS _id, *" },where, null, null, null, null);
			 
			 	if(cursor != null)
			 	{
			 		cursor.moveToFirst();
			 		return cursor;
			 	}
			 	else
			 	{
			 		return null;
			 	}
				
		 } catch (Exception e) {
		    return null;
		 }
	}


	public boolean updateRow(String note, String details, String spins)  {
		// TODO Auto-generated method stub
			String where = "ID" + "=" + note;

	        SQLiteDatabase db = this.getWritableDatabase();
			/*
			 * CHANGE 4:
			 */
			// TODO: Update data in the row with new fields.
			// TODO: Also change the function's arguments to be what you need!
			// Create row's data:
			ContentValues newValues = new ContentValues();
			newValues.put("Spinpos", spins);
			newValues.put("Details", details);
		
			
			// Insert it into the database.
			return db.update(TABLE_COUNTRY, newValues, where, null) != 0;
		
	}

	
	
	
	
	

	
	
	
	
	
}