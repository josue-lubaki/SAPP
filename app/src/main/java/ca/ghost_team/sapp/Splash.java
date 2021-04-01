package ca.ghost_team.sapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ghost_team.sapp.activity.Login;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.CategorieAnnonce;
import ca.ghost_team.sapp.model.Message;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;
import static ca.ghost_team.sapp.activity.Login.connect_user;
import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    private CircularProgressIndicator myProgress;
    int pStatus = 0;
    private final Handler handler = new Handler();
    private SharedPreferences pref;
    private String TAG = Splash.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Desactiver le ToolBar
        getSupportActionBar().hide();

        pref = getApplicationContext().getSharedPreferences(Login.NAME_PREFS, MODE_PRIVATE);

        myProgress = findViewById(R.id.myProgressBar);

        new Thread(() -> {
            while (pStatus < 100) {
                pStatus += 1;
                handler.post(() -> myProgress.setProgress(pStatus));

                try {
                    // Just to display the progress slowly
                    sleep(15);
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

                CategorieAnnonce[] categories = {
                        new CategorieAnnonce(1, "Pantalon"),
                        new CategorieAnnonce(2, "T-Shirt"),
                        new CategorieAnnonce(3, "Hoodie"),
                        new CategorieAnnonce(4, "Short"),
                        new CategorieAnnonce(5, "Casquette"),
                        new CategorieAnnonce(6, "Autres")
                };

                db.categorieAnnonceDao().insertCategorie(categories);

                // Recupere les informations sauvergardées avec les Preferences
                String username = pref.getString("username","");
                String password = pref.getString("password","");

                if(!TextUtils.isEmpty(username.trim()) && !TextUtils.isEmpty(password.trim())){

                    ID_USER_CURRENT = 0;
                    // Lancer la requête pour verifier si le Username et Password donné par le User est correct
                    ID_USER_CURRENT = connect_user(getApplication(), username.trim(), password.trim());

                    // User trouvé
                    if (ID_USER_CURRENT != 0) {

                        //Lancer l'activity Main
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        }).start();
    }

}