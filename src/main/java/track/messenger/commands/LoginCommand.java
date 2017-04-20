package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;
import track.messenger.store.UserStore;


public class LoginCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {
        UserStore users = (UserStore) stores.get(User.class);
        LoginMessage msg = (LoginMessage) message;
        User user = users.getUser(msg.getUsername());
        try {
            if (user != null && crypto.check(user.getPassword(), msg.getPassword())) {
                session.setUser(user);
                session.send(new StatusMessage(user, Status.AUTHORIZED, user.getUsername()));
                session.send(new InfoResultMessage(user));
            } else {
                session.send(new StatusMessage(user, Status.AUTHORIZATION_ERROR));
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": failed authorization " + e.toString());
        }
    }
}
