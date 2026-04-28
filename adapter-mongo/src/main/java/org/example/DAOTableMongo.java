package org.example;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DAOTableMongo {

    private final UserMongoRepository repository;

    public DAOTableMongo(UserMongoRepository repository) {
        this.repository = repository;
    }

    public List<UserMongo> findAll() {
        return repository.findAll();
    }

    public List<UserMongo> findByNom(String nom) {
        return repository.findByNom(nom);
    }

    public List<UserMongo> findByPays(String pays) {
        return repository.findByLocalisationPays(pays);
    }
}