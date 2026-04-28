package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class DemoRestController {

    private final DAOTableMongo dao;

    public DemoRestController(DAOTableMongo dao) {
        this.dao = dao;
    }

    // GET http://localhost:8080/users
    @GetMapping
    public List<TableMongo> getAll() {
        return dao.findAll();
    }

    // GET http://localhost:8080/users/nom/Martin
    @GetMapping("/nom/{nom}")
    public List<TableMongo> getByNom(@PathVariable String nom) {
        return dao.findByNom(nom);
    }

    // GET http://localhost:8080/users/pays/France
    @GetMapping("/pays/{pays}")
    public List<TableMongo> getByPays(@PathVariable String pays) {
        return dao.findByPays(pays);
    }
}