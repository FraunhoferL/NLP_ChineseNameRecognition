package ChineseNameRecognition;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 20171003203 张宇暄
 * @date 2019/12/9
 */
public class TextConvert {
    //普通文档转换为格式文档
    public void textToWords(File fileIn, File fileOut) throws Exception{
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fileIn), "GBK");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileOut), "GBK");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            for (int i = 0; i < line.length(); i++){
                String word = line.substring(i,i+1);
                writer.write(word + "\n");
            }
        }
        bufferedReader.close();
        writer.close();
    }

    //处理好的文档转换回普通文档
    public void wordToText(File fileIn, File fileOut) throws Exception{
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fileIn), "GBK");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileOut), "GBK");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            if (line.equals("\n")){
                writer.write(line);
            }else{
                String[] words = line.split("\t");
                if (words.length < 2){
                    writer.write(line);
                    continue;
                }
                String word = words[0];
                //System.out.println(word);
                String tag = words[1];
                if (tag.equals("O")){
                    writer.write(word);
                }else if (tag.equals("F")){
                    writer.write(" " + word);
                    System.out.print(word);
                }else if (tag.equals("E")||tag.equals("S")){
                    writer.write(word + "/nr ");
                    System.out.println(word);
                }else if (tag.equals("I")||tag.equals("B")){
                    System.out.print(word);
                    writer.write(word);
                }
            }
        }
        bufferedReader.close();
        writer.close();
    }

    public static void main(String[] args) throws Exception{
        TextConvert textConvert = new TextConvert();
        File fileIn = new File("output(test3).txt");
        File fileOut = new File("Test3_Result.txt");
//        textConvert.textToWords(fileIn,fileOut);
        textConvert.wordToText(fileIn,fileOut);
    }
}
