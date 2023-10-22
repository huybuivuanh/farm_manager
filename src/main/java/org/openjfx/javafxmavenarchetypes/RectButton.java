package org.openjfx.javafxmavenarchetypes;

import javafx.scene.control.Button;

public class RectButton extends Button {

    public RectButton(String colour, String text){
        super(text);
        System.out.println(colour);
        super.setStyle("-fx-background-color: " + colour + "; ");
        super.setWidth(50);
        super.setPrefHeight(50);
    }
}
