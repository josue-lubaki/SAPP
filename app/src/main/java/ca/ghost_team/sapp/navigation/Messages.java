package ca.ghost_team.sapp.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.Utils.Utilitaire;
import ca.ghost_team.sapp.adapter.ListMessageAdapter;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.LayoutMessageBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.MessageRepo;
import ca.ghost_team.sapp.repository.UtilisateurRepo;
import ca.ghost_team.sapp.service.API.MessageAPI;
import ca.ghost_team.sapp.service.API.UtilisateurAPI;
import ca.ghost_team.sapp.service.SappAPI;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Messages extends Fragment {
    private static final String TAG = Messages.class.getSimpleName();
    private LayoutMessageBinding binding;
    private RecyclerView recyclerViewListMessage;
    private ListMessageAdapter mMessageAdapter;
    private SwipeRefreshLayout swipeRefreshLayoutMessage;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_message, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setTitle(R.string.message);
        this.activity = (MainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associer le champ
        recyclerViewListMessage = binding.recyclerViewListMessage;
        swipeRefreshLayoutMessage = binding.refreshMessageList;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewListMessage.setLayoutManager(linearLayoutManager);

        // Init Adapter
        mMessageAdapter = new ListMessageAdapter(getActivity());
        recyclerViewListMessage.setAdapter(mMessageAdapter);
        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        messageViewModel.getAllMessagesReceiver().observe(getViewLifecycleOwner(), conversation -> {
            mMessageAdapter.addConversation(conversation);
            mMessageAdapter.notifyDataSetChanged();
            Log.i(TAG, "RecyclerView Messages correct");
        });

        swipeRefreshLayoutMessage.setOnRefreshListener(() -> {


            // Supression  message dans rom
            SappDatabase db = Room.databaseBuilder(activity.getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                    .allowMainThreadQueries().build();
            db.messageDao().deleteAllAMessage();

            SappAPI.getApi().create(MessageAPI.class).getAllMessagesViaAPI(BaseApplication.ID_USER_CURRENT).enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    // Si conncetion Failed
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                        return;
                    }

                    List<Message> newMessage = response.body();
                    Log.i(TAG, "newAnnonce : " + newMessage);
                    Message[] tablMessage = new Message[newMessage.size()];
                    newMessage.toArray(tablMessage);





                    for (Message msd : tablMessage)
                        new MessageRepo(activity.getApplication()).sendMessage(msd);

                    for(Message msg : newMessage){
                        if(msg.getIdReceiver() == BaseApplication.ID_USER_CURRENT){


                            SappAPI.getApi().create(UtilisateurAPI.class).getUtilisateurbyID(msg.getIdSender()).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    // Si conncetion Failed
                                    if (!response.isSuccessful()) {
                                        Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                                        return;
                                    }

                                    Log.i(TAG, "response : " + response);
                                    String messageServer = response.body();

                                    // Decoder les message venu du Server
                                    assert messageServer != null;
                                    String messageClean = Utilitaire.decode(messageServer, 5);

                                    // Extraire les informations venus du Server
                                    String[] table = messageClean.split("/");
                                    int utilisateurIdFromServer = Integer.parseInt(table[0]);
                                    String utilisateurUsernameFromServer = table[1];
                                    String utilisateurNomFromServer = table[2];
                                    String utilisateurEmailFromServer = table[3];

                                    String content = "";
                                    content += "idUtilisateur : " + utilisateurIdFromServer + "\n";
                                    content += "utilisateurNom : " + utilisateurNomFromServer + "\n";
                                    content += "utilisateurUsername : " + utilisateurUsernameFromServer + "\n";
                                    content += "Email : " + utilisateurEmailFromServer + "\n";
                                    Log.i(TAG, content);

                                    Utilisateur userAuth = new Utilisateur(
                                            utilisateurNomFromServer,
                                            utilisateurUsernameFromServer,
                                            null,
                                            utilisateurEmailFromServer
                                    );
                                    userAuth.setIdUtilisateur(utilisateurIdFromServer);
                                    new UtilisateurRepo(activity.getApplication()).insertUtilisateur(userAuth);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    // Si erreur 404
                                    Log.e(TAG, t.getMessage());
                                }
                            });
                        } // end if
                    }// end for
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    // Si erreur 404
                    Log.e(TAG, t.getMessage());
                }
            });
            swipeRefreshLayoutMessage.setRefreshing(false);
        });
    }
}
