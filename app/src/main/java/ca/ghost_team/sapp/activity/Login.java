package ca.ghost_team.sapp.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.database.sappDatabase;
import ca.ghost_team.sapp.databinding.ActivityLoginBinding;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    private ActivityLoginBinding binding;

    private final String USERNAME = "ghost";
    private final String PASSWORD = "1234";


    // Recréation des liens avec la view
    private EditText username;
    private EditText password;
    private TextView sign_up;
    private ImageButton btn_login;

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
                // Lancer la requête pour verifier si le Username et Password donné par le User est correct
                ID_USER_CURRENT = connect_user(getApplication(),username.getText().toString(), password.getText().toString());

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
    }

    // Faire la requête pour retrieve l'ID de l'Utilisateur courant
    public static int connect_user(Application application, String username_user, String password_user) {
        sappDatabase db = Room.databaseBuilder(application, sappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();

        return db.utilisateurDao().retrieve_ID_User(username_user, password_user);
    }

}