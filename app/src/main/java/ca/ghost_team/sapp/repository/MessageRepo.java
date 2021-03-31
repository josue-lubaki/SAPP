package ca.ghost_team.sapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.dao.AnnonceDao;
import ca.ghost_team.sapp.dao.MessageDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Message;

public class MessageRepo {
    private final MessageDao dao;
    private LiveData<List<Message>> allMessages;
    private LiveData<List<Message>> allMessagesReceiver;

    public MessageRepo(Application application) {
        SappDatabase database = SappDatabase.getInstance(application);
        dao = database.messageDao();
        this.allMessages = dao.allMessages(BaseApplication.ID_USER_CURRENT);
        this.allMessagesReceiver = dao.allMessagesReceiver(BaseApplication.ID_USER_CURRENT);
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

    public LiveData<List<Message>> getAllMessageBetween(int idSender, int idAnnonceDiscussion) {
        return dao.allMessagesBetween(BaseApplication.ID_USER_CURRENT, idSender, idAnnonceDiscussion);
    }

    public LiveData<List<Message>> getAllMessagesReceiver() {
        return allMessagesReceiver;
    }

    public void sendMessage(Message message){
        new SendMessageAsyncTask(dao).execute(message);
    }

    private static class SendMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private final MessageDao unMessageDao;

        private SendMessageAsyncTask(MessageDao dao) {
            this.unMessageDao = dao;
        }

        @Override
        protected Void doInBackground(Message... message) {
            unMessageDao.sendMessage(message);
            return null;
        }

    }
}
