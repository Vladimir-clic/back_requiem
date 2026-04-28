package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="plants")
public class LegumeMongo {

    @Id
    public String id;
    public String nom;
    public String type;
    public List<String> saisons;
    public int croissance_jours;
    public String besoin_eau;
    public String ensoleillement;
    public int rendement;
    public String image_url;

}
