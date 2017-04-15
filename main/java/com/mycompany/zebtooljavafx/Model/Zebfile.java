/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.Model;

import java.util.Objects;

/**
 *
 * @author Holger-Desktop
 */
public class Zebfile {
    String fName, lName;
    MonthInYear monthInYear;
    
     public Zebfile(String fName, String lName, MonthInYear monthInYear) {
        this.fName = fName;
        this.lName = lName;
        this.monthInYear = monthInYear;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.fName);
        hash = 97 * hash + Objects.hashCode(this.lName);
        hash = 97 * hash + Objects.hashCode(this.monthInYear);
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
        final Zebfile other = (Zebfile) obj;
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.monthInYear, other.monthInYear)) {
            return false;
        }
        return true;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public MonthInYear getMonthInYear() {
        return monthInYear;
    }    
}
