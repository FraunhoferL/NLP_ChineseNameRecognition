package ChineseNameRecognition;

/**
 * @author 20171003203 张宇暄
 * @date 2019/12/8
 */
public class Test {
    public static void main(String[] args) throws Exception{
        CorpusHandler ch = new CorpusHandler();
        ch.readCorpus("199801.txt");
    }
}
