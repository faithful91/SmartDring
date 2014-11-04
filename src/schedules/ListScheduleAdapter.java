package schedules;
import java.util.List;

import com.example.smartdring.R;
import com.example.smartdring.R.id;
import com.example.smartdring.R.layout;


import android.content.Context;
import android.util.Log;
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

    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_schedule, parent, false);
    Log.e("TAG", "1");	
    TextView profileId = (TextView) rowView.findViewById(R.id.profileId);
    TextView profileName = (TextView) rowView.findViewById(R.id.profileName);
    TextView profileHour= (TextView) rowView.findViewById(R.id.profileHour);
    TextView profileMinute = (TextView) rowView.findViewById(R.id.profileMinute);
    TextView state = (TextView) rowView.findViewById(R.id.profileState);
    Log.e("TAG", "2");	
    TextView day0 = (TextView) rowView.findViewById(R.id.day0);
    TextView day1 = (TextView) rowView.findViewById(R.id.day1);
    TextView day2 = (TextView) rowView.findViewById(R.id.day2);
    TextView day3 = (TextView) rowView.findViewById(R.id.day3);
    TextView day4 = (TextView) rowView.findViewById(R.id.day4);
    TextView day5 = (TextView) rowView.findViewById(R.id.day5);
    TextView day6 = (TextView) rowView.findViewById(R.id.day6);

    Log.e("TAG", "2;5");	

    profileId.setText(""+values.get(position).getId());
    Log.e("TAG", "2,6");	

    profileName.setText(values.get(position).getProfileName());
    Log.e("TAG", "2,7");	

    profileHour.setText(""+values.get(position).getProfileHour());

    profileMinute.setText(""+values.get(position).getProfileMinute());
    state.setText(values.get(position).getState());
    Log.e("TAG", "3");	

    day0.setText(""+values.get(position).getDay0());
    day1.setText(""+values.get(position).getDay1());
    day2.setText(""+values.get(position).getDay2());
    day3.setText(""+values.get(position).getDay3());
    day4.setText(""+values.get(position).getDay4());
    day5.setText(""+values.get(position).getDay5());
    day6.setText(""+values.get(position).getDay6());





   
    return rowView;
  }
  
  
} 