package ca.ghost_team.sapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ca.ghost_team.sapp.model.Message;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM MessageTable")
    LiveData<List<Message>> allMessages();

    @Insert
    void sendMessage(Message message);

    @Insert
    void sendMessage(Message... message);
}