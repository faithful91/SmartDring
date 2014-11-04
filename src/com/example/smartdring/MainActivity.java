package com.example.smartdring;

import schedules.ScheduleList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	
	final static private long ONE_SECOND = 1000;
	final static private long TWENTY_SECONDS = ONE_SECOND * 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_profile);

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
				
			}
		});
	}

	@Override
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
			Intent intent = new Intent(this, ScheduleList.class);
			startActivity(intent);

		}
		
		
		

		else {
			return false;
		}
		return true;
	}
	
}
