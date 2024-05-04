import { Participant } from "../register-participant/participant.interface";

export interface ConferenceFeedback {
    uuid: string | null;
    conferenceUUID: string | null;
    feedback: Date | null;
    conferenceParticipant: Participant | null;

}