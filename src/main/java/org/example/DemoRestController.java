package org.example;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class DemoRestController {

    private final DAOTableMongo dao;

    public DemoRestController(DAOTableMongo dao) {
        this.dao = dao;
    }

    // GET http://localhost:8080/users
    @GetMapping("/users")
    public List<UserMongo> getAllUsers() {
        try {
            return dao.findAllUsers();
        } catch (Exception e) {
            System.out.println("Erreur getAll : " + e.getMessage());
            throw e;
        }
    }

    // GET http://localhost:8080/users/nom/Martin
    @GetMapping("/users/nom/{nom}")
    public List<UserMongo> getByNom(@PathVariable String nom) {
        return dao.findByNom(nom);
    }

    // GET http://localhost:8080/legumes
    @GetMapping("/legumes")
    public List<LegumeMongo> getAllLegumes(){
        return dao.findAllLegumes();
    }

    // GET http://localhost:8080/legumes/nom/Tomate
    @GetMapping("/legumes/nom/{nom}")
    public List<LegumeMongo> findLegumeByNom(@PathVariable String nom){
        return dao.findLegumeByNom(nom);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String motdepasse = body.get("motdepasse");

        System.out.println("Email reçu : " + email);
        System.out.println("MDP reçu : " + motdepasse);

        UserMongo user = dao.findByEmailAndMotdepasse(email, motdepasse);

        System.out.println("User trouvé : " + user);

        if (user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }

    // POST http://localhost:8080/legumes/new
    @PostMapping("/legumes/new")
    public LegumeMongo newLegume (@RequestBody LegumeMongo legumeMongo){
        return dao.createLegume(legumeMongo);
    }
}

