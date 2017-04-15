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
public class WorkOnOther {
    private String name;
    private Employee emp;
    private HashMap<MonthInYear, Float> hoursPerMonthInYear = new HashMap<>();

    public WorkOnOther(String name, Employee emp) {
        this.name = name;
        this.emp = emp;
    }

    public String getName() {
        return name;
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

    void resetMonthInYear(MonthInYear monthInYear) {
        hoursPerMonthInYear.replace(monthInYear, 0F);
    }

}
