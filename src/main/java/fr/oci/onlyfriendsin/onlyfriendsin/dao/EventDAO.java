package fr.oci.onlyfriendsin.onlyfriendsin.dao;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.Event;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface EventDAO extends JpaRepository<Event,Long> {
    Collection<Event> findByGroupIn(Collection<Group> groups);
}