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
    private final LiveData<List<Message>> allMessages;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.messageRepo = new MessageRepo(application);
        this.allMessages = messageRepo.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }
}
