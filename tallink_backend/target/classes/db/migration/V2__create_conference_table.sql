CREATE TABLE IF NOT EXISTS conference
(
    uuid UUID NOT NULL,
    conference_room_uuid UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_datetime TIMESTAMP NOT NULL,
    end_datetime TIMESTAMP NOT NULL,
    is_cancelled BOOLEAN,
    PRIMARY KEY (uuid),
    CONSTRAINT fk_conference_conference_room
        FOREIGN KEY (conference_room_uuid)
        REFERENCES conference_room (uuid)
);