package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableMongoRepository extends MongoRepository<TableMongo, Integer> {

    // Spring génère automatiquement ces requêtes
    List<TableMongo> findByNom(String nom);
    List<TableMongo> findByLocalisationPays(String pays);

    List<TableMongo> findAll();
}