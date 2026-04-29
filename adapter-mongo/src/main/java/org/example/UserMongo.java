package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users_infos")
@TypeAlias("")
public class UserMongo {

    @Id
    public String id;

    public String nom;
    public String prenom;
    public String email;
    public String adresse;
    public String motdepasse;
    public String superficie_ha;
    public Date date_inscription;

}