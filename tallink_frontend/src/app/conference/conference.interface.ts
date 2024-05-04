import { ConferenceRoom } from "../conference-room/conference-room.interface";
import { Participant } from "../register-participant/participant.interface";

export interface Conference {
    uuid: string | null;
    conferenceRoomUUID: string | null;
    name: string | null;
    description: string | null;
    startDateTime: Date | null;
    endDateTime: Date | null;
    cancelled: Boolean | null;
    conferenceRoom: ConferenceRoom | null;
    conferenceParticipants: Participant[] | null;

}