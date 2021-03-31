package ca.ghost_team.sapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.activity.DetailAnnonce;
import ca.ghost_team.sapp.activity.MessageActivity;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.model.Utilisateur;


public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ListMessageVH>{
    private static final String TAG = ListMessageAdapter.class.getSimpleName();
    Context context;
    List<Message> listConversation;
    SappDatabase db;
    public static final String ID_ANNONCE_CURRENT_LIST_MESSAGE = "ca.ghost_team.sapp.adapter.idAnnonceCurrent";
    public static final String ID_RECEIVER_CURRENT_LIST_MESSAGE = "ca.ghost_team.sapp.adapter.idReceiverCurrent";

    public ListMessageAdapter(Context context) {
        this.context = context;
        this.listConversation = new ArrayList<>();
        this.db = Room.databaseBuilder(context, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ListMessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_message_item, parent, false);
        return new ListMessageAdapter.ListMessageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMessageVH holder, int position) {
        Message message = listConversation.get(position);

        int idSender = message.getIdSender();
        int idAnnonce = message.getAnnonceId();

        if(message.getIdReceiver() == BaseApplication.ID_USER_CURRENT){
            Utilisateur userSender = db.utilisateurDao().getInfoUtilisateur(idSender);
            Annonce annonce = db.annonceDao().getInfoAnnonce(idAnnonce);

            if(userSender != null && annonce != null){
                holder.username.setText(userSender.getUtilisateurNom());
                holder.annonceTitle.setText(annonce.getAnnonceTitre());
            }
        }

        holder.cardViewListMessageItem.setOnClickListener(v -> {
            Log.i(TAG,"IdAnnonceCurrent : " + idAnnonce);
            Log.i(TAG,"IdSenderCurrent : " + message.getIdSender());

            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra(ID_ANNONCE_CURRENT_LIST_MESSAGE, idAnnonce);
            intent.putExtra(ID_RECEIVER_CURRENT_LIST_MESSAGE, message.getIdSender());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listConversation.size();
    }

    /**
     * Methode qui permet de passer à la liste de conversation la nouvelle liste venant de la BD
     * @param liste liste à passer à l'adapter
     * @return void
     * */
    public void addConversation(List<Message> liste){
        listConversation = liste;
        notifyDataSetChanged();
    }


    static class ListMessageVH extends RecyclerView.ViewHolder{

        TextView username;
        TextView annonceTitle;
        CardView cardViewListMessageItem;

        public ListMessageVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.text_list_msg_item_username);
            annonceTitle = itemView.findViewById(R.id.text_list_msg_item_titre_Annonce);
            cardViewListMessageItem = itemView.findViewById(R.id.cardView_list_message_item);
        }

    }

}
