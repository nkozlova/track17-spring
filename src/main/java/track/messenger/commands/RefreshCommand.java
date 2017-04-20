package track.messenger.commands;

import track.messenger.messages.ChatHistResultMessage;
import track.messenger.net.Session;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.security.CryptoSystem;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.UserStore;
import track.messenger.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class RefreshCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {

        MessageStore messages = (MessageStore) stores.get(TextMessage.class);
        UserStore users = (UserStore) stores.get(User.class);
        List<TextMessage> messagesHistory =
                messages.getChatHistorySince(session.getActiveChatId(), session.getLastRefreshTime());
        try {
            if (messagesHistory != null && messagesHistory.size() != 0) {
                List<String> history = messagesHistory
                        .stream()
                        .sorted((first, second) -> first.getTimestampAsDate()
                                .compareTo(second.getTimestampAsDate()))
                        .map(textMessage -> {
                            User user = users.getUser(textMessage.getSenderId());
                            return textMessage.getTimestamp() + " " + user.getUsername() +
                                    ": " + textMessage.getText();
                        }).collect(Collectors.toList());
                session.send(new ChatHistResultMessage(session.getUser(), history));
                session.setLastRefreshTime(new Date());
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": failed update chat " + e.toString());
        }
    }
}
