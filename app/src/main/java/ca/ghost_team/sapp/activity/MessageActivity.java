package ca.ghost_team.sapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.ghost_team.sapp.R;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Desactiver le ToolBar
        getSupportActionBar().hide();
    }
}