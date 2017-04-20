package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * Простое текстовое сообщение
 */
public class TextMessage extends Message implements Serializable {
    private String text;
    private Long chatId;

    public TextMessage() {
        super(null, Type.MSG_TEXT);
        this.text = "";
    }

    public TextMessage(User sender, String data) throws InstantiationException {
        super(sender, Type.MSG_TEXT);
        String[] tokens = data.split(" ");
        try {
            chatId = Long.parseLong(tokens[0]);
            this.text = data.substring(tokens[0].length()).trim();
        } catch (Exception e) {
            throw new InstantiationException("Invalid data format");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        TextMessage message = (TextMessage) other;
        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "text='" + text + '\'' +
                '}';
    }

    public Long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = Long.parseLong(chatId);
    }

    public void setText(String text) {
        this.text = text;
    }
}