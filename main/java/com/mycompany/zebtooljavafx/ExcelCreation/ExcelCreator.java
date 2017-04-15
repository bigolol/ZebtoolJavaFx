/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelCreation;

import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import com.mycompany.zebtooljavafx.Model.Project;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Holger-Desktop
 */
public class ExcelCreator {
    public static XSSFWorkbook createWorkbookFor(
            List<Employee> employees,
            MonthInYear start, 
            MonthInYear end,
            ExcelCreationData data,
            List<Project> projects,
            List<String> otherThanProjects) {
        MonthInYear current = new MonthInYear(start.getMonth(), start.getYear());
        XSSFWorkbook wb = new XSSFWorkbook();
        while(current.compareTo(end) <= 0) {
            ProjectMonthTable currentProjMonthTable = new ProjectMonthTable(current, employees, projects, otherThanProjects);            
            XSSFSheet currentSheet = wb.createSheet(current.toString());
            XSSFCellStyle style = wb.createCellStyle();
            style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
            ProjectMonthTableToWorksheet.createTableIntoWorksheet(currentSheet, currentProjMonthTable, data, style);
            current.increment();
        }
        return wb;
    }
}
