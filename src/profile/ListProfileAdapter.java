package profile;

import java.util.List;

import com.example.smartdring.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListProfileAdapter extends ArrayAdapter<Profile> {
	private final Context context;
	private final List<Profile> profileList;
	SharedPreferences pref = null;

	public ListProfileAdapter(Context context, List<Profile> profileList) {
		super(context, R.layout.activity_main, profileList);
		this.context = context;
		this.profileList = profileList;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
String profileName;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_main, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		profileName=profileList.get(position).getName();
		
		textView.setText(profileName);
		
		if (profileName.equals(getActifProfile()))
			textView.setTextColor(Color.CYAN);
		// Change the icon for Windows and iPhone
	
		
		
		
		/*	if (s.startsWith("Windows7") || s.startsWith("iPhone")
				|| s.startsWith("Solaris")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageResource(R.drawable.ic_launcher);
		}
*/
		return rowView;
	}
	public String getActifProfile() {
		String profile="";
		pref = context.getApplicationContext().getSharedPreferences("ActifProfile", 0);
		Editor editor = pref.edit();
				
			editor.commit();
			profile = pref.getString("profile", null);
			return profile;
	
		}

}