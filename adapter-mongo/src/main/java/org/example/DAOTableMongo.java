package org.example;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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

    public List<LegumeMongo> findLegumesByUserId(String user_id) {
        List<PlantationMongo> plantations = PlantationRepository.findByUser_id(user_id);

        List<String> legumeIds = plantations.stream()
                .map(p -> p.plante_id)
                .collect(Collectors.toList());

        return LegumeRepository.findAllById(legumeIds);
    }
}