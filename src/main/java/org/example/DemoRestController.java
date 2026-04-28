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
    public List<UserMongo> getAll() {
        return dao.findAll();
    }

    // GET http://localhost:8080/users/nom/Martin
    @GetMapping("/users/nom/{nom}")
    public List<UserMongo> getByNom(@PathVariable String nom) {
        return dao.findByNom(nom);
    }

    // GET http://localhost:8080/users/pays/France
    @GetMapping("/users/pays/{pays}")
    public List<UserMongo> getByPays(@PathVariable String pays) {
        return dao.findByPays(pays);
    }

    // GET http://localhost:8080/legumes
    @GetMapping("/legumes")
    public List<LegumeMongo> getAllLegumes(){
        return dao.findAllLegumes();
    }

    // GET http://localhost:8080/legumes
    @GetMapping("/legumes/nom/{nom}")
    public List<LegumeMongo> findLegumeByNom(@PathVariable String nom){
        return dao.findLegumeByNom(nom);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String motdepasse = body.get("motdepasse");

        UserMongo user = dao.findByEmailAndPassword(email, motdepasse);

        if (user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
}

