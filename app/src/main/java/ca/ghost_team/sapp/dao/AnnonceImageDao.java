package ca.ghost_team.sapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceImage;
import ca.ghost_team.sapp.model.Utilisateur;

@Dao
public interface AnnonceImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAnnoncesImages(AnnonceImage... annonceImage);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnnonceImage(AnnonceImage annonceImage);

    @Query("SELECT * FROM annonceimagetable")
    LiveData<List<AnnonceImage>> allAnnoncesImages();

    // Requête qui permet de récuperer une annonceImage par rapport à une Annonce
    @Query("SELECT * FROM annonceimagetable WHERE idAnnonceImage = :annonceImageId")
    AnnonceImage findLocationAnnonceImageByAnnonce(long annonceImageId);

    @Query("DELETE FROM annonceimagetable WHERE idAnnonceImage =:idAnnonceImage")
    void deleteAnnonceImage(int idAnnonceImage);

    @Update
    void updateAnnonce(AnnonceImage annonceImage);

    @Query("DELETE FROM annonceimagetable")
    void deleteAllAnnonceImage();

    @Query("SELECT 1")
    int start();
}
