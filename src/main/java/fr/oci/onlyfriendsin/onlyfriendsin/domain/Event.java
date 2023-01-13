package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import fr.oci.onlyfriendsin.onlyfriendsin.dto.EventCreationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long identifier;

    private String eventTitle;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime scheduledStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime scheduledEndTime;

    @ManyToOne
    private Group group;

    @ManyToOne(optional = false)
    private User eventCreator;

    private String description;

    private String location;

    public Event(User creator, Group group, EventCreationDTO eventDTO) {
        setEventCreator(creator);
        setGroup(group);
        eventDTO.loadData(this);
        group.getEvents().add(this);
    }
}
