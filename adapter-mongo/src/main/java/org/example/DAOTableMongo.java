package org.example;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DAOTableMongo {

    private final TableMongoRepository repository;

    public DAOTableMongo(TableMongoRepository repository) {
        this.repository = repository;
    }

    public List<TableMongo> findAll() {
        return repository.findAll();
    }

    public List<TableMongo> findByNom(String nom) {
        return repository.findByNom(nom);
    }

    public List<TableMongo> findByPays(String pays) {
        return repository.findByLocalisationPays(pays);
    }
}