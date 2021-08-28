package ca.ghost_team.sapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.Utils.Utilitaire;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityRegisterBinding;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.repository.UtilisateurRepo;
import ca.ghost_team.sapp.service.API.UtilisateurAPI;
import ca.ghost_team.sapp.service.SappAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private static final String TAG = Register.class.getSimpleName();
    private ActivityRegisterBinding binding;

    // Recréation des liens avec la view
    private EditText register_name;
    private EditText register_username;
    private EditText register_password;
    private EditText register_email;
    private TextView sign_in;
    private ImageButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        getSupportActionBar().hide();

        // Assigner les champs
        register_username = binding.tvRegisterUsername;
        register_password = binding.tvRegisterPassword;
        register_email = binding.tvRegisterEmail;
        register_name = binding.tvRegisterName;
        sign_in = binding.signIn;
        btn_register = binding.btnRegister;

        sign_in.setOnClickListener(v -> {
            // Lancer l'activity Login
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });

        btn_register.setOnClickListener(v -> {
            // Vérifier si le champs ne sont pas vide

            if (TextUtils.isEmpty(register_username.getText()) ||
                    TextUtils.isEmpty(register_password.getText().toString()) ||
                    !isMailValid(register_email.getText().toString().trim()) ||
                    TextUtils.isEmpty(register_name.getText().toString())) {

                if (TextUtils.isEmpty(register_name.getText()))
                    register_name.setError("Name required");
                if (TextUtils.isEmpty(register_username.getText()))
                    register_username.setError("Username required");
                if (TextUtils.isEmpty(register_password.getText()))
                    register_password.setError("Password required");
                if (TextUtils.isEmpty(register_email.getText()))
                    register_email.setError("Email require");
                if (!register_email.getText().toString().trim().contains("@"))
                    register_email.setError("Email require '@'");

                Snackbar.make(v, "Please fill texts in the field", 5000)
                        .setAction("I understand", d -> {
                        }).show();

                // Mettre le Focus dur le champs Username
                register_name.requestFocus();
            } else {

                Utilisateur newUser = new Utilisateur(
                        register_name.getText().toString().trim(),
                        register_username.getText().toString().trim(),
                        register_password.getText().toString().trim(),
                        register_email.getText().toString().trim()
                );



                SappAPI.getApi().create(UtilisateurAPI.class).createUtilisateurViaAPI(newUser.checksumForRegister()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        // Si conncetion Failed
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                            return;
                        }
                        Log.i(TAG, "response : " + response);

                        String messageServer = response.body();
                        Log.i(TAG, "user : " + messageServer);

                        //Decrypter les messages du server
                        assert messageServer != null;
                        String messageClean = Utilitaire.decode(messageServer, 5);

                        // Extraire les informations venus du Server
                        String[] table = messageClean.split("/");
                        int utilisateurIdFromServer = Integer.parseInt(table[0]);
                        String utilisateurNomFromServer = table[1];
                        String utilisateurEmailFromServer = table[2];

                        // L'Utilisateur a été inseré dans la base de donnée distante
                        // On peut à présent l'insérer dans la base de données locale
                        // avec son mot de passe Hashé
                        Utilisateur userAuth = new Utilisateur(
                                utilisateurNomFromServer,
                                register_username.getText().toString().trim(),
                                Utilitaire.hashage(register_password.getText().toString().trim()),
                                utilisateurEmailFromServer
                        );
                        userAuth.setIdUtilisateur(utilisateurIdFromServer);

                        new UtilisateurRepo(getApplication()).insertUtilisateur(userAuth);
                        Toast.makeText(Register.this, "Enregistré avec succès", Toast.LENGTH_SHORT).show();

                        //Lancer l'activity Main
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                        Log.i(TAG, "Utilisateur enregistré");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Si erreur 404
                        Log.i(TAG, t.getMessage());
                        Log.e(TAG, t.getMessage());
                        Toast.makeText(Register.this, "Username déjà Utilisée existe déjà", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    public boolean isMailValid(String mail) {
        return mail != null && mail.contains("@");
    }
}