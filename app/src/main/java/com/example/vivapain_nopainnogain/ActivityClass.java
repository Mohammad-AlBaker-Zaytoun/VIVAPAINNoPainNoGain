package com.example.vivapain_nopainnogain;

public class ActivityClass {
    private int ActivityId;
    private String ActivityName;

    public ActivityClass() {
        ActivityId = -1;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }
}
