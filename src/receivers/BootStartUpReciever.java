package receivers;

import services.TestService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootStartUpReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		// Start Service On Boot Start Up
		Intent service = new Intent(context, TestService.class);
		service.putExtra("boot", "boot");
		context.startService(service);
	}
}
