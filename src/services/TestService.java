package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import schedules.Schedule;

import dataBaseAdapters.DBAdapterSchedules;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
	Toast.makeText(getApplicationContext(), "Service Created",1).show();
	
		super.onCreate();
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Service Destroy",1).show();
		
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		dbSchedule=new DBAdapterSchedules(this) ;
		dbSchedule.open();
		

		Bundle b = intent.getExtras();

		String eventToDel="";
		eventToDel=(String) b.get("eventDel");

	if(!(eventToDel.equals(""))&&eventToDel.equals("boot"))cancelEvent(eventToDel);
		
		String eventBoot="";
		eventToDel=(String) b.get("boot");
		if(eventToDel.equals("boot"))
		bootAddAllScheduleToSystem();
		
		
		listActiveSchedule=new ArrayList<Schedule>();
		listActiveSchedule=dbSchedule.getActiveSchedules();
		// TODO Auto-generated method stub
		
		Toast.makeText(getApplicationContext(), "Service Working",1).show();

		
		
		return super.onStartCommand(intent, flags, startId);
	}
		public void cancelEvent(String id)
	{	int eventIdConvPos=Integer.parseInt(id);	
		am = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE)	;
		
		pi = PendingIntent.getBroadcast( this, eventIdConvPos, new Intent("com.authorwjf.wakeywakey"), 0 );
		am.cancel(pi);
		pi.cancel();
		

	}
	public void bootAddAllScheduleToSystem()
	{
		 for(int i=0;i<listActiveSchedule.size();i++ )
			{
			 hour=listActiveSchedule.get(i).getProfileHour();
			 min=listActiveSchedule.get(i).getProfileMinute();
			    Log.e("TAG", ""+min);	
	
			 int id=setup(hour,min);

			 listActiveSchedule.get(i).setIdEv(""+id);
			 dbSchedule.addIdEvInDb(listActiveSchedule.get(i).getId(), id);  
			 
			 Log.e("TAG", "id :"+listActiveSchedule.get(i).getIdEv());	

			}
	}

	private int setup(int hour,int min ) {

		Calendar calendar = Calendar.getInstance();
        calendar.getTime();
       
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),   calendar.get(Calendar.DAY_OF_MONTH), hour,  min);
        Log.e("TAG","Calendar: " +calendar.getTime());
        br = new BroadcastReceiver() {
        	
            @Override
			public void onReceive(Context c, Intent i) {
				Toast.makeText(c, "Rise and fucken girl!", Toast.LENGTH_LONG).show();
				Log.e("TAG", "Alarm marche: " );

			}
        };
		registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
	    final int _id = (int) System.currentTimeMillis();

	   
	    
		pi = PendingIntent.getBroadcast( this, _id, new Intent("com.authorwjf.wakeywakey"), 0 );
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
       am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

       return _id;

	}

}
