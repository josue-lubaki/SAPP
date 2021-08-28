package ca.ghost_team.sapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.dao.AnnonceFavorisDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceFavorisRepo {
    private final LiveData<List<Annonce>> AllAnnonceFavoriteByUser;
    private final AnnonceFavorisDao dao;

    public AnnonceFavorisRepo(Application app) {

        dao = SappDatabase.getInstance(app).annonceFavorisDao();
        AllAnnonceFavoriteByUser = dao.findAnnonceFavoriteByUser(ID_USER_CURRENT);
    }

    public LiveData<List<Annonce>> getAllAnnonceFavoriteByUser() {
        return AllAnnonceFavoriteByUser;
    }

    public void insertLiked(AnnonceFavoris... annonce) {
        new insertAnnonceAsyncTask(dao).execute(annonce);
    }

    public void disLikeAnnonce(AnnonceFavoris... annonce) {
        new disLikeAnnonceAsyncTask(dao).execute(annonce);
    }

    private static class insertAnnonceAsyncTask extends AsyncTask<AnnonceFavoris, Void, Void> {

        private final AnnonceFavorisDao uneAnnonceDao;

        private insertAnnonceAsyncTask(AnnonceFavorisDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(AnnonceFavoris... annonces) {
            uneAnnonceDao.insertLiked(ID_USER_CURRENT,annonces[0].getAnnonceId());

            return null;
        }
    }

    private static class disLikeAnnonceAsyncTask extends AsyncTask<AnnonceFavoris, Void, Void> {

        private final AnnonceFavorisDao uneAnnonceDao;

        private disLikeAnnonceAsyncTask(AnnonceFavorisDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(AnnonceFavoris... annonces) {
            uneAnnonceDao.deleteAnnonceByID(ID_USER_CURRENT,annonces[0].getAnnonceId());
            return null;
        }
    }


}
