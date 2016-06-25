/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.sprites;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;

/**
 * A class to represent any sprite with a rectangular bounding box. Contains no
 * code for the actual image, and is therefor abstract.
 *
 * @author pramukh
 */
public abstract class RectangularSprite extends Sprite<Rectangle>
{

    private final DoubleProperty width = new SimpleDoubleProperty();
    private final DoubleProperty height = new SimpleDoubleProperty();

    public RectangularSprite(double width, double height)
    {
        this(0, 0, width, height);
    }

    public RectangularSprite(double x, double y, double width, double height)
    {
        this(x, y, width, height, DEFAULT_ANGLE);
    }

    public RectangularSprite(double x, double y, double width, double height, double angle)
    {
        super(x, y, angle);
        this.width.set(width);
        this.height.set(height);
    }

    public double getWidth()
    {
        return width.get();
    }

    public void setWidth(double value)
    {
        width.set(value);
    }

    public DoubleProperty widthProperty()
    {
        return width;
    }

    public double getHeight()
    {
        return height.get();
    }

    public void setHeight(double value)
    {
        height.set(value);
    }

    public DoubleProperty heightProperty()
    {
        return height;
    }

    @Override
    public Rectangle getBoundingBox()
    {
        return new Rectangle(getTranslateX(), getTranslateY(), width.get(), height.get());
    }
}
