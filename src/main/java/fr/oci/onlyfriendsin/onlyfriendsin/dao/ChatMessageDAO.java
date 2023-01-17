package fr.oci.onlyfriendsin.onlyfriendsin.dao;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageDAO extends JpaRepository<ChatMessage, Long> {
}
