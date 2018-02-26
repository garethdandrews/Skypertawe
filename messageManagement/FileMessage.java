package messageManagement;

import java.io.File;

/**
 * @file FileMessage.java
 * @author Gareth Andrews
 * @date 10 Dec 2016
 * @see Message
 *
 * Stores data about a file message.
 */
public class FileMessage extends Message {

    private Integer m_textFileId;

    public FileMessage() {}

    //File message constructor
    public FileMessage(String messageBlock, Integer sender,
                      Integer recipient, String dateTime, String senderName) {
        this.m_messageBlock = messageBlock;
        this.m_messageType = "<file>";
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_dateTime = dateTime;
        this.m_senderName = senderName;
        this.m_textFileId = MailBox.MailMan.maxTextFileId()+1;
        this.m_messageId = MailBox.MailMan.maxMessageId()+1;
    }

    //Message from file
    public FileMessage(String messageBlock, String messageType, Integer sender,
                      Integer recipient, String dateTime, String senderName,
                      int messageId, Integer textFileId) {
        this.m_messageBlock = messageBlock;
        this.m_messageType = messageType;
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_dateTime = dateTime;
        this.m_senderName = senderName;
        this.m_textFileId = textFileId;
        this.m_messageId = messageId;

    }

    public Integer getM_textFileId() {
        return m_textFileId;
    }
}
