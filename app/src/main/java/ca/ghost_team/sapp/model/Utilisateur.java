package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Utilisateur")
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    private int idUtilisateur;

    @ColumnInfo(name = "nom_Utilisateur")
    private String utilisateur_nom;

    @ColumnInfo(name = "prenom_Utilisateur")
    private String utilisateur_prenom;

    @ColumnInfo(name = "dateNaissance_Utilisateur")
    private String utilisateur_dateNaissance;

//    @ColumnInfo(name = "nombreAnonce_Utilisateur")
//    private int utilisateur_nombreAnnonce;

    public Utilisateur(String utilisateur_nom, String utilisateur_prenom, String utilisateur_dateNaissance) {
        this.utilisateur_nom = utilisateur_nom;
        this.utilisateur_prenom = utilisateur_prenom;
        this.utilisateur_dateNaissance = utilisateur_dateNaissance;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getUtilisateur_nom() {
        return utilisateur_nom;
    }

    public void setUtilisateur_nom(String utilisateur_nom) {
        this.utilisateur_nom = utilisateur_nom;
    }

    public String getUtilisateur_prenom() {
        return utilisateur_prenom;
    }

    public void setUtilisateur_prenom(String utilisateur_prenom) {
        this.utilisateur_prenom = utilisateur_prenom;
    }

    public String getUtilisateur_dateNaissance() {
        return utilisateur_dateNaissance;
    }

    public void setUtilisateur_dateNaissance(String utilisateur_dateNaissance) {
        this.utilisateur_dateNaissance = utilisateur_dateNaissance;
    }

//    public int getUtilisateur_nombreAnnonce() {
//        return utilisateur_nombreAnnonce;
//    }
//
//    public void setUtilisateur_nombreAnnonce(int utilisateur_nombreAnnonce) {
//        this.utilisateur_nombreAnnonce = utilisateur_nombreAnnonce;
//    }

}
