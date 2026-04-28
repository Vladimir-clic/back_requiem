package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users_infos")
public class UserMongo {

    @Id
    public Integer id;
    public String nom;
    public String prenom;
    public String email;
    public Localisation localisation;
    public Exploitation exploitation;
    public String date_inscription;

    // Classes imbriquées pour les objets MongoDB
    public static class Localisation {
        public String region;
        public String pays;
    }

    public static class Exploitation {
        public Integer superficie_ha;
        public String type;
    }
}