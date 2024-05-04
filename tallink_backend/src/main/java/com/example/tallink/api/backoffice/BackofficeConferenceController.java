package com.example.tallink.api.backoffice;

import com.example.tallink.entities.Conference;
import com.example.tallink.entities.ConferenceFeedback;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.services.ConferenceFeedbackService;
import com.example.tallink.services.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/backoffice/conference")
public class BackofficeConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ConferenceFeedbackService conferenceFeedbackService;

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Conference getConferenceByUUID(@PathVariable UUID uuid) {
        return this.conferenceService.getConferenceByUUID(uuid);
    }

    @GetMapping("/get_all")
    @ResponseStatus(HttpStatus.OK)
    public List<Conference> getAllConferenceRooms() {
        return this.conferenceService.getAllConferences();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse createConference(@RequestBody final Conference conference) {
        TallinkResponse tallinkResponse = conferenceService.isConferenceNotAvailableForSelectedPeriod(conference);
        if (!tallinkResponse.isSuccess()) {
            return tallinkResponse;
        }

        return conferenceService.crateConference(conference);
    }

    @PostMapping("/cancel/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Conference cancelConference(@PathVariable UUID uuid) {
        return conferenceService.cancelConference(uuid);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse updateConference(@RequestBody final Conference conference) {
        TallinkResponse tallinkResponse = conferenceService.isConferenceNotAvailableForSelectedPeriod(conference);
        if (!tallinkResponse.isSuccess()) {
            return tallinkResponse;
        }

        return conferenceService.updateConference(conference);
    }

    @GetMapping("/{uuid}/feedbacks")
    @ResponseStatus(HttpStatus.OK)
    public List<ConferenceFeedback> getAllConferenceFeedbacks(
            @PathVariable UUID uuid
    ) {
        List<ConferenceFeedback> conferenceFeedbacks = this.conferenceFeedbackService.getConferenceFeedbacksByConferenceUUID(uuid);
        return this.conferenceFeedbackService.hideConferenceParticipantLastName(conferenceFeedbacks);
    }
}
