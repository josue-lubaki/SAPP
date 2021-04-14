package ca.ghost_team.sapp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.repository.AnnonceFavorisRepo;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.UtilisateurRepo;


public class BaseApplication extends MultiDexApplication {

    public static String NAME_DB = "SappDatabase";
    public static int ID_USER_CURRENT;
    public static boolean SAVEDME;
    private SappDatabase db;
    private AnnonceRepo annonceRepo;
    private AnnonceFavorisRepo annonceFavorisRepo;
    private UtilisateurRepo utilisateurRepo;
    private final String TAG =  BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        db = SappDatabase.getInstance(this);
        annonceRepo = new AnnonceRepo(this);
        annonceFavorisRepo = new AnnonceFavorisRepo(this);
        utilisateurRepo = new UtilisateurRepo(this);

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("ca.ghost_team.sapp") // Required for Analytics.
//                .setProjectId("sapp-3aaa2") // Required for Firebase Installations.
//                .setApiKey("72:5D:A3:C4:46:C6:26:64:E8:10:99:14:B2:7F:52:A4:0A:3D:54:A8") // Required for Auth.
//                .build();
//        FirebaseApp.initializeApp(this, options, "SAPP");

        // Firebase Subscribe
        Log.i("XXXX", "subscription init !");
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance()
                .subscribeToTopic("mychatroom")
                .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Log.i("XXXXX", "ok !");
                            }else {
                                Log.i("XXXXX", "ko !");
                            }
                        }
                );
    }

}