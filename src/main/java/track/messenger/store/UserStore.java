package track.messenger.store;

import track.messenger.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class UserStore extends AbstractStore<User> {

    @Override
    public void setup(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setBytes(2, user.getPassword().getBytes());
    }

    @Override
    public String values() {
        return "(?, ?)";
    }

    @Override
    public List<User> fill(ResultSet resultSet) throws SQLException {
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getBytes("password"));
            users.add(user);
        }
        if (users.isEmpty()) {
            return null;
        } else {
            return users;
        }
    }

    @Override
    public String columns() {
        return "(username, password)";
    }

    public User getUser(String username) {
        List<User> users = get("username = '" + username + "'");
        if (users != null) {
            User user = users.get(0);
            return user;
        } else {
            return null;
        }
    }

    public User getUser(Long id) {
        List<User> users = get("id = '" + id.toString() + "'");
        if (users != null) {
            User user = users.get(0);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Class getDataClass() {
        return User.class;
    }

    public void saveUser(User user) {
        save(Collections.nCopies(1, user));
    }

}
