package track.messenger.store;

import java.io.Serializable;


public class ChatRelation implements Serializable {
    private Long id;
    private Long adminId;
    private Long chatId;
    private Long participantId;

    public ChatRelation() {}

    public ChatRelation(Long adminId, Long chatId, Long participantId) {
        this.adminId = adminId;
        this.chatId = chatId;
        this.participantId = participantId;
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }
}
