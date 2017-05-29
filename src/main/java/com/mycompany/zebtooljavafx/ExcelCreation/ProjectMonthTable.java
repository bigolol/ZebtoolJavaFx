/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelCreation;

import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import com.mycompany.zebtooljavafx.Model.Project;
import com.mycompany.zebtooljavafx.Model.WorkOnOther;
import com.mycompany.zebtooljavafx.Model.WorkOnProject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Holger-Desktop
 */
public class ProjectMonthTable {
    private MonthInYear monthInYear;   
    private List<Employee> employees;
    private List<Project> projects;
    private List<String> otherThanProject;
    
    private List<List<Float>> valuesPerEmployee = new ArrayList<>();
    private List<Float> allProjectTotalsPerEmployee = new ArrayList<>();
    private List<Float> totalsPerProject = new ArrayList<>();
    private List<Float> otherTotalsPerEmployee = new ArrayList<>();
    private List<Float> totalsPerOther = new ArrayList<>();


    public ProjectMonthTable(MonthInYear monthInYear, 
            List<Employee> employees, 
            List<Project> projects,
            List<String> otherThanProject) {
        this.monthInYear = monthInYear;
        this.employees = employees;
        this.projects = projects;
        this.otherThanProject = otherThanProject;
        generate();
    }

    private void generate() {
        initProjTotalsWithZero();
        initOtherTotalsWithZero();
        
        for(Employee e : employees) {
            List<Float> hoursThisMonthForThisEmployee = new ArrayList<>();
            List<WorkOnProject> workOnProjectList = e.getWorkOnProjectsList();            
            int currentProjPos = 0;
            
            float totalProjectForEmployee = 0;
            for(Project proj : projects) {
                hoursThisMonthForThisEmployee.add(e.getHoursForProject(proj, monthInYear));
                totalsPerProject.set(
                        currentProjPos, totalsPerProject.get(
                            currentProjPos) + 
                                e.getHoursForProject(proj, monthInYear));
                totalProjectForEmployee += e.getHoursForProject(proj, monthInYear);
                currentProjPos++;
            }
            
            allProjectTotalsPerEmployee.add(totalProjectForEmployee);

            int currentOtherPos = 0;
            
            float totalForOtherForEmployee = 0;
            for(String otherThanProj : otherThanProject) {
                hoursThisMonthForThisEmployee.add(
                        e.getHoursForOther(
                        otherThanProj, monthInYear));
                totalForOtherForEmployee += e.getHoursForOther(otherThanProj, monthInYear);
                totalsPerOther.set(
                        currentOtherPos, totalsPerOther.get(
                            currentOtherPos) + 
                                e.getHoursForOther(otherThanProj, monthInYear));
                currentOtherPos++;
            }
            
            otherTotalsPerEmployee.add(totalForOtherForEmployee);

            valuesPerEmployee.add(hoursThisMonthForThisEmployee);
        }
    }

    private void initOtherTotalsWithZero() {
        for(String s : otherThanProject) {
            totalsPerOther.add(new Float(0));
        }
    }

    private void initProjTotalsWithZero() {
        for(Project p : projects) {
            totalsPerProject.add(new Float(0));
        }
    }

    private void updateEmployeeTotal(int employeeNo, WorkOnProject wop) {
        allProjectTotalsPerEmployee.set(employeeNo, allProjectTotalsPerEmployee.get(employeeNo) +
                        wop.getHoursForMonthInYear(monthInYear));
    }

    private void updateProjectTotal(int currentProjPos, WorkOnProject wop) {
        totalsPerProject.set(
                currentProjPos, totalsPerProject.get(currentProjPos) +
                        wop.getHoursForMonthInYear(monthInYear));
    }
    
    public List<List<Float>> getValuesPerEmployee() {
        return valuesPerEmployee;
    }

    public List<Float> getTotalsPerProject() {
        return totalsPerProject;
    }

    public List<Float> getAllProjectTotalsPerEmployee() {
        return allProjectTotalsPerEmployee;
    }

    public List<Float> getOtherTotalsPerEmployee() {
        return otherTotalsPerEmployee;
    }

    public List<Float> getTotalsPerOther() {
        return totalsPerOther;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<String> getOtherThanProject() {
        return otherThanProject;
    }
    
    
    
}
