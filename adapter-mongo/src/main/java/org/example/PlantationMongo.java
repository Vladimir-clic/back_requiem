package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "plantes_users")
public class PlantationMongo {

    @Id
    public String id;

    @DBRef
    public String user_id;

    @DBRef
    public String legume_id;

    public Date date_plantation;
    public int surface;
    public String etat;
}


