package com.example.smartdring;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ScheduleList extends ListActivity implements OnClickListener {
    /** Called when the activity is first created. */
	
	DBAdapter db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        getListView().setOnCreateContextMenuListener(this);
        ((Button)findViewById(android.R.id.button1)).setOnClickListener(this);
        db = new DBAdapter(this);
        db.open();
        DataBind();
    }
    
    @Override // Cr�ation du menu principal
    public boolean onCreateOptionsMenu(Menu menu) {    	
    	menu.add(0,100,0,"Tout effacer");
    	return true;
    }
    
    @Override // Selection d'un item du menu
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case 100: 
    		db.Truncate();
    		DataBind();
    		break;    	
    	}
    	return true;
    }
    
	@Override // Selection d'un item de la liste
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor = (Cursor)l.getAdapter().getItem(position);
		String titre  = cursor.getString(cursor.getColumnIndex("titre"));
		Toast.makeText(this,"Item id "+id+" : "+titre, Toast.LENGTH_SHORT).show();
		super.onListItemClick(l, v, position, id);
	}
	
	@Override // Creation du menu contextuel
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Action");
		menu.add(0,100,0,"Supprimer");
		menu.add(0,200,1,"Editer");
	}
    
	@Override // Selection d'un item du menu contextuel
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()){
		case 100:
			db.supprimerProduit(info.id);
			DataBind();
			break;
		case 200:
			Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();				
			break;			
		}
		return true;
	}
	
    @Override
    protected void onDestroy() {
    	db.close();
    	super.onDestroy();
    }
    
    public void DataBind(){
    	Cursor c = db.recupererLaListeDesProduits();
    	startManagingCursor(c);
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
    	R.layout.list_schedule,c,new String[]{"titre","description","codebarre"},
    	new int[]{R.id.textTitre,R.id.TextDescription,R.id.TextCodeBarre});
    	setListAdapter(adapter);
    }

	@Override
	public void onClick(View v) {
		long num = SystemClock.currentThreadTimeMillis();
		db.insererUnProduit(""+num, "Produit n�"+num, "Nouveau produit"+num);
		DataBind();
	}
        
}