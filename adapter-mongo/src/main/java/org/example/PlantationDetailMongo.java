package org.example;

import java.util.Date;
import java.util.List;

public class PlantationDetailMongo {


    public String id;
    public String plantation_id;
    public String nom;
    public String type;
    public List<String> saisons;
    public int croissance_jours;
    public List<String> besoin_eau;
    public String ensoleillement;
    public int rendement;
    public String image_url;
    // Infos de la plantation
    public Date date_plantation;
    public int surface_m2;
    public String etat;
}