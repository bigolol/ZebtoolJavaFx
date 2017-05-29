/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx;

import com.mycompany.zebtooljavafx.ExcelCreation.ExcelCreationData;
import com.mycompany.zebtooljavafx.ExcelCreation.ExcelCreator;
import com.mycompany.zebtooljavafx.ExcelExtraction.AskIfReplaceZebDataPopupBox;
import com.mycompany.zebtooljavafx.ExcelExtraction.ExtractZebInfo;
import com.mycompany.zebtooljavafx.ExcelExtraction.FromFolderExtractor;
import com.mycompany.zebtooljavafx.Model.CompanyModel;
import com.mycompany.zebtooljavafx.Model.Employee;
import com.mycompany.zebtooljavafx.Model.MonthInYear;
import com.mycompany.zebtooljavafx.view.ViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Holger-Desktop
 */
public class MainViewController implements Initializable {
    
    private Stage stage;    
    
    
    
    @FXML
    private Button createExcelButton;
    @FXML
    private Button moveToFullTimeButton;
    @FXML
    private Button moveToStudentButton;
    @FXML
    private ListView<String> fullTimeEmployeeList;
    @FXML
    private ListView<String> studentsEmployedList;
    @FXML
    private AnchorPane dropArea;
    @FXML
    private ComboBox<String> startMonthDropDown;
    @FXML
    private ComboBox<String> endMonthDropDown; 
    @FXML
    private ProgressBar parseExcelFilesProgressBar;
    
    private FromFolderExtractor fromFolderExtractor = new FromFolderExtractor();
    
    private CompanyModel model;
    
    @FXML
    public void handleDragEntered(DragEvent evt) {
        System.out.println("drag entered");
        dropArea.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    @FXML
    public void handleDragExited(DragEvent evt) {
        System.out.println("drag exited");
        dropArea.setBackground(Background.EMPTY);
    }
    
    @FXML
    public void handleDragDropped(DragEvent evt) {
            System.out.println("dropped");
    }
    
    @FXML
    public void openAddFileOrFolderDialog() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose folders containing ZEBs");
        File defaultDirectory = new File(System.getProperty("user.dir"));
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if(selectedDirectory != null) {
            parseExcelFilesProgressBar.setOpacity(1);
            fromFolderExtractor.extractFromFolder(defaultDirectory, 
                    model.getEmployees(), model.getProjects(), 
                    new AskIfReplaceZebDataPopupBox());
            
            updateEmployeeLists();
            
            parseExcelFilesProgressBar.setOpacity(0);            
        }        
        
    }

    private void updateEmployeeLists() {
        fullTimeEmployeeList.setItems(ViewModel.getFulltimeEmployees(model.getEmployees()));
        studentsEmployedList.setItems(ViewModel.getStudentsWorkingAtCompany(model.getEmployees()));
    }
    
    @FXML
    public void onCreateExcelButtonClick() {
        XSSFWorkbook createdWB = ExcelCreator.createWorkbookFor(model.getEmployees(),
                getMonthInYearFromSelection(startMonthDropDown.getSelectionModel()),
                getMonthInYearFromSelection(endMonthDropDown.getSelectionModel()),
                new ExcelCreationData(true, true),
                model.getProjects(), 
                ExtractZebInfo.getOtherThanProjects());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save created workbook");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel file 2007", ".xlsx"));
        fileChooser.setInitialFileName("created.xlsx");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                createdWB.write(fileOut);
                fileOut.close();        
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("There was an error trying to save the workbook");
                alert.setContentText("There was an error trying to save the workbook");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    public void selectAllFulltimeButtonClicked() {
         studentsEmployedList.getSelectionModel().clearSelection();
         fullTimeEmployeeList.getSelectionModel().selectAll();
    }
    
    @FXML
    public void selectAllButtonClicked() {
        fullTimeEmployeeList.getSelectionModel().selectAll();
        studentsEmployedList.getSelectionModel().selectAll();
    }
    
    @FXML
    public void SelectAllStudentsButtonClicked() {
         fullTimeEmployeeList.getSelectionModel().clearSelection();
         studentsEmployedList.getSelectionModel().selectAll();
    }
    
    @FXML
    public void onMoveToStudentsButtonClicked() {
        ObservableList<String> selectedItems = fullTimeEmployeeList.getSelectionModel().getSelectedItems();
        for(String s : selectedItems) {
            String firstName = s.split(" ")[0];
            String lastName = s.split(" ")[1];
            model.changeEmployeeToStudent(firstName, lastName);
        }
        updateEmployeeLists();
    }
    
    @FXML    
    public void onMoveToFulltimeButtonClicked() {
        ObservableList<String> selectedItems = studentsEmployedList.getSelectionModel().getSelectedItems();
        for(String s : selectedItems) {
            String firstName = s.split(" ")[0];
            String lastName = s.split(" ")[1];
            model.changeEmployeeToFulltime(firstName, lastName);
        }
        updateEmployeeLists();
    }    
    
    /**  
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fullTimeEmployeeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsEmployedList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        parseExcelFilesProgressBar.setOpacity(0);
        parseExcelFilesProgressBar.progressProperty().bind(fromFolderExtractor.getPercentageParsed());
        
        MonthInYear starter = new MonthInYear(1, 2013);
        
        for(int i = 0; i < 500; ++i) {
            startMonthDropDown.getItems().add(starter.toString());
            endMonthDropDown.getItems().add(starter.toString());
            starter.increment();
        }
        
        startMonthDropDown.getSelectionModel().select(10);
        endMonthDropDown.getSelectionModel().select(10);
        
        startMonthDropDown.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(startMonthDropDown.getSelectionModel().getSelectedIndex() > endMonthDropDown.getSelectionModel().getSelectedIndex()) {
                    endMonthDropDown.getSelectionModel().select(startMonthDropDown.getSelectionModel().getSelectedIndex());
                }
            }
        });
        endMonthDropDown.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(endMonthDropDown.getSelectionModel().getSelectedIndex() < startMonthDropDown.getSelectionModel().getSelectedIndex()) {
                    startMonthDropDown.getSelectionModel().select(endMonthDropDown.getSelectionModel().getSelectedIndex());
                }
            }
        });
        
    }    
    
    private MonthInYear getMonthInYearFromSelection(SingleSelectionModel<String> selection) {
        String monthString = selection.getSelectedItem().split(" ")[0];        
        int year = Integer.valueOf(selection.getSelectedItem().split(" ")[1]);
        return new MonthInYear(monthString, year);
    }

    public void setStage(Stage primaryStage) {
        this.stage = stage;        
    }

    public void setModel(CompanyModel model) {
        this.model = model;
    }
}
