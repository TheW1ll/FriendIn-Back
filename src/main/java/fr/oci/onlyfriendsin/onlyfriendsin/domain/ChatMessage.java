package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long identifier;

    @ManyToOne
    private Group group;

    private String messageContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar postDate;
}
