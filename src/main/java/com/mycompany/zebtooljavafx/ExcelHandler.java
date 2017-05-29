/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx;

import com.mycompany.zebtooljavafx.ExcelExtraction.AskIfReplaceFileData;
import com.mycompany.zebtooljavafx.ExcelExtraction.AskIfReplaceZebDataPopupBox;
import com.mycompany.zebtooljavafx.ExcelExtraction.FromFolderExtractor;
import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.Project;
import java.io.File;
import java.util.List;
import javafx.scene.control.ProgressBar;
import com.mycompany.zebtooljavafx.view.ViewModel;

/**
 *
 * @author Holger-Desktop
 */
public class ExcelHandler {
    private FromFolderExtractor folderExtractor = new FromFolderExtractor();
    private AskIfReplaceFileData askIfReplaceFileData = new AskIfReplaceZebDataPopupBox();
    private List<Employee> employees;
    private List<Project> projects;

    public ExcelHandler(
            ViewModel viewModel,
            List<Employee> employees, 
            List<Project> projects) {
        this.employees = employees;
        this.projects = projects;
    }

    public void selectedFolder(File f) {
        if(f == null) return;
        folderExtractor.extractFromFolder(f, employees, projects, askIfReplaceFileData);
    }
    
    public FromFolderExtractor getFolderExtractor() {
        return folderExtractor;
    }

    public void setFolderExtractor(FromFolderExtractor folderExtractor) {
        this.folderExtractor = folderExtractor;
    }
    
    public void changeToStudent(String fname, String lname) {
        
    }

}
