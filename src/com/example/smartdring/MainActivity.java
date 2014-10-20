package com.example.smartdring;




import java.util.ArrayList;
import java.util.List;



import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	AudioManager mgr=null;

	TextView lb;
   ListView list1 ;
	private List<SoundProfile> myCars = new ArrayList<SoundProfile>();
	  SoundEdit e=new SoundEdit();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       setContentView(R.layout.list);
 
		lb =(TextView)findViewById(R.id.label);
	       list1 = (ListView) findViewById(R.id.list);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };
        SoundProfile Silent=new SoundProfile("Silent","MyPref");
        SoundProfile Normale=new SoundProfile("Normale","Pref3");
        myCars.add(Silent);
        myCars.add(Normale);
        MainActivityAdapter adapter = new MainActivityAdapter(this, myCars);
        list1.setAdapter(adapter) ;
       getWindow().setBackgroundDrawableResource(R.drawable.img2);
       registerForContextMenu(list1);
       registerClickCallback();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId()==R.id.list) {
    	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
    		menu.setHeaderTitle(myCars.get(info.position).getName());}
		
		menu.add("Activer");
		menu.add("Modifier");

	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				
	              viewClicked.showContextMenu();
					SoundProfile clickedCar = myCars.get(position);

				String message = "You clicked position " 
								+ " Which is car make " + clickedCar.getName();
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	
	  public boolean onContextItemSelected(MenuItem item) {
          AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

          if (item.getTitle() == "Activer") {
        	  mgr=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        	  SharedPreferences pref1 = getApplicationContext().getSharedPreferences(myCars.get(info.position).getSharedPref(), MODE_WORLD_READABLE); 
        		String spget=pref1.getString("alarm", null);
        		 
        		mgr.setStreamVolume(AudioManager.STREAM_ALARM, Integer.parseInt(spget), 0);

        	  
          }
          else if (item.getTitle() == "Modifier") {
        	  Toast.makeText(getApplicationContext(), myCars.get(info.position).getName(),
       			   Toast.LENGTH_LONG).show();
       	  Intent intent = new Intent(this, SoundEdit.class);
       	  intent.putExtra("sp", myCars.get(info.position).getSharedPref());   
       	  startActivity(intent);
       	  } else {
                  return false;
          }
          return true;
	  }
	  
	  
	 
		 }

