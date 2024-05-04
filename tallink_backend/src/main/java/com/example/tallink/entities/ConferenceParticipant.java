package com.example.tallink.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "conference_participant")
public class ConferenceParticipant {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String gender;

    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "conference_uuid")
    private UUID conferenceUUID;

    @ManyToOne
    @JoinColumn(name = "conference_uuid",referencedColumnName = "uuid", insertable = false, updatable = false)
    @JsonIgnoreProperties("conferenceParticipants")
    private Conference conference;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getConferenceUUID() {
        return conferenceUUID;
    }

    public void setConferenceUUID(UUID conferenceUUID) {
        this.conferenceUUID = conferenceUUID;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
