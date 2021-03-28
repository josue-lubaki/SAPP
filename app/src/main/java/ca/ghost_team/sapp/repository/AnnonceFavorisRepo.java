package ca.ghost_team.sapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.dao.AnnonceFavorisDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceFavorisRepo {
    private final LiveData<List<Annonce>> AllAnnonceFavoriteByUser;
    private final AnnonceFavorisDao dao;

    public AnnonceFavorisRepo(Application app) {

        dao = SappDatabase.getInstance(app).annonceFavorisDao();
        AllAnnonceFavoriteByUser  = dao.findAnnonceFavoriteByUser(ID_USER_CURRENT);
    }

    public LiveData<List<Annonce>> getAllAnnonceFavoriteByUser() {
        return AllAnnonceFavoriteByUser;
    }

}
