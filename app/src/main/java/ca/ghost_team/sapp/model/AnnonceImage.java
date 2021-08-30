package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "annonceimagetable",
        foreignKeys = {
//                @ForeignKey(entity = Annonce.class,
//                        parentColumns = "idAnnonce",
//                        childColumns = "annonceId",
//                        onDelete = CASCADE)
        }
)
public class AnnonceImage {

    @PrimaryKey(autoGenerate = true)
    private int idAnnonceImage;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "location")
    private String location;

    public AnnonceImage(String title, String location) {
        this.title = title;
        this.location = location;
    }

    public AnnonceImage(int idAnnonceImage, String title, String location) {
        this.idAnnonceImage = idAnnonceImage;
        this.title = title;
        this.location = location;
    }

    public AnnonceImage() {}

    public int getIdAnnonceImage() {
        return idAnnonceImage;
    }

    public void setIdAnnonceImage(int idAnnonceImage) {
        this.idAnnonceImage = idAnnonceImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
