package ca.ghost_team.sapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Date;

import ca.ghost_team.sapp.activity.Login;
import ca.ghost_team.sapp.database.sappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Utilisateur;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    private CircularProgressIndicator myProgress;
    int pStatus = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Desactiver le ToolBar
        getSupportActionBar().hide();

        myProgress = findViewById(R.id.myProgressBar);

        new Thread(() -> {
            while (pStatus < 100) {
                pStatus += 1;
                handler.post(() -> myProgress.setProgress(pStatus));

                try {
                    // Just to display the progress slowly
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /* Lorsque la boucle s'arrête, on demarre l'Intent pour lancer l'activité principale */
            if(myProgress.getProgress() == 100){
                // Forcer le demarrage de la Base de données
//                sappDatabase db = Room.databaseBuilder(getApplication(),sappDatabase.class,BaseApplication.NAME_DB)
//                        .allowMainThreadQueries().build();
//                db.annonceDao().start();
//
//                db.utilisateurDao().insertUtilisateur(new Utilisateur(
//                        "Josue Lubaki",
//                        "Lubaki",
//                        "Heroes",
//                        "jojo@gmail.com"));
//                db.utilisateurDao().insertUtilisateur(new Utilisateur(
//                        "Ismael Coulibaly",
//                        "ismo",
//                        "zoba",
//                        "ismael@gmail.com"));
//
//                db.annonceDao().insertAnnonce(new Annonce(
//                        R.drawable.collection,
//                        "Ma collection",
//                        "Je te vends mes plus beaux vetements",
//                        150,
//                        new Date(),
//                        false,
//                        1
//                ));
//
//                db.annonceDao().insertAnnonce(new Annonce(
//                        R.drawable.chemise,
//                        "Ma Chemise",
//                        "Je te vends mes plus beaux vetements",
//                        150,
//                        new Date(),
//                        false,
//                        2
//                ));
//
//                db.annonceDao().insertAnnonce(new Annonce(
//                        R.drawable.culotte2,
//                        "Ma Culotte",
//                        "Je te vends mes plus beaux vetements",
//                        150,
//                        new Date(),
//                        false,
//                        2
//                ));
//
//                db.annonceDao().insertAnnonce(new Annonce(
//                        R.drawable.culotte1,
//                        "Mon Jogging",
//                        "Je te vends mes plus beaux vetements",
//                        150,
//                        new Date(),
//                        false,
//                        1
//                ));

                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        }).start();
 }

}