package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8000")
@Controller
public class UserManagementController {

    @Autowired
    private UserDAO userDAO;

    /**
     * Tente de créer un utilisateur à partir d'un id et d'un mot de passe. Peut échouer si un utilisateur avec
     * le même identifier existe déjà
     * @param identifier
     * @param password
     * @return true si et seulement si l'utilisateur a pu être créé
     */
    @PostMapping("/createUser")
    @ResponseBody
    public boolean create(String identifier, String password) {
        Optional<User> maybeUser = userDAO.findById(identifier);
        if (maybeUser.isPresent()) {
            return false;
        }
        User user = new User(identifier,password);
        userDAO.save(user);
        return true;
    }

    /**
     * vérifie si l'utilisateur existe bien, avec ce password
     * @param identifier
     * @param password
     * @return vrai ssi identifiant existe et correspond au mot de passe
     */
    @GetMapping("/checkLogin")
    @ResponseBody
    public boolean check(String identifier, String password) {
        Optional<User> maybeUser = userDAO.findById(identifier);
        if (maybeUser.isEmpty()) {
            return false;
        }
        User user = maybeUser.get();
        return Objects.equals(user.getPassword(), password);
    }

}
