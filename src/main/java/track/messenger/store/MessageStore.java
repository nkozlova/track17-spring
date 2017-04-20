package track.messenger.store;

import track.messenger.messages.TextMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class MessageStore extends AbstractStore<TextMessage> {

    @Override
    public void setup(PreparedStatement statement, TextMessage message) throws SQLException {
        statement.setString(1, message.getTimestamp());
        statement.setLong(2, message.getChatId());
        statement.setLong(3, message.getSenderId());
        statement.setString(4, message.getText());
    }

    @Override
    public String values() throws SQLException {
        return "(?, ?, ?, ?)";
    }

    @Override
    public List<TextMessage> fill(ResultSet resultSet) throws SQLException {
        List<TextMessage> messages = new LinkedList<>();
        while (resultSet.next()) {
            TextMessage msg = new TextMessage();
            msg.setId(resultSet.getLong("id"));
            msg.setTimestamp(resultSet.getString("timestamp"));
            msg.setChatId(resultSet.getLong("chatId"));
            msg.setSenderId(resultSet.getLong("senderId"));
            msg.setText(resultSet.getString("text"));
            messages.add(msg);
        }
        return messages;
    }

    @Override
    public String columns() {
        return "(timestamp, chatId, senderId, text)";
    }

    @Override
    public Class getDataClass() {
        return TextMessage.class;
    }

    public void saveMessage(TextMessage msg) {
        save(Collections.nCopies(1, msg));
    }

    public List<TextMessage> getChatHistory(Long chatId) {
        return get("chatId = '" + chatId.toString() + "' order by timestamp");
    }

    public List<TextMessage> getChatHistorySince(Long chatId, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, z HH:mm:ss");
        String dateAsString = dateFormat.format(date);
        return get("chatId = '" + chatId.toString() + "' and timestamp > '" + dateAsString +  "' order by timestamp");
    }
}