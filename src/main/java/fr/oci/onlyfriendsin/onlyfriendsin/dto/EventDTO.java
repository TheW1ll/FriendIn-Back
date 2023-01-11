package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Event;
import lombok.Data;
import java.util.Calendar;

@Data
public class EventDTO {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final Calendar dateDebut;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final Calendar dateFin;

    private final String groupName;

    private final String eventName;

    public EventDTO(Event event){
        dateDebut = event.getScheduledStartTime();
        dateFin = event.getScheduledEndTime();
        eventName = event.getEventTitle();
        groupName = event.getGroup().getName();
    }
}