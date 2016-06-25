/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.buildingLibrary;

import eu.lestard.advanced_bindings.api.MathBindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

/**
 *
 * @author pramukh
 * @param <T> the shape of this building
 */
public abstract class Building<T extends Shape> extends Region
{

    public static final double DEFAULT_ANGLE = 70;
    public static final double DEFAULT_X = 0;
    public static final double DEFAULT_Y = 0;

    private final DoubleProperty angle = new SimpleDoubleProperty();
    protected final DoubleProperty cos = new SimpleDoubleProperty();

    
    {
        //more efficient, as less object creations and recalculations
        cos.bind(MathBindings.cos(MathBindings.toRadians(angle)));
    }
    protected final DoubleProperty sin = new SimpleDoubleProperty();

    
    {
        //more efficient, as less object creations and recalculations
        sin.bind(MathBindings.sin(MathBindings.toRadians(angle)));
    }

    public final void setAngle(Double value)
    {
        angle.set(value);
    }

    public final Double getAngle()
    {
        return angle.get();
    }

    public final DoubleProperty angleProperty()
    {
        return angle;
    }

    public Building()
    {
        this(DEFAULT_ANGLE);
    }

    public Building(double angle)
    {
        this(angle, DEFAULT_X, DEFAULT_Y);
    }

    public Building(double angle, double x, double y)
    {
        super.translateXProperty().set(x);
        super.translateYProperty().set(y);
        this.angle.set(angle);

    }

    public abstract T getBoundingBox();
}
