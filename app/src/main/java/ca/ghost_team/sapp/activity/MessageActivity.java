package ca.ghost_team.sapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.MessageAdapter;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.viewmodel.AnnonceVendueViewModel;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    private String TAG = MessageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Desactiver le ToolBar
        getSupportActionBar().hide();

        // Init RecyclerView
        mMessageRecycler = findViewById(R.id.recycler_message);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMessageRecycler.setLayoutManager(linearLayoutManager);

        // Init Adapter
        mMessageAdapter = new MessageAdapter(this);

        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        messageViewModel.getAllMessages().observe(this, messages -> {
            mMessageAdapter.addAnnonce(getMessages());
            mMessageAdapter.notifyDataSetChanged();

            Log.i(TAG, "RecyclerView Message correct");
        });

        mMessageRecycler.setAdapter(mMessageAdapter);
    }

    List<Message> getMessages(){
        List<Message> list = new ArrayList<>();

        list.add(new Message("Bonjour", BaseApplication.ID_USER_CURRENT, new Date()));
        list.add(new Message("Salut", 2, new Date()));
        list.add(new Message("Bonsoir", BaseApplication.ID_USER_CURRENT, new Date()));

        return list;
    }


}