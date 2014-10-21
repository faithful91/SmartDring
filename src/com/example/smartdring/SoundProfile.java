package com.example.smartdring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class SoundProfile{
private String Name;
private String SharedPref;
private List<String> profileName = new ArrayList<String>();
private List<String> profileNameDel = new ArrayList<String>();

public List<SoundProfile> Profiles = new ArrayList<SoundProfile>();
Context fileContext;
String path;
public SoundProfile (String Name,String SharedPref ){this.Name=Name;this.SharedPref=SharedPref;} 
public SoundProfile(Context fileContext) {this.fileContext=fileContext;
}
public String getName(){return Name;}
public String getSharedPref(){return SharedPref;}

public void setName(String Name){this.Name=Name;}
public void setSharedPref(String SharedPref){this.SharedPref=SharedPref;}
public void loadProfiles(){

	



	





	readFromFile();

for (int i=0;i<profileName.size();i++)
    
{
     	SoundProfile g = new SoundProfile(profileName.get(i),profileName.get(i)+"sp");
         Profiles.add(g);
     }
}

//Ecrire sur le fichier pour ajouter un profil
private void writeToFile(String data) {
    try {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileContext.getApplicationContext().openFileOutput("config.txt", Context.MODE_APPEND));
        outputStreamWriter.write(data);
        outputStreamWriter.close();
    }
    catch (IOException e) {
        Log.e("Exception", "File write failed: " + e.toString());
    } 
}
//Lire à partir d'un fichier .txt combien de profile existe

private void readFromFile() {
 InputStream inputStream = null;
		try {
			inputStream = fileContext.getApplicationContext().openFileInput("config.txt");
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e("TAG", "File write failed: " + e.toString());		}
 if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            try {
				while ( (receiveString = bufferedReader.readLine()) != null ) {
					profileName.add(receiveString);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("TAG", "File write failed: " + e.toString());			}

            try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    
   

  
}



public void delete(String data) {
	for (int i=0;i<profileName.size();i++)
	    
	{
if (!profileName.get(i).equals(data)) profileNameDel.add(profileName.get(i));
			}writeToFileDel("");

	for (int j=0;j<profileNameDel.size();j++)
	{	
	writeToFile(profileNameDel.get(j));
	writeToFile(	       System.getProperty("line.separator"));}

	Log.e("TAG", "tayarto");
	

	
	        }


public void removeLine(final File file, final int lineIndex) throws IOException{
    final List<String> lines = new LinkedList<String>();
    
    final Scanner reader = new Scanner(fileContext.getApplicationContext().openFileInput("config.txt"));
    while(reader.hasNextLine())
        lines.add(reader.nextLine());
    reader.close();
    assert lineIndex >= 0 && lineIndex <= lines.size() - 1;
    lines.remove(lineIndex);
    final BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
    for(final String line : lines)
        writer.write(line);
    writer.flush();
    writer.close();
}
private void writeToFileDel(String data) {
    try {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileContext.getApplicationContext().openFileOutput("config.txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(data);
        outputStreamWriter.close();
    }
    catch (IOException e) {
        Log.e("Exception", "File write failed: " + e.toString());
    } 
}
}