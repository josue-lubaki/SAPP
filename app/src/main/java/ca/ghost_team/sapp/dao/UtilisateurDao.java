package ca.ghost_team.sapp.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Utilisateur;

@Dao
public interface UtilisateurDao  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertallUtilisateur(Utilisateur ... Utilisateur);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUtilisateur(Utilisateur x);

    @Query("SELECT * FROM Utilisateur ORDER BY nom_Utilisateur")
    LiveData< List<Utilisateur> > allUtilisateur();

    @Delete
    void deleteUtilisisateur (Utilisateur x);

    @Update
    void updateUtlisateur ( Utilisateur x);

    // Requête qui permet de récuperer toutes les annonces par rapport à un Utilisateur
    @Query("SELECT * FROM annonceTable WHERE utilisateurId = :utilisateurId")
    LiveData<List<Annonce>> findAnnonceByUser(final int utilisateurId);


}

