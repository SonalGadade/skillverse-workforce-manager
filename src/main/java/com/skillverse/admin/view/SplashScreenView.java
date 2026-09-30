package com.skillverse.admin.view;





import com.skillverse.admin.controller.SplashScreenController;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class SplashScreenView extends Application {

    private ImageView logo;
    private Label title;
    private Label subtitle;

    @Override
    public void start(Stage stage) {


        Image image = null;
        try {
            InputStream is = getClass().getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = getClass().getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                image = new Image(is);
            }
        } catch (Exception e) {
            image = null;
        }

        if (image != null) {
            logo = new ImageView(image);
            logo.setFitWidth(410);
            logo.setPreserveRatio(true);
            logo.setSmooth(true);
        } else {
            logo = new ImageView();
        }


        title = new Label("SkillVerse AI");

        title.setFont(
                Font.font("Arial", 38)
        );

        title.setStyle(
                "-fx-font-weight:bold;"
        );

        title.setTextFill(
                Color.web("#17213D")
        );


        subtitle = new Label(
                "LEARN  •  GROW  •  LEAD"
        );

        subtitle.setFont(
                Font.font("Arial", 15)
        );

        subtitle.setTextFill(
                Color.web("#5D63B8")
        );


        VBox content = new VBox(
                8,
                logo,
                title,
                subtitle
        );

        content.setAlignment(Pos.CENTER);


        StackPane root =
                new StackPane(content);

        root.setStyle(
                "-fx-background-color:linear-gradient(" +
                "to bottom right," +
                "#DCEBFF,#E7E1FF,#CFE6FF" +
                ");"
        );


        Scene scene =
                new Scene(
                        root,
                        1280,
                        720
                );

        stage.setTitle("SkillVerse AI");

        stage.setScene(scene);

        stage.show();


        SplashScreenController controller =
                new SplashScreenController(
                        stage,
                        this
                );

        controller.startSplash();
    }

    public ImageView getLogo() {
        return logo;
    }

    public Label getTitle() {
        return title;
    }

    public Label getSubtitle() {
        return subtitle;
    }
}