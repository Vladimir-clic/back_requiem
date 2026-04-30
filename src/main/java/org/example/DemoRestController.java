package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class DemoRestController {

    @Autowired
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
    public List<LegumeMongo> getAllLegumes() {
        return dao.findAllLegumes();
    }

    // GET http://localhost:8080/legumes/nom/Tomate
    @GetMapping("/legumes/nom/{nom}")
    public List<LegumeMongo> findLegumeByNom(@PathVariable String nom) {
        return dao.findLegumeByNom(nom);
    }

    // POST http://localhost:8080/users/login
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String motdepasse = body.get("motdepasse");

        System.out.println("Email reçu : " + email);
        System.out.println("MDP reçu : " + motdepasse);

        UserMongo user = dao.findByEmailAndMotdepasse(email, motdepasse);

        System.out.println("User trouvé : " + user);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }

    // POST http://localhost:8080/users/register
    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
        UserMongo newUser = new UserMongo();
        newUser.email = body.get("email");
        newUser.motdepasse = body.get("motdepasse");
        newUser.nom = body.get("nom");
        newUser.prenom = body.get("prenom");
        newUser.adresse = body.get("adresse");

        try {
            newUser.date_inscription = new Date();

            UserMongo saved = dao.createUser(newUser);

            if (saved != null) {
                return ResponseEntity.ok(saved);
            } else {
                return ResponseEntity.status(409).body("Email déjà utilisé");
            }
        } catch (Exception e) {
            System.out.println("Erreur register : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    // POST http://localhost:8080/legumes/new
    @PostMapping("/legumes/new")
    public ResponseEntity<?> registerLegume(@RequestBody Map<String, Object> body) {

        LegumeMongo newLegume = new LegumeMongo();
        newLegume.nom = (String) body.get("nom");
        newLegume.type = (String) body.get("type");
        newLegume.saisons = (List<String>) body.get("saisons");
        newLegume.croissance_jours = (Integer) body.get("croissance_jours");
        newLegume.ensoleillement = (String) body.get("ensoleillement");
        newLegume.besoin_eau = (List<String>) body.get("besoin_eau");
        newLegume.rendement = (Integer) body.get("rendement");
        newLegume.image_url = (String) body.get("image");

        //work in progress

        List<LegumeMongo> legumesMongo = dao.findAllLegumes();

        try {
            for (LegumeMongo legumeMongo : legumesMongo) {
                if (legumeMongo.nom.equals(newLegume.nom)) {
                    return ResponseEntity.status(409).body("Le légume " + legumeMongo.nom + " existe déjà.");
                }
            }

            LegumeMongo saved = dao.createLegume(newLegume);

            if (saved != null) {
                return ResponseEntity.ok(saved);
            } else {
                return ResponseEntity.status(409).body("Légume déjà existant");
            }
        } catch (Exception e) {
            System.out.println("Erreur register : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    // GET http://localhost:8080/inventaire/users/{user_id}
    @GetMapping("/inventaire/users/{user_id}")
    public ResponseEntity<?> getLegumesByUser(@PathVariable String user_id) {
        try {
            List<PlantationDetailMongo> legumes = dao.findLegumesByUserId(user_id);
            return ResponseEntity.ok(legumes);
        } catch (Exception e) {
            System.out.println("Erreur getLegumesByUser : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/plantation/{plantation_id}")
    public ResponseEntity<?> getPlantationById(@PathVariable String plantation_id) {
        try {
            PlantationDetailMongo detail = dao.findPlantationById(plantation_id);
            if (detail != null) {
                return ResponseEntity.ok(detail);
            } else {
                return ResponseEntity.status(404).body("Plantation non trouvée");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    // POST http://localhost:8080/add_plantation/users/{user_id}
    @PostMapping("/add_plantation/users/{user_id}")
    public ResponseEntity<?> addPlantation(@PathVariable String user_id, @RequestBody Map<String, Object> body) {
        // Récupère la plante par son nom
        String nom = (String) body.get("nom");
        List<LegumeMongo> legumes = dao.findLegumeByNom(nom);

        if (legumes.isEmpty()) {
            return ResponseEntity.status(404).body("Plante non trouvée : " + nom);
        }

        LegumeMongo legume = legumes.get(0);

        PlantationMongo plantation = new PlantationMongo();
        plantation.user_id = user_id;
        plantation.plante_id = legume.id;
        plantation.surface_m2 = (Integer) body.get("surface_m2");
        plantation.etat = "en cours";
        plantation.date_plantation = new Date();

        dao.savePlantation(plantation);
        return ResponseEntity.ok(plantation);
    }

    // POST http://localhost:8080/edit_plantation/{plantation_id}
    @PostMapping("/edit_plantation/{plantation_id}")
    public ResponseEntity<?> updatePlantation(@PathVariable String plantation_id, @RequestBody Map<String, Object> body) {
        try {
            PlantationMongo updated = dao.updatePlantation(plantation_id, body);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.status(404).body("Plantation non trouvée");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    // POST http://localhost:8080/analyze/{user_id}/{legume_id}
    @PostMapping("/analyze/{user_id}/{legume_id}")
    public ResponseEntity<?> analyzePlant(
            @PathVariable String user_id,
            @PathVariable String legume_id,
            @RequestBody Map<String, Object> capteurs) {
        try {
            // Récupère le légume depuis MongoDB
            List<LegumeMongo> legumes = dao.findAllLegumes();
            LegumeMongo legume = legumes.stream()
                    .filter(l -> l.id.equals(legume_id))
                    .findFirst()
                    .orElse(null);

            if (legume == null) {
                return ResponseEntity.status(404).body("Légume introuvable");
            }

            // Construit le body pour Django
            Map<String, Object> djangoBody = Map.of(
                    "plant", Map.of(
                            "nom", legume.nom,
                            "type", legume.type,
                            "besoin_eau", legume.besoin_eau,
                            "ensoleillement", legume.ensoleillement,
                            "saison", legume.saisons,
                            "croissance_jours", legume.croissance_jours
                    ),
                    "plants_user", Map.of(
                            "date_plantation", "2026-03-01",
                            "surface_m2", 10,
                            "etat", "en_croissance"
                    ),
                    "capteurs", capteurs
            );

            // Appelle Django IA
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(djangoBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://10.30.2.249/api/analyze/",
                    request,
                    Map.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            System.out.println("Erreur analyze : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }
}

