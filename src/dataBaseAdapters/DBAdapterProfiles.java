package dataBaseAdapters;

import java.util.ArrayList;
import java.util.List;

import com.example.smartdring.SoundProfile;

import schedules.Schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapterProfiles {
    public static final String db_table = "profilesTable";

    	public static final String db_id= "_id";
	    public static final String db_profileName = "profileName";
	    

	DatabaseHelper	DBHelper;
	Context			context;
	SQLiteDatabase	db;
	
	public DBAdapterProfiles(Context context){
		this.context = context;
		DBHelper = new DatabaseHelper(context);
	}	
	
	public class DatabaseHelper extends SQLiteOpenHelper{

		Context			context;
		
		public DatabaseHelper(Context context) {
			super(context, db_table, null, 1);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table "
		            + db_table + " (" 
		            + db_id + " integer primary key autoincrement, " 
		            + db_profileName + " text not null, "
		            +");");			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Toast.makeText(context, "Mise � jour de la Base de donn�es version "+oldVersion+" vers "+newVersion, Toast.LENGTH_SHORT).show();
			db.execSQL("DROP TABLE IF EXISTS profilesTable");
			onCreate(db);
		}
		
	}
	
	public DBAdapterProfiles open(){
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		db.close();
	}
	
	public void Truncate(){
		db.execSQL("DELETE FROM profilestable");
	}
	
	public long insererUnProduit(String profileName,int startHour,int startMinute,String state,
			boolean day0,boolean day1,boolean day2,boolean day3,boolean day4,boolean day5,boolean day6
								)
	{	
		ContentValues values = new ContentValues();
		values.put(db_profileName, profileName);
		
		return db.insert(db_table, null, values);
	}
	
	
	public boolean supprimerProduit(long profileName){
		return db.delete(db_table, db_profileName+"="+profileName, null)>0;
	}
	
	public Cursor recupererLaListeDesProduits(){
		return db.query("schedulesTable", new String[]{
				db_id,
				db_profileName,
				}, null, null, null, null, null);
	}
	 public List<SoundProfile> getAllProfiles() {
         List<SoundProfile> soundProfilesList = new ArrayList<SoundProfile>();
         // Select All Query
         String selectQuery = "SELECT  * FROM " + db_table;
      
         Cursor cursor = db.rawQuery(selectQuery, null);
      
         // looping through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
                 SoundProfile soundProfile = new SoundProfile();
                 soundProfile.setName(cursor.getString(1));
                 soundProfilesList.add(soundProfile);
             } while (cursor.moveToNext());
         }
      
         return soundProfilesList;
     }
}
