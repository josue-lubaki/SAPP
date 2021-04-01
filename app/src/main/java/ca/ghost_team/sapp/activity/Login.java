package ca.ghost_team.sapp.activity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityLoginBinding;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

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
            }
            else {
                ID_USER_CURRENT = 0;
                // Lancer la requête pour verifier si le Username et Password donné par le User est correct
                ID_USER_CURRENT = connect_user(getApplication(), username.getText().toString().trim(), password.getText().toString().trim());

                // User trouvé
                if (ID_USER_CURRENT != 0) {

                    //Lancer l'activity Main
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.i(TAG, "Utilisateur trouvé");
                } else {
                    Snackbar.make(v, "Username Or Password incorrect", 5000)
                            .setAction("Okay", d -> {
                            }).show();
                }
            }
        });

        /* Listener pour le switch */
        switch_save_credentials.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Si le switch est activé
            if(isChecked){
                savePreferences(buttonView);
            }
        });
    }


    /**
     * Methode qui nous permet de bloquer le retour en arrière après une déconnexion
     * */
    @Override
    public void onBackPressed() {
        Log.i(TAG, "On empêche le Button Back");
    }


    /**
     * Methode qui permet de sauvergarder les informations
     * @return void
     * */
    private void savePreferences(View view) {
        if (!TextUtils.isEmpty(username.getText()) && !TextUtils.isEmpty(password.getText().toString())) {

            ID_USER_CURRENT = 0;
            // Lancer la requête pour verifier si le Username et Password donné par le User est correct
            ID_USER_CURRENT = connect_user(getApplication(), username.getText().toString().trim(), password.getText().toString().trim());

            // User trouvé
            if (ID_USER_CURRENT != 0) {
                usernamePref = username.getText().toString().trim();
                passwordPref = password.getText().toString().trim();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", usernamePref);
                editor.putString("password", passwordPref);
                editor.apply();
            }
            else {
                Snackbar.make(view, "Username Or Password incorrect", 5000)
                        .setAction("Okay", d -> {
                        }).show();
            }
        }
    }

    /**
     * Faire la requête pour rerchercher l'ID de l'Utilisateur courant
     * @param application c'est le context de l'application hôte
     * @param username_user le nom de l'Utilisateur dont il faut vérifier dans la BD
     * @param password_user le password pour y accéder
     *
     * @return int
     * */
    public static int connect_user(Application application, String username_user, String password_user) {
        SappDatabase db = Room.databaseBuilder(application, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();

        return db.utilisateurDao().retrieve_ID_User(username_user, password_user);
    }

}