package com.example.smartdring;




import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Intent;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
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
	TextView lb;
   ListView list1 ;
	private List<SoundProfile> myCars = new ArrayList<SoundProfile>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       setContentView(R.layout.list);
 
		lb =(TextView)findViewById(R.id.label);
	       list1 = (ListView) findViewById(R.id.list);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };
        SoundProfile Silent=new SoundProfile("Silent");
        SoundProfile Normale=new SoundProfile("Normale");
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
		 }

