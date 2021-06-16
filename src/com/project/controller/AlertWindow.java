package com.project.controller;

import javafx.scene.control.Alert;

public class AlertWindow {

    public static void AlertDisplay(String information) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(information);
        a.show();
    }
}
