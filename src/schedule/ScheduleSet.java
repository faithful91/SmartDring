package schedule;

import services.SchedeleService;
import com.example.smartdring.R;
import dataBaseAdapters.DBAdapterProfiles;
import dataBaseAdapters.DBAdapterSchedules;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class ScheduleSet extends Activity implements OnClickListener {
	private TimePicker timePicker1;
	int Hour;
	int Minute;
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;
	Spinner listProfileSpinner;
	DBAdapterProfiles dbProfile;
	DBAdapterSchedules dbSchedule;
	ToggleButton day0toggle;
	ToggleButton day1toggle;
	ToggleButton day2toggle;
	ToggleButton day3toggle;
	ToggleButton day4toggle;
	ToggleButton day5toggle;
	ToggleButton day6toggle;
	Button doneButton;
	Switch state;
	String profileId;
	String whoCallMe;
	ListScheduleAdapter lsadapter;
	ListView listSchedule;
	ScheduleList e;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_set);
		getWindow().setBackgroundDrawableResource(R.drawable.img2);
		dbProfile = new DBAdapterProfiles(this);
		dbSchedule = new DBAdapterSchedules(this);
		listSchedule = (ListView) findViewById(R.id.list1);
	
		dbProfile.open();
		dbSchedule.open();

		timePicker1 = (TimePicker) findViewById(R.id.timePicker);
		doneButton = (Button) findViewById(R.id.doneButton);
		listProfileSpinner = (Spinner) findViewById(R.id.listProfileSpinner);
		state = (Switch) findViewById(R.id.State);
		day0toggle = (ToggleButton) findViewById(R.id.day0toggle);
		day1toggle = (ToggleButton) findViewById(R.id.day1toggle);
		day2toggle = (ToggleButton) findViewById(R.id.day2toggle);
		day3toggle = (ToggleButton) findViewById(R.id.day3toggle);
		day4toggle = (ToggleButton) findViewById(R.id.day4toggle);
		day5toggle = (ToggleButton) findViewById(R.id.day5toggle);
		day6toggle = (ToggleButton) findViewById(R.id.day6toggle);
		((Button) findViewById(R.id.doneButton)).setOnClickListener(this);

		Intent iin = getIntent();
		Bundle b = iin.getExtras();
		profileId = (String) b.get("sp");
		whoCallMe = (String) b.get("callVar");

		listProfileSpinner = (Spinner) findViewById(R.id.listProfileSpinner);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				dbProfile.getAllProfilesForSpinner());
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		listProfileSpinner.setAdapter(dataAdapter);

		if (whoCallMe.equals("OldProg"))
			loadSchedulePref(profileId);
		else {
			String profileName = (String) b.get("profile");
			for (int i = 0; i < dbProfile.getAllProfilesForSpinner().size(); i++) {
				if (dbProfile.getAllProfilesForSpinner().get(i)
						.equals(profileName))
					listProfileSpinner.setSelection(i);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_set, menu);
		return true;
	}

	// load d'un programme qui existe pour le modifier
	public void loadSchedulePref(String profileId) {
		String profileName = dbSchedule.getSchedulePref(profileId)
				.getProfileName();
		int profileHour = dbSchedule.getSchedulePref(profileId)
				.getProfileHour();
		int profileMinute = dbSchedule.getSchedulePref(profileId)
				.getProfileMinute();
		String stateinit = dbSchedule.getSchedulePref(profileId).getState();
		boolean day0 = dbSchedule.getSchedulePref(profileId).getDay0();
		boolean day1 = dbSchedule.getSchedulePref(profileId).getDay1();
		boolean day2 = dbSchedule.getSchedulePref(profileId).getDay2();
		boolean day3 = dbSchedule.getSchedulePref(profileId).getDay3();
		boolean day4 = dbSchedule.getSchedulePref(profileId).getDay4();
		boolean day5 = dbSchedule.getSchedulePref(profileId).getDay5();
		boolean day6 = dbSchedule.getSchedulePref(profileId).getDay6();

		timePicker1.setCurrentHour(profileHour);
		timePicker1.setCurrentMinute(profileMinute);
		if (stateinit.equals("active"))
			state.setChecked(true);
		else
			state.setChecked(false);
		day0toggle.setChecked(day0);
		day1toggle.setChecked(day1);
		day2toggle.setChecked(day2);
		day3toggle.setChecked(day3);
		day4toggle.setChecked(day4);
		day5toggle.setChecked(day5);
		day6toggle.setChecked(day6);
		for (int i = 0; i < dbProfile.getAllProfilesForSpinner().size(); i++) {
			if (dbProfile.getAllProfilesForSpinner().get(i).equals(profileName))
				listProfileSpinner.setSelection(i);
		}
	}

	// sauvegarder les changements en appuyant sur save
	public Schedule saveOldSchedule(String profileId) {
		int profileIdinit = dbSchedule.getSchedulePref(profileId).getId();
		String profileNamex = listProfileSpinner.getSelectedItem().toString();
		int profileHour = timePicker1.getCurrentHour();
		int profileMinute = timePicker1.getCurrentMinute();
		String stateinit;
		if (state.isChecked() == true)
			stateinit = "active";
		else
			stateinit = "desactive";
		boolean day0 = day0toggle.isChecked();
		boolean day1 = day1toggle.isChecked();
		boolean day2 = day2toggle.isChecked();
		boolean day3 = day3toggle.isChecked();
		boolean day4 = day4toggle.isChecked();
		boolean day5 = day5toggle.isChecked();
		boolean day6 = day6toggle.isChecked();
		Schedule schedule = new Schedule(profileIdinit, profileNamex,
				profileHour, profileMinute, stateinit, day0, day1, day2, day3,
				day4, day5, day6);

		return schedule;
	}

	// Sauvegarder nouveau programme
	public Schedule saveNewSchedule() {
		int profileIdinit;
		profileIdinit = 0;
		String profileNamex = listProfileSpinner.getSelectedItem().toString();

		int profileHour = timePicker1.getCurrentHour();
		int profileMinute = timePicker1.getCurrentMinute();
		String stateinit;
		if (state.isChecked() == true)
			stateinit = "active";
		else
			stateinit = "desactive";
		boolean day0 = day0toggle.isChecked();
		boolean day1 = day1toggle.isChecked();
		boolean day2 = day2toggle.isChecked();
		boolean day3 = day3toggle.isChecked();
		boolean day4 = day4toggle.isChecked();
		boolean day5 = day5toggle.isChecked();
		boolean day6 = day6toggle.isChecked();
		Schedule schedule = new Schedule(profileIdinit, profileNamex,
				profileHour, profileMinute, stateinit, day0, day1, day2, day3,
				day4, day5, day6);
		return schedule;
	}

	@Override
	public void onClick(View arg0) {
		listSchedule = (ListView) findViewById(R.id.list1);

		int profileIdInFut = 0;
		Intent service = new Intent(this, SchedeleService.class);
		// Tester s'il s'agit d'un nouveau ou ancien programme
		if (whoCallMe.equals("OldProg")) {
			dbSchedule.saveOldSchedulePrefInDB(saveOldSchedule(profileId));
			service.putExtra("id", profileId);
			service.putExtra("uniqueId","ScheduleSet");  

		} else {
			profileIdInFut = dbSchedule
					.saveNewSchedulePrefInDB(saveNewSchedule());
			service.putExtra("id", "" + profileIdInFut);

			service.putExtra("uniqueId","ScheduleSet");  
		}

	
		this.startService(service);
		finish();

		
		}
	
}
