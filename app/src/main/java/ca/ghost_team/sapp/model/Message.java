package ca.ghost_team.sapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "MessageTable")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int idMessage;

    private String message;
    private int idSender;
    private Date creationDate;

    public Message(String message, int idSender, Date creationDate) {
        this.message = message;
        this.idSender = idSender;
        this.creationDate = creationDate;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
