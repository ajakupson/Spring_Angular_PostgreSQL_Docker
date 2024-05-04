package com.example.tallink.repositories;

import com.example.tallink.entities.ConferenceFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConferenceFeedbackRepository extends JpaRepository<ConferenceFeedback, String> {

    ConferenceFeedback findByConferenceUUIDAndParticipantUUID(UUID conferenceUUID, UUID participantUUID);

    List<ConferenceFeedback> findByConferenceUUID(UUID conferenceUUID);
}
