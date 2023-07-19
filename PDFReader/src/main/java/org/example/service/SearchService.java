package org.example.service;

import org.example.useCases.JavaDocReader;
import org.example.useCases.PDFReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchService {
    private JavaDocReader javaDocReader;
    private PDFReader pdfReader;

    private Pattern pattern;

    private Matcher matcher;

    private List<PDFReader> pdfReaderList;

    private List<JavaDocReader> javaDocReaderList;


    public void processPDF(String filePath, String keywordSearch) throws IOException {
        //stores PDFReader objects to be checked using ExecutorService
        pdfReaderList = new ArrayList<>();

        pdfReader = new PDFReader(keywordSearch);

        //updates PDFReader's file state and loads document into PDDocument object
        pdfReader.setPDDocument(new File(filePath));

        //sets output text using PDFStripper object
        pdfReader.setOutputText();

        pdfReaderList.add(pdfReader);

        searchPDFDocs(pdfReaderList);
    }

    public void searchPDFDocs(List<PDFReader> pdfReaderList) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future;
        for (PDFReader p : pdfReaderList) {
            future = executor.submit(p);
            try {
                if (future.get() != null) {
                    System.out.println(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    public void processJavaDoc(String filePath, String keywordSearch) throws IOException {
        javaDocReaderList = new ArrayList<>();
        javaDocReader = new JavaDocReader(keywordSearch);

        //sets up IO for reading javaDocs.
        javaDocReader.setReader(new File(filePath));
        javaDocReader.setOutputText();

        javaDocReaderList.add(javaDocReader);

        searchJavaDoc(javaDocReaderList);

    }

    public void searchJavaDoc(List<JavaDocReader> javaDocReaderList) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future;
        for (JavaDocReader j : javaDocReaderList) {
            future = executor.submit(j);
            try {
                if (future.get() != null) {
                    System.out.println(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }


    public JavaDocReader getJavaDocReader() {
        return javaDocReader;
    }

    public void setJavaDocReader(JavaDocReader javaDocReader) {
        this.javaDocReader = javaDocReader;
    }

    public String getPDFText() {
        return pdfReader.getOutputText();
    }

    public StringBuilder getAllOtherText() {
        return javaDocReader.getOutputText();
    }

    public JavaDocReader getAllOtherReader() {
        return javaDocReader;
    }

    public void setAllOtherReader(JavaDocReader javaDocReader) {
        this.javaDocReader = javaDocReader;
    }

    public PDFReader getPdfReader() {
        return pdfReader;
    }

    public void setPdfReader(PDFReader pdfReader) {
        this.pdfReader = pdfReader;
    }
}
