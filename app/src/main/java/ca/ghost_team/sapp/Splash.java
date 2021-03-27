package ca.ghost_team.sapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import ca.ghost_team.sapp.activity.Login;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.CategorieAnnonce;

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
                    sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /* Lorsque la boucle s'arrête, on demarre l'Intent pour lancer l'activité principale */
            if (myProgress.getProgress() == 100) {
                // Forcer le demarrage de la Base de données
                SappDatabase db = Room.databaseBuilder(getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                        .allowMainThreadQueries().build();
                db.annonceDao().start();
//
//
//                db.utilisateurDao().insertUtilisateur(new Utilisateur(
//                        "Josue Lubaki",
//                        "Lubaki",
//                        "Heroes",
//                        "jojo@gmail.com"));
//
//                db.utilisateurDao().insertUtilisateur(new Utilisateur(
//                        "Ismael",
//                        "Coulibaly",
//                        "hybs",
//                        "ismael@gmail.com"));
//
//                db.utilisateurDao().insertUtilisateur(new Utilisateur(
//                        "Jonathan",
//                        "Kanyinda",
//                        "PC JO",
//                        "jonathan@gmail.com"));
//
                CategorieAnnonce[] categories = {
                        new CategorieAnnonce(1, "Pantalon"),
                        new CategorieAnnonce(2, "T-Shirt"),
                        new CategorieAnnonce(3, "Hoodie"),
                        new CategorieAnnonce(4, "Short"),
                        new CategorieAnnonce(5, "Casquette"),
                        new CategorieAnnonce(6, "Autres")
                };

                db.categorieAnnonceDao().insertCategorie(categories);


                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }

}