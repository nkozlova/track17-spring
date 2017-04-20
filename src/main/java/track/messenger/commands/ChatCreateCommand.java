package track.messenger.commands;

import track.messenger.Chat;
import track.messenger.messages.Status;
import track.messenger.net.Session;
import track.messenger.store.ChatRelation;
import track.messenger.store.ChatRelationStore;
import track.messenger.messages.ChatCreateMessage;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;

import java.util.List;
import java.util.stream.Collectors;


public class ChatCreateCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {
        ChatRelationStore chatRelations = (ChatRelationStore) stores.get(ChatRelation.class);
        ChatCreateMessage msg = (ChatCreateMessage) message;
        List<Long> participants = msg.getParticipants();
        try {
            if (participants.size() == 1) {
                Chat foundChat = chatRelations.getChat(msg.getSenderId(), participants.get(0));
                if (foundChat != null) {
                    Long foundChatId = foundChat.getId();
                    session.setActiveChatId(foundChatId);
                    session.send(new StatusMessage(session.getUser(), Status.CHAT_EXISTS, foundChatId.toString()));
                    return;
                }
            }
            participants.add(msg.getSenderId());
            Long chatId = chatRelations.getNewChatId();
            List<ChatRelation> relations = participants.stream()
                    .map(partId -> new ChatRelation(msg.getSenderId(), chatId, partId))
                    .collect(Collectors.toList());
            chatRelations.saveRelations(relations);
            session.setActiveChatId(chatId);
            session.send(new StatusMessage(session.getUser(), Status.CHAT_CREATED, chatId.toString()));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": failed creating chat " + e.toString());
        }

    }
}
