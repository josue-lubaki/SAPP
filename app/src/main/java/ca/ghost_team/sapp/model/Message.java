package ca.ghost_team.sapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "MessageTable",
        foreignKeys = {
                @ForeignKey(entity = Utilisateur.class,
                        parentColumns = "idUtilisateur",
                        childColumns = "idReceiver",
                        onDelete = CASCADE),

                @ForeignKey(entity = Annonce.class,
                        parentColumns = "idAnnonce",
                        childColumns = "annonceId",
                        onDelete = CASCADE)
        })
public class Message {
    @NonNull
    @PrimaryKey
    private String idMessage;

    private String message;
    private int idSender;
    private int idReceiver;
    private int annonceId;
    private boolean deleted;
    private Date creationDate;

//    public Message(String message, int idSender, int idReceiver, int annonceId) {
//        this.message = message;
//        this.idSender = idSender;
//        this.idReceiver = idReceiver;
//        this.annonceId = annonceId;
//        this.creationDate = new Date();
//    }

    public Message() {
        this.idMessage = UUID.randomUUID().toString();
        this.deleted = false;
        this.creationDate = new Date();
    }

    public String getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(String idMessage) {
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

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public int getAnnonceId() {
        return annonceId;
    }

    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", message='" + message + '\'' +
                ", idSender=" + idSender +
                ", idReceiver=" + idReceiver +
                ", annonceId=" + annonceId +
                ", creationDate=" + creationDate +
                '}';
    }
}
