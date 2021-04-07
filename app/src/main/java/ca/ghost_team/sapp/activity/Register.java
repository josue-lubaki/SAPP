package ca.ghost_team.sapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityRegisterBinding;
import ca.ghost_team.sapp.model.Utilisateur;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

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
    private SappDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        db = Room.databaseBuilder(getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();

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
                    TextUtils.isEmpty(register_name.getText().toString()) ) {

                if (TextUtils.isEmpty(register_name.getText()))
                    register_name.setError("Name required");
                if (TextUtils.isEmpty(register_username.getText()))
                    register_username.setError("Username required");
                if (TextUtils.isEmpty(register_password.getText()))
                    register_password.setError("Password required");
                if (TextUtils.isEmpty(register_email.getText()))
                    register_email.setError("Email require");
                 if(!register_email.getText().toString().trim().contains("@"))
                     register_email.setError("Email require '@'");


                Snackbar.make(v, "Please fill texts in the field", 5000)
                        .setAction("I understand", d -> {
                        }).show();

                // Mettre le Focus dur le champs Username
                register_name.requestFocus();
            } else {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[4];
                        field[0] = "fullname";
                        field[1] = "username";
                        field[2] = "password";
                        field[3] = "email";
                        //Creating array for data
                        String[] data = new String[4];
                        data[0] = register_name.getText().toString().trim();
                        data[1] = register_username.getText().toString().trim();
                        data[2] = register_password.getText().toString().trim();
                        data[3] = register_email.getText().toString().trim();
                        PutData putData = new PutData("http://192.168.2.183/sappserver/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();

                                if(result.equals("Sign Up Success")){
                                    Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "Result : " + result);

                                    // On instancie l'objet Utilisateur
                                    Utilisateur utilisateur = new Utilisateur(
                                            register_name.getText().toString().trim(),
                                            register_username.getText().toString().trim(),
                                            register_password.getText().toString().trim(),
                                            register_email.getText().toString().trim()
                                    );

                                    // Auto Login
                                    ID_USER_CURRENT = Login.connect_user(getApplication(), utilisateur.getUtilisateurUsername(), utilisateur.getUtilisateurPassword());

                                    if (ID_USER_CURRENT != 0) {
                                        // L'Utilisateur existe déjà
                                        Toast.makeText(Register.this, "Désolé, L'Utilisateur existe déjà", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    // on insère l'Utilisateur sur le Thread Main
                                    db.utilisateurDao().insertUtilisateur(utilisateur);
                                    Toast.makeText(Register.this, "Enregistré avec succès", Toast.LENGTH_SHORT).show();

                                    //Lancer l'activity Main
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                    Log.i(TAG, "Utilisateur enregistré");
                                }
                                else{
                                    Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
                                }

                                Log.i("PutData", result);
                            }
                        }
                        //End Write and Read data with URL
                    }
                });


            }
        });
    }
    public boolean isMailValid(String mail){

        if (mail==null || !mail.contains("@") ){
            return false;
        }
        return true;
    }
}