package receivers;

import dataBaseAdapters.DBAdapterSchedules;
import services.ChangeVolumeService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SoundTimer extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle b = intent.getExtras();
		DBAdapterSchedules db=new DBAdapterSchedules(context);
		db.open();
		String profileId = (String) b.get("za3ma");
	    Log.e("TAG", "profileIDDD"+profileId);	
		boolean existe=db.verifId(profileId);
		if (existe){
		Intent i = new Intent(context, ChangeVolumeService.class);
		i.putExtra("uniqueId", "SoundTimer");
		i.putExtra("zozo", profileId);
		context.startService(i);
		}
		else{		Intent i = new Intent(context, ChangeVolumeService.class);

			i.putExtra("uniqueId", "SoundTimer");
		i.putExtra("zozo", "");}
	}

}
