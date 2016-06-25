/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.buildingLibrary;

import eu.lestard.advanced_bindings.api.MathBindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;

/**
 *
 * @author pramukh
 */
public class RectangularBuilding extends Building<Rectangle>
{

    
    

    public static final double DEFAULT_HEIGHT = 100;
    public static final double DEFALUT_WIDTH = 100;
    public static final double DEFALUT_DEPTH = 100;

    private final ObjectProperty<Image> leftImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> rightImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> topImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> frontImage = new SimpleObjectProperty<>();
    private final DoubleProperty depth = new SimpleDoubleProperty();
    

    RectangularBuilding(Image image)
    {
        this(image, image, image, image);
    }

    RectangularBuilding(Image front, Image left, Image top, Image right)
    {
        this(front, left, top, right, DEFAULT_ANGLE);
    }

    RectangularBuilding(Image front, Image left, Image top, Image right, double angle)
    {
        this(front, left, top, right, angle, DEFAULT_X, DEFAULT_Y);
    }

    RectangularBuilding(Image front, Image left, Image top, Image right, double angle, double x, double y)
    {

        this(front, left, top, right, angle, x, y, DEFAULT_HEIGHT, DEFALUT_WIDTH, DEFALUT_DEPTH);

    }

    @SuppressWarnings(value = "OverridableMethodCallInConstructor")
    RectangularBuilding(Image front, Image left, Image top, Image right, double angle, double x, double y, double height, double width, double depth)
    {
        super(angle, x, y);
        frontImage.setValue(front);
        leftImage.setValue(left);
        topImage.setValue(top);
        rightImage.setValue(right);
        
        this.prefHeightProperty().set(height);
        this.prefWidthProperty().set(width);
        this.depth.setValue(depth);

        Group root = new Group(makeBuilding());
        getChildren().add(root);

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

    

    private Group makeBuilding()
    {
        Group root = new Group(makeFront(), makeLeft(), makeTop(), makeRight());
        return root;
    }

    protected Node makeLeft()
    {

        ImageView leftSide = new ImageView();
        leftSide.imageProperty().bind(this.leftImage);
        leftSide.fitHeightProperty().bind(heightProperty());
        leftSide.fitWidthProperty().bind(depthProperty());
        Group leftGroup = new Group(leftSide);
        Shear leftSideShear = new Shear();
        leftSideShear.xProperty().bind(cos.divide(sin));
        //leftSideShear.pivotXProperty().bind(leftGroup.translateXProperty());
        Scale leftSideScale = new Scale(1, Double.NaN);
        leftSideScale.yProperty().bind(sin);
        leftSide.getTransforms().addAll(leftSideShear, leftSideScale);
        Rotate leftSideRotate = new Rotate(90, Rotate.Y_AXIS);
        leftGroup.getTransforms().addAll(leftSideRotate, new Rotate(90, Rotate.Z_AXIS));
        leftGroup.translateZProperty().bind(heightProperty().negate().multiply(sin));
        leftGroup.translateYProperty().bind(heightProperty().negate().multiply(cos));
        return leftGroup;
    }

    protected Node makeFront()
    {

        ImageView front = new ImageView();
        front.imageProperty().bind(frontImage);
        front.fitHeightProperty().bind(this.heightProperty());
        front.fitWidthProperty().bind(this.widthProperty());
        Rotate frontRotate = new Rotate(0, Rotate.X_AXIS);
        front.getTransforms().add(frontRotate);
        front.translateZProperty().bind(front.fitHeightProperty().negate().multiply(sin));
        front.translateYProperty().bind(front.fitHeightProperty().negate().multiply(cos).add(depth));
        frontRotate.angleProperty().bind(super.angleProperty());
        return front;
    }

    protected Node makeRight()
    {
        ImageView rightSide = new ImageView();
        rightSide.imageProperty().bind(rightImage);
        rightSide.fitHeightProperty().bind(heightProperty());
        rightSide.fitWidthProperty().bind(depthProperty());

        Group rightGroup = new Group(rightSide);
        Shear rightSideShear = new Shear();

        rightSideShear.xProperty().bind(cos.divide(sin));
        rightSideShear.pivotXProperty().bind(rightGroup.translateXProperty());
        Scale rightSideScale = new Scale(1, Double.NaN);
        rightSideScale.yProperty().bind(sin);
        rightSide.getTransforms().addAll(rightSideShear, rightSideScale);
        Rotate rightSideRotate = new Rotate(90, Rotate.Y_AXIS);
        rightGroup.getTransforms().addAll(rightSideRotate, new Rotate(-90, Rotate.Z_AXIS));
        rightGroup.translateYProperty().bind(depth);
        rightGroup.translateXProperty().bind((widthProperty()));

        return rightGroup;
    }

    protected Node makeTop()
    {
        ImageView top = new ImageView();
        top.imageProperty().bind(topImage);
        top.fitHeightProperty().bind(depth);
        top.fitWidthProperty().bind(widthProperty());
        top.translateYProperty().bind(heightProperty().multiply(cos).negate());
        top.translateZProperty().bind(heightProperty().multiply(sin).negate());

        Group topGroup = new Group(top);

        return topGroup;
    }

    @Override
    public Rectangle getBoundingBox()
    {
        return new Rectangle(getTranslateX(), getTranslateY(), getWidth(), getHeight());
    }
}
