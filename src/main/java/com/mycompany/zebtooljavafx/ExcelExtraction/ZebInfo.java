/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelExtraction;

import com.mycompany.zebtooljavafx.Model.Project;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Holger-Desktop
 */
public class ZebInfo {
    private String empFName;
    private String empLName;
    private List<Project> projects;
    private List<Float> hoursPerProjectInMonth;
    private MonthInYear monthInYear;
    private List<Float> hoursForOther;
    private boolean isHiwi;

    public ZebInfo(String empFName, String empLName, List<Project> projects, List<Float> hoursPerProjectInMonth, MonthInYear monthInYear, List<Float> hoursForOther, boolean isHiwi) {
        this.empFName = empFName;
        this.empLName = empLName;
        this.projects = projects;
        this.hoursPerProjectInMonth = hoursPerProjectInMonth;
        this.monthInYear = monthInYear;
        this.hoursForOther = hoursForOther;
        this.isHiwi = isHiwi;
    }

    public boolean isHiwi() {
        return isHiwi;
    }
  
    public String getEmpFName() {
        return empFName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Float> getHoursPerProjectInMonth() {
        return hoursPerProjectInMonth;
    }

    public MonthInYear getMonthInYear() {
        return monthInYear;
    }
    
    public List<Float> getHoursForOther() {
        return hoursForOther;
    }
    
    
}
