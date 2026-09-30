package com.skillverse.hr.view;

import javafx.scene.control.ScrollPane;

public class HRFeedView {

    private final Feed feed;

    public HRFeedView() {
        this.feed = new Feed("HR");
    }

    public ScrollPane createHRFeedContent() {
        return feed.getFeedContentBox();
    }
}
