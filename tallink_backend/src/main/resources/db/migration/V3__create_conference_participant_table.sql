CREATE TABLE IF NOT EXISTS conference_participant
(
    uuid UUID NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    conference_uuid UUID NOT NULL,
    PRIMARY KEY (uuid),
    CONSTRAINT fk_conference_participant_conference
        FOREIGN KEY (conference_uuid)
        REFERENCES conference (uuid)
);