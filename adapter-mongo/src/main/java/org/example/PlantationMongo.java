package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "plants_users")
public class PlantationMongo {

    @Id
    public String id;

    public String user_id;
    public String plante_id;

    public Date date_plantation;
    public int surface_m2;
    public String etat;
}


