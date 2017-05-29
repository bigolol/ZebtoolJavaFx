/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelCreation;

/**
 *
 * @author Holger-Desktop
 */
public class ExcelCreationData {
    private boolean removeZeroHoursProjects;
    private boolean removeZeroHoursEmployees;
    private int startX = 0;
    private int startY = 0;

    public ExcelCreationData(boolean removeZeroHoursProjects, boolean removeZeroHoursEmployees, int startX, int startY) {
        this.removeZeroHoursProjects = removeZeroHoursProjects;
        this.removeZeroHoursEmployees = removeZeroHoursEmployees;
        this.startX = startX;
        this.startY = startY;
    }

    public ExcelCreationData(boolean removeZeroHoursProjects, boolean removeZeroHoursEmployees) {
        this.removeZeroHoursProjects = removeZeroHoursProjects;
        this.removeZeroHoursEmployees = removeZeroHoursEmployees;
    }
    
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public boolean isRemoveZeroHoursProjects() {
        return removeZeroHoursProjects;
    }

    public boolean isRemoveZeroHoursEmployees() {
        return removeZeroHoursEmployees;
    }
    
    
}
