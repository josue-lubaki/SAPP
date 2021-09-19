package ca.ghost_team.sapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "annonceimagetable")
public class AnnonceImage {

    @PrimaryKey(autoGenerate = true)
    private int idAnnonceImage;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "imagecode")
    private String imagecode;

    @Ignore
    public AnnonceImage(String title, String location) {
        this.title = title;
        this.location = location;
    }

    @Ignore
    public AnnonceImage(int idAnnonceImage, String title, String location, String imagecode) {
        this.idAnnonceImage = idAnnonceImage;
        this.title = title;
        this.location = location;
        this.imagecode = imagecode;
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

    public String getImagecode() {
        return imagecode;
    }

    public void setImagecode(String imagecode) {
        this.imagecode = imagecode;
    }
}
