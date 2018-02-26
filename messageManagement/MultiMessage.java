package messageManagement;

import java.util.ArrayList;

/**
 * Created by sion_rees on 11/12/2016.
 */
public class MultiMessage extends Message{
    private String m_multiKey;

    public MultiMessage(){};

    public MultiMessage(String messageBlock, Integer sender, Integer recipient, String dateTime,
                        String senderName, ArrayList<Profile> keyGen) {
        this.m_messageBlock = messageBlock;
        this.m_messageType = "<multi>";
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_dateTime = dateTime;
        this.m_senderName = senderName;
        this.m_messageId = MailBox.MailMan.maxMessageId()+1;
        this.m_multiKey = createKey(keyGen);
    }



    public MultiMessage(String messageBlock, Integer sender, Integer recipient, String dateTime,
                        String senderName, int messageId, String m_multiKey) {
        this.m_messageBlock = messageBlock;
        this.m_sender = sender;
        this.m_recipient = recipient;
        this.m_dateTime = dateTime;
        this.m_senderName = senderName;
        this.m_messageId = messageId;
        this.m_multiKey = m_multiKey;
    }

    private String createKey(ArrayList<Profile> keyGen) {
        String multiKey = "";
        while(!keyGen.isEmpty()){
            int min = getMinIndex(keyGen);
            multiKey = multiKey + String.valueOf(keyGen.get(min).getProfileId()) + ".";
            keyGen.remove(min);
        }
        return multiKey;

    }

    private int getMinIndex(ArrayList<Profile> keyGen){
        int min = 0;
        for(int i = 0; i < keyGen.size(); i++){
            if(keyGen.get(i).getProfileId() < min){
                min = i;
            }
        }
        return min;
    }

    public String getM_multiKey() {
        return m_multiKey;
    }
}
