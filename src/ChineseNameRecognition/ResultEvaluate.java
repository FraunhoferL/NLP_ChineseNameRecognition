package ChineseNameRecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 20171003203 张宇暄
 * @date 2019/12/9
 */
public class ResultEvaluate {
    private void evaluate(File file) throws Exception {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "GBK");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        //名字总数
        int totalNameNum = 0;
        //错误识别的名字数
        int wrongNameNum = 0;
        //识别总数
        int totalRecognizedNum = 0;
        boolean trueFlag = true;
        boolean resultFlag = true;
        List<String> trueNameTags = new ArrayList<>();
        List<String> resultNameTags = new ArrayList<>();
        StringBuilder trueTags = new StringBuilder();
        StringBuilder resultTags = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                String[] words = line.split("\t");
                String word = words[0];
                String trueTag = words[1];
                String resultTag = words[2];

                if (trueTag.equals("O")) {
                    if (!trueFlag) {
                        trueNameTags.add(trueTags.toString());
                        trueTags.delete(0, trueTags.length());
                    }
                    trueFlag = true;
                } else {
                    trueFlag = false;
                    trueTags.append(trueTag);
                }

                if (resultTag.equals("O")) {
                    if (!resultFlag) {
                        resultNameTags.add(resultTags.toString());
                        resultTags.delete(0, resultTags.length());
                    }
                    resultFlag = true;
                } else {
                    resultFlag = false;
                    resultTags.append(resultTag);
                }
            }
        }

        totalNameNum = trueNameTags.size();
        trueNameTags.retainAll(resultNameTags);
        wrongNameNum = resultNameTags.size() - trueNameTags.size();
        totalRecognizedNum = resultNameTags.size();


        double p = (totalNameNum - wrongNameNum) * 1.0 / totalRecognizedNum;
        double r = (1 - (wrongNameNum * 1.0 / totalNameNum));
        double f = (2.0 * p * r)/(p + r);
        System.out.println("名字总数为：" + totalNameNum);
        System.out.println("错误识别的名字数为：" + wrongNameNum);
        System.out.println("识别总数为：" + totalRecognizedNum);
        //把结果转化为百分比表示
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        String p_p = numberFormat.format(p);
        String r_p = numberFormat.format(r);
        String f_p = numberFormat.format(f);
        System.out.println("正确率为：" + p_p);
        System.out.println("召回率为：" + r_p);
        System.out.println("F-度量值为：" + f_p);
    }

    public static void main(String[] args) throws Exception {
        ResultEvaluate resultEvaluate = new ResultEvaluate();
        File file = new File("output(test).txt");
        resultEvaluate.evaluate(file);
    }
}
