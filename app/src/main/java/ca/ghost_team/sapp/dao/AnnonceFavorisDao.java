package ca.ghost_team.sapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;

@Dao
public interface AnnonceFavorisDao {

    @Query("SELECT annonceTable.* " +
            "FROM annonceTable " +
            "INNER JOIN AnnonceFavoris " +
            "ON annonceTable.idAnnonce = AnnonceFavoris.annonceId " +
            "WHERE AnnonceFavoris.utilisateurId =:idUser")
    LiveData<List<Annonce>> findAnnonceFavoriteByUser(int idUser);

    @Query("SELECT annonceTable.* " +
            "FROM annonceTable " +
            "INNER JOIN AnnonceFavoris " +
            "ON annonceTable.idAnnonce = AnnonceFavoris.annonceId " +
            "WHERE AnnonceFavoris.utilisateurId =:idUser")
    List<Annonce> findListAnnonceFavoriteByUser(int idUser);


    @Query("SELECT COUNT(*) FROM AnnonceFavoris WHERE utilisateurId = :idUser")
    int getAnnonceFavorisCount(int idUser);

    @Query("SELECT COUNT(*) FROM AnnonceFavoris WHERE annonceId = :idAnnonce AND utilisateurId = :idUser")
    int getAnnonceFavorisIfExist(int idAnnonce, int idUser);


    @Query("DELETE FROM AnnonceFavoris WHERE utilisateurId = :idUser AND annonceId = :idAnnonce")
    void deleteAnnonceByID(int idUser, int idAnnonce);

    @Query("INSERT INTO annonceFavoris(utilisateurId, annonceId) VALUES(:idUser, :idAnnonce)")
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLiked(int idUser, int idAnnonce);
}
