package track.messenger.commands;

import track.messenger.net.Session;
import track.messenger.messages.Message;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;


public interface Command {

    void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException;
}
