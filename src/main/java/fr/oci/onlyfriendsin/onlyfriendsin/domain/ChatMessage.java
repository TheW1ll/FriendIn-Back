package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import fr.oci.onlyfriendsin.onlyfriendsin.dto.ChatMessageDTO;
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
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long identifier;

    @ManyToOne(optional = false)
    private User poster;

    @ManyToOne
    private Group group;

    private String messageContent;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime postDate;

    public ChatMessage(Group group, User poster, ChatMessageDTO chatMessageDTO){
        this.poster = poster;
        this.group = group;
        this.messageContent = chatMessageDTO.getContenu();
        this.postDate = chatMessageDTO.getDatePost();
        group.getChatMessages().add(this);
    }
}
