package fr.oci.onlyfriendsin.onlyfriendsin.dao;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDAO extends JpaRepository<Group,Long> {
}
