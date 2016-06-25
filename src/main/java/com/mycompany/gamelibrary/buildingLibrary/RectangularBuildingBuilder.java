/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.buildingLibrary;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import static com.mycompany.gamelibrary.buildingLibrary.RectangularBuilding.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author pramukh
 */
public class RectangularBuildingBuilder extends BuildingBuilder<Rectangle>
{

        private final DoubleProperty width = new SimpleDoubleProperty();
    private final DoubleProperty height = new SimpleDoubleProperty();
    private final DoubleProperty depth = new SimpleDoubleProperty();
    private final ObjectProperty<Image> leftImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> rightImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> topImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> frontImage = new SimpleObjectProperty<>();

    public RectangularBuildingBuilder()
    {
        this(DEFAULT_HEIGHT, DEFALUT_WIDTH, DEFALUT_DEPTH);
    }

    public RectangularBuildingBuilder(double height, double width, double depth)
    {
        this(height, width, depth, DEFAULT_ANGLE);
    }

    public RectangularBuildingBuilder(double height, double width, double depth, double angle)
    {
        this(height, width, depth, angle, DEFAULT_X, DEFAULT_Y);
    }

    public RectangularBuildingBuilder(double height, double width, double depth, double angle, double x, double y)
    {
        this(null, null, null, null, angle, height, width, depth, x, y);
    }

    public RectangularBuildingBuilder(Image front, Image left, Image top, Image right, double angle, double height, double width, double depth, double x, double y)
    {
        super(angle, x, y);
        frontImage.setValue(front);
        leftImage.setValue(left);
        topImage.setValue(top);
        rightImage.setValue(right);
        
        this.width.setValue(width);
        this.height.setValue(height);
        this.depth.setValue(depth);
        
    }

    @Override
    public RectangularBuilding build()
    {
         RectangularBuilding b = new RectangularBuilding(frontImage.get(), leftImage.get(), topImage.get(), rightImage.get(), angleProperty().getValue(), xProperty().getValue(), yProperty().getValue(), height.getValue(),width.getValue(), depth.getValue());
         return b;
    }

    public final void setWidth(Double value)
    {
        width.set(value);
    }

    public final Double getWidth()
    {
        return width.get();
    }

    public final DoubleProperty widthProperty()
    {
        return width;
    }

    public final void setHeight(Double value)
    {
        height.set(value);
    }

    public final Double getHeight()
    {
        return height.get();
    }

    public final DoubleProperty heightProperty()
    {
        return height;
    }

    public final void setDepth(Double value)
    {
        depth.set(value);
    }

    public final Double getDepth()
    {
        return depth.get();
    }

    public final DoubleProperty depthProperty()
    {
        return depth;
    }
    
    public Image getLeftImage()
    {
        return leftImage.getValue();
    }

    public void setLeftImage(Image value)
    {
        leftImage.setValue(value);
    }

    public ObjectProperty<Image> leftImageProperty()
    {
        return leftImage;
    }

    public Image getRightImage()
    {
        return rightImage.getValue();
    }

    public void setRightImage(Image value)
    {
        rightImage.setValue(value);
    }

    public ObjectProperty<Image> rightImageProperty()
    {
        return rightImage;
    }

    public Image getTopImage()
    {
        return topImage.getValue();
    }

    public void setTopImage(Image value)
    {
        topImage.setValue(value);
    }

    public ObjectProperty<Image> topImageProperty()
    {
        return topImage;
    }

    public Image getFrontImage()
    {
        return frontImage.getValue();
    }

    public void setFrontImage(Image value)
    {
        frontImage.setValue(value);
    }

    public ObjectProperty<Image> frontImageProperty()
    {
        return frontImage;
    }

}
