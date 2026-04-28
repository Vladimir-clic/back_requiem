package org.example;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DAOTableMongo {

    private final UserMongoRepository UserRepository;
    private final LegumeMongoRepository LegumeRepository;

    public DAOTableMongo(UserMongoRepository UserRepository, UserMongoRepository userRepository, LegumeMongoRepository legumeRepository) {
        this.UserRepository = userRepository;
        LegumeRepository = legumeRepository;
    }

    public List<LegumeMongo> findAllLegumes(){
        return LegumeRepository.findAll();
    }

    public List<LegumeMongo> findLegumeByNom(String nom) {
        return LegumeRepository.findByNom(nom);
    }

    public List<UserMongo> findAll() {
        return UserRepository.findAll();
    }

    public List<UserMongo> findByNom(String nom) {
        return UserRepository.findByNom(nom);
    }

    public List<UserMongo> findByPays(String pays) {
        return UserRepository.findByLocalisationPays(pays);
    }
}