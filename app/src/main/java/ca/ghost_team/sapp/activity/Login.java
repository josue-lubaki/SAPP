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
import ca.ghost_team.sapp.Utils.Utilitaire;
import ca.ghost_team.sapp.service.SappAPI;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityLoginBinding;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.repository.UtilisateurRepo;
import ca.ghost_team.sapp.service.API.UtilisateurAPI;
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

                Utilisateur userLogger = new Utilisateur(
                        null,
                        username.getText().toString().trim(),
                        password.getText().toString().trim(),
                       null
                );

                // RETROFIT
                SappAPI.getApi().create(UtilisateurAPI.class).getUtilisateurViaAPI(userLogger.checksumForLogin()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        // Si conncetion Failed
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                            return;
                        }

                        Log.i(TAG, "response : " + response);
                        String messageServer = response.body();

                        // Decoder les message venu du Server
                        assert messageServer != null;
                        String messageClean = Utilitaire.decode(messageServer, 5);

                        // Extraire les informations venus du Server
                        String[] table = messageClean.split("/");
                        int utilisateurIdFromServer = Integer.parseInt(table[0]);
                        String utilisateurNomFromServer = table[1];
                        String utilisateurEmailFromServer = table[2];

                        String content = "";
                        content += "idUtilisateur : " + utilisateurIdFromServer + "\n";
                        content += "utilisateurNom : " + utilisateurNomFromServer + "\n";
                        content += "Email : " + utilisateurEmailFromServer + "\n";
                        Log.i(TAG, content);

                        BaseApplication.ID_USER_CURRENT = utilisateurIdFromServer;
                        String passwordUtilisateur = Utilitaire.hashage(password.getText().toString().trim());
                        Utilisateur userAuth = new Utilisateur(
                                utilisateurNomFromServer,
                                username.getText().toString().trim(),
                                passwordUtilisateur,
                                utilisateurEmailFromServer
                        );
                        userAuth.setIdUtilisateur(utilisateurIdFromServer);

                        new UtilisateurRepo(getApplication()).insertUtilisateur(userAuth);
                        // Connecter l'Utilisateur
                        ID_USER_CURRENT = userAuth.getIdUtilisateur();

                        if(SAVEDME){
                            usernamePref = username.getText().toString().trim();
                            passwordPref = passwordUtilisateur;

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
                    public void onFailure(Call<String> call, Throwable t) {
                        // Si erreur 404
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