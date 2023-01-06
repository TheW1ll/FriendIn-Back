package fr.oci.onlyfriendsin.onlyfriendsin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String identifier;
    private String password;
    @ManyToMany(mappedBy = "members")
    private Collection<Group> userGroups;

    @OneToMany(mappedBy = "owner")
    private Collection<Group> createdGroups = new ArrayList<>();

}
