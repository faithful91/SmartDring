package schedules;
public class Schedule {

    private int id;
    private String profileName;
    private int profileHour;
    private int profileMinute;
    private String state;
    private boolean day0;
    private boolean day1;
    private boolean day2;
    private boolean day3;
    private boolean day4;
    private boolean day5;
    private boolean day6;
    private String idEv;
  
    public Schedule(int id,String profileName, int profileHour, int profileMinute, 
    		String state,boolean day0, boolean day1, boolean day2, boolean day3, boolean day4, boolean day5,
                    boolean day6) 
    {
        this.id = id;
        this.profileName = profileName;
        this.profileHour = profileHour;
        this.profileMinute = profileMinute;
        this.state=state;
        this.day0 = day0;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
     }

   
    public Schedule() {
		// TODO Auto-generated constructor stub
	}


	public int getId()
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getProfileName() 
    {
        return profileName;
    }
    
    public void setProfileName(String profileName) 
    {
        this.profileName = profileName;
    }
    
    public String getState() 
    {
        return state;
    }
    
    public void setState(String state) 
    {
        this.state = state;
    }

    public int getProfileHour()
    {
        return profileHour;
    }
 
    public void setProfileHour(int profileHour) 
    {
        this.profileHour = profileHour;
    }

    public int getProfileMinute() 
    {
        return profileMinute;
    }

    public void setProfileMinute(int profileMinute) 
    {
        this.profileMinute = profileMinute;
    }
 
    public boolean getDay0()
    {
        return day0;
    }

    public void setDay0(boolean day0) 
    {
        this.day0 = day0;
    }

    public boolean getDay1() 
    {
        return day1;
    }

    
    public void setDay1(boolean day1)
    {
        this.day1 = day1;
    }

   
    public boolean getDay2()
    {
        return day2;
    }

   
    public void setDay2(boolean day2) 
    {
        this.day2 = day2;
    }

    
    public boolean getDay3() 
    {
        return day3;
    }

    
    public void setDay3(boolean day3) 
    {
        this.day3 = day3;
    }

    
    public boolean getDay4() 
    {
        return day4;
    }

   
    public void setDay4(boolean day4) 
    {
        this.day4 = day4;
    }

    
    public boolean getDay5() 
    {
        return day5;
    }

    
    public void setDay5(boolean day5) 
    {
        this.day5 = day5;
    }

   
    public boolean getDay6()
    {
        return day6;
    }

    
    public void setDay6(boolean day6) 
    {
        this.day6 = day6;
    }

    public String getIdEv() 
    {
    	return idEv;
    }
    
    
    public void setIdEv(String idEv) 
    {
        this.idEv = idEv;
    }
   


  
    
      @Override
	public boolean equals(Object o) {
        boolean result = false;
        
        if (o instanceof Schedule) {
            Schedule compare = (Schedule) o;
            result = (this.id > 0 && this.id == compare.getId());
        }
        
        return result;
    }

   
    public boolean equalsSansId(Schedule s) {
        boolean result = false;
        
        result = (
                this.getDay0() == s.getDay0() &&
                this.getDay1() == s.getDay1() &&
                this.getDay2() == s.getDay2() &&
                this.getDay3() == s.getDay3() &&
                this.getDay4() == s.getDay4() &&
                this.getDay5() == s.getDay5() &&
                this.getDay6() == s.getDay6() &&
                this.getProfileHour() == s.getProfileHour() &&
                this.getProfileMinute() == s.getProfileMinute() 
                );
    
        return result;
    }

}