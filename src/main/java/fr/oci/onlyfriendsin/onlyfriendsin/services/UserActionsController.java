package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.EventDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.GroupDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Event;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.EventCreationAnswerDTO;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.EventCreationDTO;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.EventDTO;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.GroupInfoDTO;
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
}
