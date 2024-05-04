package com.example.tallink.services;

import com.example.tallink.entities.Conference;
import com.example.tallink.entities.ConferenceFeedback;
import com.example.tallink.entities.ConferenceParticipant;
import com.example.tallink.entities.ConferenceRoom;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.repositories.ConferenceFeedbackRepository;
import com.example.tallink.repositories.ConferenceParticipantRepository;
import com.example.tallink.repositories.ConferenceRepository;
import com.example.tallink.repositories.ConferenceRoomRepository;
import com.example.tallink.utils.xConstants;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;

    private final ConferenceRoomRepository conferenceRoomRepository;

    private final ConferenceParticipantRepository conferenceParticipantRepository;

    private final ConferenceFeedbackRepository conferenceFeedbackRepository;

    public ConferenceService(
            ConferenceRepository conferenceRepository,
            ConferenceRoomRepository conferenceRoomRepository,
            ConferenceParticipantRepository conferenceParticipantRepository,
            ConferenceFeedbackRepository conferenceFeedbackRepository
    ) {
        this.conferenceRepository = conferenceRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.conferenceParticipantRepository = conferenceParticipantRepository;
        this.conferenceFeedbackRepository = conferenceFeedbackRepository;
    }

    public Conference getConferenceByUUID(UUID uuid) {
        return this.conferenceRepository.findByUuid(uuid);
    }

    public List<Conference> getAllConferences() {
        return this.conferenceRepository.findAll();
    }

    public List<Conference> getConferencesByRoomUUID(UUID roomUUID) {
        return this.conferenceRepository.findByConferenceRoomUUID(roomUUID);
    }

    public TallinkResponse isConferenceNotAvailableForSelectedPeriod(Conference conference) {
        if (conference.getStartDateTime().after(conference.getEndDateTime())) {
            return new TallinkResponse(false, "Conference start datetime cannot be after end datetime", conference);
        }

        List<Conference> allConferences = this.getConferencesByRoomUUID(conference.getConferenceRoomUUID());
        ConferenceRoom conferenceRoom = this.conferenceRoomRepository.findByUuid(conference.getConferenceRoomUUID());

        if (conferenceRoom.getStatus().equals(xConstants.ROOM_UNDER_CONSTRUCTION)) {
            return new TallinkResponse(false, "Unable to create conference since room is under construction", null);
        }

        if (allConferences
                .stream()
                .anyMatch
                (
                    c ->
                    c.getUuid() != conference.getUuid() && (
                        c.getStartDateTime().compareTo(conference.getStartDateTime()) == 0 ||
                        c.getStartDateTime().compareTo(conference.getStartDateTime()) < 0 &&
                        c.getEndDateTime().compareTo(conference.getStartDateTime()) > 0
                    )
                )
        ) {
            return new TallinkResponse(false, "Room not available for specified datetime period", null);
        }

        return new TallinkResponse(true, null, null);
    }

    public TallinkResponse crateConference(Conference conference) {
        Conference newConference = conferenceRepository.save(conference);
        conferenceRepository.flush();

        return new TallinkResponse(true, "Conference created successfully", newConference);
    }

    public TallinkResponse updateConference(Conference conference) {
        Conference updatedConference = conferenceRepository.save(conference);
        conferenceRepository.flush();

        return new TallinkResponse(true, "Conference updated successfully", updatedConference);
    }

    public Conference cancelConference(UUID uuid) {
        Conference conference = conferenceRepository.findByUuid(uuid);
        conference.setCancelled(true);

        conference = conferenceRepository.save(conference);
        conferenceRepository.flush();

        return conference;
    }

    public TallinkResponse findAvailableConferencesByDateRange(
            Timestamp startDateTime,
            Timestamp endDateTime
    ) {
        List<Conference> conferences = this.conferenceRepository.findAvailableByDateRange(startDateTime, endDateTime);

        return new TallinkResponse(true, null, conferences);
    }

    public TallinkResponse registerParticipant(
            UUID conferenceUuid,
            ConferenceParticipant newParticipant
    ) {
        ConferenceParticipant existingParticipant = this.conferenceParticipantRepository.findByEmailAndConferenceUuid(newParticipant.getEmail(), conferenceUuid);
        if (existingParticipant != null) {
            return new TallinkResponse(false, "Participant with such email already registered", existingParticipant);
        }

        newParticipant.setConferenceUUID(conferenceUuid);
        newParticipant = this.conferenceParticipantRepository.save(newParticipant);
        this.conferenceParticipantRepository.flush();

        return new TallinkResponse(true, "Registered successfully", newParticipant);
    }

    public TallinkResponse cancelRegistration(UUID participantUUID) {
        ConferenceParticipant participant = this.conferenceParticipantRepository.findByUuid(participantUUID);
        if (participant == null) {
            return new TallinkResponse(false, "Participant not found", null);
        }

        UUID conferenceUUID = participant.getConferenceUUID();

        List<ConferenceFeedback> feedbacks = this.conferenceFeedbackRepository.findByConferenceUUID(conferenceUUID);
        this.conferenceFeedbackRepository.deleteAll(feedbacks);
        this.conferenceFeedbackRepository.flush();

        this.conferenceParticipantRepository.delete(participant);
        this.conferenceParticipantRepository.flush();

        Conference conference = this.conferenceRepository.findByUuid(conferenceUUID);
        return new TallinkResponse(true, "Registration successfully cancelled", conference);
    }
}
