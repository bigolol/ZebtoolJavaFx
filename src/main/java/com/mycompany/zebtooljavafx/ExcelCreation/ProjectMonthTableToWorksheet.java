/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelCreation;

import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.Project;
import java.util.List;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Holger-Desktop
 */
public class ProjectMonthTableToWorksheet {
    private static int projIdCol = 0;
    private static int projDeptCol = 1;
    private static int projTitleCol = 2;
    private static int startX;
    private static int startY;
    
    public static void createTableIntoWorksheet(
            XSSFSheet sheet,
            ProjectMonthTable projectMonthTable,
            ExcelCreationData creationData,
            CellStyle style) {
        
        startX = creationData.getStartX();
        startY = creationData.getStartY();
        
        createHeadingRow(sheet, projectMonthTable);
                
        createProjectAndOtherDataCol(projectMonthTable, sheet);      
        
        //add sum per project and other
        
        for(int i = 0; i < projectMonthTable.getTotalsPerProject().size(); ++i) {
            sheet.getRow(startY + 1 + i).createCell(startX + projTitleCol + 1).setCellValue(projectMonthTable.getTotalsPerProject().get(i));
        }
        for(int i = 0; i < projectMonthTable.getTotalsPerOther().size(); ++i) {
            sheet.getRow(startY + 1 + i + projectMonthTable.getTotalsPerProject().size())
                            .createCell(startX + projTitleCol + 1)
                            .setCellValue(projectMonthTable.getTotalsPerOther().get(i));
        }
        
        List<List<Float>> valuesList = projectMonthTable.getValuesPerEmployee(); 
        final int employeeSumRowNumber = startY + 1 + projectMonthTable.getProjects().size() + projectMonthTable.getOtherThanProject().size();
        sheet.createRow(employeeSumRowNumber);
        for (int i = 0; i < valuesList.size(); i++) {
            
            List<Float> currentValues = valuesList.get(i);
            for (int j = 0; j < currentValues.size(); j++) {
                XSSFCell createdCell = sheet.getRow(startY + 1 + j).createCell(startX + projTitleCol + 2 + i);
                createdCell.setCellValue(currentValues.get(j));
                createdCell.setCellStyle(style);
            }            
            XSSFCell cell = sheet.getRow(employeeSumRowNumber).createCell(startX + projTitleCol + 2 + i);
            cell.setCellValue(
                    projectMonthTable.getOtherTotalsPerEmployee().get(i) + 
                    projectMonthTable.getAllProjectTotalsPerEmployee().get(i));
            cell.setCellStyle(style);
            sheet.autoSizeColumn(startX + projTitleCol + 2 + i);
        }
    }

    private static void createHeadingRow(XSSFSheet sheet, ProjectMonthTable projectMonthTable) {
        //create all heading cols:
        XSSFRow titleRow = sheet.createRow(startY);
        titleRow.createCell(startX + projIdCol).setCellValue("P-Nr");
        titleRow.createCell(startX + projDeptCol).setCellValue("GF");
        titleRow.createCell(startX + projTitleCol).setCellValue("Projektname");
        
        titleRow.createCell(startX + projTitleCol + 1).setCellValue("Summe");
        
        int currentYOffset = projTitleCol + 2;
        for(Employee e : projectMonthTable.getEmployees()) {
            titleRow.createCell(startY + currentYOffset).setCellValue(e.getFirstName() + " " + e.getLastName());
            currentYOffset++;
        }
    }

    private static void createProjectAndOtherDataCol(ProjectMonthTable projectMonthTable, XSSFSheet sheet) {
        int currentYOffset = 1;
        for(Project currentProject : projectMonthTable.getProjects()) {
            XSSFRow row = sheet.createRow(startY + currentYOffset);
            createProjectDataCells(row, currentProject);
            currentYOffset++;
        }
        for(String otherThanProjName : projectMonthTable.getOtherThanProject()) {
            XSSFRow row = sheet.createRow(startY + currentYOffset);
            row.createCell(startX + projTitleCol).setCellValue(otherThanProjName);
            currentYOffset++;
        }
        sheet.autoSizeColumn(startX + projIdCol);
        sheet.autoSizeColumn(startX + projDeptCol);
        sheet.autoSizeColumn(startX + projTitleCol);
    }

    private static void createProjectDataCells(XSSFRow row, Project p) {
        row.createCell(startX + projIdCol).setCellValue(p.getId());
        row.createCell(startX + projDeptCol).setCellValue(p.getDepartment());
        row.createCell(startX + projTitleCol).setCellValue(p.getName());
    }
}
