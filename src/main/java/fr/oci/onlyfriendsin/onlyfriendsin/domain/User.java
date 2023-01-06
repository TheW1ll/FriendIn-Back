package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String identifier;
    private String password;
    @ManyToMany(mappedBy = "groupMembers")
    private List<Group> userGroups;
}
