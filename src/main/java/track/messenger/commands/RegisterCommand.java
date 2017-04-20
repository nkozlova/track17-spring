package track.messenger.commands;

import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;
import track.messenger.User;
import track.messenger.store.UserStore;


public class RegisterCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {
        UserStore users = (UserStore) stores.get(User.class);
        RegisterMessage msg = (RegisterMessage) message;
        try {
            if (users.getUser(msg.getUsername()) != null) {
                session.send(new StatusMessage(session.getUser(), Status.USER_EXISTS));
                throw new CommandException(this.getClass() + ": user already exists");
            }
            String encryptedPassword = crypto.encrypt(msg.getPassword());
            User user = new User(msg.getUsername(), encryptedPassword);

            session.setUser(user);
            session.send(new StatusMessage(user, Status.USER_CREATED, user.getUsername()));
            session.send(new InfoResultMessage(user));

            users.saveUser(user);
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": failed registration " + e.toString());
        }
    }
}
