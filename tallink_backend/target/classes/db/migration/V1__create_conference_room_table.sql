CREATE TABLE IF NOT EXISTS conference_room
(
    uuid UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    max_capacity SMALLINT NOT NULL,
    PRIMARY KEY (uuid)
);