package ca.ghost_team.sapp.activity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.service.SappAPI;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityLoginBinding;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.repository.UtilisateurRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;
import static ca.ghost_team.sapp.BaseApplication.SAVEDME;

public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    private ActivityLoginBinding binding;

    // Recréation des liens avec la view
    private EditText username;
    private EditText password;
    private TextView sign_up;
    private ImageButton btn_login;
    private SharedPreferences prefs;
    public static String NAME_PREFS = "Credentials";
    private String usernamePref;
    private String passwordPref;
    private SwitchCompat switch_save_credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // Pour cacher le toolBar
        getSupportActionBar().hide();

        // Assigner les champs
        username = binding.tvLoginUsername;
        password = binding.tvLoginPassword;
        sign_up = binding.btnSignUp;
        btn_login = binding.btnLogin;
        switch_save_credentials = binding.switchSaveCredentials;

        prefs = getSharedPreferences(NAME_PREFS, MODE_PRIVATE);

        sign_up.setOnClickListener(v -> {
            // Lancer l'activity Main
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        btn_login.setOnClickListener(v -> {

            // Vérifier si le champs ne sont pas vide
            if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText().toString())) {
                if (TextUtils.isEmpty(username.getText()))
                    username.setError("Username required");
                if (TextUtils.isEmpty(password.getText()))
                    password.setError("Password required");

                Snackbar.make(v, "Please fill texts in the field", 5000)
                        .setAction("I understand", d -> {
                        }).show();

                // Mettre le Focus dur le champs Username
                username.requestFocus();
            } else {

                // RETROFIT
                SappAPI api = new SappAPI();
                api.getApi().getUtilisateurViaAPI(
                        username.getText().toString(),
                        password.getText().toString()
                ).enqueue(new Callback<Utilisateur>() {
                    @Override
                    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                        // Si conncetion Failed
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                            return;
                        }

                        Log.i(TAG, "response : " + response);
                        Utilisateur user = response.body();

                        String content = "";
                        content += "idUtilisateur : " + user.getIdUtilisateur() + "\n";
                        content += "utilisateurNom : " + user.getUtilisateurNom() + "\n";
                        content += "utilisateurUsername : " + user.getUtilisateurUsername() + "\n";
                        content += "Email : " + user.getUtilisateurEmail() + "\n";
                        content += "password : " + user.getUtilisateurPassword() + "\n\n";
                        Log.i(TAG, content);
                        new UtilisateurRepo(getApplication()).insertUtilisateur(user);
                        // Connecter l'Utilisateur
                        ID_USER_CURRENT = user.getIdUtilisateur();

                        if(SAVEDME){
                            usernamePref = username.getText().toString().trim();
                            passwordPref = user.getUtilisateurPassword();

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", usernamePref);
                            editor.putString("password", passwordPref);
                            editor.apply();
                        }

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Log.i(TAG, "Utilisateur trouvé");

                    }

                    @Override
                    public void onFailure(Call<Utilisateur> call, Throwable t) {
                        // Si erreur 404
                        Log.i(TAG, t.getMessage());
                        Log.e(TAG, t.getMessage());
                    }
                });

            }
        });

        /* Listener pour le switch */
        switch_save_credentials.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Si le switch est activé
            SAVEDME = isChecked;
        });


    }


    /**
     * Methode qui nous permet de bloquer le retour en arrière après une déconnexion
     */
    @Override
    public void onBackPressed() {
        Log.i(TAG, "On empêche le Button Back");
    }



    /**
     * Faire la requête pour rerchercher l'ID de l'Utilisateur courant
     *
     * @param application   c'est le context de l'application hôte
     * @param username_user le nom de l'Utilisateur dont il faut vérifier dans la BD
     * @param password_user le password pour y accéder
     * @return int
     */
    public static int connect_user(Application application, String username_user, String password_user) {
        SappDatabase db = Room.databaseBuilder(application, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();

        return db.utilisateurDao().retrieve_ID_User(username_user, password_user);
    }

}