/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelExtraction;

import com.mycompany.zebtooljavafx.Model.MonthInYear;
import com.mycompany.zebtooljavafx.Model.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 *
 * @author Holger-Desktop
 */
public class ExtractZebInfo {
    final static int projectStartRow = 4;
    final static int totalHoursCol = 4;
    final static int deptCol = 1;
    final static int projNrCol = 2;
    final static int projNameCol = 3;       
    
    private static List<String> otherThanProjects = new ArrayList<>();
    
    public static ZebInfo extractZebInfo(File f) throws FileNotFoundException, IOException {
        FileInputStream inputStream = new FileInputStream(f);         
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        String name = extractName(sheet);
        MonthInYear monthInYear = extractMonthYear(f);
        List<Project> projectsInFile = extractAllProjectsFromSheet(sheet);
        List<Float> hoursPerProjectInMonth = extractHoursPerProject(sheet);
        List<Float> hoursForOther = extractHoursForOther(sheet);
        boolean isHiwi = f.getName().endsWith("Hiwi");
        
        return new ZebInfo(
                name.split(" ")[0], 
                name.split(" ")[1], 
                projectsInFile, 
                hoursPerProjectInMonth,
                monthInYear,
                hoursForOther,
                isHiwi);
    }

    public static List<String> getOtherThanProjects() {
        return otherThanProjects;
    }
    
    private static int getOtherThanProjectsBeginningRow(Sheet sheet) {
        int pos = projectStartRow;
        while(!getStringFromCell(sheet, pos, projNameCol).isEmpty()) {
            pos++;
        }
        while(getStringFromCell(sheet, pos, projNameCol).isEmpty()) {
            pos++;
        }
        return pos;
    }
            
    private static List<Float> extractHoursForOther(Sheet sheet) {        
        
        int otherThanProjectsBeginningRow = getOtherThanProjectsBeginningRow(sheet);
        int currentRow = otherThanProjectsBeginningRow;
        
        boolean addToOtherProjects = false;
        if(otherThanProjects.size() == 0) {
            otherThanProjects = new ArrayList<>();
            addToOtherProjects = true;
        }
        
        List<Float> created = new ArrayList<>();
        
        while(!getStringFromCell(sheet, currentRow, projNameCol).equals("Istzeit")) {
            created.add(new Float(getNumberFromCell(sheet, currentRow, totalHoursCol)));
            if(addToOtherProjects)
                otherThanProjects.add(getStringFromCell(sheet, currentRow, projNameCol));
            
            currentRow++;
        }
        
        return created;
    }
    
    private static List<Float> extractHoursPerProject(Sheet sheet) {
        List<Float> created = new ArrayList<>();
        
        for(int currentRow = projectStartRow; 
                !getStringFromCell(sheet, currentRow, projNameCol).isEmpty(); //while the name cell isnt empty
                ++currentRow) {
            created.add(new Float(getNumberFromCell(sheet, currentRow, totalHoursCol)));
        }        
        
        return created;
    }    
    
    private static List<Project> extractAllProjectsFromSheet(Sheet sheet) {
        
        int currentRow = projectStartRow;
        
        List created = new ArrayList();
        
        while(extractProjFromRow(sheet, currentRow) != null) {
            created.add(extractProjFromRow(sheet, currentRow));
            currentRow++;
        }
        
        return created;
    }
    
    private static Project extractProjFromRow(Sheet sheet, int row) {
        
        
        String deptString = getStringFromCell(sheet, row, deptCol);
        int projNumber = (int) getNumberFromCell(sheet, row, projNrCol);
        String projName = getStringFromCell(sheet, row, projNameCol);
        
        if(deptString.isEmpty() || projName.isEmpty()) {
            return null;
        }
        
        return new Project(projName, deptString, projNumber);        
    }
     
    private static MonthInYear extractMonthYear(File f) {
        String fileName = f.getName();
        String[] seperated = fileName.split("-");
        String monthString = seperated[1];
        int month = Integer.valueOf(monthString);
        String yearString = seperated[2];
        int year = Integer.valueOf(yearString);
        return new MonthInYear(month, year);        
    }
    
    private static String extractName(Sheet sheet) {
        final int nameRow = 1;
        final int nameCol = 2;
        return getStringFromCell(sheet, nameRow, nameCol);
    }
    
    private static String getStringFromCell(Sheet sheet, int row, int col) {
        Row r = sheet.getRow(row);
        Cell c = r.getCell(col);
        return c.getStringCellValue();
    }

    private static double getNumberFromCell(Sheet sheet, int row, int col) {
        Row r = sheet.getRow(row);
        Cell c = r.getCell(col);
        return c.getNumericCellValue();
    }


    

}
