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
	AudioManager mgr=null;
TextView testtxt;
	TextView lb;
   ListView list1 ;
	private List<SoundProfile> myCars = new ArrayList<SoundProfile>();
	  SoundEdit e=new SoundEdit();
		private List<String> profileName = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       setContentView(R.layout.list);

	    



		lb =(TextView)findViewById(R.id.label);
	       list1 = (ListView) findViewById(R.id.list);
	       readFromFile();
	       
       
        for (int i=0;i<profileName.size();i++)
        {
        	SoundProfile g = new SoundProfile(profileName.get(i),profileName.get(i)+"spp");
            myCars.add(g);

        }
        
      
        
        MainActivityAdapter adapter = new MainActivityAdapter(this, myCars);
        list1.setAdapter(adapter) ;
       getWindow().setBackgroundDrawableResource(R.drawable.img2);
       registerForContextMenu(list1);
       registerClickCallback();
       testtxt =(TextView)findViewById(R.id.music);
Toast.makeText(getApplicationContext(), readFromFile(),
		   Toast.LENGTH_LONG).show();

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
	  
	  private void writeToFile(String data) {
		    try {
		        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_APPEND));
		        outputStreamWriter.write(data);
		        outputStreamWriter.close();
		    }
		    catch (IOException e) {
		        Log.e("Exception", "File write failed: " + e.toString());
		    } 
		}


		private String readFromFile() {

		    String ret = "";

		    try {
		        InputStream inputStream = openFileInput("config.txt");

		        if ( inputStream != null ) {
		            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		            String receiveString = "";
		            StringBuilder stringBuilder = new StringBuilder();

		            while ( (receiveString = bufferedReader.readLine()) != null ) {
		            	profileName.add(receiveString);
		                
		            }

		            inputStream.close();
		            ret = stringBuilder.toString();
		        }
		    }
		    catch (FileNotFoundException e) {
		        Log.e("login activity", "File not found: " + e.toString());
		    } catch (IOException e) {
		        Log.e("login activity", "Can not read file: " + e.toString());
		    }

		    return ret;
		}
	 
		 }

