package track.messenger.commands;

import track.messenger.messages.Status;
import track.messenger.net.Session;
import track.messenger.store.ChatRelation;
import track.messenger.store.ChatRelationStore;
import track.messenger.Chat;
import track.messenger.messages.Message;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;
import track.messenger.User;
import track.messenger.store.MessageStore;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.TextMessage;


public class TextCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {
        ChatRelationStore chatRelations = (ChatRelationStore) stores.get(ChatRelation.class);
        MessageStore messages = (MessageStore) stores.get(TextMessage.class);
        try {
            User user = session.getUser();
            TextMessage msg = (TextMessage) message;
            if (user != null) {
                Chat chat = chatRelations.getChat(msg.getChatId());
                if (chat != null && chat.contains(user)) {
                    messages.saveMessage(msg);
                    session.setActiveChatId(msg.getChatId());
                    session.send(new StatusMessage(user, Status.MESSAGE_DELIVERED, chat.getId().toString()));
                } else {
                    session.send(new StatusMessage(user, Status.WRONG_DESTINATION));
                }
            } else {
                session.send(new StatusMessage(user, Status.NOT_AUTHORIZED));
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": failed send message");
        }
    }
}
