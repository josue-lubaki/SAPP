package ca.ghost_team.sapp.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.ghost_team.sapp.model.Utilisateur;

@Dao
public interface UtilisateurDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertallUtilisateur(Utilisateur ... Utilisateur);

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    public void insertUtilisateur(Utilisateur x);

    @Query("SELECT * FROM Utilisateur ORDER BY nom_Utilisateur")
    LiveData< List<Utilisateur> > allUtilisateur();

    @Delete
    void deleteUtilisisateur (Utilisateur x);

    @Update
    void updateUtlisateur ( Utilisateur x);



}

