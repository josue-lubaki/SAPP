package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "annonceTable")
public class Annonce {

    @PrimaryKey(autoGenerate = true)
    private int idAnnonce;

    @ColumnInfo(name="image_annonce")
    private int annonce_image;

    @ColumnInfo(name = "titre_annonce")
    private String annonce_titre;

    @ColumnInfo(name="description_annonce")
    private String annonce_description;

    @ColumnInfo(name ="prix_annonce")
    private int annonce_prix;

    @ColumnInfo(name= "date_annonce")
    private String annonce_date;

    //cette variable permet de savoir si l'annonce est mise en favoris ou non
    @ColumnInfo(name = "liked_annonce")
    private boolean annonce_liked;


    public Annonce(int annonce_image, String annonce_titre, String annonce_description, int annonce_prix, String annonce_date, boolean annonce_liked) {
        this.annonce_titre = annonce_titre;
        this.annonce_description = annonce_description;
        this.annonce_prix = annonce_prix;
        this.annonce_date = annonce_date;
        this.annonce_liked = annonce_liked;
        this.annonce_image = annonce_image;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public int getAnnonce_image() {
        return annonce_image;
    }

    public void setAnnonce_image(int annonce_image) {
        this.annonce_image = annonce_image;
    }

    public String getAnnonce_titre() {
        return annonce_titre;
    }

    public void setAnnonce_titre(String annonce_titre) {
        this.annonce_titre = annonce_titre;
    }

    public String getAnnonce_description() {
        return annonce_description;
    }

    public void setAnnonce_description(String annonce_description) {
        this.annonce_description = annonce_description;
    }

    public int getAnnonce_prix() {
        return annonce_prix;
    }

    public void setAnnonce_prix(int annonce_prix) {
        this.annonce_prix = annonce_prix;
    }

    public String getAnnonce_date() {
        return annonce_date;
    }

    public void setAnnonce_date(String annonce_date) {
        this.annonce_date = annonce_date;
    }

    public boolean isAnnonce_liked() {
        return annonce_liked;
    }

    public void setAnnonce_liked(boolean annonce_liked) {
        this.annonce_liked = annonce_liked;
    }
}
