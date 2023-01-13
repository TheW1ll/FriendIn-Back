package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreationDTO {
    private final LocalDateTime dateDebut;

    private final LocalDateTime dateFin;

    private final String title;

    private final String description;

    private final String location;

    /**
     * charge les données de ce DTO dans l'évènement indiqué
     * @param event l'évènement dans lequel on charge les données
     */
    public void loadData(Event event) {
        event.setScheduledStartTime(dateDebut);
        event.setScheduledEndTime(dateFin);
        event.setEventTitle(title);
        event.setDescription(description);
        event.setLocation(location);
    }
}
