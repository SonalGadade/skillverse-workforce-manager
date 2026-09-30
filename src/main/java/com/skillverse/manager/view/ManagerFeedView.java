package com.skillverse.manager.view;

import com.skillverse.hr.view.Feed;
import javafx.scene.control.ScrollPane;

public class ManagerFeedView {

    private final Feed feed;

    public ManagerFeedView() {
        this.feed = new Feed("Manager");
    }

    public ScrollPane createManagerFeedContent() {
        return feed.getFeedContentBox();
    }
}
