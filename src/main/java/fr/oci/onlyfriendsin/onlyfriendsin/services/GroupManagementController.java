package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.GroupDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.GroupCreationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8000")
@Controller
public class GroupManagementController {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Crée un nouveau groupe
     * @param creatorId l'identifiant du créateur du groupe
     * @param groupName le nom du groupe à créer
     * @return l'identifiant du groupe créé
     */
    @PostMapping("/createGroup/{creatorId}/{groupName}")
    @ResponseBody
    public GroupCreationResponseDTO create(@PathVariable String creatorId,
                                           @PathVariable String groupName,
                                           @RequestBody String description) {
        Optional<User> user = userDAO.findById(creatorId);
        if (user.isPresent()) {
            Group groupToCreate = new Group(user.get(),groupName,description);
            groupDAO.save(groupToCreate);
            return new GroupCreationResponseDTO(true,groupToCreate.getIdentifier());
        }
        return new GroupCreationResponseDTO(false,0);
    }

    /**
     * Invite un membre dans le groupe
     * @param groupId l'identifiant du groupe dans lequel on invite
     * @param invitedUserId l'identifiant du membre invité
     * @param creatorPassword le mot de passe du créateur (le seul autorisé à inviter)
     * @return booléen qui dit si l'invitation a réussi ou non
     */
    @PostMapping("/inviteToGroup/{groupId}/{invitedUserId}/{creatorPassword}")
    @ResponseBody
    public boolean invite(@PathVariable long groupId,
                          @PathVariable String invitedUserId,
                          @PathVariable String creatorPassword) {
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if (maybeGroup.isEmpty()) {
            return false;
        }
        Group group = maybeGroup.get();
        if (!Objects.equals(group.getOwner().getPassword(), creatorPassword)){
            return false;
        }
        Optional<User> maybeInvitedUser = userDAO.findById(invitedUserId);
        if (maybeInvitedUser.isEmpty()){
            return false;
        }
        User invitedUser = maybeInvitedUser.get();
        group.addNewUser(invitedUser);
        groupDAO.save(group);
        userDAO.save(invitedUser);
        return true;
    }

    public boolean removeFromGroup(@PathVariable long groupId,
                                   @PathVariable String removedUserId,
                                   @PathVariable String creatorPassword){
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if (maybeGroup.isEmpty()) {
            return false;
        }
        Group group = maybeGroup.get();
        if (!Objects.equals(group.getOwner().getPassword(), creatorPassword)){
            return false;
        }
        Optional<User> maybeRemovedUser = userDAO.findById(removedUserId);
        if (maybeRemovedUser.isEmpty()){
            return false;
        }
        User removedUser = maybeRemovedUser.get();
        group.removeUser(removedUser);
        groupDAO.save(group);
        userDAO.save(removedUser);
        return true;
    }
    /**
     * Supprime un groupe
     * @param groupId L'identifiant du groupe que l'on souhaite supprimer
     * @param creatorPassword Le mot de passe du créateur du groupe
     * @return vrai si la suppression a réussi
     */
    @DeleteMapping("/deleteGroup/{groupId}/{creatorPassword}")
    @ResponseBody
    public boolean deleteGroup(@PathVariable long groupId,
                               @PathVariable String creatorPassword) {
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if (maybeGroup.isEmpty()) {
            return false;
        }
        Group group = maybeGroup.get();
        if (!Objects.equals(group.getOwner().getPassword(), creatorPassword)){
            return false;
        }
        groupDAO.delete(group);
        return true;
    }

    /**
     * Permet de modifier le titre et la description d'un groupe
     * @param groupId le groupe que l'on souhaite modifier
     * @param creatorPassword le mot de passe du créateur du groupe
     * @param groupName le nouveau nom du groupe
     * @param description la nouvelle description du groupe
     * @return vrai si la modification a réussi
     */
    @PatchMapping("/modifyGroup/{groupId}/{creatorPassword}/{groupName}")
    @ResponseBody
    public boolean modifyGroup(@PathVariable long groupId,
                               @PathVariable String creatorPassword,
                               @PathVariable String groupName,
                               @RequestBody String description) {
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if (maybeGroup.isEmpty()) {
            return false;
        }
        Group group = maybeGroup.get();
        if (!Objects.equals(group.getOwner().getPassword(), creatorPassword)){
            return false;
        }

        group.setName(groupName);
        group.setDescription(description);
        groupDAO.save(group);
        return true;
    }
}
