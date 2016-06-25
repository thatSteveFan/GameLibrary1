/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.UI;

import javafx.scene.Node;
import javafx.scene.control.Slider;

/**
 * A slider that rejects focus and instead moves it to another node
 *
 * @author pramukh
 */
public class FocusRejectingSlider extends Slider
{

    public FocusRejectingSlider(Node focusRequester)
    {
        super();
        onMouseReleasedProperty().set(e
                -> 
                {
                    focusRequester.requestFocus();
                    System.out.println("SliderReleased");
        });
    }

    public FocusRejectingSlider(double min, double max, double value, Node focusRequester)
    {
        super(min, max, value);
        onMouseReleasedProperty().set(e
                -> 
                {
                    focusRequester.requestFocus();
                    System.out.println("SliderReleased");
        });
    }

}
