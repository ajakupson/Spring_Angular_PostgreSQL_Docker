CREATE TABLE IF NOT EXISTS conference_feedback
(
    uuid UUID NOT NULL,
    conference_uuid UUID NOT NULL,
    participant_uuid UUID NOT NULL,
    feedback TEXT NOT NULL,
    PRIMARY KEY (uuid),
    CONSTRAINT fk_conference_feedback_conference
        FOREIGN KEY (conference_uuid)
        REFERENCES conference (uuid),
    CONSTRAINT fk_conference_feedback_conference_participant
        FOREIGN KEY (participant_uuid)
        REFERENCES conference_participant (uuid)
);