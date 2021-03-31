package ca.ghost_team.sapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.Utils.Conversion;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.model.Utilisateur;

public class MessageAdapter extends RecyclerView.Adapter{
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private final SappDatabase db;
    private Context mContext;
    private List<Message> mMessageList;


    public MessageAdapter(Context context) {
        mContext = context;
        mMessageList = new ArrayList<>();
        db = Room.databaseBuilder(context, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);

        if (message.getIdSender() == BaseApplication.ID_USER_CURRENT) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item_msg_sender, parent, false);
            return new SentMessageVH(view);

        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item_msg_receiver, parent, false);
            return new ReceivedMessageVH(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageVH) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:{
                //((ReceivedMessageVH) holder).bind(message);

                ((ReceivedMessageVH) holder).messageTextReceiver.setText(message.getMessage());

                // Format the stored timestamp into a readable String using method.
                ((ReceivedMessageVH) holder).timeTextReceiver.setText(Conversion.toTimeStr(message.getCreationDate()));
                Utilisateur user = db.utilisateurDao().getInfoUtilisateur(message.getIdSender());

                if(user != null)
                    ((ReceivedMessageVH) holder).nameTextReceiver.setText(user.getUtilisateurNom());
            }

        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    /**
     * Methode qui permet de passer à la liste de Message la nouvelle liste venant de la BD
     * @param listMessages liste à passer à l'adapter
     * @return void
     * */
    public void addAnnonce(List<Message> listMessages) {
        mMessageList = listMessages;
        notifyDataSetChanged();
    }

    /**
     * Classe Interne qui permet de permet de connecter les champs au View Holder pour
     * l'affichage de celui qui reçoit un message
     * */
    static class ReceivedMessageVH extends RecyclerView.ViewHolder {
        TextView messageTextReceiver, timeTextReceiver, nameTextReceiver;
        ImageView profileImage;

        ReceivedMessageVH(View itemView) {
            super(itemView);
            messageTextReceiver = itemView.findViewById(R.id.text_message_receiver);
            timeTextReceiver = itemView.findViewById(R.id.text_time_receiver);
            nameTextReceiver = itemView.findViewById(R.id.text_name_receiver);
            profileImage = itemView.findViewById(R.id.image_profile_receiver);
        }
    }

    /**
     * Classe Interne qui permet de permet de connecter les champs au View Holder pour
     * l'affichage de celui qui envoie un message
     * */
    static class SentMessageVH extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageVH(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_sender);
            timeText = itemView.findViewById(R.id.text_time_sender);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Conversion.toTimeStr(message.getCreationDate()));
        }
    }


}
