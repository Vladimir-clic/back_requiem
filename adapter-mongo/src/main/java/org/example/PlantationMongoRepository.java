package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlantationMongoRepository extends MongoRepository<PlantationMongo, String> {
    @Query("{ 'user_id': ?0 }")
    List<PlantationMongo> findByUser_id(String user_id);
}
