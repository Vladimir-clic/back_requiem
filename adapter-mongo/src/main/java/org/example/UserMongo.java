package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users_infos")
public class UserMongo {

    @Id
    public Integer id;
    public String nom;
    public String prenom;
    public String email;
    public String motdepasse;

    public Date date_inscription;

    public String adresse;

    public Integer superficie_ha;
    public String type;
}