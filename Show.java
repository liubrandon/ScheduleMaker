
import java.util.ArrayList;

public class Show {
	String showName,dj1,dj2,dj3;
	Proctor proctor;

	// three digit integer, first digit 1 to 7 for Sunday to Saturday, 
	// next two digits for starting time, e.g. 407 means Wednesday 7 to 8 PM
	int dayTime = -111;

	//array with time codes for the slots listed available in their application
	ArrayList<Integer> availableTimes = new ArrayList<Integer>();

	public Show(String showName, String dj1, String dj2, String dj3, ArrayList<Integer> availableTimes) {
		this.showName=showName;
		this.dj1=dj1;
		this.dj2=dj2;
		this.dj3=dj3;
		this.availableTimes=availableTimes;
	}

	public Show(String showName, String dj1, String dj2, String dj3, Proctor proctor, int dayTime) {
		this.showName=showName;
		this.dj1=dj1;
		this.dj2=dj2;
		this.dj3=dj3;
		this.proctor=proctor;
		this.dayTime=dayTime;
	}
	
	public String getShowName() {
		return showName;
	}
	
	public String getDJ1() {
		return dj1;
	}
	
	public String getDJ2() {
		return dj2;
	}
	
	public String getDJ3() {
		return dj3;
	}
	
	public Proctor getProctor() {
		return proctor;
	}
	
	public int getDayTime()
	{
		return dayTime;
	}

	public int getDay() {
		return dayTime/100;
	}
	
	public int getTime() {
		return dayTime%100;
	}

	public void setDayTime(int d)
	{
		dayTime = d;
	}

	public ArrayList<Integer> getAvailableTimes()
	{
		return availableTimes;
	}
	/*public String toString()
	{
		return showName + "\n\n" + dj1 + "\n" + dj2 + "\n" + dj3 + "\n\n" + "(" + proctor.getName() + ")" + "\n\nTime code: " + dayTime;
	}*/
	/*public String timeCodeToString(int code)
	{
		
	}*/

	public String toString()
	{
		String times = "";
		for(int a : availableTimes)
		{
			times = times + a + ", ";
		}
		return showName + "\n\n" + dj1 + "\n" + dj2 + "\n" + dj3 + "\n\nAvailable times: " + times;
	}
	public String toString1()
	{
		String times = "";
		for(int a : availableTimes)
		{
			times = times + a + ", ";
		}
		return showName + "\n\n" + dj1 + "\n" + dj2 + "\n" + dj3 + "\n\nTime code: " + dayTime;
	}

	public String toAbbrString() {
        String temp=proctor.getName();
        if (!temp.equals("*"))
            temp = temp.substring(0,temp.indexOf(" ")+2)+".";
        return showName + "\n\n" + dj1 + "\n" + dj2 + "\n" + dj3 + "\n\n" + "(" + temp + ")";
    }
}
