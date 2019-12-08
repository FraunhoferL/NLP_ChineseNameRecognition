package ChineseNameRecognition;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 20171003203 张宇暄
 * @date 2019/12/8
 */
public class CorpusHandler {
    //读取语料库
    public void readCorpus(String Filename)throws Exception{
        File file = new File(Filename);
        //获取总行数
        int lineCount = getFileCount(file);
        Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        Writer trainWriter = new OutputStreamWriter(new FileOutputStream("Train.txt"), "GBK");
        Writer testWriter = new OutputStreamWriter(new FileOutputStream("Test.txt"), "GBK");
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        while((line = lineNumberReader.readLine()) != null){
                //System.out.println(lineNumberReader.getLineNumber());
                boolean isTest = (lineNumberReader.getLineNumber()-1)%5 == 0;
                String[] words = line.split("  ");
                //标记前一个词的标注是否是/nr
                boolean preTagIsNr = false;
                for (int i = 1; i < words.length; i++){
                    //去空格，并且按斜杠分隔词语和标注
                    String[] unit = words[i].trim().split("/");
                    //取词
                    String w = unit[0];
                    //取标注
                    String tag = unit[1];
                    //测试分词结果
//                  System.out.println(w+"的标注为："+tag);
                    if("nr".equals(tag)){
                        //标注为/nr的词，如果前面的标注也是/nr，则判定为名字
                        if (preTagIsNr){
                            if (w.length() == 1){
                                //w为单字且为名字时标注为单字人名
                                tag = "S";
                            }else{
                                tag = "B";
                            }
                            preTagIsNr = false;
                        }else{
                            //如果前面不是/nr则判定为姓
                            tag = "F";
                            preTagIsNr = true;
                        }
                    }else{
                        //如果标注不是/nr，判定为非人名
                        tag = "O";
                        preTagIsNr = false;
                    }
                    //如果是单字，直接打印
                    if (w.length() == 1){
                        writeToFile(trainWriter,testWriter,isTest,w ,tag);
                        //System.out.println(w + "/" + tag);
                    }else{
                        //不是一个字，继续分词
                        for(int j = 0; j < w.length(); j++){
                            String c = w.substring(j,j+1);
                            if ("B".equals(tag)){
                                if (j == 0){
                                    //名字的首字标注为B
                                    writeToFile(trainWriter,testWriter,isTest,c , "B");
                                    //System.out.println(c + "/" + "B");
                                }else if (j == w.length()-1){
                                    //名字的尾字标注为E
                                    writeToFile(trainWriter,testWriter,isTest,c , "E");
                                    //System.out.println(c + "/" + "E");
                                }else{
                                    //标注为中间字
                                    writeToFile(trainWriter,testWriter,isTest,c, "I");
                                    //System.out.println(c + "/" + "I");
                                }
                            }else if ("F".equals(tag)){
                                //姓氏标注
                                writeToFile(trainWriter,testWriter,isTest,c,"F");
                                //System.out.println(c + "/" + "F");
                            }else{
                                //非姓名标注
                                writeToFile(trainWriter,testWriter,isTest,c ,"O");
                                //System.out.println(c + "/" + "O");
                            }
                        }
                    }
                }
                if(isTest){
                    testWriter.write("\n");
                }else{
                    trainWriter.write("\n");
                }
        }
        trainWriter.close();
        testWriter.close();
        lineNumberReader.close();
    }

    private void writeToFile(Writer trainWriter,Writer testWriter,boolean isTest,String text,String tag)throws Exception{
        if (isTest){
            testWriter.write(text + "\t" + tag + "\n");
        }else{
            trainWriter.write(text + "\t" + tag + "\n");
        }
    }

    private int getFileCount(File file)throws Exception{
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
        while (lineNumberReader.readLine() != null){
        }
        int count = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        return  count;
    }
}
