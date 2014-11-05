package com.example.smartdring;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityAdapter extends ArrayAdapter<SoundProfile> {
  private final Context context;
  private final List<SoundProfile> profileList;

  public MainActivityAdapter(Context context, List<SoundProfile> profileList) {
    super(context, R.layout.activity_main, profileList);
    this.context = context;
    this.profileList = profileList;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {   

    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.activity_main, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.label);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    textView.setText(profileList.get(position).getName());
    // Change the icon for Windows and iPhone
    String s = profileList.get(position).getName();
    if (s.startsWith("Windows7") || s.startsWith("iPhone")
        || s.startsWith("Solaris")) {
      imageView.setImageResource(R.drawable.ic_launcher);
    } else {
      imageView.setImageResource(R.drawable.ic_launcher);
    }

    return rowView;
  }
  
  
} 