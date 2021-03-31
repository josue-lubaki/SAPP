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
import ca.ghost_team.sapp.model.Utilisateur;

@Dao
public interface AnnonceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAnnonces(Annonce... annonce);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnnonce(Annonce annonce);

    @Query("SELECT * FROM annonceTable")
    LiveData<List<Annonce>> allAnnonces();

    // Requête qui permet de récuperer toutes les annonces par rapport à un Utilisateur
    @Query("SELECT * FROM annonceTable WHERE utilisateurId = :utilisateurId")
    LiveData<List<Annonce>> findAnnonceByUser(int utilisateurId);

    // Requête qui permet de récuperer toutes les annonces par rapport à un Utilisateur
    @Query("SELECT * FROM annonceTable WHERE idAnnonce = :annonceId")
    Annonce getInfoAnnonce(int annonceId);

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

    // connaêtre les informations de l'annonceur
    @Query("SELECT Utilisateur.* FROM Utilisateur INNER JOIN annonceTable ON idUtilisateur = utilisateurId " +
            "WHERE (titre_annonce LIKE :titre AND prix_annonce =:prix OR description_annonce LIKE :description)")
    List<Utilisateur> infoAnnonceur(String titre, int prix, String description);

}
