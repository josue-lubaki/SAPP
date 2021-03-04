package ca.ghost_team.sapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.ghost_team.sapp.databinding.ActivityMainBinding;
import ca.ghost_team.sapp.navigation.AddPost;
import ca.ghost_team.sapp.navigation.Favoris;
import ca.ghost_team.sapp.navigation.Home;
import ca.ghost_team.sapp.navigation.Message;
import ca.ghost_team.sapp.navigation.Profil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private final String LOG_TAG = "mainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView navBar = binding.navBar;
        navBar.setOnNavigationItemSelectedListener(this);
        showFragment(Home.class);
    }

    // TODO : Créer les Frames de connexion

    // TODO : Rajouter code de la barre de Recherche
    // ICI

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch(item.getItemId()){
            case R.id.optionHome:
                showFragment(Home.class);
                Log.i(LOG_TAG,"show Home.class");
                break;
            case R.id.optionFavoris:
                showFragment(Favoris.class);
                Log.i(LOG_TAG,"show Favoris.class");
                break;
            case R.id.optionAddPost:
                showFragment(AddPost.class);
                Log.i(LOG_TAG,"show AddPost.class");
                break;
            case R.id.optionMessage:
                showFragment(Message.class);
                Log.i(LOG_TAG,"show Message.class");
                break;
            case R.id.optionProfil:
                showFragment(Profil.class);
                Log.i(LOG_TAG,"show Profil.class");
                break;
        }
        return false;
    }

    /*****************  Affichage des Fragments  *****************/
    public void showFragment(Class<? extends Fragment> fragment) {
        try {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragment.getName());

            if(currentFragment == null)
                currentFragment = fragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, currentFragment, fragment.getName()).commit();

        } catch(InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            Log.d(LOG_TAG,"erreur au moment d'instancier fragment");
        }
    }

}