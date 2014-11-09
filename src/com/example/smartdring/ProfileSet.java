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
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileSet extends Activity {
	SeekBar alarm = null;
	SeekBar music = null;
	SeekBar ring = null;
	SeekBar system = null;
	SeekBar voice = null;
	AudioManager mgr = null;
	TextView alarmtxtv = null;
	TextView musictxtv = null;
	TextView ringtxtv = null;
	TextView systemtxtv = null;
	TextView voicetxtv = null;
	SharedPreferences pref = null;
	Editor editor = null;
	String SharedPref;
	String spget = null;
	private Handler handler;
	private ContentObserver mVolumeObserver;
	private Context c = this;

	public ProfileSet(Context c) {
		this.c = c;
	}

	public ProfileSet() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound_edit);
		Intent iin = getIntent();
		Bundle b = iin.getExtras();
		SharedPref = (String) b.get("sp");

		mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		alarm = (SeekBar) findViewById(R.id.alarm);
		music = (SeekBar) findViewById(R.id.music);
		ring = (SeekBar) findViewById(R.id.ring);
		system = (SeekBar) findViewById(R.id.system);
		voice = (SeekBar) findViewById(R.id.voice);
		alarmtxtv = (TextView) findViewById(R.id.alarmtxtv);
		musictxtv = (TextView) findViewById(R.id.musictxtv);
		ringtxtv = (TextView) findViewById(R.id.ringtxtv);
		systemtxtv = (TextView) findViewById(R.id.systemtxtv);
		voicetxtv = (TextView) findViewById(R.id.voicetxtv);
		Toast.makeText(getApplicationContext(), SharedPref, Toast.LENGTH_LONG)
				.show();

		verifPrefSharedExist(SharedPref);

		initBar(alarm, AudioManager.STREAM_ALARM, alarmtxtv, "alarm");
		initBar(music, AudioManager.STREAM_MUSIC, musictxtv, "music");
		initBar(ring, AudioManager.STREAM_RING, ringtxtv, "ring");
		initBar(system, AudioManager.STREAM_SYSTEM, systemtxtv, "system");

		initBar(voice, AudioManager.STREAM_VOICE_CALL, voicetxtv, "voice");
		Handler mHandler = new Handler();
		// methode d'observateur
		mVolumeObserver = new ContentObserver(mHandler) {
			@Override
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
			}

		};

	}

	public void verifPrefSharedExist(String SharedPref) {

		String alarm = null;
		String music = null;
		String ring = null;
		String system = null;
		String voice = null;

		pref = c.getApplicationContext().getSharedPreferences(SharedPref, 0);

		Editor editor = pref.edit();
		alarm = pref.getString("alarm", null);
		music = pref.getString("music", null);
		ring = pref.getString("ring", null);
		system = pref.getString("system", null);
		voice = pref.getString("voice", null);

		if (alarm == null)
			editor.putString("alarm", "0");
		if (music == null)
			editor.putString("music", "0");
		if (ring == null)
			editor.putString("ring", "0");
		if (system == null)
			editor.putString("system", "0");
		if (voice == null) {
			editor.putString("voice", "0");

			editor.commit();
		}

	}

	private void initBar(SeekBar bar, final int stream, final TextView txtv,
			final String dataName) {

		bar.setMax(mgr.getStreamMaxVolume(stream));
		spget = pref.getString(dataName, null);

		bar.setProgress(Integer.parseInt(spget));

		txtv.setText(spget + "/"
				+ String.valueOf(mgr.getStreamMaxVolume(stream)));

		bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar bar, int progress,
					boolean fromUser) {
				mgr.setStreamVolume(0, progress, AudioManager.FLAG_PLAY_SOUND);
				pref = getApplicationContext().getSharedPreferences(SharedPref,
						0);
				Editor editor = pref.edit();

				editor.putString(dataName, String.valueOf(progress));
				editor.commit();

				txtv.setText(String.valueOf(progress) + "/"
						+ String.valueOf(mgr.getStreamMaxVolume(stream)));

			}

			@Override
			public void onStartTrackingTouch(SeekBar bar) {
				// no-op
			}

			@Override
			public void onStopTrackingTouch(SeekBar bar) {
				// no-op
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Observateur
		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System.CONTENT_URI, true,
						mVolumeObserver);

	}

	public void activeProfile(String SPA) {
		verifPrefSharedExist(SPA);
		AudioManager mgr1 = null;

		String alarm1;
		String music1;
		String ring1;
		String system1;
		String voice1;

		SharedPreferences pref1 = c.getApplicationContext()
				.getSharedPreferences(SPA, 1);

		alarm1 = pref1.getString("alarm", null);

		music1 = pref1.getString("music", null);
		ring1 = pref1.getString("ring", null);
		system1 = pref1.getString("system", null);
		voice1 = pref1.getString("voice", null);
		Log.e("TAG", "avantp");

		Log.e("TAG", "apresp");
		Log.e("TAG", ring1);

		mgr1 = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);

		mgr1.setStreamVolume(AudioManager.STREAM_RING, Integer.parseInt(ring1),
				AudioManager.ADJUST_LOWER);
		mgr1.setStreamVolume(AudioManager.STREAM_ALARM,
				Integer.parseInt(alarm1), AudioManager.FLAG_PLAY_SOUND);
		mgr1.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				Integer.parseInt(voice1), AudioManager.FLAG_PLAY_SOUND);
		mgr1.setStreamVolume(AudioManager.STREAM_SYSTEM,
				Integer.parseInt(system1), AudioManager.FLAG_PLAY_SOUND);
		mgr1.setStreamVolume(AudioManager.STREAM_MUSIC,
				Integer.parseInt(music1), AudioManager.FLAG_PLAY_SOUND);

	}

}
