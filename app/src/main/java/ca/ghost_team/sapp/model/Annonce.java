package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "annonceTable",
        foreignKeys = {
//                @ForeignKey(entity = Utilisateur.class,
//                        parentColumns = "idUtilisateur",
//                        childColumns = "utilisateurId",
//                        onDelete = CASCADE),

//                @ForeignKey(entity = CategorieAnnonce.class,
//                        parentColumns = "idCategorie",
//                        childColumns = "categorieId",
//                        onDelete = CASCADE)
        }
)
public class Annonce {

    @PrimaryKey(autoGenerate = true)
    private int idAnnonce;

    // @SerializedName("image")
    @ColumnInfo(name = "annonceImage")
    private String annonceImage;

    //  @SerializedName("titre")
    @ColumnInfo(name = "annonceTitre")
    private String annonceTitre;

    // @SerializedName("description")
    @ColumnInfo(name = "annonceDescription")
    private String annonceDescription;

    // @SerializedName("prix")
    @ColumnInfo(name = "annoncePrix")
    private int annoncePrix;

    @ColumnInfo(name = "annonceDate")
    private String annonceDate;

    //  @SerializedName("zip")
    @ColumnInfo(name = "annonceZip")
    private String annonceZip;

    // il s'agit de la clé étrangère qui permet à l'annonce d'avoir une réference vers l'Utilisateur qui l'a publiée
    private int utilisateurId;
    private int categorieId;

    public Annonce(String annonceImage, String annonceTitre, String annonceDescription, int annoncePrix, String annonceDate, String annonceZip, int utilisateurId, int categorieId) {
        this.annonceImage = annonceImage;
        this.annonceTitre = annonceTitre;
        this.annonceDescription = annonceDescription;
        this.annoncePrix = annoncePrix;
        this.annonceDate = annonceDate;
        this.annonceZip = annonceZip;
        this.utilisateurId = utilisateurId;
        this.categorieId = categorieId;
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

    public String getAnnonceDate() {
        return annonceDate;
    }

    public void setAnnonceDate(String annonceDate) {
        this.annonceDate = annonceDate;
    }

    public String getAnnonceZip() {
        return annonceZip;
    }

    public void setAnnonceZip(String annonceZip) {
        this.annonceZip = annonceZip;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
}
