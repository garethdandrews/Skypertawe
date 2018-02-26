package messageManagement;


/**
 * @file FileMessage.java
 * @author Sion Rees
 * @date 09 Dec 2016
 *
 * Stores data about a message.
 */
public class Message {

    protected Integer m_messageId;
    protected String m_messageBlock;
    protected String m_messageType;
    protected Integer m_sender;
    protected Integer m_recipient;
    protected String m_dateTime;
    protected String m_senderName;

    public Message() {}

    //Message constructor
    public Message(String messageBlock, Integer sender,
                   Integer recipient, String dateTime, String senderName){
        this.m_messageBlock = messageBlock;
        this.m_messageType = "<text>";
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_senderName = senderName;
        this.m_dateTime = dateTime;
        //Makes sure id is new
        this.m_messageId = MailBox.MailMan.maxMessageId()+1;

    }
    //Message from file
    public Message(String messageBlock, String messageType, Integer sender,
                   Integer recipient, String dateTime, String senderName, int messageId){
        this.m_messageBlock = messageBlock;
        this.m_messageType = messageType;
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_senderName = senderName;
        this.m_dateTime = dateTime;
        this.m_messageId = messageId;
    }

    public int getMessageId() {
        return m_messageId;
    }

    public String getMessageBlock() {
        return m_messageBlock;
    }

    public String getMessageType() {
        return m_messageType;
    }

    public void setMessageType(String messageType) {
        this.m_messageType = messageType;
    }

    public Integer getSender() {
        return m_sender;
    }

    public Integer getRecipient() {
        return m_recipient;
    }

    public String getDateOfMessage() {
        return m_dateTime;
    }

    public String getSenderName() {
        return m_senderName;
    }
}
