package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */

public abstract class Message implements Serializable {

    private Long id;
    private Long senderId;
    private Type type;
    private Date timestamp;

    protected Message() {}

    public Message(User sender, Type type) {
        if (sender != null) {
            this.senderId = sender.getId();
        }
        this.type = type;
        this.timestamp = new Date();
    }

    public Type getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, z HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public Date getTimestampAsDate() {
        return this.timestamp;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(String timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, z HH:mm:ss");
        try {
            this.timestamp = dateFormat.parse(timestamp);
        } catch (ParseException pe) {
            this.timestamp = new Date();
        }
    }
}
