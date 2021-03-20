package ca.ghost_team.sapp;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import ca.ghost_team.sapp.database.sappDatabase;
import ca.ghost_team.sapp.repository.AnnonceFavorisRepo;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.UtilisateurRepo;


public class BaseApplication extends MultiDexApplication {

    public static String NAME_DB = "sappDatabase";
    public static int ID_USER_CURRENT;
    private sappDatabase db;
    private AnnonceRepo annonceRepo;
    private AnnonceFavorisRepo annonceFavorisRepo;
    private UtilisateurRepo utilisateurRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        db = sappDatabase.getInstance(this);
        annonceRepo = new AnnonceRepo(this);
        annonceFavorisRepo = new AnnonceFavorisRepo(this);
        utilisateurRepo = new UtilisateurRepo(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}