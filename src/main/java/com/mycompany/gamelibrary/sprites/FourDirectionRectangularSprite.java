/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gamelibrary.sprites;

import static com.mycompany.gamelibrary.sprites.Direction.DOWN;
import eu.lestard.advanced_bindings.api.MathBindings;
import java.util.List;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * A sprite with four images for the when moving four different directions
 *
 * @author pramukh
 */
public class FourDirectionRectangularSprite extends RectangularSprite
{

    public static final int DEFAULT_HEIGTH = 50;
    public static final int DEFAULT_WIDTH = 50;
    public static final Direction DEFAULT_DIRECTION = DOWN;

    private final ObjectProperty<Image> leftImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> rightImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> upImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> downImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Direction> currentDirection = new SimpleObjectProperty<>();

    /**
     * takes the first four images from the list and uses them for the
     * constructor
     *
     * @param list
     * @throws IllegalArgumentException
     */
    public FourDirectionRectangularSprite(List<? extends Image> list) throws IllegalArgumentException
    {
        this(list.get(0), list.get(1), list.get(2), list.get(3));

    }

    public FourDirectionRectangularSprite(Image left, Image right, Image up, Image down)
    {
        this(DEFAULT_WIDTH, DEFAULT_HEIGTH, left, right, up, down);
    }

    public FourDirectionRectangularSprite(double width, double height, Image left, Image right, Image up, Image down)
    {
        this(width, height, DEFAULT_DIRECTION, left, right, up, down);
    }

    public FourDirectionRectangularSprite(double width, double height, Direction startingDirection, Image left, Image right, Image up, Image down)
    {
        this(0, 0, width, height, startingDirection, left, right, up, down);
    }

    public FourDirectionRectangularSprite(double x, double y, double width, double height, Direction startingDirection, Image left, Image right, Image up, Image down)
    {
        this(x, y, width, height, DEFAULT_ANGLE, startingDirection, left, right, up, down);
    }

    @SuppressWarnings(value = "OverridableMethodCallInConstructor")
    public FourDirectionRectangularSprite(double x, double y, double width, double height, double angle, Direction startingDirection, Image left, Image right, Image up, Image down)
    {
        super(x, y, width, height, angle);
        currentDirection.set(startingDirection);
        leftImage.setValue(left);
        rightImage.setValue(right);
        upImage.setValue(up);
        downImage.setValue(down);

        ImageView sprite = new ImageView();
        sprite.fitHeightProperty().bind(heightProperty());
        sprite.fitWidthProperty().bind(widthProperty());
        
        Pane container = new Pane(sprite);
        getChildren().add(container);
        
        Rotate rotate = new Rotate(Double.NaN, Rotate.Z_AXIS);
        Translate zShift = new Translate();
        zShift.zProperty().bind(heightProperty().multiply(MathBindings.sin(angleProperty())));
        container.getTransforms().addAll(rotate, zShift);
        
        sprite.imageProperty().bind(toImageProperty(currentDirection));

    }

    private ObjectBinding<Image> toImageProperty(ObjectProperty<Direction> prop)
    {
        return Bindings.createObjectBinding(() ->
        {
            switch (prop.getValue())
            {
                case UP:
                    return upImage.getValue();
                case DOWN:
                    return downImage.getValue();
                case LEFT:
                    return leftImage.getValue();
                case RIGHT:
                    return rightImage.getValue();
                default:
                    throw new IllegalArgumentException("invalid direction" + prop.getValue());
            }
        }, prop);
    }
    
//    @Override
//    public Image getImage()
//    {
//        switch (currentDirection.get())
//            {
//                case UP:
//                    return upImage.getValue();
//                case DOWN:
//                    return downImage.getValue();
//                case LEFT:
//                    return leftImage.getValue();
//                case RIGHT:
//                    return rightImage.getValue();
//                default:
//                    throw new IllegalArgumentException("invalid direction" + currentDirection.getValue());
//            }
//    }
}
