package ca.ghost_team.sapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.List;

import ca.ghost_team.sapp.activity.MessageActivity;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityMainBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceImage;
import ca.ghost_team.sapp.navigation.AddPost;
import ca.ghost_team.sapp.navigation.Favoris;
import ca.ghost_team.sapp.navigation.Home;
import ca.ghost_team.sapp.navigation.Messages;
import ca.ghost_team.sapp.navigation.Profil;
import ca.ghost_team.sapp.repository.AnnonceImageRepo;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.service.API.AnnonceAPI;
import ca.ghost_team.sapp.service.API.AnnonceImageAPI;
import ca.ghost_team.sapp.service.SappAPI;
import ca.ghost_team.sapp.viewmodel.AnnonceViewModel;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "mainActivity";
    private final String LOG_TAG = "mainActivity";
    private AnnonceViewModel annonceViewModel;
    private MessageViewModel messageViewModel;
    private ActivityMainBinding binding;
    public static final String ID_ANNONCE_CURRENT_NOTIFICATION = "ca.ghost_team.sapp.MainActivity.ID_ANNONCE_CURRENT_NOTIFICATION";
    public static final String ID_RECEIVER_CURRENT_NOTIFICATION = "ca.ghost_team.sapp.MainActivity.ID_RECEIVER_CURRENT_NOTIFICATION";
    private int nombremessgeNoLus = 0;
    private final String[] requiredPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MeowBottomNavigation navBar = binding.navBar;

        SappDatabase db = Room.databaseBuilder(getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();
        db.annonceDao().start();

        // add menu item
        navBar.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        navBar.add(new MeowBottomNavigation.Model(2,R.drawable.ic_favoris));
        navBar.add(new MeowBottomNavigation.Model(3,R.drawable.ic_addpost));
        navBar.add(new MeowBottomNavigation.Model(4,R.drawable.ic_message));
        navBar.add(new MeowBottomNavigation.Model(5,R.drawable.ic_profil));

        navBar.setOnShowListener(item -> {
            Class<? extends Fragment> fragment = null;
            switch(item.getId()){
                case 1:
                    fragment = Home.class;
                    Log.i(TAG,"show Home.class");
                    break;
                case 2:
                    fragment = Favoris.class;
                    Log.i(TAG,"show Home.class");
                    break;
                case 3:
                    fragment = AddPost.class;
                    Log.i(TAG,"show AddPost.class");
                    break;
                case 4:
                    fragment = Messages.class;
                    Log.i(TAG,"show Messages.class");
                    break;
                case 5:
                    fragment = Profil.class;
                    Log.i(TAG,"show Profil.class");
                    break;
            }

            // Appel de la methode showFragment()
            assert fragment != null;
            showFragment(fragment);

            for (String permission : requiredPermissions) {
                if (ContextCompat.checkSelfPermission(getApplication(), permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(requiredPermissions, 265);
                    return;
                }
            }
        });

        // init viewModel and observe count annonces
        annonceViewModel = new ViewModelProvider(this).get(AnnonceViewModel.class);
        annonceViewModel.getAllAnnonces().observe(this, annonces -> {
            navBar.setCount(1, String.valueOf(annonces.size()));
        });

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.getCountMessageNoRead().observe(this, nbreMessageNoRead -> {
            navBar.setCount(4, String.valueOf(nbreMessageNoRead));
            nombremessgeNoLus = nbreMessageNoRead;
        });

        messageViewModel.getAllMessagesReceiver().observe(this, messagesRecus -> {

            if(nombremessgeNoLus > 0){
                int idAnnonce = messagesRecus.get(messagesRecus.size() - 1).getAnnonceId();
                String message = messagesRecus.get(messagesRecus.size() - 1).getMessage();
                int idSender = messagesRecus.get(messagesRecus.size() - 1).getIdSender();

                Annonce annonce = new AnnonceRepo(this.getApplication()).getInfoAnnonce(idAnnonce);

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat
                                .Builder(this, BaseApplication.CHANNEL_DEFAULT)
                                .setSmallIcon(R.drawable.ic_message)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round))
                                .setContentTitle(annonce.getAnnonceTitre())
                                .setContentText(message)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message))
                                .setGroup(BaseApplication.CHANNEL_DEFAULT)
                                .setGroupSummary(true)
                                .setAutoCancel(true);

                Intent intent = new Intent(this, MessageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ID_ANNONCE_CURRENT_NOTIFICATION, idAnnonce);
                intent.putExtra(ID_RECEIVER_CURRENT_NOTIFICATION, idSender);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,155,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notificationBuilder.setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(idSender, notificationBuilder.build());
            }

        });

        // Le Fragment qui s'affiche par Default
        navBar.show(1,true);

        // pour éviter les erreurs
        navBar.setOnClickMenuListener(item -> {});
        navBar.setOnReselectListener(item -> {});
    }

    /**
     * Methode qui permet de confirmer si l'Utilisateur veut quitter l'application
     * @return void
     * */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmExit)
                .setCancelable(false)
                .setPositiveButton(R.string.confirmYes, (dialog, which) -> {
                    MainActivity.super.onBackPressed();
                })
                .setNegativeButton(R.string.confirmNo, (dialog, which) -> {
                    dialog.cancel();
                })
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 265) {
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplication(), getApplication().getResources().getString(R.string.dontPermission), Toast.LENGTH_LONG).show();
                    return;
                }
                // Recupération des annonces et images
                retrieveAllAnnonces();
            }
            // methode

        }
    }

    /**
     * Methode qui permet de remplacer des Fragments sur le container principal de l'Activité
     * @param fragment le fragment dont il faut afficher
     *
     * @return void
     * */
    public void showFragment(Class<? extends Fragment> fragment) {
        try {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragment.getName());

            if(currentFragment == null)
                currentFragment = fragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.fade_out  // popExit
                    )
                    .replace(R.id.container, currentFragment, fragment.getName())
                    .disallowAddToBackStack()
                    .commit();

        } catch(InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            Log.d(TAG,"erreur au moment d'instancier fragment");
        }
    }

    /**
     * Methode qui permet d'envoyer une requête au serveur, pour la récuperation des annonces depuis la base de données
     * */
    private void retrieveAllAnnonces() {
        //Recuperation de toutes les annonces
        SappAPI.getApi().create(AnnonceAPI.class)
                .getAllAnnonceViaAPI()
                .enqueue(new Callback<List<Annonce>>() {
                    @Override
                    public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                        // Si conncetion Failed
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                            return;
                        }
                        List<Annonce> newAnnonce = response.body();
                        Log.i(TAG, "newAnnonce : " + newAnnonce);
                        // inserer l'annonce dans la base de données locale via le Repository
                        assert newAnnonce != null;
                        Annonce[] tableAnnonce = new Annonce[newAnnonce.size()];
                        newAnnonce.toArray(tableAnnonce);
                        new AnnonceRepo(getApplication()).insertAllAnnonce(tableAnnonce);

                        // recupération de toutes les images depuis la base de données
                        retrieveAllImages();
                        Toast.makeText(getApplication(), R.string.refreshPage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<List<Annonce>> call, Throwable t) {
                        // Si erreur 404
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    /**
     * Methode qui permet d'envoyer une requête au serveur, pour la récuperation des images depuis la base de données
     * */
    private void retrieveAllImages() {
        //Recuperation de toutes les images
        SappAPI.getApi().create(AnnonceImageAPI.class)
                .getAllAnnoncesImagesViaAPI()
                .enqueue(new Callback<List<AnnonceImage>>() {
                    @Override
                    public void onResponse(Call<List<AnnonceImage>> call, Response<List<AnnonceImage>> response) {
                        // Si conncetion Failed
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                            return;
                        }
                        List<AnnonceImage> newAnnonceImage = response.body();
                        Log.i(TAG, "newAnnonce : " + newAnnonceImage);
                        // inserer l'annonce dans la base de données locale via le Repository
                        assert newAnnonceImage != null;
                        AnnonceImage[] tableAnnonceImage = new AnnonceImage[newAnnonceImage.size()];
                        newAnnonceImage.toArray(tableAnnonceImage);
                        new AnnonceImageRepo(getApplication()).insertAllAnnonceImage(tableAnnonceImage);
                    }

                    @Override
                    public void onFailure(Call<List<AnnonceImage>> call, Throwable t) {
                        // Si erreur 404
                        Log.e(TAG, t.getMessage());
                    }
                });
    }
}