package ca.ghost_team.sapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;

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
}
