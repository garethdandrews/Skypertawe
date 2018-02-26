package messageManagement;

/**
 * @file URLMessage.java
 * @author Gareth Andrews
 * @date 10 Dec 2016
 * Stores data on a URLMessage.
 */
public class URLMessage extends Message {

    private String m_url;

    public URLMessage() {}

    //URL message constructor
    public URLMessage(String messageBlock, Integer sender,
                      Integer recipient, String dateTime, String senderName, String urlMessage) {
        this.m_messageBlock = messageBlock;
        this.m_messageType = "<url>";
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_dateTime = dateTime;
        this.m_senderName = senderName;
        this.m_url = urlMessage;
        this.m_messageId = MailBox.MailMan.maxMessageId()+1;
    }

    //URL message from file
    public URLMessage(String messageBlock, String messageType, Integer sender,
                      Integer recipient, String dateTime, String senderName,
                      int messageId, String urlMessage) {
        this.m_messageBlock = messageBlock;
        this.m_messageType = messageType;
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_dateTime = dateTime;
        this.m_senderName = senderName;
        this.m_url = urlMessage;
        this.m_messageId = messageId;
    }

    public String getURL() {
        return m_url;
    }
}
