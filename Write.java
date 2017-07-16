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
		Write w = new Write();
	}
	
	public Write() throws Exception{
		String[] dayNumberToString={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		String[] timeNumberToString={"","1-2","2-3","3-4","4-5","5-6","6-7","7-8","8-9","9-10","10-11"};
		
		File f=new File("generated_WPEA_schedule.xls"); //"/Users/geyangqin/Desktop/""
		WritableWorkbook myExcel=Workbook.createWorkbook(f);
		WritableSheet mySheet=myExcel.createSheet("mySheet",0);
		
		
		WritableFont cellFont1=new WritableFont(WritableFont.COURIER,30,WritableFont.BOLD);
		cellFont1.setColour(Colour.RED);
		WritableCellFormat cellFormat1=new WritableCellFormat(cellFont1);
		//cellFormat1.setBackground(Colour.YELLOW);
		cellFormat1.setBorder(Border.ALL,BorderLineStyle.THICK);
		cellFormat1.setAlignment(Alignment.CENTRE);
		cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat1.setWrap(true);
		
		WritableFont cellFont2=new WritableFont(WritableFont.COURIER,20, WritableFont.BOLD);
		cellFont2.setColour(Colour.BLACK);
		WritableCellFormat cellFormat2=new WritableCellFormat(cellFont2);
		//cellFormat2.setBackground(Colour.YELLOW);
		cellFormat2.setBorder(Border.ALL,BorderLineStyle.THIN);
		cellFormat2.setAlignment(Alignment.CENTRE);
		cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat2.setWrap(true);
		
		mySheet.setColumnView(0,17);
		mySheet.setColumnView(8,17);
		for (int i=1;i<8;i++) 
			mySheet.setColumnView(i,34);
		
		mySheet.setRowView(0,1200); 
		for (int i=1;i<=10;i++)
			mySheet.setRowView(i,4520);
		
		for(int i=0; i<=8;i++)
			for(int j=0; j<=10; j++)
				mySheet.addCell(new Label(i,j,"",cellFormat2));

		for (int i=0;i<7;i++)
			mySheet.addCell(new Label(i+1,0,dayNumberToString[i],cellFormat1));
		
		for (int i=1;i<=10;i++) {
			mySheet.addCell(new Label(0,i,timeNumberToString[i],cellFormat1));
			mySheet.addCell(new Label(8,i,timeNumberToString[i],cellFormat1));
		}

		ScheduleMaker s = new ScheduleMaker();
		
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
