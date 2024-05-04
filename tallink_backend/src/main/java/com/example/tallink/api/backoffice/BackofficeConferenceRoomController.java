package com.example.tallink.api.backoffice;

import com.example.tallink.entities.ConferenceRoom;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.services.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/backoffice/conference_room")
public class BackofficeConferenceRoomController {

    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @GetMapping("/get_all")
    @ResponseStatus(HttpStatus.OK)
    public List<ConferenceRoom> getAllConferenceRooms() {
        return this.conferenceRoomService.getAllConferenceRooms();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse createRoom(@RequestBody final ConferenceRoom room) {
        return conferenceRoomService.crateConferenceRoom(room);
    }

    @PostMapping("/{uuid}/change_status")
    @ResponseStatus(HttpStatus.OK)
    public ConferenceRoom updateStatus(@RequestBody final String status, @PathVariable UUID uuid) {
        return conferenceRoomService.updateStatusByUUID(uuid, status);
    }

    @PostMapping("/{uuid}/change_max_capacity")
    @ResponseStatus(HttpStatus.OK)
    public TallinkResponse updateMaxCapacity(@RequestBody final Short capacity, @PathVariable UUID uuid) {
        return conferenceRoomService.updateMaxCapacityByUUID(uuid, capacity);
    }
}
