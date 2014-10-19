package com.example.smartdring;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.net.Uri;

public class MainActivity extends Activity {
SeekBar alarm=null;
SeekBar music=null;
SeekBar ring=null;
SeekBar system=null;
SeekBar voice=null;
AudioManager mgr=null;
	private Handler handler;
	private ContentObserver mVolumeObserver;


@Override
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  mgr=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
  
  alarm=(SeekBar)findViewById(R.id.alarm);
  music=(SeekBar)findViewById(R.id.music);
  ring=(SeekBar)findViewById(R.id.ring);
  system=(SeekBar)findViewById(R.id.system);
  voice=(SeekBar)findViewById(R.id.voice);
  
  initBar(alarm, AudioManager.STREAM_ALARM);
  initBar(music, AudioManager.STREAM_MUSIC);
  initBar(ring, AudioManager.STREAM_RING);
  initBar(system, AudioManager.STREAM_SYSTEM);

  initBar(voice, AudioManager.STREAM_VOICE_CALL);
  Handler mHandler = new Handler();
	// methode d'observateur
	mVolumeObserver = new ContentObserver(mHandler) {
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (ring != null && mgr != null) {
				int volumering= mgr
						.getStreamVolume(AudioManager.STREAM_RING);
				ring.setProgress(volumering);
			
				if (system != null && mgr != null) {
					int volumesystem = mgr
							.getStreamVolume(AudioManager.STREAM_SYSTEM);
					system.setProgress(volumesystem);
				}
				if (music != null && mgr != null) {
					int volumemusic = mgr
							.getStreamVolume(AudioManager.STREAM_MUSIC);
					music.setProgress(volumemusic);
					
				}
				if (alarm != null && mgr != null) {
					int volumealarm = mgr
							.getStreamVolume(AudioManager.STREAM_ALARM);
					alarm.setProgress(volumealarm);
				}
				if (voice != null && mgr != null) {
					int volumevoice = mgr
							.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
					voice.setProgress(volumevoice);
				}
			
			}
		}

	};

}

private void initBar(SeekBar bar, final int stream) {
  bar.setMax(mgr.getStreamMaxVolume(stream));
  bar.setProgress(mgr.getStreamVolume(stream));
  
  bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    public void onProgressChanged(SeekBar bar, int progress,
                                  boolean fromUser) {
      mgr.setStreamVolume(stream, progress,
                          AudioManager.FLAG_PLAY_SOUND);
      Log.i(null, "hoho");
    }
    
    public void onStartTrackingTouch(SeekBar bar) {
      // no-op
    }
    
    public void onStopTrackingTouch(SeekBar bar) {
      // no-op
    }
  });
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


