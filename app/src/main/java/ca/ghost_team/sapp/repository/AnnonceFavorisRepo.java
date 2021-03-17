package ca.ghost_team.sapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.activity.Login;
import ca.ghost_team.sapp.dao.AnnonceFavorisDao;
import ca.ghost_team.sapp.database.sappDatabase;
import ca.ghost_team.sapp.model.Annonce;

public class AnnonceFavorisRepo {
    private final LiveData<List<Annonce>> AllAnnonceFavoriteByUser;
    private final AnnonceFavorisDao dao;

    public AnnonceFavorisRepo(Application app) {

        dao = sappDatabase.getInstance(app).AnnonceFavorisDao();
        AllAnnonceFavoriteByUser  = dao.findAnnonceFavoriteByUser(Login.ID_USER_CURRENT);
    }

    public LiveData<List<Annonce>> getAllAnnonceFavoriteByUser() {
        return AllAnnonceFavoriteByUser;
    }



}
