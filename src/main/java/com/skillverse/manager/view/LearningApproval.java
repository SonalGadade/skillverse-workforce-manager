package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.skillverse.manager.model.LearningApprovalModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LearningApproval {

    private Scene learningApprovalScene;
    private ListView<String> listView;

    public Scene getLearningApprovalScene(Runnable callBackActionDashboard,Runnable callBackActionFeed,Runnable callBackActionMyTeam,Runnable callBackActionPerformance,Runnable callBackActionGoalsKPIs,Runnable callBackActionFeedback,Runnable callBackActionInterview,Runnable callBackActionAnalytics,Runnable callBackActionAnonymous,Consumer<LearningApprovalModel> callBackActionApprove,Consumer<LearningApprovalModel> callBackActionReject) {

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color : #F8FAFF;");

        VBox leftMenuBox = new VBox();
        leftMenuBox.setPrefWidth(285);
        leftMenuBox.setStyle("-fx-background-color : #FFFFFF;-fx-border-color : #E6ECF5;-fx-border-width : 0px 1px 0px 0px;");

        Text logoIcon = new Text("SV");
        logoIcon.setStyle("-fx-font-size : 25px;-fx-font-weight : bold;-fx-fill : #287CF5;");

        Text logoText = new Text("SkillVerse");
        logoText.setStyle("-fx-font-size : 21px;-fx-font-weight : bold;-fx-fill : #111827;");

        HBox logoBox = new HBox(10,logoIcon,logoText);
        logoBox.setStyle("-fx-padding : 25px 25px 8px 25px;-fx-alignment : CENTER_LEFT;");

        Text managerPlatformText = new Text("Manager Platform");
        managerPlatformText.setStyle("-fx-font-size : 12px;-fx-fill : #64748B;");

        VBox platformBox = new VBox(managerPlatformText);
        platformBox.setStyle("-fx-padding : 0px 25px 20px 25px;");

        Text workspaceText = new Text("WORKSPACE");
        workspaceText.setStyle("-fx-font-size : 11px;-fx-font-weight : bold;-fx-fill : #64748B;");

        VBox workspaceBox = new VBox(workspaceText);
        workspaceBox.setStyle("-fx-padding : 5px 25px 8px 25px;");

        List<String> menuItems = new ArrayList<>();
        menuItems.add("Dashboard");
        menuItems.add("Feed");
        menuItems.add("My Team");
        menuItems.add("Performance");
        menuItems.add("Goals & KPIs");
        menuItems.add("Feedback");
        menuItems.add("Interviews");
        menuItems.add("Analytics");
        menuItems.add("Learning Approval");
        menuItems.add("Anonymous");

        listView = new ListView<>();
        listView.getItems().addAll(menuItems);
        listView.setPrefWidth(285);
        listView.setStyle("-fx-background-color : #FFFFFF;-fx-border-color : transparent;-fx-control-inner-background : #FFFFFF;-fx-padding : 0px 15px 0px 15px;");

        listView.setCellFactory(param -> new ListCell<String>() {

            @Override
            protected void updateItem(String item,boolean empty) {
                super.updateItem(item,empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color : #FFFFFF;");
                } else {
                    setText(item);
                    setStyle("-fx-background-radius : 10px;-fx-background-insets : 2px 0px 2px 0px;-fx-padding : 11px 15px 11px 15px;-fx-font-size : 13px;");

                    if (isSelected()) {
                        setStyle("-fx-background-color : #EAF3FF;-fx-text-fill : #1769E0;-fx-font-weight : bold;-fx-background-radius : 10px;-fx-background-insets : 2px 0px 2px 0px;-fx-padding : 11px 15px 11px 15px;-fx-font-size : 13px;");
                    } else {
                        setStyle("-fx-background-color : #FFFFFF;-fx-text-fill : #334155;-fx-background-radius : 10px;-fx-background-insets : 2px 0px 2px 0px;-fx-padding : 11px 15px 11px 15px;-fx-font-size : 13px;");
                    }
                }
            }
        });

        listView.getSelectionModel().select("Learning Approval");

        Text analyticsText = new Text("ANALYTICS & REPORTS");
        analyticsText.setStyle("-fx-font-size : 11px;-fx-font-weight : bold;-fx-fill : #64748B;");

        VBox analyticsBox = new VBox(analyticsText);
        analyticsBox.setStyle("-fx-padding : 18px 25px 8px 25px;");

        Text settingsText = new Text("⚙  Settings");
        settingsText.setStyle("-fx-font-size : 13px;-fx-fill : #334155;");

        Text logoutText = new Text("↪  Logout");
        logoutText.setStyle("-fx-font-size : 13px;-fx-fill : #334155;");

        VBox bottomMenuBox = new VBox(18,settingsText,logoutText);
        bottomMenuBox.setStyle("-fx-padding : 18px 25px 25px 25px;-fx-border-color : #E6ECF5;-fx-border-width : 1px 0px 0px 0px;");

        VBox.setVgrow(listView,Priority.ALWAYS);

        leftMenuBox.getChildren().addAll(logoBox,platformBox,workspaceBox,listView,analyticsBox,bottomMenuBox);

        borderPane.setLeft(leftMenuBox);

        Text skillVerseText = new Text("SkillVerse");
        skillVerseText.setStyle("-fx-font-size : 14px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text separatorText = new Text(" / ");
        separatorText.setStyle("-fx-font-size : 14px;-fx-fill : #CBD5E1;");

        Text managerPortalText = new Text("Manager Portal");
        managerPortalText.setStyle("-fx-font-size : 14px;-fx-fill : #64748B;");

        HBox topLeftBox = new HBox(8,skillVerseText,separatorText,managerPortalText);
        topLeftBox.setStyle("-fx-alignment : CENTER_LEFT;");

        Text notificationText = new Text("♧");
        notificationText.setStyle("-fx-font-size : 22px;-fx-fill : #334155;");

        Text managerText = new Text("Manager");
        managerText.setStyle("-fx-font-size : 13px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text managerEmailText = new Text("manager@skillverse.com");
        managerEmailText.setStyle("-fx-font-size : 11px;-fx-fill : #64748B;");

        VBox managerDetailsBox = new VBox(2,managerText,managerEmailText);
        managerDetailsBox.setStyle("-fx-alignment : CENTER_LEFT;");

        Text profileCircle = new Text("M");
        profileCircle.setStyle("-fx-background-color : #DCEBFF;-fx-background-radius : 50px;-fx-padding : 10px 13px 10px 13px;-fx-font-size : 14px;-fx-font-weight : bold;-fx-fill : #1769E0;");

        HBox managerProfileBox = new HBox(10,profileCircle,managerDetailsBox);
        managerProfileBox.setStyle("-fx-alignment : CENTER_LEFT;");

        HBox topRightBox = new HBox(25,notificationText,managerProfileBox);
        topRightBox.setStyle("-fx-alignment : CENTER_RIGHT;");

        HBox.setHgrow(topLeftBox,Priority.ALWAYS);

        HBox topBox = new HBox(topLeftBox,topRightBox);
        topBox.setStyle("-fx-background-color : #FFFFFF;-fx-padding : 15px 25px 15px 35px;-fx-border-color : #E6ECF5;-fx-border-width : 0px 0px 1px 0px;-fx-alignment : CENTER_LEFT;");

        borderPane.setTop(topBox);

        VBox mainBox = new VBox(20);
        mainBox.setStyle("-fx-padding : 28px 30px 30px 30px;");

        Text headingText = new Text("Learning Approval");
        headingText.setStyle("-fx-font-size : 27px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text descriptionText = new Text("Review and approve learning requests from your team.");
        descriptionText.setStyle("-fx-font-size : 13px;-fx-fill : #64748B;");

        VBox headingBox = new VBox(5,headingText,descriptionText);

        VBox pendingCard = createStatCard("8","Pending Requests","#287CF5","#EAF3FF");
        VBox approvedCard = createStatCard("24","Approved","#38BDF8","#EAFBFF");
        VBox learningCard = createStatCard("12","Active Learning","#8B5CF6","#F2EBFF");
        VBox completionCard = createStatCard("87%","Completion Rate","#14B87A","#E7FAF2");

        HBox statsBox = new HBox(18,pendingCard,approvedCard,learningCard,completionCard);
        statsBox.setFillHeight(true);

        Text requestHeading = new Text("Learning Requests");
        requestHeading.setStyle("-fx-font-size : 17px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text requestSubHeading = new Text("Review pending learning requests from employees.");
        requestSubHeading.setStyle("-fx-font-size : 12px;-fx-fill : #64748B;");

        LearningApprovalModel requestModel1 = new LearningApprovalModel("Priya Sharma","Advanced React Development","2 days ago","Pending");
        LearningApprovalModel requestModel2 = new LearningApprovalModel("Rahul Verma","Machine Learning Fundamentals","3 days ago","Pending");
        LearningApprovalModel requestModel3 = new LearningApprovalModel("Sneha Joshi","UI/UX Design Masterclass","5 days ago","Pending");

        VBox request1 = createLearningRequest(requestModel1,callBackActionApprove,callBackActionReject);
        VBox request2 = createLearningRequest(requestModel2,callBackActionApprove,callBackActionReject);
        VBox request3 = createLearningRequest(requestModel3,callBackActionApprove,callBackActionReject);

        VBox requestList = new VBox(12,request1,request2,request3);
        VBox requestBox = new VBox(12,requestHeading,requestSubHeading,requestList);
        requestBox.setPrefWidth(850);
        requestBox.setStyle("-fx-background-color : #FFFFFF;-fx-background-radius : 15px;-fx-border-color : #E5EBF3;-fx-border-radius : 15px;-fx-padding : 20px;");

        Text summaryHeading = new Text("Learning Overview");
        summaryHeading.setStyle("-fx-font-size : 17px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text summary1 = new Text("12  Active Learning Paths");
        summary1.setStyle("-fx-font-size : 13px;-fx-fill : #334155;");

        Text summary2 = new Text("87%  Completion Rate");
        summary2.setStyle("-fx-font-size : 13px;-fx-fill : #334155;");

        Text summary3 = new Text("18  Employees Learning");
        summary3.setStyle("-fx-font-size : 13px;-fx-fill : #334155;");

        VBox summaryBox = new VBox(18,summaryHeading,summary1,summary2,summary3);
        summaryBox.setPrefWidth(300);
        summaryBox.setPrefHeight(220);
        summaryBox.setStyle("-fx-background-color : #FFFFFF;-fx-background-radius : 15px;-fx-border-color : #E5EBF3;-fx-border-radius : 15px;-fx-padding : 20px;");

        HBox bottomBox = new HBox(20,requestBox,summaryBox);

        HBox.setHgrow(requestBox,Priority.ALWAYS);

        mainBox.getChildren().addAll(headingBox,statsBox,bottomBox);

        borderPane.setCenter(mainBox);

        listView.setOnMouseClicked(event -> {

            String selectedItem = listView.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                return;
            }

            if (selectedItem.equals("Dashboard")) {
                callBackActionDashboard.run();
            }
            else if (selectedItem.equals("Feed")) {
                callBackActionFeed.run();
            }
            else if (selectedItem.equals("My Team")) {
                callBackActionMyTeam.run();
            }
            else if (selectedItem.equals("Performance")) {
                callBackActionPerformance.run();
            }
            else if (selectedItem.equals("Goals & KPIs")) {
                callBackActionGoalsKPIs.run();
            }
            else if (selectedItem.equals("Feedback")) {
                callBackActionFeedback.run();
            }
            else if (selectedItem.equals("Interviews")) {
                callBackActionInterview.run();
            }
            else if (selectedItem.equals("Analytics")) {
                callBackActionAnalytics.run();
            }
            else if (selectedItem.equals("Learning Approval")) {
                listView.getSelectionModel().select("Learning Approval");
            }
            else if (selectedItem.equals("Anonymous")) {
                callBackActionAnonymous.run();
            }
        });

        learningApprovalScene = new Scene(borderPane,1200,700);

        return learningApprovalScene;
    }

    private VBox createStatCard(String number,String title,String textColor,String iconBackground) {

        Text iconText = new Text("●");
        iconText.setStyle("-fx-font-size : 17px;-fx-fill : " + textColor + ";");

        VBox iconBox = new VBox(iconText);
        iconBox.setStyle("-fx-background-color : " + iconBackground + ";-fx-background-radius : 50px;-fx-padding : 11px 14px 11px 14px;-fx-alignment : CENTER;");

        Text numberText = new Text(number);
        numberText.setStyle("-fx-font-size : 26px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size : 12px;-fx-fill : #64748B;");

        VBox textBox = new VBox(2,numberText,titleText);
        textBox.setStyle("-fx-alignment : CENTER_LEFT;");

        HBox cardContent = new HBox(14,iconBox,textBox);
        cardContent.setStyle("-fx-alignment : CENTER_LEFT;");

        VBox card = new VBox(cardContent);
        card.setPrefWidth(250);
        card.setPrefHeight(105);
        card.setStyle("-fx-background-color : #FFFFFF;-fx-background-radius : 15px;-fx-border-color : #E5EBF3;-fx-border-radius : 15px;-fx-padding : 18px;");

        return card;
    }

    private VBox createLearningRequest(LearningApprovalModel request,Consumer<LearningApprovalModel> callBackActionApprove,Consumer<LearningApprovalModel> callBackActionReject) {

        String employeeName = request.getEmployeeName();
        String courseName = request.getCourseName();
        String date = request.getDate();

        Text employeeInitial = new Text(employeeName.substring(0,1));
        employeeInitial.setStyle("-fx-font-size : 14px;-fx-font-weight : bold;-fx-fill : #287CF5;");

        VBox employeeIcon = new VBox(employeeInitial);
        employeeIcon.setStyle("-fx-background-color : #EAF3FF;-fx-background-radius : 50px;-fx-padding : 10px 13px 10px 13px;-fx-alignment : CENTER;");

        Text employeeText = new Text(employeeName);
        employeeText.setStyle("-fx-font-size : 14px;-fx-font-weight : bold;-fx-fill : #0F172A;");

        Text courseText = new Text(courseName);
        courseText.setStyle("-fx-font-size : 13px;-fx-fill : #334155;");

        Text dateText = new Text(date);
        dateText.setStyle("-fx-font-size : 11px;-fx-fill : #94A3B8;");

        VBox informationBox = new VBox(4,employeeText,courseText,dateText);
        informationBox.setStyle("-fx-alignment : CENTER_LEFT;");

        HBox employeeBox = new HBox(12,employeeIcon,informationBox);
        employeeBox.setStyle("-fx-alignment : CENTER_LEFT;");

        Button approveButton = new Button("Approve");
        approveButton.setPrefWidth(90);
        approveButton.setPrefHeight(34);
        approveButton.setStyle("-fx-background-color : #287CF5;-fx-text-fill : #FFFFFF;-fx-font-size : 12px;-fx-font-weight : bold;-fx-background-radius : 8px;-fx-cursor : hand;");

        Button rejectButton = new Button("Reject");
        rejectButton.setPrefWidth(80);
        rejectButton.setPrefHeight(34);
        rejectButton.setStyle("-fx-background-color : #FFFFFF;-fx-text-fill : #287CF5;-fx-border-color : #CFE0F7;-fx-border-radius : 8px;-fx-background-radius : 8px;-fx-font-size : 12px;-fx-cursor : hand;");

        HBox buttonsBox = new HBox(8,approveButton,rejectButton);
        buttonsBox.setStyle("-fx-alignment : CENTER_RIGHT;");

        HBox.setHgrow(employeeBox,Priority.ALWAYS);

        HBox row = new HBox(15,employeeBox,buttonsBox);
        row.setStyle("-fx-alignment : CENTER_LEFT;-fx-padding : 10px;");

        VBox requestBox = new VBox(row);
        requestBox.setPrefHeight(80);
        requestBox.setStyle("-fx-background-color : #F9FBFE;-fx-background-radius : 10px;-fx-border-color : #E5EBF3;-fx-border-radius : 10px;");

        approveButton.setOnAction(event -> {
            callBackActionApprove.accept(request);
            courseText.setText(courseName + " • Approved");
            courseText.setStyle("-fx-font-size : 13px;-fx-fill : #0EA568;-fx-font-weight : bold;");
        });

        rejectButton.setOnAction(event -> {
            callBackActionReject.accept(request);
            courseText.setText(courseName + " • Rejected");
            courseText.setStyle("-fx-font-size : 13px;-fx-fill : #64748B;-fx-font-weight : bold;");
        });

        return requestBox;
    }

    public Scene getLearningApprovalScene() {
        return learningApprovalScene;
    }
}