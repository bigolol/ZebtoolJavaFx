/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelExtraction;

import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import com.mycompany.zebtooljavafx.Model.Project;
import com.mycompany.zebtooljavafx.Model.WorkOnOther;
import com.mycompany.zebtooljavafx.Model.WorkOnProject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
public class FromFolderExtractorTest {
    
    public FromFolderExtractorTest() {
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
     * Test of extractFromFolder method, of class FromFolderExtractor.
     */
    @Test
    public void testExtractFromFolder() {
        System.out.println("extractFromFolder");
        File folder = new File("D:\\Code\\irees\\ZebToolJavaFx\\src\\test\\ressources\\zebs\\Holger");
        assertTrue(folder.isDirectory());
        FromFolderExtractor instance = new FromFolderExtractor();
        List<Employee> employees = new ArrayList<Employee>();
        List<Project> projects = new ArrayList<Project>();
        instance.extractFromFolder(folder, employees, projects, new AskIfReplaceZebDataPopupBox());
        assertEquals(new Employee("Felix", "Reitze", false), employees.get(0));
        assertEquals(new Employee("Annette", "Roser", false), employees.get(1));
        assertEquals(new Employee("Michael", "Schön", false), employees.get(2));
        
        for(Employee e : employees) {
            for(WorkOnProject workOnProject : e.getWorkOnProjectsList()) {
                assertTrue(projects.contains(workOnProject.getProj()));
            }
        }
        
        for(int i = 0; i < projects.size(); ++i) {
            System.out.print(employees.get(0).getWorkOnProjectsList().get(i).getHoursForMonthInYear(new MonthInYear(3,2017)) + "   ");
            System.out.print(employees.get(1).getWorkOnProjectsList().get(i).getHoursForMonthInYear(new MonthInYear(3,2017)) + "   ");
            System.out.print(employees.get(2).getWorkOnProjectsList().get(i).getHoursForMonthInYear(new MonthInYear(3,2017)) + "   ");
            
            System.out.println("");
        }
        System.out.println();
        
        List<WorkOnProject> onProjects = employees.get(0).getWorkOnProjectsList();
        for(int i = 0; i < onProjects.size() - 1; ++i) {
            for(int j = i + 1; j < onProjects.size(); ++j) {
                assertNotEquals(onProjects.get(i), onProjects.get(j));
            }
        }
        
        for(int i = 0; i < onProjects.size(); ++i) {
            assertEquals(projects.get(i), onProjects.get(i).getProj());
        }
        
        assertEquals(projects.size(), onProjects.size());
        
        List<WorkOnOther> workOnOthers = employees.get(0).getWorkOnOther();
        
        
        assertEquals(workOnOthers.size(), ExtractZebInfo.getOtherThanProjects().size());
        
        for(int i = 0; i < ExtractZebInfo.getOtherThanProjects().size(); ++i) {
            assertEquals(workOnOthers.get(i).getName(), ExtractZebInfo.getOtherThanProjects().get(i));
        }
    }
    
}
