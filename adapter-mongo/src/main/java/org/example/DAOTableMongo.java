package org.example;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DAOTableMongo {

    private final UserMongoRepository UserRepository;
    private final LegumeMongoRepository LegumeRepository;

    public DAOTableMongo(UserMongoRepository userRepository, LegumeMongoRepository legumeRepository) {
        UserRepository = userRepository;
        LegumeRepository = legumeRepository;
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
}