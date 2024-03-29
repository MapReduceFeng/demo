package com.example.demo.system.elastic.utils;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class IKAnalyzerUtil {
    public static List<String> cut(String msg) throws IOException {
        StringReader sr = new StringReader(msg);
        IKSegmenter ik = new IKSegmenter(sr, true);
        Lexeme lex = null;
        List<String> list = new ArrayList<>();
        while ((lex = ik.next()) != null) {
            list.add(lex.getLexemeText());
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        String text = "中国太平成立九十周年了！";
        List<String> list = IKAnalyzerUtil.cut(text);
        System.out.println(list);
    }
}
