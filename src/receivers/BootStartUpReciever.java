package receivers;

import services.SchedeleService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootStartUpReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		// Start Service On Boot Start Up
		Intent service = new Intent(context, SchedeleService.class);
		service.putExtra("boot", "boot");
		context.startService(service);
	}
}
