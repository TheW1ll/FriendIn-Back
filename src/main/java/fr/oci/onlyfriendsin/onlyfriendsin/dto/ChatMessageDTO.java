package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.ChatMessage;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private final LocalDateTime datePost;
    private final String contenu;
    private final String posterId;

    public ChatMessageDTO(ChatMessage chatMessage){
        datePost = chatMessage.getPostDate();
        contenu = chatMessage.getMessageContent();
        posterId = chatMessage.getPoster().getIdentifier();
    }

}
