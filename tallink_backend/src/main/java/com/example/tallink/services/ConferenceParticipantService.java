package com.example.tallink.services;

import com.example.tallink.repositories.ConferenceParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class ConferenceParticipantService {

    private final ConferenceParticipantRepository conferenceParticipantRepository;

    public ConferenceParticipantService(ConferenceParticipantRepository conferenceParticipantRepository) {
        this.conferenceParticipantRepository = conferenceParticipantRepository;
    }
}
