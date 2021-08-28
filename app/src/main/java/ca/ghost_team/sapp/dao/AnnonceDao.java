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

    @Query("DELETE FROM annonceTable WHERE idAnnonce =:idAnnonce")
    void deleteAnnonce(int idAnnonce);

    @Update
    void updateAnnonce(Annonce annonce);

    @Query("DELETE FROM annonceTable")
    void deleteAllAnnonce();

    @Query("SELECT 1")
    int start();

    // connaêtre les informations de l'annonceur
    @Query("SELECT Utilisateur.* FROM Utilisateur INNER JOIN annonceTable ON idUtilisateur = utilisateurId " +
            "WHERE (annonceTitre LIKE :titre AND annoncePrix =:prix OR annonceDescription)")
    List<Utilisateur> infoAnnonceur(String titre, int prix);

}
