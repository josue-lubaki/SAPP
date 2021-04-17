package ca.ghost_team.sapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

import java.util.Date;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.ListMessageAdapter;
import ca.ghost_team.sapp.adapter.MessageAdapter;
import ca.ghost_team.sapp.databinding.ActivityMessageBinding;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.repository.MessageRepo;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    private ImageButton sendMessage;
    private EditText editMessage;
    private String TAG = MessageActivity.class.getSimpleName();
    private MessageViewModel messageViewModel;
    private ActivityMessageBinding binding;
    private int idAnnonceCurrent;
    private int idReceiverCurrent;
    private int idAnnonceCurrentVendeur;
    private int idReceiverCurrentVendeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);

        // Desactiver le ToolBar
        getSupportActionBar().hide();

        sendMessage = binding.buttonSend;
        editMessage = binding.editMessage;

        Bundle bundle = getIntent().getExtras();

        /* Venant de DetailAnnonce
         * Si les informations idAnnonce et idReceiver viennent du click du button "Contacter" de DetailAnnonce */
        idAnnonceCurrent = bundle.getInt(DetailAnnonce.ID_ANNONCE_CURRENT);
        idReceiverCurrent = bundle.getInt(DetailAnnonce.ID_RECEIVER_CURRENT);

        /* Venant de ListMessageAdapter (Fragment Message)
        /* Si les informations idAnnonce et idReceiver viennent de ListMessageAdapter sur le click de l'Item conversation */
        idAnnonceCurrentVendeur = bundle.getInt(ListMessageAdapter.ID_ANNONCE_CURRENT_LIST_MESSAGE);
        idReceiverCurrentVendeur = bundle.getInt(ListMessageAdapter.ID_RECEIVER_CURRENT_LIST_MESSAGE);

        // Init RecyclerView
        mMessageRecycler = binding.recyclerMessage;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMessageRecycler.setLayoutManager(linearLayoutManager);

        // Init Adapter
        mMessageAdapter = new MessageAdapter(this);
        mMessageRecycler.setAdapter(mMessageAdapter);
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        /* Venant de ListMessageAdapter (Fragment Message) */
        if(idReceiverCurrentVendeur != 0)
            sendMessageBetween(idReceiverCurrentVendeur, idAnnonceCurrentVendeur);

        /* Venant de DetailAnnonce (Button Contacter)*/
        else
            sendMessageBetween(idReceiverCurrent, idAnnonceCurrent);

        // Envoi du Message au clic du Bouton "Contacter"
        sendMessage.setOnClickListener(this::sendMessage);

    }

    /**
     * La methode qui permet de donner à l'Adapter les messages entre deux Utilisateurs par rapport à une Annonce donnée
     *
     * @param idReceiver est l'ID du correspondant avec qui, vous voulez communiquer
     * @param idAnnonce est l'ID de l'Annonce dont la discussion fait réference
     * @return void
     * */
    private void sendMessageBetween(int idReceiver, int idAnnonce){
        messageViewModel.getAllMessagesBetween(idReceiver, idAnnonce).observe(this, messages -> {
            mMessageAdapter.addAnnonce(messages);
            mMessageAdapter.notifyDataSetChanged();

            Log.i(TAG, "Tous les messages reçus : \n" + messages);
        });
    }

    /**
     * Methode qui permet d'envoyer un Message par rapport à la provenance des informations
     * @see {idReceiverCurrentVendeur != 0} alors ce sont les informations venues du ListMessageAdapter (Fragment Message)
     * @see else les informations venues du DetailAnnonce (Button Contacter)
     * */
    private void sendMessage(View view) {

        if(TextUtils.isEmpty(editMessage.getText().toString().trim())){
            Toast.makeText(this, "Entrer un Message", Toast.LENGTH_SHORT).show();
            return;
        }

        Message myMessage;

        // Instancier le Message à envoyer et Inserer dans la BD
        // TODO implémenter le pattern Builder pour l'entité Message
        if(idReceiverCurrentVendeur != 0){
////            myMessage = new Message();
//            myMessage.setIdSender(BaseApplication.ID_USER_CURRENT);
//            myMessage.setIdReceiver(idReceiverCurrentVendeur);
//            myMessage.setMessage(editMessage.getText().toString().trim());
//            myMessage.setAnnonceId(idAnnonceCurrentVendeur);
        }
//        else{
//            myMessage = new Message();
//            myMessage.setIdSender(BaseApplication.ID_USER_CURRENT);
//            myMessage.setMessage(editMessage.getText().toString().trim());
//            myMessage.setIdReceiver(idReceiverCurrent);
//            myMessage.setAnnonceId(idAnnonceCurrent);
//        }
//
//        new MessageRepo(getApplication()).sendMessage(myMessage);
//        Log.i(TAG, "[" + myMessage.toString() + "] - ENVOYÉ !");

        // Réinitialiser le champ d'édition après l'envoi du Message
        editMessage.setText("");
    }

}