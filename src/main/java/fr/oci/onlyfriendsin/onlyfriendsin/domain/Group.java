package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friends_group")
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

    @OneToMany(mappedBy = "group", cascade = {CascadeType.REMOVE})
    private Collection<Event> events;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.REMOVE})
    private List<ChatMessage> chatMessages;

    @ManyToMany
    private Collection<User> invitedUsers;

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

    public boolean containsUser(User user){
        return members.stream()
                .anyMatch((member) -> Objects.equals(member.getIdentifier(), user.getIdentifier()));
    }

    public boolean userIsInvited(User user){
        return invitedUsers.stream()
                .anyMatch((member) -> Objects.equals(member.getIdentifier(), user.getIdentifier()));
    }

    public void addNewUser(User invitedUser) {
        if(!containsUser(invitedUser) && userIsInvited(invitedUser)){
            invitedUsers.remove(invitedUser);
            members.add(invitedUser);
            invitedUser.getInvitationFromGroups().remove(this);
            invitedUser.getUserGroups().add(this);
        }
    }

    public void removeUser(User removedUser) {
        if(containsUser(removedUser) && !Objects.equals(owner.getIdentifier(), removedUser.getIdentifier())){
            members.remove(removedUser);
            removedUser.getUserGroups().remove(this);
        }

    }

    public void inviteUser(User invitedUser) {
        if(!containsUser(invitedUser) && !userIsInvited(invitedUser)){
            invitedUsers.add(invitedUser);
            invitedUser.getInvitationFromGroups().add(this);
        }
    }

    public void removeInvitation(User user) {
        if(userIsInvited(user)){
            invitedUsers.remove(user);
            user.getInvitationFromGroups().remove(this);
        }
    }
}
