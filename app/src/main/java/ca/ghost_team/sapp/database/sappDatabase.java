package ca.ghost_team.sapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
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

            /*annonceDao.insertAnnonce(new Annonce(
                    R.drawable.collection,
                    "Ma collection",
                    "Je te vends mes plus beaux vetements",
                    150,
                    "2 days ago",
                    false));

            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "Ma Chemise",
                    "Ma chemise blue",
                    50,
                    "1 days ago",
                    true));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.img_splash2,
                    "Ma Collection",
                    "Je te vends mes plus belle robes de soirée",
                    295,
                    "3 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "Ceinture",
                    "Tu aimes les ceintures de marque ?",
                    120,
                    "2 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.collection,
                    "Jogging gris",
                    "Pret pour le sport ?",
                    45,
                    "6 days ago",
                    true));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.culotte2,
                    "Culotte",
                    "je l'aime bien pour le BasketBall",
                    55,
                    "1 days ago",
                    false));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.collection,
                    "Veste",
                    "Tu veux être présentable ?",
                    350,
                    "3 days ago",
                    true));
            annonceDao.insertAnnonce(new Annonce(
                    R.drawable.chemise,
                    "T-shirt",
                    "Je te jure que tu vas l'adorer",
                    25,
                    "7 days ago",
                    false));*/
            return null;
        }
    }
}
