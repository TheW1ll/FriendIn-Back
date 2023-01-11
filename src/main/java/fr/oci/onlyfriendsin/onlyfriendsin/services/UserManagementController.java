package fr.oci.onlyfriendsin.onlyfriendsin.services;

import fr.oci.onlyfriendsin.onlyfriendsin.dao.UserDAO;
import fr.oci.onlyfriendsin.onlyfriendsin.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
     * @param identifier l'identifiant du nouvel utilisateur
     * @param password le mot de passe du nouvel utilisateur
     * @return true si et seulement si l'utilisateur a pu être créé
     */
    @PostMapping("/createUser/{identifier}/{password}")
    @ResponseBody
    public boolean create(@PathVariable String identifier,
                          @PathVariable String password) {
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
     * @param identifier l'identifiant de l'utilisateur cherchant à se connecter
     * @param password le mot de passe de l'utilisateur cherchant à se connecter
     * @return vrai ssi identifiant existe et correspond au mot de passe
     */
    @GetMapping("/checkLogin/{identifier}/{password}")
    @ResponseBody
    public boolean check(@PathVariable String identifier,
                         @PathVariable String password)  {
        Optional<User> maybeUser = userDAO.findById(identifier);
        if (maybeUser.isEmpty()) {
            return false;
        }
        User user = maybeUser.get();
        return Objects.equals(user.getPassword(), password);
    }

}
