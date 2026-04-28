package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DAOTableMongo {

    @Autowired
    private LegumeMongoRepository legumeMongoRepository;

    private final UserMongoRepository UserRepository;
    private final LegumeMongoRepository LegumeRepository;

    public DAOTableMongo(UserMongoRepository UserRepository, UserMongoRepository userRepository, LegumeMongoRepository legumeRepository) {
        this.UserRepository = userRepository;
        LegumeRepository = legumeRepository;
    }

    public List<LegumeMongo> findAllLegumes(){
        return LegumeRepository.findAll();
    }

    public List<LegumeMongo> findLegumeByNom(String nom) {
        return LegumeRepository.findByNom(nom);
    }

    public LegumeMongo createLegume(LegumeMongo legume) {
        List<LegumeMongo> legumesMongo = legumeMongoRepository.findAll();

        if (legume.id == null) {
            // Création : génération d'un nouvel ID
            String newId = UUID.randomUUID().toString();

            // Vérification unicité (ex: par nom/pseudo)
            for (LegumeMongo existing : legumesMongo) {
                if (Objects.equals(existing.nom, legume.nom)) {
                    return null; // doublon détecté
                }
            }

            // Mapping et sauvegarde
            LegumeMongo newLegume = new LegumeMongo();
            newLegume.id   = newId;
            newLegume.nom  = legume.nom;
            newLegume.type = legume.type;
            newLegume.saisons = legume.saisons;
            newLegume.croissance_jours = legume.croissance_jours;
            newLegume.besoin_eau = legume.besoin_eau;
            newLegume.ensoleillement = legume.ensoleillement;
            newLegume.rendement = legume.rendement;
            newLegume.image_url = legume.image_url;

            return legumeMongoRepository.save(newLegume);

        } else {
            // Mise à jour : on cherche le document existant
            LegumeMongo existing = legumeMongoRepository.findById(legume.id).orElse(null);
            if (existing == null) return null;

            // Vérification unicité du nom (hors lui-même)
            for (LegumeMongo other : legumesMongo) {
                if (Objects.equals(other.nom, legume.nom) && !Objects.equals(other.id, legume.id)) {
                    return null; // doublon détecté
                }
            }

            // Mise à jour et sauvegarde
            existing.nom   = legume.nom;
            existing.type = legume.type;
            existing.saisons = legume.saisons;
            existing.croissance_jours = legume.croissance_jours;
            existing.besoin_eau = legume.besoin_eau;
            existing.ensoleillement = legume.ensoleillement;
            existing.rendement = legume.rendement;
            existing.image_url = legume.image_url;

            return legumeMongoRepository.save(existing);
        }
    }




    public List<UserMongo> findAll() {
        return UserRepository.findAll();
    }

    public List<UserMongo> findByNom(String nom) {
        return UserRepository.findByNom(nom);
    }

}