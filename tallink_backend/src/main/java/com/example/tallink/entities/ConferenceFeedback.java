package com.example.tallink.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "conference_feedback")
public class ConferenceFeedback {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID uuid;

    @Column(name = "conference_uuid")
    private UUID conferenceUUID;

    @Column(name = "participant_uuid")
    private UUID participantUUID;

    private String feedback;

    @OneToOne
    @JoinColumn(name = "conference_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private Conference conference;

    @OneToOne
    @JoinColumn(name = "participant_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private ConferenceParticipant conferenceParticipant;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getConferenceUUID() {
        return conferenceUUID;
    }

    public void setConferenceUUID(UUID conferenceUUID) {
        this.conferenceUUID = conferenceUUID;
    }

    public UUID getParticipantUUID() {
        return participantUUID;
    }

    public void setParticipantUUID(UUID participantUUID) {
        this.participantUUID = participantUUID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public ConferenceParticipant getConferenceParticipant() {
        return conferenceParticipant;
    }

    public void setConferenceParticipant(ConferenceParticipant conferenceParticipant) {
        this.conferenceParticipant = conferenceParticipant;
    }
}
