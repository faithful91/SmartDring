package schedule;

import java.util.List;

import com.example.smartdring.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListScheduleAdapter extends ArrayAdapter<Schedule> {
	private final Context context;
	private final List<Schedule> values;

	public ListScheduleAdapter(Context context, List<Schedule> schedules) {
		super(context, R.layout.list_schedule, schedules);
		this.context = context;
		this.values = schedules;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String D0;
		String D1;
		String D2;
		String D3;
		String D4;
		String D5;
		String D6;


		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_schedule, parent, false);
	//	TextView profileId = (TextView) rowView.findViewById(R.id.profileId);
		TextView profileName = (TextView) rowView
				.findViewById(R.id.profileName);
		TextView profileHour = (TextView) rowView
				.findViewById(R.id.profileHour);
		TextView profileMinute = (TextView) rowView
				.findViewById(R.id.profileMinute);
		TextView state = (TextView) rowView.findViewById(R.id.profileState);
		TextView day0 = (TextView) rowView.findViewById(R.id.day0);
		TextView day1 = (TextView) rowView.findViewById(R.id.day1);
		TextView day2 = (TextView) rowView.findViewById(R.id.day2);
		TextView day3 = (TextView) rowView.findViewById(R.id.day3);
		TextView day4 = (TextView) rowView.findViewById(R.id.day4);
		TextView day5 = (TextView) rowView.findViewById(R.id.day5);
		TextView day6 = (TextView) rowView.findViewById(R.id.day6);
		//TextView idEv = (TextView) rowView.findViewById(R.id.IdEv);
	//	profileId.setText("" + values.get(position).getId());
		profileName.setText(values.get(position).getProfileName());
		profileHour.setText("" + values.get(position).getProfileHour());
		profileMinute.setText("" + values.get(position).getProfileMinute());
		state.setText(values.get(position).getState());
		D0="" + values.get(position).getDay0();
		D1="" + values.get(position).getDay1();
		D2="" + values.get(position).getDay2();
		D3="" + values.get(position).getDay3();
		D4="" + values.get(position).getDay4();
		D5="" + values.get(position).getDay5();
		D6="" + values.get(position).getDay6();

		
		if(D0.equals("true"))
		day0.setText("On");
		else 		day0.setText("Off");
		
		if(D1.equals("true"))
			day1.setText("On");
			else 		day1.setText("Off");
		
		if(D2.equals("true"))
			day2.setText("On");
			else 		day2.setText("Off");
		
		if(D3.equals("true"))
			day3.setText("On");
			else 		day3.setText("Off");
		
		if(D4.equals("true"))
			day4.setText("On");
			else 		day4.setText("Off");
		
		if(D5.equals("true"))
			day5.setText("On");
			else 		day5.setText("Off");
		
		if(D6.equals("true"))
			day6.setText("On");
			else 		day6.setText("Off");

	//	idEv.setText("" + values.get(position).getIdEv());
		day3.setPadding(2, 2, 2, 2);
		return rowView;
	}
}