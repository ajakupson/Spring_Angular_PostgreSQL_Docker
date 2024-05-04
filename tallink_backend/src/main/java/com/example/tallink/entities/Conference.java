package com.example.tallink.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "conference")
public class Conference {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID uuid;

    @Column(name = "conference_room_uuid")
    private UUID conferenceRoomUUID;

    private String name;

    private String description;

    @Column(name = "start_datetime")
    private Timestamp startDateTime;

    @Column(name = "end_datetime")
    private Timestamp endDateTime;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @ManyToOne
    @JoinColumn(name = "conference_room_uuid",referencedColumnName = "uuid", insertable = false, updatable = false)
    @JsonIgnoreProperties("conferences")
    private ConferenceRoom conferenceRoom;

    @OneToMany
    @JoinColumn(name = "conference_uuid", referencedColumnName = "uuid")
    @JsonIgnoreProperties("conference")
    private Set<ConferenceParticipant> conferenceParticipants;

    public Conference() {}

    public Conference(
            UUID conferenceRoomUUID,
            String name,
            String description,
            Timestamp startDateTime,
            Timestamp endDateTime,
            Boolean isCancelled
    ) {
        this.conferenceRoomUUID = conferenceRoomUUID;
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isCancelled = isCancelled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getConferenceRoomUUID() {
        return conferenceRoomUUID;
    }

    public void setConferenceRoomUUID(UUID conferenceRoomUUID) {
        this.conferenceRoomUUID = conferenceRoomUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    public Set<ConferenceParticipant> getConferenceParticipants() {
        return conferenceParticipants;
    }

    public void setConferenceParticipants(Set<ConferenceParticipant> conferenceParticipants) {
        this.conferenceParticipants = conferenceParticipants;
    }
}
