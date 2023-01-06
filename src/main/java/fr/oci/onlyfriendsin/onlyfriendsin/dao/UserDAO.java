package fr.oci.onlyfriendsin.onlyfriendsin.dao;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,String> {
}
