package ca.ghost_team.sapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.xml.transform.Result;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.dao.MessageDao;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Message;

public class MessageRepo {
    private final MessageDao dao;
    private LiveData<List<Message>> allMessages;
    private LiveData<List<Message>> allMessagesReceiver;
    private LiveData<Integer> countMessageNoRead;

    public MessageRepo(Application application) {
        SappDatabase database = SappDatabase.getInstance(application);
        dao = database.messageDao();
        this.allMessages = dao.allMessages(BaseApplication.ID_USER_CURRENT);
        this.allMessagesReceiver = dao.allMessagesReceiver(BaseApplication.ID_USER_CURRENT);
        this.countMessageNoRead = dao.countMessageNoRead(BaseApplication.ID_USER_CURRENT);
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

    public LiveData<Integer> getCountMessageNoRead() {
        return countMessageNoRead;
    }

    public LiveData<List<Message>> getAllMessageBetween(int idSender, int idAnnonceDiscussion) {
        return dao.allMessagesBetween(BaseApplication.ID_USER_CURRENT, idSender, idAnnonceDiscussion);
    }

    public LiveData<List<Message>> getAllMessagesReceiver() {
        return allMessagesReceiver;
    }

    public void deleteAllMessage() {
        new DeleteMessageAsyncTask (dao).execute();
    }

    public void sendMessage(Message message){
        new SendMessageAsyncTask(dao).execute(message);
    }

    public void putReadMessage(Message... message){
        new MessageReadAsyncTask(dao).execute(message);
    }

    private static class SendMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private final MessageDao unMessageDao;

        private SendMessageAsyncTask(MessageDao dao) {
            this.unMessageDao = dao;
        }

        @Override
        protected Void doInBackground(Message... message) {
            //for(Message msg : message)
            unMessageDao.sendMessage(message[0]);
            return null;
        }

    }
    private static class DeleteMessageAsyncTask extends  AsyncTask<Void ,Void,Void>{

        private final MessageDao unMessageDao;

        public DeleteMessageAsyncTask(MessageDao unMessageDao) {
            this.unMessageDao = unMessageDao;
        }


        @Override
        protected Void  doInBackground(Void... Void) {
            unMessageDao.deleteAllAMessage();

            return null;
        }

    }

    private static class MessageReadAsyncTask extends AsyncTask<Message, Void, Void> {

        private final MessageDao unMessageDao;

        private MessageReadAsyncTask(MessageDao dao) {
            this.unMessageDao = dao;
        }

        @Override
        protected Void doInBackground(Message... message) {
            int receiver = message[0].getIdReceiver();
            int annonceId = message[0].getAnnonceId();
            unMessageDao.putRead(BaseApplication.ID_USER_CURRENT,receiver,annonceId);
            return null;
        }

    }
}
