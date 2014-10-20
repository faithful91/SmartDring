package com.example.smartdring;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

public class SoundEdit extends Activity {
SeekBar alarm=null;
SeekBar music=null;
SeekBar ring=null;
SeekBar system=null;
SeekBar voice=null;
AudioManager mgr=null;
TextView alarmtxtv=null;
TextView musictxtv=null;
TextView ringtxtv=null;
TextView systemtxtv=null;
TextView voicetxtv=null;
SharedPreferences pref = null;
Editor editor = null;
String SharedPref;

private Handler handler;
	private ContentObserver mVolumeObserver;


@Override
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_sound_edit);
  Intent iin= getIntent();
  Bundle b = iin.getExtras();
   SharedPref=(String)b.get("sp");

  mgr=(AudioManager)getSystemService(Context.AUDIO_SERVICE);


  alarm=(SeekBar)findViewById(R.id.alarm);
  music=(SeekBar)findViewById(R.id.music);
  ring=(SeekBar)findViewById(R.id.ring);
  system=(SeekBar)findViewById(R.id.system);
  voice=(SeekBar)findViewById(R.id.voice);
  alarmtxtv =(TextView)findViewById(R.id.alarmtxtv);
  musictxtv =(TextView)findViewById(R.id.musictxtv);
  ringtxtv =(TextView)findViewById(R.id.ringtxtv);
  systemtxtv =(TextView)findViewById(R.id.systemtxtv);
  voicetxtv =(TextView)findViewById(R.id.voicetxtv);

  initBar(alarm, AudioManager.STREAM_ALARM, alarmtxtv,"alarm");
  initBar(music, AudioManager.STREAM_MUSIC,musictxtv,"music");
  initBar(ring, AudioManager.STREAM_RING,ringtxtv, "ring");
  initBar(system, AudioManager.STREAM_SYSTEM,systemtxtv,"system");

  initBar(voice, AudioManager.STREAM_VOICE_CALL,voicetxtv,"voice");
  Handler mHandler = new Handler();
	// methode d'observateur
	mVolumeObserver = new ContentObserver(mHandler) {
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
		}

	};

	

}

private void initBar(SeekBar bar, final int stream, final TextView txtv,final String dataName) {
	SharedPreferences pref = getApplicationContext().getSharedPreferences(SharedPref, 0); 

	bar.setMax(mgr.getStreamMaxVolume(stream));
	final String spget=pref.getString(dataName, null);
	
  bar.setProgress(Integer.parseInt(spget));

	  Editor editor = pref.edit();	
	  editor.putString(dataName, spget);

  txtv.setText(" "+String.valueOf(mgr.getStreamVolume(stream)+"/"+String.valueOf(mgr.getStreamMaxVolume(stream))));  
  editor.commit();
  
 
  bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    public void onProgressChanged(SeekBar bar, int progress,
                                  boolean fromUser) {
      mgr.setStreamVolume(stream, progress,
                          AudioManager.FLAG_PLAY_SOUND);
  	SharedPreferences pref = getApplicationContext().getSharedPreferences(SharedPref, 0); 

      Editor editor = pref.edit();	

      editor.putString(dataName, String.valueOf(progress));
      editor.commit();

      txtv.setText(String.valueOf(mgr.getStreamVolume(stream)+"/"+String.valueOf(mgr.getStreamMaxVolume(stream))));  
     
    }
    
    public void onStartTrackingTouch(SeekBar bar) {
      // no-op
    }
    
    public void onStopTrackingTouch(SeekBar bar) {
      // no-op
    }
  });
}

public void activeProfile (String SharedPrefAc)
{
	
	}

@Override
protected void onResume() {
super.onResume();
//Observateur
this.getApplicationContext().getContentResolver().registerContentObserver( 
    android.provider.Settings.System.CONTENT_URI, true, 
    mVolumeObserver );




}
}


