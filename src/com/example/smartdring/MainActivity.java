package com.example.smartdring;

import java.util.Calendar;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
private	TextView testtxt;
	private TextView lb;
	private ListView list1;
	private	Context e1 = this;
	private SoundProfile InsSProfile = new SoundProfile(e1);
	private SoundEdit e = new SoundEdit(e1);
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;

	final static private long ONE_SECOND = 1000;
	final static private long TWENTY_SECONDS = ONE_SECOND * 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		lb = (TextView) findViewById(R.id.label);
		list1 = (ListView) findViewById(R.id.list);

		InsSProfile.loadProfiles();

		MainActivityAdapter adapter = new MainActivityAdapter(this,
				InsSProfile.getProfilesNames());
		list1.setAdapter(adapter);
		getWindow().setBackgroundDrawableResource(R.drawable.img2);
		registerForContextMenu(list1);
		registerClickCallback();
		testtxt = (TextView) findViewById(R.id.music);
		setup();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(InsSProfile.getProfilesNames()
					.get(info.position).getName());
		}
		menu.add("Activer");
		menu.add("Programmer");
		menu.add("Modifier");
		menu.add("Delete");
	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				viewClicked.showContextMenu();
				SoundProfile clickedCar = InsSProfile.getProfilesNames().get(
						position);

				String message = "You clicked position "
						+ " Which is car make " + clickedCar.getName();
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		if (item.getTitle() == "Activer") {
e.activeProfile(InsSProfile.getProfilesNames().get(info.position).getSharedPref());
		}

		else if (item.getTitle() == "Delete") {

			InsSProfile.delete(InsSProfile.getProfilesNames()
					.get(info.position).getName());
			InsSProfile.getProfilesNames().remove(
					InsSProfile.getProfilesNames().get(info.position));
			list1.invalidateViews();

		} else if (item.getTitle() == "Modifier") {
			Toast.makeText(
					getApplicationContext(),
					InsSProfile.getProfilesNames().get(info.position).getName(),
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, SoundEdit.class);
			intent.putExtra("sp",
					InsSProfile.getProfilesNames().get(info.position)
							.getSharedPref());
			startActivity(intent);
		} 
		
		
		else if (item.getTitle() == "Programmer")
		{
	
		}
		
		
		

		else {
			return false;
		}
		return true;
	}
	private void setup() {
		Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),   calendar.get(Calendar.DAY_OF_MONTH), 02, 30);
        Log.e("TAG","Calendar: " +calendar.getTime());
        br = new BroadcastReceiver() {
            @Override
			public void onReceive(Context c, Intent i) {
				Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
				Log.e("TAG", "File write failed: " );

			}
        };
		registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.authorwjf.wakeywakey"), 0 );
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

	}

}
