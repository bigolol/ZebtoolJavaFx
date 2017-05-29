/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Holger-Desktop
 */
public class CompanyModel {
    private List<Project> projects;
    private List<Employee> employees;

    public List<Project> getProjects() {
        return projects;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public CompanyModel(List<Project> projects, List<Employee> employees) {
        this.projects = projects;
        this.employees = employees;
    }

    public CompanyModel() {
        projects = new ArrayList<>();
        employees = new ArrayList<>();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public void changeEmployeeToStudent(String firstName, String lastName) {
        for(Employee e : employees) {
            if(e.getFirstName().equals(firstName) && e.getLastName().equals(lastName)) {
                e.setIsHiwi(true);
            }
        }
    }
    
    public void changeEmployeeToFulltime(String firstName, String lastName) {
        for(Employee e : employees) {
            if(e.getFirstName().equals(firstName) && e.getLastName().equals(lastName)) {
                e.setIsHiwi(false);
            }
        }
    }
}
