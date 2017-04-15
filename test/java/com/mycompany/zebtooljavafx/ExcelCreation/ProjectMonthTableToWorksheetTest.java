/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelCreation;

import com.mycompany.zebtooljavafx.ExcelExtraction.AskIfReplaceZebDataPopupBox;
import com.mycompany.zebtooljavafx.ExcelExtraction.ExtractZebInfo;
import com.mycompany.zebtooljavafx.ExcelExtraction.FromFolderExtractor;
import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import com.mycompany.zebtooljavafx.Model.Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Holger-Desktop
 */
public class ProjectMonthTableToWorksheetTest {
    
    public ProjectMonthTableToWorksheetTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createTableIntoWorksheet method, of class ProjectMonthTableToWorksheet.
     */
    @Test
    public void testCreateTableIntoWorksheet() throws FileNotFoundException, IOException {
        System.out.println("createTableIntoWorksheet");
        File folder = new File("D:\\Code\\irees\\ZebToolJavaFx\\src\\test\\ressources\\zebs\\Holger");
        FromFolderExtractor instance = new FromFolderExtractor();
        List<Employee> employees = new ArrayList<Employee>();
        List<Project> projects = new ArrayList<Project>();
        instance.extractFromFolder(folder, employees, projects, new AskIfReplaceZebDataPopupBox());
        
        ProjectMonthTable monthTable = new ProjectMonthTable(
                new MonthInYear(3,2017),
                employees, projects, ExtractZebInfo.getOtherThanProjects());
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFCellStyle style = wb.createCellStyle();
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        ProjectMonthTableToWorksheet.createTableIntoWorksheet(sheet, monthTable, new ExcelCreationData(true, true), style);
        try{
            FileOutputStream fileOut = new FileOutputStream("D:\\Code\\irees\\ZebToolJavaFx\\src\\test\\ressources\\created\\created.xlsx");
            wb.write(fileOut);
            fileOut.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
