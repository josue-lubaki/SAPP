package ca.ghost_team.sapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.databinding.ActivityRegisterBinding;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.UtilisateurRepo;

public class Register extends AppCompatActivity {

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
            if(TextUtils.isEmpty(register_username.getText()) ||
                    TextUtils.isEmpty(register_password.getText().toString()) ||
                    TextUtils.isEmpty(register_email.getText().toString()) ||
                    TextUtils.isEmpty(register_name.getText().toString())){

                if(TextUtils.isEmpty(register_email.getText()))
                    register_name.setError("Name required");
                if(TextUtils.isEmpty(register_username.getText()))
                    register_username.setError("Username required");
                if(TextUtils.isEmpty(register_password.getText()))
                    register_password.setError("Password required");
                if(TextUtils.isEmpty(register_email.getText()))
                    register_email.setError("Email required");


                Snackbar.make(v, "Please fill texts in the field", 5000)
                        .setAction("I understand", d ->{}).show();

                // Mettre le Focus dur le champs Username
                register_name.requestFocus();
            }
            else{
                // On instancie l'objet Utilisateur
                Utilisateur utilisateur = new Utilisateur(
                        register_name.getText().toString(),
                        register_username.getText().toString(),
                        register_password.getText().toString(),
                        register_email.getText().toString()
                );

                // on insère l'Utilisateur
                new UtilisateurRepo(getApplication()).inserUtilisiateur(utilisateur);

                // Lancer l'activity Login
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}