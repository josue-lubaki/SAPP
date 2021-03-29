package ca.ghost_team.sapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.model.Utilisateur;

public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ListMessageVH>{

    Context context;
    List<Message> listConversation;
    SappDatabase db;

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
    }

    @Override
    public int getItemCount() {
        return listConversation.size();
    }


    public void addConversation(List<Message> liste){
        listConversation = liste;
        notifyDataSetChanged();
    }


    static class ListMessageVH extends RecyclerView.ViewHolder{

        TextView username;
        TextView annonceTitle;

        public ListMessageVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.text_list_msg_item_username);
            annonceTitle = itemView.findViewById(R.id.text_list_msg_item_titre_Annonce);
        }

    }

}
