package com.skillverse.employee.view;

import com.skillverse.hr.view.Feed;
import javafx.scene.control.ScrollPane;

public class EmployeeFeedView {

    private final Feed feed;

    public EmployeeFeedView() {
        this.feed = new Feed("Employee");
    }

    public ScrollPane createEmployeeFeedContent() {
        return feed.getFeedContentBox();
    }
}
