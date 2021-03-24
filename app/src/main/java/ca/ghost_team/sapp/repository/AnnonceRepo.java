package ca.ghost_team.sapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.dao.AnnonceDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceRepo {
    private final AnnonceDao dao;
    private final LiveData<List<Annonce>> AllAnnonces;

    public AnnonceRepo(Application app){
        SappDatabase database = SappDatabase.getInstance(app);
        dao = database.annonceDao();
        AllAnnonces = dao.AllAnnonces(ID_USER_CURRENT);
    }

    public LiveData<List<Annonce>> getAllAnnonces() {
        return AllAnnonces;
    }

    public void insertAnnonce(Annonce annonce){
        new InsertAnnonceAsyncTask(dao).execute(annonce);
    }

    public void insertAllAnnonce(Annonce... annonce){ new InsertAllAnnonceAsyncTask(dao).execute(annonce);}

    public void updateAnnonce(Annonce annonce){
        new UpdateAnnonceAsyncTask(dao).execute(annonce);
    }

    public void deleteAllAnnonce(){
        new DeleteAllAnnoncesAsyncTask(dao).execute();
    }

    public void deleteAnnonce(Annonce annonce){
        new DeleteAnnonceAsyncTask(dao).execute(annonce);
    }

    public List<Annonce> findAnnonceByUser(int idUtilisateur){
        return (List<Annonce>) new findAnnonceByUserAsyncTask(dao).execute(idUtilisateur);
    }

    private static class InsertAnnonceAsyncTask extends AsyncTask<Annonce,Void,Void>{

        private final AnnonceDao uneAnnonceDao;
        private InsertAnnonceAsyncTask(AnnonceDao dao) {
            this.uneAnnonceDao= dao;
        }

        @Override
        protected Void doInBackground(Annonce... annonces) {
            uneAnnonceDao.insertAnnonce(annonces[0]);
            return null;
        }
    }

    private static class InsertAllAnnonceAsyncTask extends AsyncTask<Annonce,Void,Void>{

        private final AnnonceDao uneAnnonceDao;
        private InsertAllAnnonceAsyncTask(AnnonceDao dao) {
            this.uneAnnonceDao= dao;
        }

        @Override
        protected Void doInBackground(Annonce... annonces) {
            uneAnnonceDao.insertAllAnnonces(annonces);
            return null;
        }
    }

    private static class DeleteAnnonceAsyncTask extends AsyncTask<Annonce,Void,Void>{

        private final AnnonceDao uneAnnonceDao;
        private DeleteAnnonceAsyncTask(AnnonceDao dao) {
            this.uneAnnonceDao= dao;
        }

        @Override
        protected Void doInBackground(Annonce... annonces) {
            uneAnnonceDao.deleteAnnonce(annonces[0]);
            return null;
        }
    }

    private static class DeleteAllAnnoncesAsyncTask extends AsyncTask<Void,Void,Void>{

        private final AnnonceDao uneAnnonceDao;
        private DeleteAllAnnoncesAsyncTask(AnnonceDao dao) {
            this.uneAnnonceDao= dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            uneAnnonceDao.deleteAllAnnonce();
            return null;
        }
    }

    private static class UpdateAnnonceAsyncTask extends AsyncTask<Annonce,Void,Void>{

        private final AnnonceDao uneAnnonceDao;
        private UpdateAnnonceAsyncTask(AnnonceDao dao) {
            this.uneAnnonceDao= dao;
        }

        @Override
        protected Void doInBackground(Annonce... annonces) {
            uneAnnonceDao.updateAnnonce(annonces[0]);
            return null;
        }
    }

    static class findAnnonceByUserAsyncTask extends AsyncTask<Integer,Void,List<Annonce>>{

        private final AnnonceDao uneAnnonceDao;
        findAnnonceByUserAsyncTask(AnnonceDao dao) {
            this.uneAnnonceDao= dao;
        }

        @Override
        protected List<Annonce> doInBackground(Integer... id) {
            uneAnnonceDao.findAnnonceByUser(id[0]);
            return null;
        }

    }

}
