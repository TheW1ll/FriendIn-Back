package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.ChatMessageDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.EventDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.GroupDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.ChatMessage;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Event;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8000")
@Controller
public class UserActionsController {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private ChatMessageDAO chatMessageDAO;

    /**
     * Récupérer la liste des groupes d'un utilisateur
     * @param userId l'identifiant de l'utilisateur dont on récupère les groupes
     * @return la liste des groupes de l'utilisateur
     */
    @GetMapping("/getUserGroups/{userId}")
    @ResponseBody
    public List<GroupInfoDTO> getGroups(@PathVariable String userId) {
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return new ArrayList<>();
        }
        User user = maybeUser.get();
        return user.getUserGroups().stream()
                .map(GroupInfoDTO::new)
                .toList();
    }

    /**
     * Récupérer la liste des évènements de tous les groupes de l'utilisateur
     * @param userId l'identifiant de l'utilisateur dont on récupère les évènements
     * @return la liste des évènements de l'utilisateur
     */
    @GetMapping("/getUserEvent/{userId}")
    @ResponseBody
    public List<EventDTO> getEvents(@PathVariable String userId) {
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return new ArrayList<>();
        }
        User user = maybeUser.get();
        return eventDAO.findByGroupIn(user.getUserGroups())
                .stream()
                .map(EventDTO::new)
                .toList();
    }

    /**
     * Créer un événement
     * @param creatorId Le créateur de l'événement
     * @param groupId l'événement concerné
     * @param eventDTO les informations sur l'événement
     * @return La réponse sur la création de l'événement
     */
    @PostMapping("createEvent/{creatorId}/{groupId}")
    @ResponseBody
    public EventCreationAnswerDTO createEvent(@PathVariable String creatorId,
                                              @PathVariable long groupId,
                                              @RequestBody EventCreationDTO eventDTO){
        Optional<User> maybeUser = userDAO.findById(creatorId);
        if(maybeUser.isEmpty()){
            return EventCreationAnswerDTO.NO_USER;
        }
        User creator = maybeUser.get();

        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return EventCreationAnswerDTO.NO_GROUP;
        }
        Group group = maybeGroup.get();

        if(!creator.getUserGroups().contains(group)){
            return EventCreationAnswerDTO.USER_NOT_IN_GROUP;
        }

        Event event = new Event(creator, group, eventDTO);
        eventDAO.save(event);
        return EventCreationAnswerDTO.SUCCESS;
    }

    /**
     * Quitter un groupe
     * @param userId l'utilisateur voulant quitter le grupe
     * @param groupId le groupe que l'on veut quitter
     * @return Si quitter a réussi
     */
    @DeleteMapping("leaveGroup/{userId}/{groupId}")
    @ResponseBody
    public boolean createEvent(@PathVariable String userId,
                               @PathVariable long groupId){
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return false;
        }
        User user = maybeUser.get();
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return false;
        }
        Group group = maybeGroup.get();
        group.removeUser(user);
        groupDAO.save(group);
        userDAO.save(user);
        return true;
    }

    /**
     * Accepte une invitation à un groupe
     * @param userId l'identifiant de l'utilisateur acceptant l'invitation
     * @param groupId le groupe dont on accepte l'invitation
     * @return si l'utilisateur a effectivement été ajouté au groupe
     */
    @PostMapping("acceptInvitation/{userId}/{groupId}")
    @ResponseBody
    public boolean acceptInvitation(@PathVariable String userId,
                                    @PathVariable long groupId){
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return false;
        }
        User user = maybeUser.get();
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return false;
        }
        Group group = maybeGroup.get();
        if(!group.userIsInvited(user)){
            return false;
        }
        group.addNewUser(user);
        groupDAO.save(group);
        userDAO.save(user);
        return true;
    }

    @PostMapping("refuseInvitation/{userId}/{groupId}")
    @ResponseBody
    public boolean refuseInvitation(@PathVariable String userId,
                                    @PathVariable long groupId){
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return false;
        }
        User user = maybeUser.get();
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return false;
        }
        Group group = maybeGroup.get();
        if(!group.userIsInvited(user)){
            return false;
        }
        group.removeInvitation(user);
        groupDAO.save(group);
        userDAO.save(user);
        return true;
    }

    /**
     * Récupérer la liste des invitations d'un utilisateur
     * @param userId l'identifiant de l'utilisateur dont on récupère les invitations
     * @return la liste des invitations de l'utilisateur
     */
    @GetMapping("/getInvitations/{userId}")
    @ResponseBody
    public List<GroupInfoDTO> getInvitations(@PathVariable String userId) {
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return new ArrayList<>();
        }
        User user = maybeUser.get();
        return user.getInvitationFromGroups().stream()
                .map(GroupInfoDTO::new)
                .toList();
    }

    /**
     * Poste un message de chat
     * @param userId l'identifiant du poster
     * @param groupId l'identifiant du groupe où l'on poste le message
     * @param chatMessageDTO le contenu du message
     * @return si le post a réussi ou non
     */
    @PostMapping("/postChatMessage/{userId}/{groupId}")
    @ResponseBody
    public boolean postChatMessage(@PathVariable String userId,
                                   @PathVariable long groupId,
                                   @RequestBody ChatMessageDTO chatMessageDTO){
        Optional<User> maybeUser = userDAO.findById(userId);
        if(maybeUser.isEmpty()){
            return false;
        }
        User user = maybeUser.get();
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return false;
        }
        Group group = maybeGroup.get();
        ChatMessage chatMessage = new ChatMessage(group, user, chatMessageDTO);
        groupDAO.save(group);
        chatMessageDAO.save(chatMessage);
        return true;
    }
}
