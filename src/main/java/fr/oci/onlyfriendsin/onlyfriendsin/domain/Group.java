package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long identifier;
    @ManyToOne(optional = false)
    private User owner;
    private String name;

    private String description;

    @ManyToMany
    private Collection<User> members;
    @OneToMany(mappedBy = "group")
    private Collection<Event> events;

    @OneToMany(mappedBy = "group")
    private List<ChatMessage> chatMessages;

    public Group(User owner, String name, String description){
        this.setName(name);
        this.setOwner(owner);
        this.setDescription(description);

        events = new ArrayList<>();
        members = new ArrayList<>();
        chatMessages = new ArrayList<>();

        owner.getCreatedGroups().add(this);
        owner.getUserGroups().add(this);

        members.add(owner);
    }

    public void addNewUser(User invitedUser) {
        members.add(invitedUser);
        invitedUser.getUserGroups().add(this);
    }
}
