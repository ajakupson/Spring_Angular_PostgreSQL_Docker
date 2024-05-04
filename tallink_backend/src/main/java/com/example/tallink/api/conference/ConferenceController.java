package com.example.tallink.api.conference;

import com.example.tallink.dto.ConferenceFeedbackDTO;
import com.example.tallink.entities.ConferenceParticipant;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.requests.DateRange;
import com.example.tallink.services.ConferenceFeedbackService;
import com.example.tallink.services.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ConferenceFeedbackService conferenceFeedbackService;

    @PostMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse getAvailableConferencesByDateTimeRange(
            @RequestBody final DateRange dateRange
    ) {
        return conferenceService.findAvailableConferencesByDateRange(
                dateRange.getStartDateTime(),
                dateRange.getEndDateTime()
        );
    }

    @PostMapping("/{uuid}/register")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse registerParticipant(
            @PathVariable UUID uuid,
            @RequestBody final ConferenceParticipant participant
    ) {
        return this.conferenceService.registerParticipant(uuid, participant);
    }

    @PostMapping("/cancel_registration/{participantUuid}")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse cancelRegistration(
            @PathVariable String participantUuid
    ) {
        try {
            UUID uuid = UUID.fromString(participantUuid);
            return this.conferenceService.cancelRegistration(uuid);
        } catch (IllegalArgumentException exception) {
            return new TallinkResponse(false, "Invalid participant UUID", null);
        }
    }

    @PostMapping("/feedback")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse addFeedback(
            @RequestBody final ConferenceFeedbackDTO feedbackDTO
    ) {
        try {
            UUID participantUUID = UUID.fromString(feedbackDTO.getParticipantUUID());
            return this.conferenceFeedbackService.addConferenceFeedback(participantUUID, feedbackDTO.getFeedback());
        } catch (IllegalArgumentException exception) {
            return new TallinkResponse(false, "Invalid participant UUID", null);
        }
    }
}
