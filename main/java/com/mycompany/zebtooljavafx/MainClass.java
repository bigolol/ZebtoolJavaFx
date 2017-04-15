/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx;

import com.mycompany.zebtooljavafx.Model.CompanyModel;
import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.Project;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.mycompany.zebtooljavafx.view.ViewModel;

/**
 *
 * @author Holger-Desktop
 */
public class MainClass extends Application {
    
    private ExcelHandler excelHandler;
    private CompanyModel companyModel;
    
    public static void main(String[] args) {
        Application.launch(args);        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        companyModel = new CompanyModel();
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/stlyes/mainview.css");
        primaryStage.setTitle("Zeb tool");
        primaryStage.setScene(scene);
        ((MainViewController) loader.getController()).setStage(primaryStage);
        ((MainViewController) loader.getController()).setModel(companyModel);
        primaryStage.show();
    }
}
