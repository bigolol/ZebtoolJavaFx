/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelExtraction;

import com.mycompany.zebtooljavafx.Model.Project;
import com.mycompany.zebtooljavafx.Model.WorkOnProject;
import java.io.File;
import java.io.IOException;
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
public class ExtractZebInfoTest {
    
    public ExtractZebInfoTest() {
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
    
    @Test
    public void testExtractZebInfoFromZeb() throws IOException {
        File file = new File("D:\\Code\\irees\\ZebToolJavaFx\\src\\test\\ressources\\zebs\\Holger\\ZEB-01-2017-FR.xls");
        ZebInfo extracted = ExtractZebInfo.extractZebInfo(file);
        assertEquals("Felix", extracted.getEmpFName());
        assertEquals("Reitze", extracted.getEmpLName());
        assertEquals(1 ,extracted.getMonthInYear().getMonth());
        assertEquals(2017 ,extracted.getMonthInYear().getYear());
        assertEquals(new Project("DACH EPET", "EPET", 1604) , extracted.getProjects().get(0));
        assertEquals(new Float(8) , extracted.getHoursPerProjectInMonth().get(0));
        assertEquals(new Float(0) , extracted.getHoursForOther().get(0));
        
        for(Float f : extracted.getHoursPerProjectInMonth()) {
            System.out.println(f);    
        }
        System.out.println("");
        
        file = new File("D:\\Code\\irees\\ZebToolJavaFx\\src\\test\\ressources\\zebs\\Holger\\ZEB-03-2017-SCH.xls");
        extracted = ExtractZebInfo.extractZebInfo(file);
        
        for(Float f : extracted.getHoursPerProjectInMonth()) {
            System.out.println(f);    
        }
        System.out.println("");
    }
    
}
