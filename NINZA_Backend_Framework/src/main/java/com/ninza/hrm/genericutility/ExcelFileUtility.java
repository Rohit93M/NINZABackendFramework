package com.ninza.hrm.genericutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ninza.hrm.constants.IPathConstants;



public class ExcelFileUtility {
	
	public int getRowCount(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstants.excelFilePath);
		int rowCount = WorkbookFactory.create(fis).getSheet(sheetName).getLastRowNum();
		return rowCount;
		
	}
	
	public String getStringDataFromExcel(String sheetName, int rowNum, int CellNum) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstants.excelFilePath);
		String data=WorkbookFactory.create(fis).getSheet(sheetName).getRow(rowNum).getCell(CellNum).getStringCellValue();
		return data;
	}
	
	public long getNumericDataFromExcel(String sheetName, int rowNum, int CellNum) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstants.excelFilePath);
		long data =(long)WorkbookFactory.create(fis).getSheet(sheetName).getRow(rowNum).getCell(CellNum).getNumericCellValue();
		return data;
	}
	
	public void setDataIntoExcel(String sheetName, int rowNum, int cellNum, String data) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstants.excelFilePath);
		Workbook workbook = WorkbookFactory.create(fis);
		workbook.getSheet(sheetName).getRow(rowNum).createCell(cellNum).setCellValue(data);
		FileOutputStream fos = new FileOutputStream(IPathConstants.excelFilePath);
		workbook.write(fos);
		workbook.close();
	}
}
