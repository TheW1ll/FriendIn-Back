package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.GroupDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.GroupCreationResponseDTO;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.GroupInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @PostMapping("/createGroup")
    @ResponseBody
    public GroupCreationResponseDTO create(String creatorId, String groupName) {
        Optional<User> user = userDAO.findById(creatorId);
        if (user.isPresent()) {
            Group groupToCreate = new Group(user.get(),groupName);
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
    @PostMapping("/inviteToGroup")
    @ResponseBody
    public boolean invite(long groupId, String invitedUserId, String creatorPassword) {
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
        return true;
    }

    /**
     * Récupérer la liste des groupes d'un utilisateur
     * @param userId l'identifiant de l'utilisateur dont on récupère les groupes
     * @return la liste des groupes de l'utilisateur
     */
    @GetMapping("/getUserGroups")
    @ResponseBody
    public List<GroupInfoDTO> getGroups(String userId) {
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return new ArrayList<>();
        }
        User user = maybeUser.get();
        return user.getUserGroups().stream()
                .map(GroupInfoDTO::new)
                .toList();
    }

}
