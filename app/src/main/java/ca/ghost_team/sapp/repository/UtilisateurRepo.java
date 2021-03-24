package ca.ghost_team.sapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.dao.UtilisateurDao;
import ca.ghost_team.sapp.database.SappDatabase;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Utilisateur;

public class UtilisateurRepo {

    private final LiveData<List<Utilisateur>> AllUtilisateurs;
    private final UtilisateurDao dao;

    public UtilisateurRepo(Application app) {

        dao = SappDatabase.getInstance(app).utilisateurDao();
        AllUtilisateurs  = dao.allUtilisateur();
    }
    public LiveData getAllUtilitisateur(){
        return AllUtilisateurs;
    }

    public void inserUtilisiateur(Utilisateur x){ new InsertAsynchrone(dao).execute(x);}
    public void deleteUtilisateur(Utilisateur x){ new DeleteAsynchrone(dao).execute(x);}
    public void updateUtilisiateur(Utilisateur x){ new UpdateAsynchrone(dao).execute(x);}
    public List<Annonce> findAnnonceByUtilisateur(int idUtilisateur){
        return (List<Annonce>) new findAnnonceByUtilisateurAsyncTask(dao).execute(idUtilisateur);
    }


    // Classe insert user's backTask
    public static class  InsertAsynchrone extends AsyncTask<Utilisateur,Void,Void> {
        //delaration att daouser + him initiatiom
        private UtilisateurDao utilisateurDao;
        private InsertAsynchrone(UtilisateurDao dao) {
            utilisateurDao = dao;
        }

        @Override
        protected Void doInBackground(Utilisateur... utilisateurs) {
            utilisateurDao.insertUtilisateur(utilisateurs[0]);
            return null;
        }
    }
    // Classe delete user's backTask
    public static class  DeleteAsynchrone extends AsyncTask<Utilisateur,Void,Void> {
        //delaration att daouser + him initiatiom
        private UtilisateurDao utilisateurDao;
        private DeleteAsynchrone(UtilisateurDao dao) {
            utilisateurDao = dao;
        }
        @Override
        protected Void doInBackground(Utilisateur... utilisateurs) {
            utilisateurDao.deleteUtilisisateur(utilisateurs[0]);
            return null; }
    }
    // Classe update user's backTask
    public static class  UpdateAsynchrone extends AsyncTask<Utilisateur,Void,Void> {
        //delaration att daouser + him initiatiom
        private UtilisateurDao utilisateurDao;
        private UpdateAsynchrone(UtilisateurDao dao) {
            utilisateurDao = dao;
        }
        @Override
        protected Void doInBackground(Utilisateur... utilisateurs) {
            utilisateurDao.updateUtlisateur(utilisateurs[0]);
            return null;
        }

    }

    private static class findAnnonceByUtilisateurAsyncTask extends AsyncTask<Integer,Void,List<Annonce>>{

        private UtilisateurDao utilisateurDao;
        private findAnnonceByUtilisateurAsyncTask(UtilisateurDao dao) {
            this.utilisateurDao= dao;
        }

        @Override
        protected List<Annonce> doInBackground(Integer... id) {
            utilisateurDao.findAnnonceByUser(id[0]);
            return null;
        }

    }

}

