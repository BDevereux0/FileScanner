package org.example.useCases;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class MsWordReader {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\bdeve\\Desktop\\ProgrammingKnowledge\\Android Studio.docx");
        System.out.println(file.isFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String inputText = " ";
            while( br.readLine()!=null){
                String something = br.readLine();
                inputText = br.readLine();
                inputText = inputText + " ";
            }
            System.out.println(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
