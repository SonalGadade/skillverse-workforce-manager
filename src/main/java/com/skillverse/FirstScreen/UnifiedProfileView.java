package com.skillverse.FirstScreen;

import com.skillverse.CommonFeatures.ProfileView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UnifiedProfileView {

    private final String roleName;
    private final Runnable onBackToDashboard;
    private final Runnable onProfileUpdated;
    private VBox container;

    public UnifiedProfileView(String roleName, Runnable onBackToDashboard) {
        this(roleName, onBackToDashboard, null);
    }

    public UnifiedProfileView(String roleName, Runnable onBackToDashboard, Runnable onProfileUpdated) {
        this.roleName = roleName != null ? roleName : "User";
        this.onBackToDashboard = onBackToDashboard;
        this.onProfileUpdated = onProfileUpdated;
    }

    public VBox getViewContainer() {
        if (container == null) {
            container = new VBox();
            renderProfileView();
        }
        VBox.setVgrow(container, Priority.ALWAYS);
        return container;
    }

    public void renderProfileView() {
        if (container == null) {
            container = new VBox();
        }
        container.getChildren().clear();
        container.getChildren().add(new ProfileView());
    }

    public void renderEditProfileView() {
        renderProfileView();
    }
}
