package com.example.smartdring;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import android.database.ContentObserver;
import android.util.Log;
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
	AudioManager mgr=null;	private ContentObserver mVolumeObserver;

TextView testtxt;
	TextView lb;
   ListView list1 ;
   Context e1=this;
   SoundProfile InsSProfile=new SoundProfile(e1);
	  SoundEdit e=new SoundEdit();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       setContentView(R.layout.list);

	    
	      

		lb =(TextView)findViewById(R.id.label);
	       list1 = (ListView) findViewById(R.id.list);
	       

	       InsSProfile.loadProfiles();        
      
        
        MainActivityAdapter adapter = new MainActivityAdapter(this, InsSProfile.Profiles);
        list1.setAdapter(adapter) ;
       getWindow().setBackgroundDrawableResource(R.drawable.img2);
       registerForContextMenu(list1);
       registerClickCallback();
       testtxt =(TextView)findViewById(R.id.music);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId()==R.id.list) {
    	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
    		menu.setHeaderTitle(InsSProfile.Profiles.get(info.position).getName());}
		
		menu.add("Activer");
		menu.add("Modifier");
		menu.add("Delete");

	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				
	              viewClicked.showContextMenu();
					SoundProfile clickedCar = InsSProfile.Profiles.get(position);

				String message = "You clicked position " 
								+ " Which is car make " + clickedCar.getName();
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	
	  public boolean onContextItemSelected(MenuItem item) {
          AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

          if (item.getTitle() == "Activer") {

        	 

        	  
          }

          else  if (item.getTitle() == "Delete") {

        	  InsSProfile.delete(InsSProfile.Profiles.get(info.position).getName());
        	  InsSProfile.Profiles.remove(InsSProfile.Profiles.get(info.position));
        	  list1.invalidateViews();

        	  
          }
          else if (item.getTitle() == "Modifier") {
        	  Toast.makeText(getApplicationContext(), InsSProfile.Profiles.get(info.position).getName(),
       			   Toast.LENGTH_LONG).show();
       	  Intent intent = new Intent(this, SoundEdit.class);
       	  intent.putExtra("sp", InsSProfile.Profiles.get(info.position).getSharedPref());   
       	  startActivity(intent);
       	  } else {
                  return false;
          }
          return true;
	  }
	  
	
	 


		
	

		 }

