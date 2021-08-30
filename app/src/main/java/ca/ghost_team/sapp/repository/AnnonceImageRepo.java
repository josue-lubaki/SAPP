package ca.ghost_team.sapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.dao.AnnonceImageDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceImage;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceImageRepo {
    private final AnnonceImageDao dao;
    private final LiveData<List<AnnonceImage>> allAnnoncesImages;

    public AnnonceImageRepo(Application app) {
        SappDatabase database = SappDatabase.getInstance(app);
        dao = database.annonceImageDao();
        allAnnoncesImages = dao.allAnnoncesImages();
    }

    public LiveData<List<AnnonceImage>> getAllAnnoncesImages() {
        return allAnnoncesImages;
    }

    public void insertAnnonceImage(AnnonceImage annonceImage) {
        new InsertAnnonceAsyncTask(dao).execute(annonceImage);
    }

    public void insertAllAnnonceImage(AnnonceImage... annonceImage) {
        new InsertAllAnnonceAsyncTask(dao).execute(annonceImage);
    }

    public void updateAnnonceImage(AnnonceImage annonceImage) {
        new UpdateAnnonceAsyncTask(dao).execute(annonceImage);
    }

    public void deleteAllAnnonceImage() {
        new DeleteAllAnnoncesAsyncTask(dao).execute();
    }

    public void deleteAnnonceImage(AnnonceImage annonceImage) {
        new DeleteAnnonceAsyncTask(dao).execute(annonceImage);
    }


    /****************************************************************************************/
    private static class InsertAnnonceAsyncTask extends AsyncTask<AnnonceImage, Void, Void> {

        private final AnnonceImageDao uneAnnonceDao;

        private InsertAnnonceAsyncTask(AnnonceImageDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(AnnonceImage... annoncesImages) {
            uneAnnonceDao.insertAnnonceImage(annoncesImages[0]);
            return null;
        }
    }

    private static class InsertAllAnnonceAsyncTask extends AsyncTask<AnnonceImage, Void, Void> {

        private final AnnonceImageDao uneAnnonceDao;

        private InsertAllAnnonceAsyncTask(AnnonceImageDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(AnnonceImage... annoncesImages) {
            uneAnnonceDao.insertAllAnnoncesImages(annoncesImages);
            return null;
        }
    }

    private static class DeleteAnnonceAsyncTask extends AsyncTask<AnnonceImage, Void, Void> {

        private final AnnonceImageDao uneAnnonceDao;

        private DeleteAnnonceAsyncTask(AnnonceImageDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(AnnonceImage... annoncesImages) {
            uneAnnonceDao.deleteAnnonceImage(annoncesImages[0].getIdAnnonceImage());
            return null;
        }
    }

    private static class DeleteAllAnnoncesAsyncTask extends AsyncTask<Void, Void, Void> {

        private final AnnonceImageDao uneAnnonceDao;

        private DeleteAllAnnoncesAsyncTask(AnnonceImageDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            uneAnnonceDao.deleteAllAnnonceImage();
            return null;
        }
    }

    private static class UpdateAnnonceAsyncTask extends AsyncTask<AnnonceImage, Void, Void> {

        private final AnnonceImageDao uneAnnonceDao;

        private UpdateAnnonceAsyncTask(AnnonceImageDao dao) {
            this.uneAnnonceDao = dao;
        }

        @Override
        protected Void doInBackground(AnnonceImage... annoncesImages) {
            uneAnnonceDao.updateAnnonce(annoncesImages[0]);
            return null;
        }
    }

}
