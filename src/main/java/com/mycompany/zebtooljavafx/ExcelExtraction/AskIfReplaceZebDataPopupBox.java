/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zebtooljavafx.ExcelExtraction;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Holger-Desktop
 */
public class AskIfReplaceZebDataPopupBox implements AskIfReplaceFileData {
    private boolean replaceAll = false;
    private boolean replaceNone = false;
    @Override
    public boolean askIfReplaceFile(File file) {
        if(replaceAll) return true;
        if(replaceNone) return false;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Parse file again");
        alert.setHeaderText("Parse file again");
        alert.setContentText(
                "The File " + file.getName() + 
                " has already been scanned and the contained info has already been parsed. Do you want to parse it again?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType yesToAllButton = new ButtonType("Yest to all");
        ButtonType noButton = new ButtonType("No");
        ButtonType noToAllButton = new ButtonType("No to all");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, yesToAllButton, noButton, noToAllButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton){
           return true;
        } else if (result.get() == yesToAllButton) {
            replaceAll = true;
            return true;
        } else if (result.get() == noButton) {
            return false;
        } else if(result.get() == noToAllButton) {
            replaceNone = true;
            return false;
        } else {
            return false;
        }
    }
    
     public boolean isReplaceAll() {
        return replaceAll;
    }

    public void setReplaceAll(boolean replaceAll) {
        this.replaceAll = replaceAll;
    }
}
