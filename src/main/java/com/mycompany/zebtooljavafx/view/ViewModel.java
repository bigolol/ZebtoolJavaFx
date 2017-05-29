/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.view;

import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Holger-Desktop
 */
public class ViewModel {   

    public static ObservableList<String> getFulltimeEmployees(List<Employee> employees) {
        ObservableList<String> created = FXCollections.observableArrayList();
        for(Employee e : employees) if(!e.getIsHiwi()) created.add(e.getFirstName() + " " + e.getLastName());
        return created;
    }   
    
    public static  ObservableList<String> getStudentsWorkingAtCompany(List<Employee> employees) {
        ObservableList<String> created = FXCollections.observableArrayList();
        for(Employee e : employees) if(e.getIsHiwi()) created.add(e.getFirstName() + " " + e.getLastName());
        return created;
    }
    
}
