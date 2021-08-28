package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.installations.Utils;

import ca.ghost_team.sapp.Utils.Utilitaire;

@Entity(tableName = "Utilisateur")
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    private int idUtilisateur;

    @ColumnInfo(name = "utilisateurNom")
    private String utilisateurNom;

    @ColumnInfo(name = "utilisateurUsername")
    private String utilisateurUsername;
    
    @ColumnInfo(name = "utilisateurPassword")
    private String utilisateurPassword;

    @ColumnInfo(name = "utilisateurEmail")
    private String utilisateurEmail;

//    @ColumnInfo(name = "nombreAnonce_Utilisateur")
//    private int utilisateur_nombreAnnonce;

    public Utilisateur(String utilisateurNom, String utilisateurUsername, String utilisateurPassword, String utilisateurEmail) {
        this.utilisateurNom = utilisateurNom;
        this.utilisateurUsername = utilisateurUsername;
        this.utilisateurPassword = utilisateurPassword;
        this.utilisateurEmail = utilisateurEmail;
    }

    public String getUtilisateurUsername() {
        return utilisateurUsername;
    }

    public void setUtilisateurUsername(String utilisateurUsername) {
        this.utilisateurUsername = utilisateurUsername;
    }

    public String getUtilisateurEmail() {
        return utilisateurEmail;
    }

    public void setUtilisateurEmail(String utilisateurEmail) {
        this.utilisateurEmail = utilisateurEmail;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getUtilisateurNom() {
        return utilisateurNom;
    }

    public void setUtilisateurNom(String utilisateur_nom) {
        this.utilisateurNom = utilisateur_nom;
    }

    public String getUtilisateurPassword() {
        return utilisateurPassword;
    }

    public void setUtilisateurPassword(String utilisateurPassword) {
        this.utilisateurPassword = utilisateurPassword;
    }

    /**
     * La methode qui permet de créer un Checksum pour l'enregistrement de l'Utilisateur
     * Checksum pour Register
     * @return String
     */
    public String checksumForRegister(){
        String passwordHash = Utilitaire.hashage(utilisateurPassword);

        assert passwordHash != null;
        return Utilitaire.encrypt(utilisateurNom + "/"+
                utilisateurUsername + "/"+
                (passwordHash.contains("/") ? passwordHash.replace("/","e") : passwordHash)  + "/"+
                utilisateurEmail + "/"+
                System.currentTimeMillis(), 2); // Timestamp
    }

    /**
     * La methode qui permet de créer un Checksum pour l'authentification de l'utilisateur
     * Checksum pour Login
     * @return String
     */
    public String checksumForLogin(){
        String passwordHash = Utilitaire.hashage(utilisateurPassword);

        assert passwordHash != null;
        return Utilitaire.encrypt(utilisateurUsername + "/"+
                (passwordHash.contains("/") ? passwordHash.replace("/","e") : passwordHash)  + "/"+
                System.currentTimeMillis(),2); // Timestamp
    }


    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", utilisateur_nom='" + utilisateurNom + '\'' +
                ", username='" + utilisateurUsername + '\'' +
                ", password='" + utilisateurPassword + '\'' +
                ", email='" + utilisateurEmail + '\'' +
                '}';
    }
}
