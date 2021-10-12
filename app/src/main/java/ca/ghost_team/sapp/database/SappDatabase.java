package ca.ghost_team.sapp.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.Utils.Utilitaire;
import ca.ghost_team.sapp.dao.AnnonceDao;
import ca.ghost_team.sapp.dao.AnnonceFavorisDao;
import ca.ghost_team.sapp.dao.AnnonceImageDao;
import ca.ghost_team.sapp.dao.CategorieAnnonceDao;
import ca.ghost_team.sapp.dao.MessageDao;
import ca.ghost_team.sapp.dao.UtilisateurDao;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;
import ca.ghost_team.sapp.model.AnnonceImage;
import ca.ghost_team.sapp.model.CategorieAnnonce;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.model.Utilisateur;

@TypeConverters({Utilitaire.class})//Pour dire que notre base de donnne fait la covertion de chaque date en long
@Database(entities = {Annonce.class, Utilisateur.class, AnnonceFavoris.class, CategorieAnnonce.class, Message.class, AnnonceImage.class},
        version = 2,
        exportSchema = false)

public abstract class SappDatabase extends RoomDatabase {
    public static SappDatabase INSTANCE;
    public abstract AnnonceDao annonceDao();
    public abstract AnnonceImageDao annonceImageDao();
    public abstract UtilisateurDao utilisateurDao();
    public abstract MessageDao messageDao();
    public abstract AnnonceFavorisDao annonceFavorisDao();
    public abstract CategorieAnnonceDao categorieAnnonceDao();


    public static synchronized SappDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SappDatabase.class, BaseApplication.NAME_DB)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
