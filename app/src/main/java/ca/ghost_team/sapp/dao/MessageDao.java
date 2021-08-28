package ca.ghost_team.sapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.ghost_team.sapp.model.Message;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM MessageTable WHERE idReceiver = :idUser OR idSender = :idUser")
    LiveData<List<Message>> allMessages(int idUser);

    @Query("SELECT * FROM MessageTable WHERE idReceiver = :idUser GROUP BY idSender")
    LiveData<List<Message>> allMessagesReceiver(int idUser);

    @Query("SELECT * FROM MessageTable WHERE ((idReceiver = :idUser AND idSender = :idSender) OR (idReceiver = :idSender AND idSender =:idUser)) AND annonceId = :idAnnonce")
    LiveData<List<Message>> allMessagesBetween(int idUser, int idSender, int idAnnonce);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void sendMessage(Message message);

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    void sendMessage(Message... message);*/

    @Query("UPDATE MessageTable SET isRead = 1 WHERE idSender = :receiver AND idReceiver = :idUser AND annonceId = :annonceId AND isRead = 0")
    void putRead(int idUser, int receiver, int annonceId);

    @Query("SELECT COUNT(*) FROM MessageTable WHERE idReceiver = :idUser AND isRead = 0")
    LiveData<Integer> countMessageNoRead(int idUser);

    @Query("SELECT COUNT(*) FROM MessageTable WHERE idReceiver = :idUser AND isRead = 0 GROUP BY annonceId, idSender")
    LiveData<Integer> countMessageNoReadCategory(int idUser);


    @Query("DELETE FROM MessageTable")
    void deleteAllAMessage();

}
