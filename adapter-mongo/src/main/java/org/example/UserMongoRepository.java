package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongo, Integer> {

    // Spring génère automatiquement ces requêtes
    List<UserMongo> findByNom(String nom);
    List<UserMongo> findByLocalisationPays(String pays);

    List<UserMongo> findAll();
}