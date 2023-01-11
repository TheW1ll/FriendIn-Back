package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

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
    private Calendar scheduledStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar scheduledEndTime;

    @ManyToOne
    private Group group;

}
