package ca.ghost_team.sapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import ca.ghost_team.sapp.model.CategorieAnnonce;

@Dao
public interface CategorieAnnonceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategorie(CategorieAnnonce... categorieAnnonce);

    @Delete
    void deleteCategorie(CategorieAnnonce categorieAnnonce);

    @Query("SELECT idCategorie FROM categorieTable WHERE LOWER(nomCategorie) LIKE :nomcategorie LIMIT 1")
    int findIndexCategorieByName(String nomcategorie);
}
