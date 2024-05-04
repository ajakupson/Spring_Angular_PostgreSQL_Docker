package com.example.tallink.dto;

public class ConferenceFeedbackDTO {

    private String participantUUID;

    private String feedback;

    public String getParticipantUUID() {
        return participantUUID;
    }

    public void setParticipantUUID(String participantUUID) {
        this.participantUUID = participantUUID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
