package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

    // Spring génère automatiquement ces requêtes
    List<UserMongo> findByNom(String nom);

    List<UserMongo> findAll();

    UserMongo findByEmailAndMotdepasse(String email, String motdepasse);
}