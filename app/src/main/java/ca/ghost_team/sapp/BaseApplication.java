package ca.ghost_team.sapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
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
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.repository.AnnonceFavorisRepo;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.MessageRepo;
import ca.ghost_team.sapp.repository.UtilisateurRepo;


public class BaseApplication extends MultiDexApplication {
    public static final String CHANNEL_DEFAULT = "ca.ghost_team.sapp.default.level";
    public static String NAME_DB = "SappDatabase";
    public static int ID_USER_CURRENT;
    public static String BASE_URL = "http://192.168.0.38/sappserver/";
    public static boolean SAVEDME;
    private SappDatabase db;
    private AnnonceRepo annonceRepo;
    private AnnonceFavorisRepo annonceFavorisRepo;
    private UtilisateurRepo utilisateurRepo;
    private MessageRepo messageRepo;
    private final String TAG =  BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        db = SappDatabase.getInstance(this);
        annonceRepo = new AnnonceRepo(this);
        annonceFavorisRepo = new AnnonceFavorisRepo(this);
        utilisateurRepo = new UtilisateurRepo(this);
        messageRepo = new MessageRepo(this);
        creatChannel();
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

    private void creatChannel(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_DEFAULT,
                    "Demo App",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{100, 200, 100});
            channel.setSound(RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    Notification.AUDIO_ATTRIBUTES_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }
    }

}