package com.example.smartdring;

public class SoundProfile {
private String Name;
private String SharedPref;


public SoundProfile (String Name,String SharedPref ){this.Name=Name;this.SharedPref=SharedPref;}
 
public String getName(){return Name;}
public String getSharedPref(){return SharedPref;}

public void setName(String Name){this.Name=Name;}
public void setSharedPref(String SharedPref){this.SharedPref=SharedPref;}

}