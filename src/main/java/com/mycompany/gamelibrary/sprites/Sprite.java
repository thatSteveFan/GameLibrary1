/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.sprites;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

/**
 * Represents any moving object in a game
 * <p>
 * Handles x and y translates
 *
 * @author pramukh
 * @param <T> the shape of the bounding shape
 */
public abstract class Sprite<T extends Shape> extends Parent
{

    public static final double DEFAULT_ANGLE = 25;
    private final DoubleProperty angle = new SimpleDoubleProperty();

    public Sprite(double x, double y)
    {
        this(x, y, DEFAULT_ANGLE);
    }

    public Sprite(double x, double y, double angle)
    {
        translateXProperty().set(x);
        translateYProperty().set(y);
        this.angle.set(angle);
    }

    public double getAngle()
    {
        return angle.get();
    }

    public void setAngle(double value)
    {
        angle.set(value);
    }

    public DoubleProperty angleProperty()
    {
        return angle;
    }


    public abstract T getBoundingBox();
}
