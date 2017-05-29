/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.Model;

import java.util.Arrays;

/**
 *
 * @author Holger-Desktop
 */
public class MonthInYear implements Comparable<MonthInYear>{
    private int month;
    private int year;
    private String[] monthNames = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};

    @Override
    public String toString() {
        return monthNames[month - 1] + " " + String.valueOf(year);
    }   
    
    public MonthInYear(String month, int year) {
        this.year = year;
        for (int i = 0; i < monthNames.length; i++) {
            String monthName = monthNames[i];
            if(monthName.equals(month)) {
                this.month = i + 1;
                break;
            }
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.month;
        hash = 97 * hash + this.year;
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
        final MonthInYear other = (MonthInYear) obj;
        if (this.month != other.month) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }

    public MonthInYear() {
    }

    public MonthInYear(int month, int Year) {
        this.month = month;
        this.year = Year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int Year) {
        this.year = Year;
    }

    @Override
    public int compareTo(MonthInYear other) {
        if(year == other.year) return Integer.compare(month, other.month);
        else return Integer.compare(year, other.year);
    }
    
    public void increment() {
        if(month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
    }
    
     public void decrement() {
        if(month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
    }
}
