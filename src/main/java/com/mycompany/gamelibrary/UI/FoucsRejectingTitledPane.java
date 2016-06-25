/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.UI;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;

/**
 *
 * @author pramukh
 */
public class FoucsRejectingTitledPane extends TitledPane
{

    public FoucsRejectingTitledPane(Node taker)
    {
        super();
        addReleaseAction(taker);
    }

    public FoucsRejectingTitledPane(String title, Node content, Node taker)
    {
        super(title, content);
        addReleaseAction(taker);
    }

    private void addReleaseAction(Node taker)
    {
        expandedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
        {
                taker.requestFocus();
        });
    }
    
}
