package profile;

import com.example.smartdring.R;

import dataBaseAdapters.DBAdapterProfiles;
import schedule.ScheduleList;
import schedule.ScheduleSet;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

public class ProfileList extends Activity {
	private ListView list1;
	private Context context = this;
	private ProfileSet e = new ProfileSet(context);
	DBAdapterProfiles db;
	SharedPreferences pref = null;
	SharedPreferences prefFirstOpen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_profile);
		db = new DBAdapterProfiles(this);
		db.open();
		ProfileSet listprofile=new ProfileSet();
		prefFirstOpen = getApplicationContext().getSharedPreferences("firstOpen", 0);
		

			String profile = prefFirstOpen.getString("profile", "");
			if (profile.equals(""))
		
		{	db.addProfile("Normale");
		firstOpenPrefShared("5","5","5","5","5","Normale");
			db.addProfile("Fort");
			firstOpenPrefShared("7","15","7","7","5","Fort");
			db.addProfile("Silencieux");
			firstOpenPrefShared("0","0","0","0","0","Silencieux");
			firstOpen();
			
		}
		list1 = (ListView) findViewById(R.id.list);
		getWindow().setBackgroundDrawableResource(R.drawable.img2);
	

		ListProfileAdapter adapter = new ListProfileAdapter(this,
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
			menu.setHeaderTitle(db.getAllProfiles().get(info.position)
					.getName());
		}
		menu.add("Activer");
		menu.add("Programmer");
		menu.add("Modifier");
		menu.add("Supprimer");
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
			Intent intent = new Intent(this, ProfileSet.class);
			intent.putExtra("sp", db.getAllProfiles().get(info.position)
					.getName());

			e.activeProfile(db.getAllProfiles().get(info.position).getName());
			saveActifProfile(db.getAllProfiles().get(info.position)
					.getName());
			Toast.makeText(getApplicationContext(), "Le profil "+db.getAllProfiles().get(info.position)
		.getName()+" est activé" ,
   Toast.LENGTH_LONG).show();
			ListProfileAdapter adapter = new ListProfileAdapter(this,
					db.getAllProfiles());

			list1.setAdapter(adapter);
		}

		else if (item.getTitle() == "Supprimer") {
			if (db.getAllProfiles().get(info.position).getName().equals(getActifProfile()))
			{
				final AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Erreur");
				alert.setMessage("Impossible de supprimier le profil actif");
				alert.setNegativeButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				alert.show();

			}
			else
			db.deleteProfile(db.getAllProfiles().get(info.position).getName());
			ListProfileAdapter adapter = new ListProfileAdapter(this,
					db.getAllProfiles());

			list1.setAdapter(adapter);

		} else if (item.getTitle() == "Modifier") {
			Toast.makeText(getApplicationContext(),
					db.getAllProfiles().get(info.position).getName(),
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, ProfileSet.class);
			intent.putExtra("sp", db.getAllProfiles().get(info.position)
					.getName());
			startActivity(intent);
		}

		else if (item.getTitle() == "Programmer") {
			Intent intent = new Intent(this, ScheduleSet.class);
			intent.putExtra("sp", db.getAllProfiles().get(info.position)
					.getName());
			intent.putExtra("callVar", "NewProg");
			intent.putExtra("profile", db.getAllProfiles().get(info.position)
					.getName());
			;

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
			alert.setTitle("Entrer le nom du profil");

			alert.setView(input);
			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = input.getText().toString().trim();
							db.addProfile(value);

							ListProfileAdapter adapter = new ListProfileAdapter(
									context, db.getAllProfiles());

							list1.setAdapter(adapter);

							
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
		case R.id.time:
			{
				Intent intent = new Intent(this, ScheduleList.class);
				startActivity(intent);
			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void saveActifProfile(String profileName) {
		String profile;
		pref = getApplicationContext().getSharedPreferences("ActifProfile", 0);
		Editor editor = pref.edit();
				editor.putString("profile", profileName);
			editor.commit();
			profile = pref.getString("profile", null);
		}
	public String getActifProfile() {
		String profile="";
		pref = context.getApplicationContext().getSharedPreferences("ActifProfile", 0);
		Editor editor = pref.edit();
		editor.commit();
		profile = pref.getString("profile", null);
		return profile;
	
		}
	public void firstOpen()
	{		Editor editor = prefFirstOpen.edit();
			{editor.putString("profile", "true");
			editor.commit();}
 	}

public void firstOpenPrefShared(String alarm,String music,String ring,String system, String voice,String prefShared) {
			pref = getApplicationContext().getSharedPreferences(prefShared, 0);
			Editor editor = pref.edit();
			editor.putString("alarm", alarm);
			editor.putString("music", music);
			editor.putString("ring", ring);
			editor.putString("system", ring);
			editor.putString("voice", voice);
			editor.commit();
			}
}


