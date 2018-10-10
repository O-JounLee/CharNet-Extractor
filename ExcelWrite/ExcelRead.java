package ExcelWrite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {

	public static void readXLSFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	
	}
	
	public static void writeXLSFile() throws IOException {
		
		String excelFileName = "C:/Test.xls";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				HSSFCell cell = row.createCell(c);
				
				cell.setCellValue("Cell "+r+" "+c);
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static int[][] readXLSXFile() throws IOException
	{
		int[][] result = new int[8][8];
		InputStream ExcelFileToRead = new FileInputStream("Gravity_original.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFWorkbook test = new XSSFWorkbook(); 
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		int r=0;
		int c=0;
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
		
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
					
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
					result[r][c]=(int) cell.getNumericCellValue();
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
				c++;
			}
			System.out.println();
			c=0;
			r++;
		}
		
		return result;
	
	}
	
	public static double[][] readXLSXFile(String filename, int numRow) throws IOException
	{
		double[][] result = new double[numRow][numRow];
		InputStream ExcelFileToRead = new FileInputStream(filename);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFWorkbook test = new XSSFWorkbook(); 
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;
		
		Iterator rows = sheet.rowIterator();
		int r=0;
		int c=0;
		while (rows.hasNext())
		{
			if(r>numRow-1) break;
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
		
				if(c>numRow-1) break;
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
					
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
					result[r][c]=(double) cell.getNumericCellValue();
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
				c++;
			}
			System.out.println();
			c=0;
			r++;
		}
		
		return result;
	
	}
	
	public static void writeXLSXFile(String fileName, int[][] charNet) throws IOException {
		
		String excelFileName = fileName;//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < charNet[0].length; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < charNet[0].length; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellValue(charNet[r][c]);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
public static void writeXLSXFile(String fileName, double[][] charNet) throws IOException {
		
		String excelFileName = fileName;//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < charNet[0].length; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < charNet[0].length; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellValue(charNet[r][c]);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}


	public static void convertToEdge(String fileName, double[][]charNet, String[]charList) throws IOException{
		//id	media	media.type	type.label	audience.size
		//PO	d	     1	         NA	         0
		
		String excelFileName = fileName;//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		XSSFRow row0 = sheet.createRow(0);
		
		XSSFCell cell0 = row0.createCell(0);		
		cell0.setCellValue("from");
		XSSFCell cell1 = row0.createCell(1);		
		cell1.setCellValue("to");
		XSSFCell cell2 = row0.createCell(2);		
		cell2.setCellValue("weight");
		XSSFCell cell3 = row0.createCell(3);		
		cell3.setCellValue("type");

		int count=1;
		//iterating r number of rows
		for (int r=0;r < charNet[0].length; r++ )
		{

			//iterating c number of columns
			for (int c=0;c < charNet[0].length; c++ )
			{
				if(charNet[r][c]!=0 &&r!=c){
			    XSSFRow row = sheet.createRow(count);
				XSSFCell cellFrom = row.createCell(0);
				cellFrom.setCellValue(charList[r]);
				XSSFCell cellTo = row.createCell(1);
				cellTo.setCellValue(charList[c]);
				XSSFCell cellWeight = row.createCell(2);
				cellWeight.setCellValue(charNet[r][c]);
				XSSFCell cellType = row.createCell(3);
				cellType.setCellValue("mention");
				count = count+1;
				}

			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

	}
	
	public static void convertToNode(String fileName, double[][]charNet, String[]charList) throws IOException{
		String excelFileName = fileName;//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		
		XSSFRow row0 = sheet.createRow(0);
		XSSFCell cell0 = row0.createCell(0);		
		cell0.setCellValue("id");
		XSSFCell cell1 = row0.createCell(1);		
		cell1.setCellValue("media");
		XSSFCell cell2 = row0.createCell(2);		
		cell2.setCellValue("media.type");
		XSSFCell cell3 = row0.createCell(3);		
		cell3.setCellValue("type.label");
		XSSFCell cell4 = row0.createCell(4);		
		cell4.setCellValue("audience.size");
		
		for (int r=0;r < charNet[0].length; r++ )
		{

			double sum=0.0;
			//iterating c number of columns
			for (int c=0;c < charNet[0].length; c++ )
			{
				sum = sum+charNet[r][c];
				sum = sum+charNet[c][r];

			}
			sum=sum-charNet[r][r];

			    XSSFRow row = sheet.createRow(r+1);
			    
			    XSSFCell cellId = row.createCell(0);
			    cellId.setCellValue(charList[r]);
			    XSSFCell cellMedia=row.createCell(1);
			    cellMedia.setCellValue("a");
			    XSSFCell cellMtype = row.createCell(2);
			    cellMtype.setCellValue(1);
			    XSSFCell cellTlabel = row.createCell(3);
			    cellTlabel.setCellValue("NA");
			    XSSFCell cellAudi = row.createCell(4);
				cellAudi.setCellValue(sum);

		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static void write(String fileName, double[][]charNet1, double[][]charNet2, double ratio, String[]charList){
		
	}
	
	

//	public static void main(String[] args) throws IOException {
//		
//		//writeXLSFile();
//		//readXLSFile();
//		
//		//writeXLSXFile();
//		readXLSXFile();
//
//	}

}


