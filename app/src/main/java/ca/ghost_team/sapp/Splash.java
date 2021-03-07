package ca.ghost_team.sapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    private CircularProgressIndicator myProgress;
    int pStatus = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
 }
}