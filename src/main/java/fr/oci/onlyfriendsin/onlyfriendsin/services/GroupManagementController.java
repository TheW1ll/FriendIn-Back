package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dto.GroupInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

public class GroupManagementController {

    /**
     * Crée un nouveau groupe
     * @param creatorId l'identifiant du créateur du groupe
     * @param groupName le nom du groupe à créer
     * @return l'identifiant du groupe créé
     */
    @PostMapping("/createGroup")
    @ResponseBody
    public long create(String creatorId, String groupName) {
        //TODO
        return 0;
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
        //TODO
        return false;
    }

    /**
     * Récupérer la liste des groupes d'un utilisateur
     * @param userId l'identifiant de l'utilisateur dont on récupère les groupes
     * @return la liste des groupes de l'utilisateur
     */
    @GetMapping("/getUserGroups")
    @ResponseBody
    public List<GroupInfoDTO> getGroups(String userId) {
        //TODO
        return new ArrayList<>();
    }

}
