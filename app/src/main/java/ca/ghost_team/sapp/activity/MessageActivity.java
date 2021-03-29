package ca.ghost_team.sapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.MessageAdapter;
import ca.ghost_team.sapp.databinding.ActivityMessageBinding;
import ca.ghost_team.sapp.databinding.LayoutMessageBinding;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.repository.MessageRepo;
import ca.ghost_team.sapp.viewmodel.AnnonceVendueViewModel;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    private ImageButton sendMessage;
    private EditText editMessage;
    private String TAG = MessageActivity.class.getSimpleName();
    private ActivityMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);

        // Desactiver le ToolBar
        getSupportActionBar().hide();

        sendMessage = binding.buttonSend;
        editMessage = binding.editMessage;

        // Init RecyclerView
        mMessageRecycler = binding.recyclerMessage;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMessageRecycler.setLayoutManager(linearLayoutManager);

        // Init Adapter
        mMessageAdapter = new MessageAdapter(this);
        mMessageRecycler.setAdapter(mMessageAdapter);
        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        messageViewModel.getAllMessages().observe(this, messages -> {
            mMessageAdapter.addAnnonce(messages);
            mMessageAdapter.notifyDataSetChanged();

            Log.i(TAG, "RecyclerView Message correct");
        });

        sendMessage.setOnClickListener(this::sendMessage);

    }

    /**
     * Methode qui permet d'envoyer un Message
     * */
    private void sendMessage(View view) {
        // Vérifier que le champ message n'est pas vide
        if(TextUtils.isEmpty(editMessage.getText().toString().trim())){
            Toast.makeText(this, "Entrer un Message", Toast.LENGTH_SHORT).show();
            return;
        }

        Message myMessage = new Message(editMessage.getText().toString().trim(), BaseApplication.ID_USER_CURRENT, new Date());
        new MessageRepo(getApplication()).sendMessage(myMessage);
        Log.i(TAG, "[" + myMessage.toString() + "] - ENVOYÉ !");

        // Réinitialiser le champ d'édition après l'envoi du Message
        editMessage.setText("");

    }

}