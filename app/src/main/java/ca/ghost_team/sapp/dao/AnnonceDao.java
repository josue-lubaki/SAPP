package ca.ghost_team.sapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import ca.ghost_team.sapp.model.Annonce;

@Dao
public interface AnnonceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAnnonces(Annonce... annonce);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnnonce(Annonce annonce);

    @Query("SELECT annonceTable.* FROM annonceTable WHERE liked_annonce = 0 UNION " +
            "SELECT annonceTable.* " +
            "FROM annonceTable " +
            "INNER JOIN AnnonceFavoris " +
            "ON annonceTable.idAnnonce = AnnonceFavoris.annonceId " +
            "WHERE AnnonceFavoris.utilisateurId =:idUser")
    LiveData<List<Annonce>> AllAnnonces(int idUser);

    // Requête qui permet de récuperer toutes les annonces par rapport à un Utilisateur
    @Query("SELECT * FROM annonceTable WHERE utilisateurId = :utilisateurId")
    LiveData<List<Annonce>> findAnnonceByUser(final int utilisateurId);

    @Delete
    void deleteAnnonce(Annonce annonce);

    @Update
    void updateAnnonce(Annonce annonce);

    @Query("DELETE FROM annonceTable")
    void deleteAllAnnonce();

    @Query("DELETE FROM annoncefavoris WHERE utilisateurId = :idUser AND annonceId = :idAnnonce")
    void deleteAnnonceByID(int idUser, int idAnnonce);

    @Query("INSERT INTO annonceFavoris(utilisateurId, annonceId) VALUES(:idUser, :idAnnonce)")
    void insertLiked(int idUser, int idAnnonce);

    @Query("SELECT 1")
    int start();


}
