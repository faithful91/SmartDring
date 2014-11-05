package com.example.smartdring;

import dataBaseAdapters.DBAdapterProfiles;
import schedules.ScheduleList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView testtxt;
	private TextView lb;
	private ListView list1;
	private Context context = this;
	private SoundProfile InsSProfile = new SoundProfile(context);
	private SoundEdit e = new SoundEdit(context);
	DBAdapterProfiles db;
	final static private long ONE_SECOND = 1000;
	final static private long TWENTY_SECONDS = ONE_SECOND * 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_profile);
		list1 = (ListView) findViewById(R.id.list);
		getWindow().setBackgroundDrawableResource(R.drawable.img2);
		db = new DBAdapterProfiles(this);
		db.open();

			MainActivityAdapter adapter = new MainActivityAdapter(this,
					db.getAllProfiles());

			list1.setAdapter(adapter);
			registerForContextMenu(list1);
			registerClickCallback();
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(db.getAllProfiles()
					.get(info.position).getName());
		}
		menu.add("Activer");
		menu.add("Programmer");
		menu.add("Modifier");
		menu.add("Delete");
	}

	// afficher contextuel menu avec un simple click
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
			Intent intent = new Intent(this, SoundEdit.class);
			intent.putExtra("sp",
					db.getAllProfiles().get(info.position)
					.getName());
			
			e.activeProfile(db.getAllProfiles().get(info.position)
					.getName());

		}

		else if (item.getTitle() == "Delete") {

			db.deleteProfile(db.getAllProfiles()
					.get(info.position).getName());
			MainActivityAdapter adapter = new MainActivityAdapter(this,
					db.getAllProfiles());

			list1.setAdapter(adapter);
			

		} else if (item.getTitle() == "Modifier") {
			Toast.makeText(
					getApplicationContext(),
					db.getAllProfiles().get(info.position).getName(),
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, SoundEdit.class);
			intent.putExtra("sp",
					db.getAllProfiles().get(info.position)
					.getName());
			startActivity(intent);
		}

		else if (item.getTitle() == "Programmer") {
			Intent intent = new Intent(this, ScheduleList.class);
			startActivity(intent);

		}

		else {
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items

		switch (item.getItemId()) {
		case R.id.add_profile:
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			final EditText input = new EditText(this);
			alert.setTitle("Entre profile name");

			alert.setView(input);
			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					String value = input.getText().toString().trim();
					db.addProfile(value);

					MainActivityAdapter adapter = new MainActivityAdapter(
							context, db.getAllProfiles());

					list1.setAdapter(adapter);

					Toast.makeText(getApplicationContext(), value,
							Toast.LENGTH_SHORT).show();
				}
			});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					dialog.cancel();
				}
			});
			alert.show();
			return true;
		case R.id.action_settings:

			Toast.makeText(getApplicationContext(),
					"this is my Toasetiingst message!!! =)", Toast.LENGTH_LONG)
					.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
