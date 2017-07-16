import java.io.*;
import java.util.ArrayList;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.*;
import jxl.read.biff.BiffException;
import java.util.Scanner;
import java.util.Arrays;

public class ScheduleMaker
{
	ArrayList<Show> prevTermShows = new ArrayList<Show>();
	ArrayList<Show> currShows = new ArrayList<Show>();
	ArrayList<Show> scheduledShows = new ArrayList<Show>();
	ArrayList<Show> unscheduledShows = new ArrayList<Show>();

	public static void main(String[] args) throws IOException, BiffException
	{
		ScheduleMaker s = new ScheduleMaker();
	}

	public ScheduleMaker() throws IOException, BiffException
	{
		retrieveData();

		for(int i = currShows.size()-1; i > -1; i--)
		{
			Show show = currShows.get(i);

			int showTime = isContinuingShow(show);
			if(showTime > 0) 
			{
				show.setDayTime(showTime);
				scheduledShows.add(show);
			}
			else
			{
				ArrayList<Integer> availableTimes = show.getAvailableTimes();
				for(int a = 0; a < availableTimes.size(); a++)
				{
					int code = availableTimes.get(a);
					Show s = showAt(code);
					if(s == null)
					{ 
						show.setDayTime(code);
						scheduledShows.add(show);
					}
				}
			}
			if(show.getDayTime() < 0)
			{
				unscheduledShows.add(show);
			}
		}

		System.out.println("\n\n ------- unscheduled shows ------- \n\n");
		for(int i = 0; i < unscheduledShows.size(); i++)
		{
			System.out.println(unscheduledShows.get(i)+"\n ---------- \n");
		}
	}

	public Show showAt(int code)
	{
		for(Show s : currShows)
		{
			if(s.getDayTime() == code)
				return s;
		}
		return null;
	}

	//returns time of last term's slot of Show "show"
	public int isContinuingShow(Show checkShow)
	{
		for(int i = 0; i < prevTermShows.size(); i++)
		{
			Show currShow = prevTermShows.get(i);
			if(checkShow.getShowName().equals(checkShow.getShowName()))
			{
				if((checkShow.getDJ1().equals(currShow.getDJ1()) && checkShow.getDJ2().equals(currShow.getDJ2()) && checkShow.getDJ3().equals(currShow.getDJ3())) || (checkShow.getDJ1().equals(currShow.getDJ2()) && checkShow.getDJ2().equals(currShow.getDJ1()) && checkShow.getDJ3().equals(currShow.getDJ3())) )
				{
					if(checkShow.getAvailableTimes().contains(currShow.getDayTime()))
					{
						return currShow.getDayTime();
					}
				}
			}
		}
		return -1;
	}

	//reads data from previous term's schedule and current term's application, saves relevant data into prevShows and currShows ArrayLists
	public void retrieveData() throws IOException, BiffException
	{
		//retrieves show data from previous term's schedule .xls file 
		//stores it in ArrayList prevTermShows
		Workbook w = Workbook.getWorkbook(new File("Winter15-16.xls"));
		Sheet sheet = w.getSheet(0);
		for(int c = 1; c < 8; c++)
		{
			for(int r = 1; r < 11; r++)
			{
				String cellContents = sheet.getCell(c,r).getContents();
				if(!cellContents.equals(""))
				{
					Scanner scan = new Scanner(cellContents);
					String showName = scan.nextLine();
					scan.nextLine();
					String dj1 = scan.nextLine();
					String dj2 = scan.nextLine();
					String dj3 = scan.nextLine();
					int dayTime = (c * 100) + r;
					scan.nextLine();
					String proctorName = scan.nextLine();
					proctorName = proctorName.substring(1,proctorName.length()-1);
					Proctor proctor = new Proctor(proctorName);
					prevTermShows.add(new Show(showName, dj1, dj2, dj3, proctor, dayTime));
				}
			}
		}
		getCurrTermShows();

	}

	//retrieves current term show data from SurveyMonkey data export
	//stores in in arrayList currShows
	public void getCurrTermShows() throws IOException, BiffException
	{
		Workbook w = Workbook.getWorkbook(new File("SpringDJs_SurveyMonkey_Export.xls"));
		Sheet sheet = w.getSheet(0);
		for(int i = 2; i < sheet.getRows(); i++)
		{
			ArrayList<Integer> availableTimes = new ArrayList<Integer>();
			String showName = sheet.getCell(24,i).getContents();
			String dj1 = sheet.getCell(9, i).getContents() + " " + sheet.getCell(10, i).getContents();
			
			//DJ2 is currently stored in SurveyMonkey as "djName, phone-number" so some processing of the text is necessary
			String dj2RawText = sheet.getCell(29, i).getContents();
			String dj2 = dj2RawText;
			if(!dj2RawText.equals(""))
			{
				Scanner dj2Scan = new Scanner(dj2RawText);
				dj2 = (dj2Scan.next() + " " + dj2Scan.next()).replaceAll(",","");
			}

			for(int c = 41; c < 80; c++)
			{
				if(!sheet.getCell(c, i).getContents().equals(""))
				{
					String dayTimeLabel = sheet.getCell(c, 1).getContents();
					int time = Integer.parseInt(dayTimeLabel.substring(0,1));
					int day = returnDay(dayTimeLabel);
					availableTimes.add(day + time);
				}
			}
			/*for(int a: availableTimes)
			{ System.out.print(showName + ": " + a + ", " + "\n\n");}*/
			String dj3 = sheet.getCell(31, i).getContents();
			currShows.add(new Show(showName, dj1, dj2, dj3, availableTimes));
		}
	}

	//used to find what day the column header reads and returns the corresponding day code
	public int returnDay(String findDay)
	{
		if(findDay.indexOf("Sunday",13) > 0) return 100;
		else if(findDay.indexOf("Monday",13) > 0) return 200;
		else if(findDay.indexOf("Tuesday",13) > 0) return 300;
		else if(findDay.indexOf("Wednesday",13) > 0) return 400;
		else if(findDay.indexOf("Thursday",13) > 0) return 500;
		else if(findDay.indexOf("Friday",13) > 0) return 600;
		else if(findDay.indexOf("Saturday",13) > 0) return 700;
		return 0;
	}

	public ArrayList<Show> getScheduledShows()
	{ return scheduledShows; }

	public ArrayList<Show> getPrevShows()
	{ return prevTermShows; }
}