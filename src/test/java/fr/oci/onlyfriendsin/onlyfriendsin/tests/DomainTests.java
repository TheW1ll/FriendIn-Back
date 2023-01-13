package fr.oci.onlyfriendsin.onlyfriendsin.tests;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DomainTests {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreationGroup(){
        User user1 = new User("utilisateur","abcd123",new ArrayList<>(), new ArrayList<>());
        User user2 = new User("utilisateur2","abcd123",new ArrayList<>(), new ArrayList<>());
        Group group = new Group(user1,"groupe","description");
        assertTrue(group.getMembers().contains(user1));
        assertFalse(group.getMembers().contains(user2));
        assertEquals(group.getOwner(), user1);
        assertTrue(user1.getUserGroups().contains(group));
        assertTrue(user1.getCreatedGroups().contains(group));
        assertTrue(user2.getCreatedGroups().isEmpty());
    }

    @Test
    void testAddUserToGroup(){
        User user1 = new User("utilisateur","abcd123",new ArrayList<>(), new ArrayList<>());
        User user2 = new User("utilisateur2","abcd123",new ArrayList<>(), new ArrayList<>());
        Group group = new Group(user1,"groupe","description");
        group.addNewUser(user2);
        assertTrue(group.getMembers().contains(user2));
        assertNotEquals(group.getOwner(), user2);
        assertTrue(user2.getUserGroups().contains(group));
    }
}