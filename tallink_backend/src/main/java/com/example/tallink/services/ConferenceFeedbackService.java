package com.example.tallink.services;

import com.example.tallink.entities.Conference;
import com.example.tallink.entities.ConferenceFeedback;
import com.example.tallink.entities.ConferenceParticipant;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.repositories.ConferenceFeedbackRepository;
import com.example.tallink.repositories.ConferenceParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConferenceFeedbackService {

    private final ConferenceFeedbackRepository conferenceFeedbackRepository;

    private final ConferenceParticipantRepository conferenceParticipantRepository;

    public ConferenceFeedbackService(ConferenceFeedbackRepository conferenceFeedbackRepository, ConferenceParticipantRepository conferenceParticipantRepository) {
        this.conferenceFeedbackRepository = conferenceFeedbackRepository;
        this.conferenceParticipantRepository = conferenceParticipantRepository;
    }

    public TallinkResponse addConferenceFeedback(UUID participantUUID, String feedback) {
        ConferenceParticipant existingParticipant = this.conferenceParticipantRepository.findByUuid(participantUUID);
        if (existingParticipant == null) {
            return new TallinkResponse(false, "Participant not found", null);
        }

        Conference conference = existingParticipant.getConference();
        ConferenceFeedback existingFeedback = this.conferenceFeedbackRepository.findByConferenceUUIDAndParticipantUUID(conference.getUuid(), participantUUID);

        if (existingFeedback != null) {
            return new TallinkResponse(false, "Feedback for this conference already added", null);
        }

        ConferenceFeedback conferenceFeedback = new ConferenceFeedback();
        conferenceFeedback.setConferenceUUID(conference.getUuid());
        conferenceFeedback.setParticipantUUID(participantUUID);
        conferenceFeedback.setFeedback(feedback);

        conferenceFeedback = this.conferenceFeedbackRepository.save(conferenceFeedback);
        this.conferenceFeedbackRepository.flush();

        return new TallinkResponse(true, "Feedback successfully added", conferenceFeedback);
    }

    public List<ConferenceFeedback> getConferenceFeedbacksByConferenceUUID(UUID uuid) {
        return this.conferenceFeedbackRepository.findByConferenceUUID(uuid);
    }

    public List<ConferenceFeedback> hideConferenceParticipantLastName(List<ConferenceFeedback> conferenceFeedbacks) {
        conferenceFeedbacks.forEach(cf -> {
            ConferenceParticipant participant = cf.getConferenceParticipant();
            participant.setLastName(participant.getLastName().charAt(0) + "*".repeat(participant.getLastName().length() - 1));
            cf.setConferenceParticipant(participant);
        });

        return conferenceFeedbacks;
    }
}
