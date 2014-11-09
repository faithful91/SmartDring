package receivers;

import services.ChangeVolume;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SoundTimer extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle b = intent.getExtras();
		String profileId = (String) b.get("za3ma");
		Intent i = new Intent(context, ChangeVolume.class);
		i.putExtra("zozo", profileId);
		context.startService(i);
	}

}
