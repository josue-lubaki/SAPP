package ca.ghost_team.sapp.model;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "annonceTable",
        foreignKeys = {
                @ForeignKey(entity = Utilisateur.class,
                        parentColumns = "idUtilisateur",
                        childColumns = "utilisateurId",
                        onDelete = CASCADE),

                @ForeignKey(entity = CategorieAnnonce.class,
                        parentColumns = "idCategorie",
                        childColumns = "categorieId",
                        onDelete = CASCADE)
        }
)
public class Annonce {

    @PrimaryKey(autoGenerate = true)
    private int idAnnonce;

    @ColumnInfo(name = "image_annonce")
    private String annonce_image;

    @ColumnInfo(name = "titre_annonce")
    private String annonce_titre;

    @ColumnInfo(name = "description_annonce")
    private String annonce_description;

    @ColumnInfo(name = "prix_annonce")
    private int annonce_prix;

    @ColumnInfo(name = "date_annonce")
    private Date annonce_date;

    //cette variable permet de savoir si l'annonce est mise en favoris ou non
    @ColumnInfo(name = "liked_annonce")
    private boolean annonce_liked;

    // Cette liste nous permettra de connaître le nombre total des Annonces
    public static List<Annonce> listeTotalAnnonce =  new ArrayList<>();

    // il s'agit de la clé étrangère qui permet à l'annonce d'avoir une réference vers l'Utilisateur qui l'a publiée

    private int utilisateurId;

    private int categorieId;

    public Annonce(String annonce_image, String annonce_titre, String annonce_description, int annonce_prix, Date annonce_date, boolean annonce_liked, int utilisateurId, int categorieId) {
        this.annonce_titre = annonce_titre;
        this.annonce_description = annonce_description;
        this.annonce_prix = annonce_prix;
        this.annonce_date = annonce_date;
        this.annonce_liked = annonce_liked;
        this.annonce_image = annonce_image;
        this.utilisateurId = utilisateurId;
        listeTotalAnnonce.add(this);
        this.categorieId = categorieId;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getAnnonce_image() {
        return annonce_image;
    }

    public void setAnnonce_image(String annonce_image) {
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

    public Date getAnnonce_date() {

        return annonce_date;
    }

    public void setAnnonce_date(Date annonce_date) {
        this.annonce_date = annonce_date;
    }

    public boolean isAnnonce_liked() {
        return annonce_liked;
    }

    public void setAnnonce_liked(boolean annonce_liked) {
        this.annonce_liked = annonce_liked;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "idAnnonce=" + idAnnonce +
                ", annonce_image=" + annonce_image +
                ", annonce_titre='" + annonce_titre + '\'' +
                ", annonce_description='" + annonce_description + '\'' +
                ", annonce_prix=" + annonce_prix +
                ", annonce_date=" + annonce_date +
                ", annonce_liked=" + annonce_liked +
                ", utilisateurId=" + utilisateurId +
                ", categorieId=" + categorieId +
                '}';
    }
}
