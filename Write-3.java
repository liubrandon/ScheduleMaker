import java.io.*;
import java.util.ArrayList;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.*;

public class Write {

	public static void main(String[] args) throws Exception{
		if(args.length < 3)
		{
			System.out.println("Error: please enter the 3 arguments, 'Previous_term_schedule.xls', 'Current_term_applications.xls', and 'output_filename.xls'");
			System.exit(0);
		}
		Write w = new Write(args[0], args[1], args[2]);
	}
	
	public Write(String p, String c, String o) throws Exception{
        // Convert the day time three digit code into actual strings that would be written into the excel
		String[] dayNumberToString={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		String[] timeNumberToString={"","1-2","2-3","3-4","4-5","5-6","6-7","7-8","8-9","9-10","10-11"};
		
        // create the excel file with a sheet called mySheet
		File f=new File(o); 
		WritableWorkbook myExcel=Workbook.createWorkbook(f);
		WritableSheet mySheet=myExcel.createSheet("mySheet",0);
        
        // A font format used for those cells that display the border information, days and time
        WritableFont cellFont1=new WritableFont(WritableFont.COURIER,30,WritableFont.BOLD);
		cellFont1.setColour(Colour.RED);
		WritableCellFormat cellFormat1=new WritableCellFormat(cellFont1);
		cellFormat1.setBorder(Border.ALL,BorderLineStyle.THICK);
		cellFormat1.setAlignment(Alignment.CENTRE);
		cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat1.setWrap(true);
		
        // Another font format used for those cells that display information of shows
		WritableFont cellFont2=new WritableFont(WritableFont.COURIER,20, WritableFont.BOLD);
		cellFont2.setColour(Colour.BLACK);
		WritableCellFormat cellFormat2=new WritableCellFormat(cellFont2);
		cellFormat2.setBorder(Border.ALL,BorderLineStyle.THIN);
		cellFormat2.setAlignment(Alignment.CENTRE);
		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat2.setWrap(true);
        
        // Create the width for the 0th and 8th column, the time columns, and set the width for the rest of the columns too but wider
		mySheet.setColumnView(0,17);
		mySheet.setColumnView(8,17);
		for (int i=1;i<8;i++) 
			mySheet.setColumnView(i,34);
		
        // Create the height for the 0th row, the day row, and set the height for the rest of the rows below but taller
		mySheet.setRowView(0,1200); 
		for (int i=1;i<=10;i++)
			mySheet.setRowView(i,4520);
		
        // Initialize all the original cells to empty. Add the basic day and time cells.
		for(int i=0; i<=8;i++)
			for(int j=0; j<=10; j++)
				mySheet.addCell(new Label(i,j,"",cellFormat2));
		for (int i=0;i<7;i++)
			mySheet.addCell(new Label(i+1,0,dayNumberToString[i],cellFormat1));
		for (int i=1;i<=10;i++) {
			mySheet.addCell(new Label(0,i,timeNumberToString[i],cellFormat1));
			mySheet.addCell(new Label(8,i,timeNumberToString[i],cellFormat1));
		}
        
        // Builds the schedule with the ScheduleMaker class
		ScheduleMaker s = new ScheduleMaker(p, c);
        
        // traverse the showList of shows and add the information of those shows to the excel sheet.
		ArrayList<Show> showList = s.getPrevShows();
		for (int i=0;i<showList.size();i++) {
			Show tempShow=showList.get(i);
			Label tempLabel=new Label(tempShow.getDay(),tempShow.getTime(),tempShow.toAbbrString(),cellFormat2);
			mySheet.addCell(tempLabel);
		}
        
		myExcel.write();
		myExcel.close();
		System.out.println("Done");
	}
}
