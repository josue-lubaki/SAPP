package ca.ghost_team.sapp.database;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.dao.AnnonceDao;
import ca.ghost_team.sapp.model.Annonce;

@Database(entities = {Annonce.class}, version = 1, exportSchema = false)
public abstract class sappDatabase extends RoomDatabase {
    public static sappDatabase INSTANCE;
    public abstract AnnonceDao annonceDao();

    public static synchronized sappDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),sappDatabase.class,"sappDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private AnnonceDao annonceDao;

        public PopulateDbAsyncTask(sappDatabase instance) {
                annonceDao= instance.annonceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));

            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            return null;
        }
    }
}
