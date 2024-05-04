package com.example.tallink.repositories;

import com.example.tallink.entities.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, String> {

    public ConferenceRoom findByUuid(UUID roomUUID);
}
