package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private final LocalDateTime dateDebut;

    private final LocalDateTime dateFin;

    private final String groupName;

    private final String eventName;

    public EventDTO(Event event){
        dateDebut = event.getScheduledStartTime();
        dateFin = event.getScheduledEndTime();
        eventName = event.getEventTitle();
        groupName = event.getGroup().getName();
    }
}