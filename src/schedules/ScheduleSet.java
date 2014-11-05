package schedules;

import java.util.Calendar;

import com.example.smartdring.R;
import com.example.smartdring.R.id;
import com.example.smartdring.R.layout;
import com.example.smartdring.R.menu;

import dataBaseAdapters.DBAdapterProfiles;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ScheduleSet extends Activity {
	private TimePicker timePicker1;
	int Hour;
	int Minute;
	Button doneButton;
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;
	Spinner listProfileSpinner;
	DBAdapterProfiles db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_set);
		db=new DBAdapterProfiles(this);
        db.open();
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		doneButton = (Button) findViewById(R.id.doneButton);
		//getNameProfiletoLoad
		Intent iin = getIntent();
		Bundle b = iin.getExtras();
		String ProfileName = (String) b.get("sp");
		
		//Spinner
		listProfileSpinner=(Spinner)findViewById(R.id.listProfileSpinner);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, db.getAllProfilesForSpinner());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listProfileSpinner.setAdapter(dataAdapter);

		
        Toast.makeText(getApplicationContext(), ProfileName,
        Toast.LENGTH_LONG).show();


Button.OnClickListener buttonDoneClickListener= new Button.OnClickListener(){

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		

	

		setup();
			
			
			
		
	}};
	doneButton.setOnClickListener(buttonDoneClickListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_set, menu);
		return true;
	}

	
	private void setup() {
		Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        Hour=timePicker1.getCurrentHour();
        Minute=timePicker1.getCurrentMinute();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),   calendar.get(Calendar.DAY_OF_MONTH), Hour,  Minute);
        Log.e("TAG","Calendar: " +calendar.getTime());
        br = new BroadcastReceiver() {
            @Override
			public void onReceive(Context c, Intent i) {
				Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
				Log.e("TAG", "Alarm marche: " );

			}
        };
		registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.authorwjf.wakeywakey"), 0 );
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

	}

}
