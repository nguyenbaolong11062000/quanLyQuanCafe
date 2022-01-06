/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quancf;

import javafx.scene.control.Alert;

/**
 *
 * @author Administrator
 */
public class Utils {
    public static Alert getBox(String content,Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        
        return alert;
    }
}
