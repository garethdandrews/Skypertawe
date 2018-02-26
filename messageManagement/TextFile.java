package messageManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @file TextFile.java
 * @author Sion Rees
 * @date 09 Dec 2016
 *
 * Stores data on a text file.
 */
public class TextFile {
    private Integer m_textFileId;
    private String m_title;
    private String m_text;

    public TextFile(File file, Integer textFileId){
        this.m_title=file.getName();
        this.m_text = getFileString(file);
        this.m_textFileId = textFileId;
    }

    private String getFileString(File file){
        String tempText = "";
        try{
            Scanner in = new Scanner(file);
            while(in.hasNext()){
                tempText = m_text + in.nextLine() + "\n";
            }
        }catch(FileNotFoundException exc){
            exc.printStackTrace();
        }
        return tempText;
    }

    public Integer getM_textFileId() {
        return m_textFileId;
    }

    public String getM_title() {
        return m_title;
    }

    public String getM_text() {
        return m_text;
    }
}
