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

    private static SappDatabase db;
    private Context mContext;
    private List<Message> mMessageList;
    

    public MessageAdapter(Context context) {
        mContext = context;
        mMessageList = new ArrayList<>();
        this.db = Room.databaseBuilder(context, SappDatabase.class,"SappDatabase")
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
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageVH) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void addAnnonce(List<Message> messages) {
        mMessageList = messages;
        notifyDataSetChanged();
    }

    static class ReceivedMessageVH extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageVH(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_receiver);
            timeText = itemView.findViewById(R.id.text_time_receiver);
            nameText = itemView.findViewById(R.id.text_name_receiver);
            profileImage = itemView.findViewById(R.id.image_profile_receiver);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText("" + Conversion.toTimeStr(message.getCreationDate()));

            Utilisateur user = db.utilisateurDao().getInfoUtilisateur(message.getIdSender());
            nameText.setText(user.getUtilisateurNom());

            // Insert the profile image from the URL into the ImageView.
            // Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

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
            timeText.setText("" + Conversion.toTimeStr(message.getCreationDate()));

        }
    }


}
