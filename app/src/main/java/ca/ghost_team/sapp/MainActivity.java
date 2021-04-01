package ca.ghost_team.sapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import ca.ghost_team.sapp.databinding.ActivityMainBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.navigation.AddPost;
import ca.ghost_team.sapp.navigation.Favoris;
import ca.ghost_team.sapp.navigation.Home;
import ca.ghost_team.sapp.navigation.Message;
import ca.ghost_team.sapp.navigation.Profil;
import ca.ghost_team.sapp.viewmodel.AnnonceViewModel;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;

public class MainActivity extends AppCompatActivity{

    private final String LOG_TAG = "mainActivity";
    private AnnonceViewModel annonceViewModel;
    private MessageViewModel messageViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MeowBottomNavigation navBar = binding.navBar;

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
                    Log.i(LOG_TAG,"show Home.class");
                    break;
                case 2:
                    fragment = Favoris.class;
                    Log.i(LOG_TAG,"show Home.class");
                    break;
                case 3:
                    fragment = AddPost.class;
                    Log.i(LOG_TAG,"show AddPost.class");
                    break;
                case 4:
                    fragment = Message.class;
                    Log.i(LOG_TAG,"show Message.class");
                    break;
                case 5:
                    fragment = Profil.class;
                    Log.i(LOG_TAG,"show Profil.class");
                    break;
            }

            // Appel de la methode showFragment()
            showFragment(fragment);
        });

        // init viewModel and observe count annonces
        annonceViewModel = new ViewModelProvider(this).get(AnnonceViewModel.class);
        annonceViewModel.getAllAnnonces().observe(this, annonces -> {
            navBar.setCount(1, String.valueOf(annonces.size()));
        });

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.getAllMessagesReceiver().observe(this, messagesRecus -> {
            navBar.setCount(4, String.valueOf(messagesRecus.size()));
        });

        navBar.show(1,true);

        // pour éviter les erreurs
        navBar.setOnClickMenuListener(item -> {});
        navBar.setOnReselectListener(item -> {});
    }

    /**
     * Methode qui permet de remplacer des Fragments sur le container principal de l'Activité
     * @param fragment le fragment dont il faut afficher
     *
     * @return void*/
    public void showFragment(Class<? extends Fragment> fragment) {
        try {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragment.getName());

            if(currentFragment == null)
                currentFragment = fragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, currentFragment, fragment.getName())
                    .commit();

        } catch(InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            Log.d(LOG_TAG,"erreur au moment d'instancier fragment");
        }
    }
}