package services;

import java.util.Calendar;

import profile.ProfileSet;


import schedule.Schedule;

import dataBaseAdapters.DBAdapterSchedules;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class ChangeVolumeService extends Service {
	DBAdapterSchedules dbSchedule;
	ProfileSet e=new ProfileSet(this); 
	public ChangeVolumeService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Service Createdfok", 1).show();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Service Destroy", 1).show();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Schedule schedule;
		dbSchedule = new DBAdapterSchedules(this);
		dbSchedule.open();
		Bundle b = intent.getExtras();
		String profileId = (String) b.get("zozo");
		schedule = dbSchedule.getSchedulePref(profileId);
		Boolean day0 = schedule.getDay0();
		Boolean day1 = schedule.getDay1();
		Boolean day2 = schedule.getDay2();
		Boolean day3 = schedule.getDay3();
		Boolean day4 = schedule.getDay4();
		Boolean day5 = schedule.getDay5();
		Boolean day6 = schedule.getDay6();
		if (schedule.getState().equals("active"))
		{	Calendar cal = Calendar.getInstance();
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			//  if the schedule is setup for today, apply the settings
			if (dayOfWeek == Calendar.SUNDAY && day0
					|| dayOfWeek == Calendar.MONDAY && day1
					|| dayOfWeek == Calendar.TUESDAY && day2
					|| dayOfWeek == Calendar.WEDNESDAY && day3
					|| dayOfWeek == Calendar.THURSDAY && day4
					|| dayOfWeek == Calendar.FRIDAY && day5
					|| dayOfWeek == Calendar.SATURDAY && day6)
			{
				Toast.makeText(getApplicationContext(),
						"The profile "+schedule.getProfileName()+" is already activated", Toast.LENGTH_LONG)
						.show();
				e.activeProfile(schedule.getProfileName());
				
			}
		}

		Toast.makeText(getApplicationContext(), "" + profileId, 1).show();
		return super.onStartCommand(intent, flags, startId);
	}
}
