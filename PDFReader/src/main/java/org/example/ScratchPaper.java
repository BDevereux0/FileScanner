package org.example;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScratchPaper {




    public static void main(String[] args) throws Exception {
       
        File file = new File(
                "C:\\Users\\bdeve\\Desktop\\programmingKnowledge\\AndroidStudio.docx");
        System.out.println(file.isFile());
        XWPFDocument doc = new XWPFDocument(
                new BufferedInputStream(new FileInputStream(file)));
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        System.out.println(extractor.getText());

    }


}
