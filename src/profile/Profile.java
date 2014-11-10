package profile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;

public class Profile {

	private String Name;
	private String SharedPref;
	private List<String> profileName = new ArrayList<String>();
	private List<String> profileNameDel = new ArrayList<String>();
	public List<Profile> Profiles = new ArrayList<Profile>();
	public Context fileContext;

	public Profile(String Name, String SharedPref) {
		this.Name = Name;
		this.SharedPref = SharedPref;
	}

	public Profile(Context fileContext) {
		this.fileContext = fileContext;
	}

	public Profile() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return Name;
	}

	public String getSharedPref() {
		return SharedPref;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setSharedPref(String SharedPref) {
		this.SharedPref = SharedPref;
	}

	public List<Profile> getProfilesNames() {
		return Profiles;
	}

	public void loadProfiles() {

		writeToFile("Normale", 0);

		writeToFile(System.getProperty("line.separator"), 0);

		readFromFile();

		for (int i = 0; i < profileName.size(); i++)

		{
			Profile g = new Profile(profileName.get(i),
					profileName.get(i) + "sp");
			Profiles.add(g);
		}
	}

	// Ecrire sur le fichier pour ajouter un profil
	private void writeToFile(String data, int mode) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					fileContext.getApplicationContext().openFileOutput(
							"config.txt", mode));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	// Lire à partir d'un fichier .txt combien de profile existe
	private void readFromFile() {
		InputStream inputStream = null;
		try {
			inputStream = fileContext.getApplicationContext().openFileInput(
					"config.txt");
		} catch (FileNotFoundException e) {
			Log.e("TAG", "File write failed: " + e.toString());
		}
		if (inputStream != null) {
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String receiveString = "";
			try {
				while ((receiveString = bufferedReader.readLine()) != null) {
					profileName.add(receiveString);
				}
			} catch (IOException e) {
				Log.e("TAG", "File write failed: " + e.toString());
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Delete a profile
	public void delete(String data) {
		for (int i = 0; i < profileName.size(); i++) {
			if (!profileName.get(i).equals(data))
				profileNameDel.add(profileName.get(i));
		}
		writeToFile("", Context.MODE_PRIVATE);

		for (int j = 0; j < profileNameDel.size(); j++) {
			writeToFile(profileNameDel.get(j), Context.MODE_APPEND);
			writeToFile(System.getProperty("line.separator"),
					Context.MODE_APPEND);
		}
	}
}