package com.example.tallink.requests;

import java.sql.Timestamp;

public class DateRange {
    private Timestamp startDateTime;

    private Timestamp endDateTime;

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }
}
