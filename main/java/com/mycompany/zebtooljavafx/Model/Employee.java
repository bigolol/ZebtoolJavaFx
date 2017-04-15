/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Holger-Desktop
 */
public class Employee {
    private String firstName;
    private String lastName;
    private List<WorkOnProject> workOnProjectsList = new ArrayList<>();  
    private List<WorkOnOther> workOnOther = new ArrayList<>();
    private Boolean isHiwi;
    private int id;

    public Employee(String firstName, String lastName, Boolean isHiwi) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isHiwi = isHiwi;
    }

    public Boolean getIsHiwi() {
        return isHiwi;
    }

    public void setIsHiwi(Boolean isHiwi) {
        this.isHiwi = isHiwi;
    }
    
    public void addWork(Project proj, MonthInYear monthInYear, Float hours) {
        WorkOnProject wop = findRightWOP(proj);
        wop.addHoursPerMonth(monthInYear, hours);
    }
    
    public void addHoursToOtherThanProject(String otherActivityName, MonthInYear monthInYear, Float hours) {
        WorkOnOther wor = findRightWOR(otherActivityName);
        wor.addHoursPerMonth(monthInYear, hours);
    }

    private WorkOnOther findRightWOR(String otherActivityName) {
        for(WorkOnOther wor : workOnOther) {
            if(wor.getName().equals(otherActivityName)) return wor;
        }
        WorkOnOther newlyCreatedWOR = new WorkOnOther(otherActivityName, this);
        workOnOther.add(newlyCreatedWOR);
        return newlyCreatedWOR;
    }
    
    private WorkOnProject findRightWOP(Project proj) {
        for(WorkOnProject wop : workOnProjectsList) {
            if(wop.getProj().equals(proj)) return wop;
        }
        WorkOnProject created = new WorkOnProject(proj, this);
        workOnProjectsList.add(created);
        return created;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" + "firstName=" + firstName + ", lastName=" + lastName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.firstName);
        hash = 29 * hash + Objects.hashCode(this.lastName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.isHiwi, other.isHiwi)) {
            return false;
        }
        return true;
    }

    public List<WorkOnProject> getWorkOnProjectsList() {
        return workOnProjectsList;
    }

    public List<WorkOnOther> getWorkOnOther() {
        return workOnOther;
    }

    public Float getHoursForProject(Project proj, MonthInYear monthInYear) {
        for(WorkOnProject wop :workOnProjectsList) {
            if(wop.getProj().equals(proj)) {
                return wop.getHoursForMonthInYear(monthInYear);
            }
        }
        return 0F;
    }

    public Float getHoursForOther(String otherThanProj, MonthInYear monthInYear) {
        for(WorkOnOther woo : workOnOther) {
            if(woo.getName().equals(otherThanProj)) {
                return woo.getHoursForMonthInYear(monthInYear);
            }
        }
        return 0F;
    }

    public void removeAllDataForMonthInYear(MonthInYear monthInYear) {
        for(WorkOnOther wor : workOnOther) {
            wor.resetMonthInYear(monthInYear);
        }
        for(WorkOnProject wop : workOnProjectsList) {
            wop.resetMonthInYear(monthInYear);
        }
    }


    

   


    
}
