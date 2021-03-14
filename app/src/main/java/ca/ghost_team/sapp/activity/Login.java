package ca.ghost_team.sapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private final String USERNAME = "ghost";
    private final String PASSWORD = "1234";

    // Recréation des liens avec la view
    private EditText username;
    private EditText password;
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
        btn_login = binding.btnLogin;

        btn_login.setOnClickListener(v -> {
            // Vérifier si le champs ne sont pas vide
            if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText().toString())){
                if(TextUtils.isEmpty(username.getText()))
                    username.setError("Username required");
                if(TextUtils.isEmpty(password.getText()))
                    password.setError("Password required");

                Snackbar.make(v, "Please fill texts in the field", 5000)
                        .setAction("I understand", d ->{}).show();

                // Mettre le Focus dur le champs Username
                username.requestFocus();
            }
            else if(username.getText().toString().trim().equalsIgnoreCase(USERNAME) && password.getText().toString().trim().equals(PASSWORD) ){
                // Lancer l'activity Main
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Snackbar.make(v, "Username Or Password incorrect", 5000)
                        .setAction("Okay", d ->{}).show();
            }
        });
    }

}