package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Utilisateur")
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    private int idUtilisateur;

    @ColumnInfo(name = "Utilisateur_nom")
    private String utilisateurNom;

    @ColumnInfo(name = "Utilisateur_username")
    private String username;
    
    @ColumnInfo(name = "Utilisateur_password")
    private String password;

    @ColumnInfo(name = "Utilisateur_email")
    private String email;

//    @ColumnInfo(name = "nombreAnonce_Utilisateur")
//    private int utilisateur_nombreAnnonce;

    public Utilisateur(String utilisateurNom, String username, String password, String email) {
        this.utilisateurNom = utilisateurNom;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", utilisateur_nom='" + utilisateurNom + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
