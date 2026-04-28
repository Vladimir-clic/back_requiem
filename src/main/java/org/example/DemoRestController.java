package org.example;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class DemoRestController {

    private final DAOTableMongo dao;

    public DemoRestController(DAOTableMongo dao) {
        this.dao = dao;
    }

    // GET http://localhost:8080/users
    @GetMapping("/users")
    public List<UserMongo> getAll() {
        return dao.findAll();
    }

    // GET http://localhost:8080/users/nom/Martin
    @GetMapping("/users/nom/{nom}")
    public List<UserMongo> getByNom(@PathVariable String nom) {
        return dao.findByNom(nom);
    }

    // GET http://localhost:8080/users/pays/France
    @GetMapping("/users/pays/{pays}")
    public List<UserMongo> getByPays(@PathVariable String pays) {
        return dao.findByPays(pays);
    }

    // GET http://localhost:8080/legumes
    @GetMapping("/legumes")
    public List<LegumeMongo> getAllLegumes(){
        return dao.findAllLegumes();
    }
}

