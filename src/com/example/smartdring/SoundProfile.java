package com.example.smartdring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class SoundProfile{
private String Name;
private String SharedPref;
private List<String> profileName = new ArrayList<String>();
public List<SoundProfile> Profiles = new ArrayList<SoundProfile>();
Context fileContext;


public SoundProfile (String Name,String SharedPref ){this.Name=Name;this.SharedPref=SharedPref;}
 
public SoundProfile(Context fileContext) {
    this.fileContext = fileContext;
}

public String getName(){return Name;}
public String getSharedPref(){return SharedPref;}

public void setName(String Name){this.Name=Name;}
public void setSharedPref(String SharedPref){this.SharedPref=SharedPref;}
public void loadProfiles(){
    Log.e("TAG", "avant readFile write failed: " );

    Log.e("TAG", "aprés readFile write failed: " );

for (int i=0;i<profileName.size();i++)
     


{
     	SoundProfile g = new SoundProfile(profileName.get(i),profileName.get(i)+"sp");
         Profiles.add(g);

     }
}
//Lire à partir d'un fichier .txt combien de profile existe
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

}