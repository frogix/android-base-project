package org.hse.android;

public class ScheduleItemHeader extends ScheduleItem {
    private String title;

    public ScheduleItemHeader(String titleText)
    {
        super();
        this.title = titleText;
    }

    public String getTitle() { return this.title; }
    public void setTitle(String titleText){ this.title = titleText; }
}