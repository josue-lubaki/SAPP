package ca.ghost_team.sapp.model;

import android.os.SystemClock;

import java.util.Date;

public class Annonce {
    private int imageAnnonce;
    private String titre;
    private String description;
    private int prix;
    private String date;
    private boolean isLiked;

    public Annonce(int imageAnnonce, String titre, String description, int prix, String date, boolean isLiked) {
        this.imageAnnonce = imageAnnonce;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.date = date;
        this.isLiked = isLiked;
    }

    public int getImageAnnonce() {
        return imageAnnonce;
    }

    public void setImageAnnonce(int imageAnnonce) {
        this.imageAnnonce = imageAnnonce;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
