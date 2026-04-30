package org.example;


import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DAOTableMongo {

    private final UserMongoRepository UserRepository;
    private final LegumeMongoRepository LegumeRepository;
    private final PlantationMongoRepository PlantationRepository;

    public DAOTableMongo(UserMongoRepository userRepository, LegumeMongoRepository legumeRepository, PlantationMongoRepository plantationRepository) {
        UserRepository = userRepository;
        LegumeRepository = legumeRepository;
        PlantationRepository = plantationRepository;
    }

    public List<LegumeMongo> findAllLegumes() {
        return LegumeRepository.findAll();
    }

    public List<LegumeMongo> findLegumeByNom(String nom) {
        return LegumeRepository.findByNom(nom);
    }


    public List<UserMongo> findAllUsers() {
        return UserRepository.findAll();
    }

    public List<UserMongo> findByNom(String nom) {
        return UserRepository.findByNom(nom);
    }

    public UserMongo findByEmailAndMotdepasse(String email, String motdepasse) {
        return UserRepository.findByEmailAndMotdepasse(email, motdepasse);
    }

    public UserMongo createUser(UserMongo user) {
        // Vérifie si l'email existe déjà
        List<UserMongo> users = UserRepository.findAll();
        for (UserMongo existing : users) {
            if (Objects.equals(existing.email, user.email)) {
                return null; // email déjà utilisé
            }
        }
        return UserRepository.save(user);

    }

    public LegumeMongo createLegume(LegumeMongo legume) {
        List<LegumeMongo> legumes = LegumeRepository.findAll();
        for (LegumeMongo existing : legumes) {
            if (Objects.equals(existing.nom, legume.nom)) {
                return null;
            }
        }
        return LegumeRepository.save(legume);
    }

    public List<PlantationDetailMongo> findLegumesByUserId(String user_id) {
        List<PlantationMongo> plantations = PlantationRepository.findByUser_id(user_id);

        List<PlantationDetailMongo> result = new ArrayList<>();
        for (PlantationMongo plantation : plantations) {
            LegumeMongo legume = LegumeRepository.findById(plantation.plante_id).orElse(null);
            if (legume != null) {
                PlantationDetailMongo detail = new PlantationDetailMongo();
                detail.id = legume.id;
                detail.plantation_id = plantation.id;
                detail.nom = legume.nom;
                detail.type = legume.type;
                detail.saisons = legume.saisons;
                detail.croissance_jours = legume.croissance_jours;
                detail.besoin_eau = legume.besoin_eau;
                detail.ensoleillement = legume.ensoleillement;
                detail.rendement = legume.rendement;
                detail.image_url = legume.image_url;
                detail.date_plantation = plantation.date_plantation;
                detail.surface_m2 = plantation.surface_m2;
                detail.etat = plantation.etat;
                result.add(detail);
            }
        }
        return result;
    }

        public PlantationMongo savePlantation(PlantationMongo plantation) {
        return PlantationRepository.save(plantation);
    }

    public PlantationMongo updatePlantation(String plantation_id, Map<String, Object> body) {
        PlantationMongo plantation = PlantationRepository.findById(plantation_id).orElse(null);
        if (plantation == null) return null;

        if (body.containsKey("surface_m2")) plantation.surface_m2 = (Integer) body.get("surface_m2");
        if (body.containsKey("etat")) plantation.etat = (String) body.get("etat");
        if (body.containsKey("date_plantation")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setTimeZone(TimeZone.getTimeZone("Europe.Paris"));
                plantation.date_plantation = sdf.parse((String) body.get("date_plantation"));
            } catch (Exception e) {
                System.out.println("Erreur date : " + e.getMessage());
            }
        }

        return PlantationRepository.save(plantation);
    }
}