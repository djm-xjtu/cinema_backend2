package com.fivesix.fivesixserver.classifier;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SegmentUtil {
    public static String getPath(){
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        java.net.URL url = classLoader.getResource("");
        String ROOT_CLASS_PATH = url.getPath() + "/";
        File rootFile = new File(ROOT_CLASS_PATH);
        String WEB_INFO_DIRECTORY_PATH = rootFile.getParent() + "/";
        File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);
        // 这里 SERVLET_CONTEXT_PATH 就是WebRoot的路径
        String SERVLET_CONTEXT_PATH = webInfoDir.getParent() + "/";
        System.out.println(SERVLET_CONTEXT_PATH);
        return SERVLET_CONTEXT_PATH;
    }
    // 自定义词典
    public static final String CUSTOMWORDS_PATH = System.getProperty("user.dir")+"/src/main/java/com/fivesix/fivesixserver/classifier/customwords.txt";
    // 停用词表
    public static final String STOPWORDS_PATH = System.getProperty("user.dir")+"/src/main/java/com/fivesix/fivesixserver/classifier/cn_stopwords.txt";
    public static Set<String> stopWords = new HashSet<>();
    // 否定词表
    public static final String NONWORDS_PATH = System.getProperty("user.dir")+"/src/main/java/com/fivesix/fivesixserver/classifier/cn_nonwords.txt";
    public static Set<String> nonWords = new HashSet<>();
    // 分割符集合
    public static final Set<String> SPLITWORDS;
    // 不可见字符
    public static final String REGREX = "[\\u200b-\\u200f]|[\\u200e-\\u200f]|[\\u202a-\\u202e]|[\\u2066-\\u2069]|\ufeff|\u06ec";

    /**
     * 加载辅助词表，辅助词表只会影响最后分析的准确度，不会导致发生错误
     */
    static {
        try {
            stopWords.addAll(FileUtil.fileToList(STOPWORDS_PATH));
        } catch (IOException e) {
            System.err.println("停用词表加载失败：" + STOPWORDS_PATH);
        }

        try {
            nonWords.addAll(FileUtil.fileToList(NONWORDS_PATH));
        } catch (IOException e) {
            System.err.println("否定词表加载失败：" + NONWORDS_PATH);
        }

        try {
            FileUtil.fileToList(CUSTOMWORDS_PATH).forEach(CustomDictionary::add);
        } catch (IOException e) {
            System.err.println("自定义词表加载失败：" + CUSTOMWORDS_PATH);
        }

        SPLITWORDS = Stream.of(",", ".", "!", "?", "，", "。", "！", "？", "[").collect(Collectors.toSet());
    }

    /**
     * 重写的分词方法，去除停用词，转换否定词
     */
    public static Set<String> segment(String text) {
        HashSet<String> words = new HashSet<>(); // 使用set忽略了词频
        int tag = 1; // 标记，遇到否定词标记取反

        List<Term> termList = HanLP.segment(text);
        for (Term t: termList) {
            String word = t.word;

            // 遇到否定词
            if (nonWords.contains(word)) {
                tag = -tag;
                continue;
            }
            // 遇到分隔符，中断否定
            if (SPLITWORDS.contains(word) || word.trim().isEmpty()) {
                if (tag == -1) {
                    tag = -tag;
                }
                continue;
            }
            // 剔除停用词，不可见字符和人名（nr词性表示人名）
            if (stopWords.contains(word) || word.matches(REGREX) || t.nature.startsWith("nr")) {
                continue;
            }
            // 转换否定词，添加N前缀
            if (tag == -1) {
                word = "N" + word;
            }
            // 添加词性后缀
            words.add(word + t.nature);
        }
        return words;
    }
}
