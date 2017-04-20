package track.messenger.commands;

import track.messenger.net.Session;
import track.messenger.store.ChatRelation;
import track.messenger.store.ChatRelationStore;
import track.messenger.messages.ChatListResultMessage;
import track.messenger.messages.Message;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;

import java.util.List;


public class ChatListCommand implements Command {


    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {
        ChatRelationStore chatRelations = (ChatRelationStore) stores.get(ChatRelation.class);
        try {
            List<Long> chatIds = chatRelations.getUserChats(session.getUser().getId());
            session.send(new ChatListResultMessage(session.getUser(), chatIds));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": failed get list of chats");
        }
    }
}
