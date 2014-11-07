package dataBaseAdapters;

import java.util.ArrayList;
import java.util.List;

import schedules.Schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapterScheduleSet {
    public static final String db_table = "schedulesTable";

    	public static final String db_id= "_id";
	    public static final String db_profileName = "profileName";
	    public static final String db_startHour = "startHour";
	    public static final String db_startMinute = "startMin";
	    public static final String db_state = "state";
	    public static final String db_day0 = "day0";
	    public static final String db_day1 = "day1";
	    public static final String db_day2 = "day2";
	    public static final String db_day3 = "day3";
	    public static final String db_day4 = "day4";
	    public static final String db_day5 = "day5";
	    public static final String db_day6 = "day6";

	DatabaseHelper	DBHelper;
	Context			context;
	SQLiteDatabase	db;
	
	public DBAdapterScheduleSet(Context context){
		this.context = context;
		DBHelper = new DatabaseHelper(context);
	}	
	
	public class DatabaseHelper extends SQLiteOpenHelper{

		Context			context;
		
		public DatabaseHelper(Context context) {
			super(context, "schedulesTable", null, 1);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table "
		            + db_table + " (" 
		            + db_id + " integer primary key autoincrement, " 
		            + db_profileName + " text not null, "
		            + db_startHour + " integer not null default 0, "
		            + db_startMinute + " integer not null default 0, "
		            + db_state + " text not null, "
		            + db_day0 + " integer not null default 0, "
		            + db_day1 + " integer not null default 0, "
		            + db_day2 + " integer not null default 0, "
		            + db_day3 + " integer not null default 0, "
		            + db_day4 + " integer not null default 0, "
		            + db_day5 + " integer not null default 0, "
		            + db_day6 + " integer not null default 0 "
		            +");");			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Toast.makeText(context, "Mise � jour de la Base de donn�es version "+oldVersion+" vers "+newVersion, Toast.LENGTH_SHORT).show();
			db.execSQL("DROP TABLE IF EXISTS schedulesTable");
			onCreate(db);
		}
		
	}
	
	public DBAdapterScheduleSet open(){
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		db.close();
	}
	
	public void Truncate(){
		db.execSQL("DELETE FROM schedulesTable");
	}
	
	public long addSchedule(String profileName,int startHour,int startMinute,String state,
			boolean day0,boolean day1,boolean day2,boolean day3,boolean day4,boolean day5,boolean day6
								)
	{	
		ContentValues values = new ContentValues();
		values.put(db_profileName, profileName);
		values.put(db_startHour, startHour);
		values.put(db_startMinute, startMinute);
		values.put(db_state, state);
		values.put(db_day0, day0);
		values.put(db_day1, day1);
		values.put(db_day2, day2);
		values.put(db_day3, day3);
		values.put(db_day4, day4);
		values.put(db_day5, day5);
		values.put(db_day6, day6);
		return db.insert("schedulesTable", null, values);
	}
	
	
	public boolean deleteSchedule(String profileName){
		return db.delete(db_table, ""+db_profileName+"="+"'"+profileName+"'", null)>0;
	}
	
	public Cursor recupererLaListeDesProduits(){
		return db.query("schedulesTable", new String[]{
				db_id,
				db_profileName,
				db_startHour,
				db_startMinute,
				db_state,
				db_day0,
				db_day1,
				db_day2,
				db_day3,
				db_day4,
				db_day5,
				db_day6}, null, null, null, null, null);
	}
	

}
