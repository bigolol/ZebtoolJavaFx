/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.Model;

import java.util.HashMap;

/**
 *
 * @author Holger-Desktop
 */
public class WorkOnProject {
    private Project proj;
    private Employee emp;
    private HashMap<MonthInYear, Float> hoursPerMonthInYear = new HashMap<>();

    public WorkOnProject(Project proj, Employee emp) {
        this.proj = proj;
        this.emp = emp;
    }

    public Project getProj() {
        return proj;
    }

    public Employee getEmp() {
        return emp;
    }
    
    public void addHoursPerMonth(MonthInYear month, float hours) {
        if(hoursPerMonthInYear.containsKey(month)) {
            hoursPerMonthInYear.replace(month, hours);
        } else {
            hoursPerMonthInYear.put(month, hours);
        }
    }
    
    public float getHoursForMonthInYear(MonthInYear monthInYear) {
        Float val = hoursPerMonthInYear.get(monthInYear);
        return val == null ? 0 : val;
    }

    @Override
    public String toString() {
        return "WorkOnProject{" + "proj=" + proj.toString() + ", emp=" + emp.toString() + '}';
    }

    void resetMonthInYear(MonthInYear monthInYear) {
        hoursPerMonthInYear.replace(monthInYear, 0F);
    }
}
