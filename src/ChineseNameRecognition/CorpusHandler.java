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
                String[] words = line.split("\t");
                for (int i = 1; i < words.length; i++){
                    //去空格，并且按斜杠分隔词语和标注
                    String[] unit = words[i].trim().split("/");
                    //取词
                    String w = unit[0];
                    //取标注
                    String tag = unit[1];
                    System.out.println(w+"的标注为："+tag);
//                    if("nr".equals(tag)){
//
//                    }
                }
        }
    }
}
