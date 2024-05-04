package com.example.tallink.repositories;

import com.example.tallink.entities.ConferenceParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConferenceParticipantRepository extends JpaRepository<ConferenceParticipant, String> {
    List<ConferenceParticipant> findByEmail(String email);

    ConferenceParticipant findByEmailAndConferenceUuid(String email, UUID conferenceUUID);

    ConferenceParticipant findByUuid(UUID uuid);
}
