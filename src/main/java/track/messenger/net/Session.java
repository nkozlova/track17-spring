package track.messenger.net;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Date;


import org.mockito.internal.util.io.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.Message;


/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session {

    static Logger log = LoggerFactory.getLogger(Session.class);
    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private Long activeChatId = 0L;
    private Date lastRefreshTime = new Date();

    public Long getActiveChatId() {
        return activeChatId;
    }

    public Date getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setActiveChatId(Long activeChatId) {
        this.activeChatId = activeChatId;
    }

    public void setLastRefreshTime(Date lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    private static final int MAX_MSG_SIZE = 32 * 1024;
    private static final int TIMEOUT = 100;
    private User user;

    private Socket socket;
    private Protocol protocol;
    private boolean alive;

    private InputStream in;
    private OutputStream out;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public Message getMessage() throws IOException, ProtocolException {

        byte[] buf = new byte[MAX_MSG_SIZE];
        int read;
        Message ret = null;
        try {
            read = in.read(buf, 0, MAX_MSG_SIZE);
            if (read > 0) {
                ret = protocol.decode(Arrays.copyOf(buf, read));
            }
        } catch (SocketTimeoutException ste) {
            ret = null;
        }
        return ret;
    }

    public Session(Socket clientSocket) {
        try {
            socket = clientSocket;
            socket.setSoTimeout(TIMEOUT);
            System.out.println("Connection: " + socket.getInetAddress() + ", " + this.toString());
            in = socket.getInputStream();
            out = socket.getOutputStream();
            protocol = new ObjectProtocol();
            alive = true;
        } catch (Exception e) {
            System.out.println(this.getClass() + "Can't create session " + e.toString());
            close();
        }
    }

    public void send(Message msg) throws ProtocolException, IOException {
        if (msg.getSenderId() != null) {
            log.info("sent to: " + msg.getSenderId().toString());
        } else {
            log.info("sent to: " + null);
        }
        out.write(protocol.encode(msg));
        out.flush();
    }

    // onMessage не нужен


    public void kill() {
        if (!alive) {
            IOUtil.closeQuietly(in);
            IOUtil.closeQuietly(out);
            IOUtil.closeQuietly(socket);
        }
    }

    public void close() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}