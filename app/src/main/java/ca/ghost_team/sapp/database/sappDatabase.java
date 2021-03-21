package ca.ghost_team.sapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.dao.AnnonceDao;
import ca.ghost_team.sapp.dao.AnnonceFavorisDao;
import ca.ghost_team.sapp.dao.CategorieAnnonceDao;
import ca.ghost_team.sapp.dao.UtilisateurDao;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;
import ca.ghost_team.sapp.model.CategorieAnnonce;
import ca.ghost_team.sapp.model.Utilisateur;
import conv.Conversion;

@TypeConverters({Conversion.class})//Pour dire que notre base de donnne fait la covertion de chaque date en long
@Database(entities = {Annonce.class, Utilisateur.class, AnnonceFavoris.class, CategorieAnnonce.class}, version = 1, exportSchema = false)
public abstract class sappDatabase extends RoomDatabase {
    public static sappDatabase INSTANCE;
    public abstract AnnonceDao annonceDao();
    public abstract UtilisateurDao utilisateurDao();
    public abstract AnnonceFavorisDao AnnonceFavorisDao();
    public abstract CategorieAnnonceDao categorieAnnonceDao();

    public static synchronized sappDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),sappDatabase.class, BaseApplication.NAME_DB)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
