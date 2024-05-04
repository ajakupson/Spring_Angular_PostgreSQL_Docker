package com.example.tallink.services;

import com.example.tallink.entities.Conference;
import com.example.tallink.entities.ConferenceRoom;
import com.example.tallink.helpers.TallinkResponse;
import com.example.tallink.repositories.ConferenceRepository;
import com.example.tallink.repositories.ConferenceRoomRepository;
import com.example.tallink.utils.xConstants;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceRepository conferenceRepository;

    public ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository, ConferenceRepository conferenceRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public List<ConferenceRoom> getAllConferenceRooms() {
        return this.conferenceRoomRepository.findAll();
    }

    public TallinkResponse crateConferenceRoom(ConferenceRoom room) {
        ConferenceRoom newRoom = conferenceRoomRepository.save(room);
        conferenceRoomRepository.flush();

        return new TallinkResponse(true, "Room successfully created", newRoom);
    }

    public ConferenceRoom updateStatusByUUID(UUID uuid, String status) {
        ConferenceRoom conferenceRoom = this.conferenceRoomRepository.findByUuid(uuid);
        conferenceRoom.setStatus(status);

        this.conferenceRoomRepository.save(conferenceRoom);
        this.conferenceRoomRepository.flush();

        if (status.equals(xConstants.ROOM_UNDER_CONSTRUCTION)) {
            Set<Conference> conferences = conferenceRoom.getConferences();
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            conferences.forEach(c -> {
                if (c.getStartDateTime().after(currentTimestamp)) {
                    c.setCancelled(true);

                    this.conferenceRepository.save(c);
                }
            });

            this.conferenceRepository.flush();
        }

        return conferenceRoom;
    }

    public TallinkResponse updateMaxCapacityByUUID(UUID uuid, Short capacity) {
        ConferenceRoom conferenceRoom = this.conferenceRoomRepository.findByUuid(uuid);
        Set<Conference> conferences = conferenceRoom.getConferences();
        if (conferences.stream().anyMatch(c -> c.getConferenceParticipants().size() > capacity)) {
            return new TallinkResponse(false, "Cannot update room max capacity since conference registered participants count is more than new capacity", conferenceRoom);
        }

        conferenceRoom.setMaxCapacity(capacity);
        this.conferenceRoomRepository.save(conferenceRoom);
        this.conferenceRoomRepository.flush();

        return new TallinkResponse(true, "Room max capacity successfully updated", conferenceRoom);

    }
}
