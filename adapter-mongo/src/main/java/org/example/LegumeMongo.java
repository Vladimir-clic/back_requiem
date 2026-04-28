package org.example;

import org.springframework.data.annotation.Id;

import java.util.List;

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
