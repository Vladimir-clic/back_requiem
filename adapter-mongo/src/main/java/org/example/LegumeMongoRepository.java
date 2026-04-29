package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LegumeMongoRepository extends MongoRepository<LegumeMongo, String> {

    List<LegumeMongo> findByNom(String nom);
    List<LegumeMongo> findAll();
    //List<LegumeMongo> findById(String id);
}
