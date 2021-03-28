package ca.ghost_team.sapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.dao.MessageDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Message;

public class MessageRepo {
    private final MessageDao dao;
    private final LiveData<List<Message>> allMessages;

    public MessageRepo(Application application) {
        SappDatabase database = SappDatabase.getInstance(application);
        dao = database.messageDao();
        this.allMessages = dao.allMessages();
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }
}
