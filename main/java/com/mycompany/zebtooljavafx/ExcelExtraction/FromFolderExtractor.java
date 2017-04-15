/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelExtraction;

import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.Project;
import com.mycompany.zebtooljavafx.Model.Zebfile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;

/**
 *
 * @author Holger-Desktop
 */
public class FromFolderExtractor {
    private List<Zebfile> alreadyParsedFiles = new ArrayList<>();
    private FloatProperty percentageParsed = new SimpleFloatProperty(0);
   
    public void extractFromFolder(
            File folder, 
            List<Employee> employees, 
            List<Project> projects,
            AskIfReplaceFileData askIfReplace) {    
        List<File> zebFiles = new ArrayList<>();
        for(File file : folder.listFiles()) {
            if(!file.isDirectory() && file.getName().endsWith(".xls")) {                
                zebFiles.add(file);
            } else if(file.isDirectory()) {
                extractFromFolder(file, employees, projects, askIfReplace);
            }
        }
        
        percentageParsed.set(0);
        
        float amtMorePerFileParsed = 1.0F / zebFiles.size();
        
        for(File f : zebFiles) {
            handleExcelFile(f, askIfReplace, employees, projects);
            percentageParsed.set(percentageParsed.get()+ amtMorePerFileParsed);
        }
    }
        
    public ReadOnlyFloatProperty getPercentageParsed() {
        return percentageParsed;
    }

    private void handleExcelFile(
            File file, 
            AskIfReplaceFileData askIfReplace,
            List<Employee> employees, 
            List<Project> projects) {
        if(isZeb(file)) {            
            extractInfoAndAddFromFile(file, employees, askIfReplace, projects);            
        }
    }

    private static boolean isZeb(File file) {
        return file.getName().startsWith("ZEB");
    }

    private void extractInfoAndAddFromFile(
            File file, 
            List<Employee> employees,
            AskIfReplaceFileData askIfReplace,
            List<Project> projects) {
        try {
            ZebInfo extracted = ExtractZebInfo.extractZebInfo(file);            
            
            Zebfile zebfile = new Zebfile(
                    extracted.getEmpFName(),
                    extracted.getEmpLName(),
                    extracted.getMonthInYear());
            
            Employee empInList = extractEmpFromList(employees, extracted);
            if(alreadyParsedFiles.contains(zebfile)) {
                if(askIfReplace.askIfReplaceFile(file)) {
                    empInList.removeAllDataForMonthInYear(extracted.getMonthInYear());                    
                } else {
                    return;
                }
            }            
            addProjectAndOtherHoursToEmployee(empInList, extracted, projects);
            alreadyParsedFiles.add(zebfile);
        } catch (IOException ex) {
            Logger.getLogger(FromFolderExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addProjectAndOtherHoursToEmployee(Employee empInList, ZebInfo extracted, List<Project> projects) {
        addProjectWorkHoursToEmployee(extracted, projects, empInList);
        addOtherHoursToEmployee(extracted, empInList);
    }
    
    private void addOtherHoursToEmployee(ZebInfo extracted, Employee empInList) {
        int i = 0;
        for(String otherDesc : ExtractZebInfo.getOtherThanProjects()) {
            empInList.addHoursToOtherThanProject(otherDesc, extracted.getMonthInYear(), extracted.getHoursForOther().get(i));
            ++i;
        }
    }
    
    private void addProjectWorkHoursToEmployee(ZebInfo extracted, List<Project> projects, Employee empInList) {
        int i = 0;
        for(Project proj : extracted.getProjects()) {
            Project projInList = extractProjFromListOrAddNew(proj, projects); 
            empInList.addWork(proj, extracted.getMonthInYear(), extracted.getHoursPerProjectInMonth().get(i));
            i++;
        }
    }    
    
    private Project extractProjFromListOrAddNew(Project proj, List<Project> projects) {
        for(Project projInList : projects) {
            if(projInList.equals(proj)) {
                return proj;
            }
        }
        projects.add(proj);
        return proj;
    }

    private Employee extractEmpFromList(List<Employee> employees, ZebInfo extracted) {
        for(Employee emp : employees) {
            if(emp.getFirstName().equals(extracted.getEmpFName()) &&
                    emp.getLastName().equals(extracted.getEmpLName())) {
                return emp;
            }
        }
        Employee newlyCreatedEmp = new Employee(extracted.getEmpFName(), extracted.getEmpLName(), extracted.isHiwi());
        employees.add(newlyCreatedEmp);
        return newlyCreatedEmp;
    }

    

   
}
