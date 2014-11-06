package schedules;

import java.util.ArrayList;
import java.util.List;

import com.example.smartdring.R;
import com.example.smartdring.SoundEdit;
import com.example.smartdring.R.id;
import com.example.smartdring.R.layout;


import dataBaseAdapters.DBAdapterSchedules;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleList extends Activity implements OnClickListener   {
    /** Called when the activity is first created. */
	
		DBAdapterSchedules db;
	 	public static final String db_table = "scheduleTable";
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
		ListView listSchedule;

		public List<Schedule> Schedules = new ArrayList<Schedule>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        ((Button)findViewById(android.R.id.button1)).setOnClickListener(this);
        listSchedule=(ListView)findViewById(R.id.list1);
        db = new DBAdapterSchedules(this);
        db.open();
        ListScheduleAdapter adapter = new ListScheduleAdapter(this,
				db.getAllSchedules());
		listSchedule.setAdapter(adapter);
  
    
		registerForContextMenu(listSchedule);
		registerClickCallback();
    
    
    
    
    }
    private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.list1);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				viewClicked.showContextMenu();

			}
		});
	}

    public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		if (item.getTitle() == "Edit")
			{
			Intent intent = new Intent(this, ScheduleSet.class);
			intent.putExtra("sp",""+
					db.getAllSchedules().get(info.position)
					.getId());
			intent.putExtra("callVar","OldProg");
					
			startActivity(intent);

			}

		else if (item.getTitle() == "Delete")
			{
			db.deleteSchedule(""+db.getAllSchedules()
					.get(info.position).getId());
			ListScheduleAdapter adapter = new ListScheduleAdapter(this,
					db.getAllSchedules());
			listSchedule.setAdapter(adapter);
			}
		else {
			return false;
		}
		return true;
	}

    
    
    public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(db.getAllSchedules()
					.get(info.position).getProfileName());
		}
		menu.add("Edit");
		menu.add("Delete");
	}
    
    @Override // Cr�ation du menu principal
    public boolean onCreateOptionsMenu(Menu menu) {    	
    	menu.add(0,100,0,"Tout effacer");
    	return true;
    }
 	
    @Override
    protected void onDestroy() {
    	db.close();
    	super.onDestroy();
    }
    
    public void onClick(View v) {
		long num = SystemClock.currentThreadTimeMillis();
		db.addSchedule("Fort",11, 11, "active", true, true, true, true,true,true,true);
		
		ListScheduleAdapter adapter = new ListScheduleAdapter(this,
				db.getAllSchedules());
		listSchedule.setAdapter(adapter);
  	}
        
}