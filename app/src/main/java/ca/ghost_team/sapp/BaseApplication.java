package ca.ghost_team.sapp;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.repository.AnnonceFavorisRepo;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.UtilisateurRepo;


public class BaseApplication extends MultiDexApplication {

    public static String NAME_DB = "SappDatabase";
    public static int ID_USER_CURRENT;
    private SappDatabase db;
    private AnnonceRepo annonceRepo;
    private AnnonceFavorisRepo annonceFavorisRepo;
    private UtilisateurRepo utilisateurRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        db = SappDatabase.getInstance(this);
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