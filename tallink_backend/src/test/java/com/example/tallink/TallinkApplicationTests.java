package com.example.tallink;

import com.example.tallink.entities.Conference;
import com.example.tallink.entities.ConferenceRoom;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.repositories.ConferenceFeedbackRepository;
import com.example.tallink.repositories.ConferenceParticipantRepository;
import com.example.tallink.repositories.ConferenceRepository;
import com.example.tallink.repositories.ConferenceRoomRepository;
import com.example.tallink.services.ConferenceRoomService;
import com.example.tallink.services.ConferenceService;
import com.example.tallink.utils.xConstants;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TallinkApplicationTests {

    @Mock
    ConferenceRoomRepository conferenceRoomRepository;

    @Mock
    ConferenceRepository conferenceRepository;

    @Mock
    ConferenceParticipantRepository conferenceParticipantRepository;

    @Mock
    ConferenceFeedbackRepository conferenceFeedbackRepository;

    @Test
    public void testCreateConferenceRoom() {
        ConferenceRoomService conferenceRoomService = new ConferenceRoomService(conferenceRoomRepository, conferenceRepository);
        ConferenceRoom conferenceRoom = new ConferenceRoom("Conference room 1", xConstants.ROOM_AVAILABLE, "Location 1", (short) 45);

        when(conferenceRoomRepository.save(conferenceRoom)).thenReturn(conferenceRoom);
        TallinkResponse response = conferenceRoomService.crateConferenceRoom(conferenceRoom);
        ConferenceRoom createdConferenceRoom = (ConferenceRoom) response.getData();

        verify(conferenceRoomRepository, times(1)).save(conferenceRoom);
        assertTrue(response.isSuccess());
        assertEquals("Conference room 1", createdConferenceRoom.getName());
    }

    @Test
    public void testFindConferenceRoomByUUID() {
        ConferenceRoomRepository conferenceRoomRepository = mock(ConferenceRoomRepository.class);

        UUID conferenceRoomUUID = UUID.randomUUID();
        ConferenceRoom conferenceRoom = new ConferenceRoom(conferenceRoomUUID, "Conference room 1", xConstants.ROOM_AVAILABLE, "Location 1", (short) 45);

        when(conferenceRoomRepository.findByUuid(conferenceRoomUUID)).thenReturn(conferenceRoom);

        ConferenceRoom foundConferenceRoom = conferenceRoomRepository.findByUuid(conferenceRoomUUID);

        assertEquals(conferenceRoomUUID, foundConferenceRoom.getUuid());
    }

    @Test
    public void testShouldNotCreateConferenceForRoomUnderConstruction() {
        UUID conferenceRoomUUID = UUID.randomUUID();
        ConferenceRoom conferenceRoom = new ConferenceRoom(conferenceRoomUUID, "Conference room 1", xConstants.ROOM_UNDER_CONSTRUCTION, "Location 1", (short) 45);

        when(conferenceRoomRepository.findByUuid(conferenceRoomUUID)).thenReturn(conferenceRoom);

        ConferenceService conferenceService = new ConferenceService(
             conferenceRepository,
             conferenceRoomRepository,
             conferenceParticipantRepository,
             conferenceFeedbackRepository
        );

        Conference conference = new Conference(
                conferenceRoomUUID,
                "Conference name",
                "Conference description",
                new Timestamp(2024, 5, 3, 10, 20, 0, 0),
                new Timestamp(2024, 5, 4, 12, 20, 0, 0),
                false
        );

        TallinkResponse response = conferenceService.isConferenceNotAvailableForSelectedPeriod(conference);

        assertFalse(response.isSuccess());
        assertEquals(response.getMessage(), "Unable to create conference since room is under construction");
    }

    @Test
    public void testShouldNotCreateConferenceIfStartDateTimeIsGreaterThanEndDateTime() {
        ConferenceService conferenceService = new ConferenceService(
                conferenceRepository,
                conferenceRoomRepository,
                conferenceParticipantRepository,
                conferenceFeedbackRepository
        );

        Conference conference = new Conference(
                null,
                "Conference name",
                "Conference description",
                new Timestamp(2024, 5, 4, 10, 20, 0, 0),
                new Timestamp(2024, 5, 3, 12, 20, 0, 0),
                false
        );

        TallinkResponse response = conferenceService.isConferenceNotAvailableForSelectedPeriod(conference);

        assertFalse(response.isSuccess());
        assertEquals(response.getMessage(), "Conference start datetime cannot be after end datetime");
    }
}
