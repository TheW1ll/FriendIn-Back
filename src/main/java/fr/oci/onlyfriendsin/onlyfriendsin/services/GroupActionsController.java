package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.EventDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.GroupDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.ChatMessageDTO;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.EventDTO;
import fr.oci.onlyfriendsin.onlyfriendsin.dto.GroupOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8000")
@Controller
public class GroupActionsController {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    /**
     * Récupérer la liste des évènements d'un groupe
     * @param groupId l'identifiant du groupe dont on récupère les évènements
     * @return la liste des évènements du groupe
     */
    @GetMapping("/getGroupEvents/{groupId}")
    @ResponseBody
    public List<EventDTO> getEvents(@PathVariable long groupId) {
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return new ArrayList<>();
        }
        Group group = maybeGroup.get();
        return group.getEvents().stream()
                .map(EventDTO::new)
                .toList();
    }

    /**
     * Récupérer la liste des utilisateurs du groupe
     * @param groupId l'identifiant du groupe dont on récupère les membres
     * @return la liste des membres du groupe
     */
    @GetMapping("/getGroupMembers/{groupId}")
    @ResponseBody
    public List<String> getMembers(@PathVariable long groupId) {
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return new ArrayList<>();
        }
        Group group = maybeGroup.get();
        return group.getMembers().stream()
                .map(User::getIdentifier)
                .toList();
    }

    /**
     * Permet de récupérer le login du créateur du groupe
     * @param groupId le groupe dont on veut récupérer le créateur
     * @return l'identifiant du créateur du groupe
     */
    @GetMapping("/getOwner/{groupId}")
    @ResponseBody
    public GroupOwnerDTO getOwner(@PathVariable long groupId) {
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return new GroupOwnerDTO(false,"");
        }
        Group group = maybeGroup.get();
        return new GroupOwnerDTO(true,group.getOwner().getIdentifier());
    }

    @GetMapping("/getGroupMessages/{groupId}")
    @ResponseBody
    public List<ChatMessageDTO> getGroupMessages(@PathVariable long groupId){
        Optional<Group> maybeGroup = groupDAO.findById(groupId);
        if(maybeGroup.isEmpty()){
            return new ArrayList<>();
        }
        Group group = maybeGroup.get();
        return group.getChatMessages().stream()
                .map(ChatMessageDTO::new)
                .toList();
    }
}
