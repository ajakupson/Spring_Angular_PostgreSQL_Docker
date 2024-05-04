package com.example.tallink.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "conference_room")
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID uuid;

    private String name;

    private String status;

    private String location;

    private Short maxCapacity;

    public ConferenceRoom() {}

    public ConferenceRoom(String name, String status, String location, Short maxCapacity) {
        this.name = name;
        this.status = status;
        this.location = location;
        this.maxCapacity = maxCapacity;
    }

    public ConferenceRoom(UUID uuid, String name, String status, String location, Short maxCapacity) {
        this.uuid = uuid;
        this.name = name;
        this.status = status;
        this.location = location;
        this.maxCapacity = maxCapacity;
    }

    @OneToMany
    @JoinColumn(name = "conference_room_uuid", referencedColumnName = "uuid")
    @JsonIgnoreProperties("conferenceRoom")
    private Set<Conference> conferences;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Short getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }
}
