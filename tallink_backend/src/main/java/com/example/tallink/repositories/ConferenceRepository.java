package com.example.tallink.repositories;

import com.example.tallink.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, String> {
    public List<Conference> findByConferenceRoomUUID(UUID roomUUID);

    public Conference findByUuid(UUID conferenceUUID);

    @Query(nativeQuery = true)
    public List<Conference> findAvailableByDateRange(Timestamp startDateTime, Timestamp endDateTime);
}
