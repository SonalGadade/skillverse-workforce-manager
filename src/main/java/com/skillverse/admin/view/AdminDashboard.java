
package com.skillverse.admin.view;

import java.io.InputStream;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.skillverse.admin.controller.AdminDashboardController;
import com.skillverse.admin.controller.DashboardHomeController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminDashboard {

    private final Stage stage;

    private BorderPane root;
    private BorderPane centerContainer;

    private Button dashboardButton;
    private Button employeesButton;
    private Button departmentsButton;
    private Button jobsButton;
    private Button talentMatchingButton;
    private Button analyticsButton;
    private Button activityLogButton;
    private Button aboutUsButton;
    private Button profileButton;
    private Button logoutButton;

    public AdminDashboard(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {

        root = new BorderPane();

        root.setStyle(
            "-fx-background-color:#F5F7FC;"
        );

       

        VBox sidebar = new VBox();

        sidebar.setPrefWidth(245);
        sidebar.setMinWidth(245);
        sidebar.setMaxWidth(245);

        sidebar.setPadding(
            new Insets(25, 15, 20, 15)
        );

        sidebar.setStyle(
            "-fx-background-color:" +
            "linear-gradient(to bottom,#EEF2FF,#E6EAFB);"
        );

    

        HBox logoBox = new HBox(10);

        logoBox.setAlignment(
            Pos.CENTER_LEFT
        );

        ImageView logoImageView = null;
        try {
            InputStream is = getClass().getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = getClass().getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                logoImageView = new ImageView(new Image(is));
                logoImageView.setFitHeight(42);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }
        } catch (Exception e) {
            logoImageView = null;
        }

        Node logoNode;
        if (logoImageView != null) {
            logoNode = logoImageView;
        } else {
            Label logo = new Label("SV");
            logo.setPrefSize(44, 44);
            logo.setAlignment(Pos.CENTER);
            logo.setStyle(
                "-fx-background-color:" +
                "linear-gradient(to bottom right,#526EF4,#7654E8);" +
                "-fx-text-fill:white;" +
                "-fx-font-size:17px;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:12;"
            );
            logoNode = logo;
        }

        VBox logoText = new VBox(2);

        Label brand =
            new Label("SkillVerse");

        brand.setFont(
            Font.font("Arial",18)
        );

        brand.setStyle(
            "-fx-font-weight:bold;"
        );

        brand.setTextFill(
            Color.web("#202A44")
        );

        Label adminPanel =
            new Label("ADMIN PANEL");

        adminPanel.setFont(
            Font.font("Arial",9)
        );

        adminPanel.setTextFill(
            Color.web("#71809E")
        );

        logoText.getChildren().addAll(
            brand,
            adminPanel
        );

        logoBox.getChildren().addAll(
            logoNode,
            logoText
        );

      

        VBox navigation = new VBox(6);

        navigation.setPadding(
            new Insets(20, 0, 20, 0)
        );

        dashboardButton = menuButton("⌂", "Dashboard");
        dashboardButton.setId("dashboardButton");

        employeesButton = menuButton("♙", "Employees");
        employeesButton.setId("employeesButton");

        departmentsButton = menuButton("▣", "Departments");
        departmentsButton.setId("departmentsButton");

        jobsButton = menuButton("▤", "Jobs");
        jobsButton.setId("jobsButton");

        talentMatchingButton = menuButton("◇", "Talent Matching");
        talentMatchingButton.setId("talentMatchingButton");

        analyticsButton = menuButton("▥", "Analytics");
        analyticsButton.setId("analyticsButton");


        activityLogButton = menuButton("☷", "Activity Log");
        activityLogButton.setId("activityLogButton");

        aboutUsButton = menuButton("✦", "About Us");
        aboutUsButton.setId("aboutUsButton");

        navigation.getChildren().addAll(
            dashboardButton,
            employeesButton,
            departmentsButton,
            jobsButton,
            talentMatchingButton,
            analyticsButton,
            activityLogButton,
            aboutUsButton
        );

     

        ScrollPane navigationScroll =
    new ScrollPane();

navigationScroll.setContent(
    navigation
);

navigationScroll.setFitToWidth(true);

navigationScroll.setHbarPolicy(
    ScrollPane.ScrollBarPolicy.NEVER
);

navigationScroll.setVbarPolicy(
    ScrollPane.ScrollBarPolicy.AS_NEEDED
);

// Hide scrollbar visually
navigationScroll.setStyle(
    "-fx-background-color:transparent;" +
    "-fx-background-insets:0;" +
    "-fx-padding:0;"
);

navigationScroll.skinProperty().addListener(
    (obs, oldSkin, newSkin) -> {

        if (newSkin != null) {

            navigationScroll.lookupAll(
                ".scroll-bar"
            ).forEach(node -> {

                node.setStyle(
                    "-fx-opacity:0;" +
                    "-fx-background-color:transparent;"
                );
            });
        }
    }
);

navigationScroll.setFitToHeight(false);

VBox.setVgrow(
    navigationScroll,
    javafx.scene.layout.Priority.ALWAYS
);


        VBox bottom = new VBox(6);

        profileButton =
            menuButton("♙", "Admin Profile");

        logoutButton =
            menuButton("↪", "Logout");

        logoutButton.setStyle(
            logoutStyle()
        );

        bottom.getChildren().addAll(
            profileButton,
            logoutButton
        );

        

        sidebar.getChildren().addAll(
            logoBox,
            navigationScroll,
            bottom
        );


        centerContainer =
            new BorderPane();

        DashboardHomeView homeView =
            new DashboardHomeView(stage);

        DashboardHomeController homeController =
            new DashboardHomeController(
                stage,
                homeView
            );

        centerContainer.setCenter(
            homeController.initialize()
        );

        root.setLeft(sidebar);

        root.setCenter(
            centerContainer
        );

       

        AdminDashboardController controller =
            new AdminDashboardController(
                stage,
                this
            );

        controller.initialize();

        return new Scene(
            root,
            1280,
            720
        );
    }

  

    private Button menuButton(
        String icon,
        String text
    ) {

        Button button =
            new Button(
                icon + "    " + text
            );

        button.setPrefHeight(43);

        button.setMinHeight(43);

        button.setMaxWidth(
            Double.MAX_VALUE
        );

        button.setAlignment(
            Pos.CENTER_LEFT
        );

        button.setPadding(
            new Insets(0, 10, 0, 15)
        );

        button.setStyle(
            inactiveStyle()
        );

        button.setOnMouseEntered(e -> {

            if (!button.getStyle().contains(
                "#5865D8"
            )) {

                button.setStyle(
                    hoverStyle()
                );
            }
        });

        button.setOnMouseExited(e -> {

            if (!button.getStyle().contains(
                "#5865D8"
            )) {

                button.setStyle(
                    inactiveStyle()
                );
            }
        });

        return button;
    }


    public void setActiveButton(Button selectedBtn) {
        Button[] navButtons = new Button[] {
            dashboardButton, employeesButton, departmentsButton,
            jobsButton, talentMatchingButton, analyticsButton,
            activityLogButton, profileButton
        };
        for (Button btn : navButtons) {
            if (btn != null) {
                if (btn == selectedBtn) {
                    btn.setStyle(activeStyle());
                } else {
                    btn.setStyle(inactiveStyle());
                }
            }
        }
    }

    public String activeStyle() {

        return
            "-fx-background-color:" +
            "linear-gradient(to right,#5865D8,#7562E8);" +
            "-fx-text-fill:white;" +
            "-fx-font-family:Arial;" +
            "-fx-font-size:13px;" +
            "-fx-font-weight:bold;" +
            "-fx-background-radius:10;" +
            "-fx-cursor:hand;";
    }

    private String inactiveStyle() {

        return
            "-fx-background-color:transparent;" +
            "-fx-text-fill:#46526D;" +
            "-fx-font-family:Arial;" +
            "-fx-font-size:13px;" +
            "-fx-background-radius:10;" +
            "-fx-cursor:hand;";
    }

    private String hoverStyle() {

        return
            "-fx-background-color:#D8DDF8;" +
            "-fx-text-fill:#3443A5;" +
            "-fx-font-family:Arial;" +
            "-fx-font-size:13px;" +
            "-fx-font-weight:bold;" +
            "-fx-background-radius:10;" +
            "-fx-cursor:hand;";
    }

    private String logoutStyle() {

        return
            "-fx-background-color:transparent;" +
            "-fx-text-fill:#D9536A;" +
            "-fx-font-family:Arial;" +
            "-fx-font-size:13px;" +
            "-fx-background-radius:10;" +
            "-fx-cursor:hand;";
    }

   

    public BorderPane getCenterContainer() {
        return centerContainer;
    }

    public Button getDashboardButton() {
        return dashboardButton;
    }

    public Button getEmployeesButton() {
        return employeesButton;
    }

    public Button getDepartmentsButton() {
        return departmentsButton;
    }


    public Button getJobsButton() {
        return jobsButton;
    }

    public Button getTalentMatchingButton() {
        return talentMatchingButton;
    }

    public Button getAnalyticsButton() {
        return analyticsButton;
    }

    public Button getActivityLogButton() {
        return activityLogButton;
    }

    public Button getAboutUsButton() {
        return aboutUsButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}




