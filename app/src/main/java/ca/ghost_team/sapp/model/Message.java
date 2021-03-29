package ca.ghost_team.sapp.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

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
    @PrimaryKey(autoGenerate = true)
    private int idMessage;

    private String message;
    private int idSender;
    private int idReceiver;
    private int annonceId;
    private Date creationDate;

    public Message(String message, int idSender, int idReceiver, int annonceId, Date creationDate) {
        this.message = message;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.annonceId = annonceId;
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
