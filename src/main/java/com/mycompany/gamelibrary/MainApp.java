package com.mycompany.gamelibrary;

import com.mycompany.gamelibrary.UI.*;
import eu.lestard.advanced_bindings.api.MathBindings;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application
{

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    public final DoubleProperty buildingAngle = new SimpleDoubleProperty(70);
    private final DoubleProperty sin = new SimpleDoubleProperty();

    private final DoubleProperty cameraAngle = new SimpleDoubleProperty(25);

    private final DoubleProperty cameraSin = new SimpleDoubleProperty();

    
    {
        cameraSin.bind(MathBindings.sin(MathBindings.toRadians(cameraAngle)));
    }
    private final DoubleProperty cameraCos = new SimpleDoubleProperty();

    
    {
        cameraCos.bind(MathBindings.cos(MathBindings.toRadians(cameraAngle)));
    }

    private StringProperty fps = new SimpleStringProperty();

    private final DoubleProperty notreDameXTranslate = new SimpleDoubleProperty(200);
    private final DoubleProperty notreDameYTranslate = new SimpleDoubleProperty(200);

    private final DoubleProperty width = new SimpleDoubleProperty(200);
    private final DoubleProperty height = new SimpleDoubleProperty(300);
    private final DoubleProperty depth = new SimpleDoubleProperty(400);

    private final DoubleProperty zoom = new SimpleDoubleProperty(500);

    @Override
    public void start(Stage stage) throws Exception
    {
        VBox root = new VBox();

        Group subRoot = new Group();

        VBox sliderArea = sliderArea(subRoot);
        TitledPane sliderWrapper = new FoucsRejectingTitledPane("controls", sliderArea, subRoot);
        //http://stackoverflow.com/questions/28287398/what-is-the-preferred-way-of-getting-the-frame-rate-of-a-javafx-application
        AnimationTimer frameRateMeter = new AnimationTimer()
        {

            @Override
            public void handle(long now)
            {
                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0)
                {
                    arrayFilled = true;
                }
                if (arrayFilled)
                {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    fps.setValue(String.format("Current frame rate: %.1f", frameRate));
                }
            }
        };
        frameRateMeter.start();

        root.getChildren().add(sliderWrapper);
        ImageView image = new ImageView("https://www.petfinder.com/wp-content/uploads/2012/11/140272627-grooming-needs-senior-cat-632x475.jpg");

        image.setFitWidth(50);
        image.setFitHeight(50 * 475 / 632.0);
        Rotate imageRotate = new Rotate(0, Rotate.X_AXIS);
        imageRotate.angleProperty().bind(cameraAngle);
        image.getTransforms().add(imageRotate);
        image.translateZProperty().bind(cameraSin.multiply(50 * 475).divide(632.0).negate());
        image.setTranslateX(500);
        image.setTranslateY(500);
        subRoot.getChildren().add(image);

//        setUpConstraints(root);
        Scene scene = new Scene(root, 600, 600);

        //SquareBuilding secondBuilding = new SquareBuilding(new Image("http://cloud.graphicleftovers.com/15054/81254/color-square-tiles-pattern.jpg"));
        //secondBuilding.angleProperty().bind(buildingAngle);
        //secondBuilding.sizeProperty().bind(width.multiply(3));
        //secondBuilding.translateXProperty().bind(notreDameXTranslate.add(width.multiply(1.5)));
        //secondBuilding.translateYProperty().bind(notreDameYTranslate.add(width.multiply(1.5)));
        //subRoot.getChildren().add(secondBuilding);
        Image BuildingImage = new Image("http://cloud.graphicleftovers.com/15054/81254/color-square-tiles-pattern.jpg");

        SubScene subScene = new SubScene(subRoot, 350, 250, true, SceneAntialiasing.DISABLED);
        AmbientLight sceneLight = new AmbientLight(Color.RED);
        sceneLight.setLightOn(true);
        ((Group) subScene.getRoot()).getChildren().add(sceneLight);

        Pane subPane = new Pane(subScene);

        subScene.widthProperty().bind(subPane.widthProperty());
        subScene.heightProperty().bind(subPane.heightProperty());
        subPane.minWidthProperty().set(200);
        subPane.minHeightProperty().set(200);
        subPane.setPrefHeight(Integer.MAX_VALUE - 1);
        //subPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        PerspectiveCamera camera = new PerspectiveCamera();

        camera.fieldOfViewProperty().set(20);
        camera.translateZProperty().bind(zoom.negate());
        Rotate cameraRotate = new Rotate(0, Rotate.X_AXIS);
        cameraRotate.angleProperty().bind(cameraAngle);
        camera.getTransforms().add(cameraRotate);
        camera.setFarClip(4000);

        IntegerProperty cameraXShift = new SimpleIntegerProperty(0);
        IntegerProperty cameraYShift = new SimpleIntegerProperty(0);

        camera.translateXProperty().bind(stage.widthProperty().negate().divide(2).add(cameraXShift).add(500));
        camera.translateYProperty().bind((cameraYShift).add(500));

        subScene.setCamera(camera);

        BooleanProperty upKeyDown = new SimpleBooleanProperty(false);
        BooleanProperty downKeyDown = new SimpleBooleanProperty(false);
        BooleanProperty leftKeyDown = new SimpleBooleanProperty(false);
        BooleanProperty rightKeyDown = new SimpleBooleanProperty(false);

        BooleanProperty wKeyDown = new SimpleBooleanProperty(false);
        BooleanProperty aKeyDown = new SimpleBooleanProperty(false);
        BooleanProperty sKeyDown = new SimpleBooleanProperty(false);
        BooleanProperty dKeyDown = new SimpleBooleanProperty(false);

        IntegerProperty startMouseX = new SimpleIntegerProperty();
        IntegerProperty startMouseY = new SimpleIntegerProperty();

//        Property<AxisRotates> currentRotation = new SimpleObjectProperty<>();
//
//        subScene.onMousePressedProperty().set(e
//                -> 
//                {
//                    startMouseX.set((int) e.getSceneX());
//                    startMouseY.set((int) e.getSceneY());
//                    AxisRotates r = new AxisRotates();
//                    camera.getTransforms().addAll(r);
//                    currentRotation.setValue(r);
//                    //System.out.println("pressed");
//        });
//
//        subScene.onMouseDraggedProperty().set(e
//                -> 
//                {
//                    //System.out.println("dragged");
//                    if (currentRotation.getValue() != null)
//                    {
//                        currentRotation.getValue().setX((int) e.getSceneY() - startMouseY.get());
//                        currentRotation.getValue().setY(-(int) e.getSceneX() + startMouseX.get());
//                    }
//                    else
//                    {
//                    }
//        });
//
//        subScene.onMouseReleasedProperty().set(e
//                -> 
//                {
//                    currentRotation.setValue(null);
//                    //System.out.println("released");
//        });

        subScene.onKeyPressedProperty().set((e)
                -> 
                {
                    //System.out.println("Key Pressed");

                    switch (e.getCode())
                    {
                        case UP:
                            upKeyDown.setValue(true);
                            break;
                        case LEFT:
                            leftKeyDown.setValue(true);
                            break;
                        case DOWN:
                            downKeyDown.setValue(true);
                            break;
                        case RIGHT:
                            rightKeyDown.setValue(true);
                            break;
                        case W:
                            wKeyDown.setValue(true);
                            break;
                        case A:
                            aKeyDown.setValue(true);
                            break;
                        case S:
                            sKeyDown.setValue(true);
                            break;
                        case D:
                            dKeyDown.setValue(true);
                            break;
                    }

                    if (e.getCode() == KeyCode.P)
                    {
                        //notreDameRightX.setAngle(notreDameRightX.getAngle() + 1);
                    }
                    e.consume();
        });

        subScene.onKeyReleasedProperty().set(e
                -> 
                {
                    switch (e.getCode())
                    {
                        case UP:
                            upKeyDown.setValue(false);
                            break;
                        case LEFT:
                            leftKeyDown.setValue(false);
                            break;
                        case DOWN:
                            downKeyDown.setValue(false);
                            break;
                        case RIGHT:
                            rightKeyDown.setValue(false);
                            break;
                        case W:
                            wKeyDown.setValue(false);
                            break;
                        case A:
                            aKeyDown.setValue(false);
                            break;
                        case S:
                            sKeyDown.setValue(false);
                            break;
                        case D:
                            dKeyDown.setValue(false);
                            break;
                    }

        });

        subScene.setOnMouseClicked(e -> subScene.requestFocus());

        Timeline t = new Timeline(new KeyFrame(new Duration(5), "animation", e
                                               -> 
                                               {
                                                   if (upKeyDown.getValue() || wKeyDown.getValue())
                                                   {
                                                       cameraYShift.set(cameraYShift.get() - 1);
                                                       image.setTranslateY(image.getTranslateY() - 1);
                                                   }
                                                   if (downKeyDown.getValue() || sKeyDown.getValue())
                                                   {
                                                       cameraYShift.set(cameraYShift.get() + 1);
                                                       image.setTranslateY(image.getTranslateY() + 1);
                                                   }
                                                   if (leftKeyDown.getValue() || aKeyDown.getValue())
                                                   {
                                                       cameraXShift.set(cameraXShift.get() - 1);
                                                       image.setTranslateX(image.getTranslateX() - 1);
                                                   }
                                                   if (rightKeyDown.getValue() || dKeyDown.getValue())
                                                   {
                                                       cameraXShift.set(cameraXShift.get() + 1);
                                                       image.setTranslateX(image.getTranslateX() + 1);
                                                   }

                                       }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();

        GridPane grid = grid(10);
        grid.setGridLinesVisible(true);

        grid.setMinSize(1000, 1000);
        //grid.setBackground(new Background(new BackgroundImage(new Image("http://www.myfreetextures.com/wp-content/uploads/2015/01/grass-free-texture.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        subRoot.getChildren().add(grid);

        //grid.add(s, 0, 0);
        GridPane grid2 = grid(10);
        grid2.setGridLinesVisible(true);

        grid2.setMinSize(1000, 1000);
        //grid.setBackground(new Background(new BackgroundImage(new Image("http://www.myfreetextures.com/wp-content/uploads/2015/01/grass-free-texture.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        grid2.getTransforms().add(new Rotate(-90, Rotate.X_AXIS));
        subRoot.getChildren().add(grid2);

        root.getChildren().add(subPane);

        subRoot.requestFocus();
        stage.titleProperty().bind(fps);
        stage.setScene(scene);
        stage.show();
    }

    private VBox sliderArea(Node focusser)
    {

        Label xLabel = new Label("X Offset");
        Slider xSlider = new FocusRejectingSlider(0, 1000, notreDameXTranslate.getValue(), focusser);
        xSlider.showTickMarksProperty().set(true);
        xSlider.showTickLabelsProperty().set(true);
        xSlider.setMajorTickUnit(100);
        notreDameXTranslate.bind(xSlider.valueProperty());

        Label yLabel = new Label("Y Offset");
        Slider ySlider = new FocusRejectingSlider(0, 1000, notreDameYTranslate.getValue(), focusser);
        ySlider.showTickMarksProperty().set(true);
        ySlider.showTickLabelsProperty().set(true);
        ySlider.setMajorTickUnit(100);
        notreDameYTranslate.bind(ySlider.valueProperty());

        Label buildingAngleLabel = new Label("Building Angle");
        Slider buildingAngleSlider = new FocusRejectingSlider(0, 90, buildingAngle.getValue(), focusser);
        buildingAngle.bind(buildingAngleSlider.valueProperty());

        Label cameraAngleLabel = new Label("Camera Angle");
        Slider cameraAngleSlider = new FocusRejectingSlider(0, 90, cameraAngle.getValue(), focusser);
        cameraAngle.bind(cameraAngleSlider.valueProperty());

        Label widthLabel = new Label("Width");
        Slider widthSlider = new FocusRejectingSlider(Double.MIN_NORMAL, 1000, width.getValue(), focusser);
        widthSlider.showTickMarksProperty().set(true);
        widthSlider.showTickLabelsProperty().set(true);
        widthSlider.setMajorTickUnit(100);
        width.bind(widthSlider.valueProperty());

        Label heightLabel = new Label("Height");
        Slider heightSlider = new FocusRejectingSlider(Double.MIN_NORMAL, 1000, width.getValue(), focusser);
        heightSlider.showTickMarksProperty().set(true);
        heightSlider.showTickLabelsProperty().set(true);
        heightSlider.setMajorTickUnit(100);
        height.bind(heightSlider.valueProperty());

        Label depthLabel = new Label("Depth");
        Slider depthSlider = new FocusRejectingSlider(Double.MIN_NORMAL, 1000, width.getValue(), focusser);
        depthSlider.showTickMarksProperty().set(true);
        depthSlider.showTickLabelsProperty().set(true);
        depthSlider.setMajorTickUnit(100);
        depth.bind(depthSlider.valueProperty());

        Label zoomLabel = new Label("Zoom");
        Slider zoomSlider = new FocusRejectingSlider(-500, 5000, zoom.getValue(), focusser);
        zoom.bind(zoomSlider.valueProperty());

        return new VBox(xLabel, xSlider, yLabel, ySlider, buildingAngleLabel,
                        buildingAngleSlider, cameraAngleLabel, cameraAngleSlider,
                        widthLabel, widthSlider, heightLabel, heightSlider,
                        depthLabel, depthSlider, zoomLabel, zoomSlider);
    }

    private GridPane grid(double size)
    {
        GridPane temp = new GridPane();
        List<ColumnConstraints> cList = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100 / size);
            cList.add(cc);
        }
        temp.getColumnConstraints().addAll(cList);

        List<RowConstraints> rList = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100 / size);
            rList.add(rc);
        }
        temp.getRowConstraints().addAll(rList);

        return temp;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
