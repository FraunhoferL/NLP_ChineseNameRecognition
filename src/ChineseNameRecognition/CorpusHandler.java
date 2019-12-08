package ChineseNameRecognition;

import java.io.*;

/**
 * @author 20171003203 张宇暄
 * @date 2019/12/8
 */
public class CorpusHandler {
    //读取语料库
    public void readCorpus(String Filename)throws Exception{
        File file = new File(Filename);
        Reader reader = null;
        reader = new InputStreamReader(new FileInputStream(file));
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        while((line = lineNumberReader.readLine()) != null){

        }
    }
}
