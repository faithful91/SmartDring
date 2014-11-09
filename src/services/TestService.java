package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import receivers.SoundTimer;
import schedules.Schedule;

import dataBaseAdapters.DBAdapterSchedules;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;
	Context c;
	DBAdapterSchedules dbSchedule;
	int hour;
	int min;
	List<Integer> eventIds;
	int x;
	List<Schedule> listActiveSchedule;

	@Override
	public IBinder onBind(Intent intent) {
		onStart(intent, 0);
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Service Created", 1).show();

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Service Destroy", 1).show();

		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		dbSchedule = new DBAdapterSchedules(this);
		dbSchedule.open();

		Bundle b = intent.getExtras();

		String eventToDel = "";
		eventToDel = (String) b.get("eventDel");

		if (!(eventToDel == null))
			cancelEvent(eventToDel);

		String eventBoot = "";
		eventToDel = (String) b.get("boot");
		if (eventBoot.equals("boot"))
			bootAddAllScheduleToSystem();

		String profofileIdFromSet = "";
		profofileIdFromSet = (String) b.get("id");
		if (!profofileIdFromSet.equals(""))
			addAlarm(profofileIdFromSet);

		listActiveSchedule = new ArrayList<Schedule>();
		listActiveSchedule = dbSchedule.getActiveSchedules();
		// TODO Auto-generated method stub

		Toast.makeText(getApplicationContext(), "Service Working", 1).show();

	}

	public void cancelEvent(String id) {
		int eventIdConvPos = Integer.parseInt(id);
		am = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);

		pi = PendingIntent.getBroadcast(this, eventIdConvPos, new Intent(
				"com.authorwjf.wakeywakey"), 0);
		am.cancel(pi);
		pi.cancel();

	}

	public void bootAddAllScheduleToSystem() {
		for (int i = 0; i < listActiveSchedule.size(); i++) {
			hour = listActiveSchedule.get(i).getProfileHour();
			min = listActiveSchedule.get(i).getProfileMinute();
			Log.e("TAG", "" + min);

			int id = setup(10, 01, 10);

			listActiveSchedule.get(i).setIdEv("" + id);
			dbSchedule.addIdEvInDb(listActiveSchedule.get(i).getId(), id);

			Log.e("TAG", "id :" + listActiveSchedule.get(i).getIdEv());

		}
	}

	public int setup(int hour, int min, int id) {

		Calendar calendar = Calendar.getInstance();
		calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 200);
		Log.e("TAG", "Calendar: " + calendar.getTime());

		final int _id = (int) System.currentTimeMillis();

		Intent scheduleIntent = new Intent(this, SoundTimer.class);

		scheduleIntent.putExtra("za3ma", "" + id);

		pi = PendingIntent.getBroadcast(this, 0, scheduleIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY, pi);
		return _id;

	}

	public void addAlarm(String profileId) {
		Schedule schedule = new Schedule();
		schedule = dbSchedule.getSchedulePref(profileId);
		int hour = schedule.getProfileHour();
		int min = schedule.getProfileMinute();
		int id = schedule.getId();
		setup(hour, min, id);

	}
}
