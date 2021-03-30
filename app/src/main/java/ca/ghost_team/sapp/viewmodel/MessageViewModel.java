package ca.ghost_team.sapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.model.Message;
import ca.ghost_team.sapp.repository.MessageRepo;

public class MessageViewModel extends AndroidViewModel {
    private final MessageRepo messageRepo;
    private LiveData<List<Message>> allMessages;
    private LiveData<List<Message>> allMessagesReceiver;
    private LiveData<List<Message>> allMessagesBetween;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.messageRepo = new MessageRepo(application);
        this.allMessages = messageRepo.getAllMessages();
        this.allMessagesReceiver = messageRepo.getAllMessagesReceiver();
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

    public LiveData<List<Message>> getAllMessagesBetween(int idSender) {
        return allMessagesBetween = messageRepo.getAllMessageBetween(idSender);
    }

    public LiveData<List<Message>> getAllMessagesReceiver() {
        return allMessagesReceiver;
    }

    public void sendMessage(Message message){
        messageRepo.sendMessage(message);
    }
}
