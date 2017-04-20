package track.messenger.store;

import track.messenger.Chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ChatRelationStore extends AbstractStore<ChatRelation> {

    @Override
    public void setup(PreparedStatement statement, ChatRelation relation) throws SQLException {
        statement.setLong(1, relation.getAdminId());
        statement.setLong(2, relation.getChatId());
        statement.setLong(3, relation.getParticipantId());
    }

    @Override
    public String values() throws SQLException {
        return "(?, ?, ?)";
    }

    @Override
    public List<ChatRelation> fill(ResultSet resultSet) throws SQLException {
        List<ChatRelation> messages = new LinkedList<>();
        while (resultSet.next()) {
            ChatRelation relation = new ChatRelation();
            relation.setId(resultSet.getLong("id"));
            relation.setAdminId(resultSet.getLong("adminId"));
            relation.setChatId(resultSet.getLong("chatId"));
            relation.setParticipantId(resultSet.getLong("participantId"));
            messages.add(relation);
        }
        return messages;
    }

    @Override
    public String columns() {
        return "(adminId, chatId, participantId)";
    }

    public Chat getChat(Long id) {
        List<ChatRelation> relations = get("chatId = '" + id + "'");
        if (relations == null || relations.size() == 0) {
            return null;
        } else {
            return new Chat(relations);
        }
    }

    public Chat getChat(Long firstId, Long secondId) {
        List<ChatRelation> relations = get("adminId = '" + firstId + "' and participantId = '" + secondId +
                "' or adminId = '" + secondId + "' and participantId = '" + firstId + "'");
        if (relations == null || relations.size() == 0) {
            return null;
        } else {
            return new Chat(relations);
        }
    }

    public List<Long> getUserChats(Long userId) {
        return get("participantId = '" + userId + "'").stream()
                .map(ChatRelation::getChatId)
                .collect(Collectors.toList());
    }

    @Override
    public Class getDataClass() {
        return ChatRelation.class;
    }

    public void saveChat(Chat chat) {
        save(chat.getRelations());
    }

    public void saveRelations(List<ChatRelation> relations) {
        save(relations);
    }

    public Long getNewChatId() {
        return getMax("chatId") + 1L;
    }
}
