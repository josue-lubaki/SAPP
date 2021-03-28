package ca.ghost_team.sapp.model;

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
    private String annonceImage;

    @ColumnInfo(name = "titre_annonce")
    private String annonceTitre;

    @ColumnInfo(name = "description_annonce")
    private String annonceDescription;

    @ColumnInfo(name = "prix_annonce")
    private int annoncePrix;

    @ColumnInfo(name = "date_annonce")
    private Date annonceDate;

    //cette variable permet de savoir si l'annonce est mise en favoris ou non
    @ColumnInfo(name = "liked_annonce")
    private boolean annonceLiked;

    // Cette liste nous permettra de connaître le nombre total des Annonces
    public static List<Annonce> listeTotalAnnonce =  new ArrayList<>();

    // il s'agit de la clé étrangère qui permet à l'annonce d'avoir une réference vers l'Utilisateur qui l'a publiée
    private int utilisateurId;
    private int categorieId;


    public Annonce(String annonceImage, String annonceTitre, String annonceDescription, int annoncePrix, Date annonceDate, boolean annonceLiked, int utilisateurId, int categorieId) {
        this.annonceTitre = annonceTitre;
        this.annonceDescription = annonceDescription;
        this.annoncePrix = annoncePrix;
        this.annonceDate = annonceDate;
        this.annonceLiked = annonceLiked;
        this.annonceImage = annonceImage;
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

    public String getAnnonceImage() {
        return annonceImage;
    }

    public void setAnnonceImage(String annonceImage) {
        this.annonceImage = annonceImage;
    }

    public String getAnnonceTitre() {
        return annonceTitre;
    }

    public void setAnnonceTitre(String annonceTitre) {
        this.annonceTitre = annonceTitre;
    }

    public String getAnnonceDescription() {
        return annonceDescription;
    }

    public void setAnnonceDescription(String annonceDescription) {
        this.annonceDescription = annonceDescription;
    }

    public int getAnnoncePrix() {
        return annoncePrix;
    }

    public void setAnnoncePrix(int annoncePrix) {
        this.annoncePrix = annoncePrix;
    }

    public Date getAnnonceDate() {

        return annonceDate;
    }

    public void setAnnonceDate(Date annonce_date) {
        this.annonceDate = annonce_date;
    }

    public boolean isAnnonceLiked() {
        return annonceLiked;
    }

    public void setAnnonceLiked(boolean annonceLiked) {
        this.annonceLiked = annonceLiked;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "idAnnonce=" + idAnnonce +
                ", annonce_image=" + annonceImage +
                ", annonce_titre='" + annonceTitre + '\'' +
                ", annonce_description='" + annonceDescription + '\'' +
                ", annonce_prix=" + annoncePrix +
                ", annonce_date=" + annonceDate +
                ", annonce_liked=" + annonceLiked +
                ", utilisateurId=" + utilisateurId +
                ", categorieId=" + categorieId +
                '}';
    }
}
