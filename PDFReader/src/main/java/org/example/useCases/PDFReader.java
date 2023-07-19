package org.example.useCases;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.useCaseInterface.ReaderInterface;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFReader implements Callable<String> {

    private PDDocument doc;

    private String outputText;

    private File file;

    private PDFTextStripper pdfTextStripper;

    private String keywordSearch;

    public PDFReader(String keywordSearch) throws IOException {
        this.pdfTextStripper = new PDFTextStripper();
        this.keywordSearch = keywordSearch;
    }

    public void setPDDocument(File file) throws IOException {
        //move this to a constructor for PDFReader to fix crashing with command line.

        setStripper(pdfTextStripper);
        this.file = file;
        doc = PDDocument.load(file);
    }

    public void setOutputText() throws IOException {

        outputText = getStripper().getText(this.getDoc());
        doc.close();
    }

    @Override
    public String call() throws Exception {
        Pattern pattern;
        Matcher matcher;
        String lowerCaseKeyword = getKeywordSearch().toLowerCase();

        //changing searchedText to lowercase to reduce my brain's strain
        String lowerCaseInputText = getOutputText().toLowerCase();

        //sets text to search for
        pattern = Pattern.compile(lowerCaseKeyword);
        //scans text for pattern
        matcher = pattern.matcher(lowerCaseInputText);

        if (matcher.find()) {
            return getFile().getName();
        } else
            return null;

    }

    public PDFTextStripper getPdfTextStripper() {
        return pdfTextStripper;
    }

    public void setPdfTextStripper(PDFTextStripper pdfTextStripper) {
        this.pdfTextStripper = pdfTextStripper;
    }

    public String getKeywordSearch() {
        return keywordSearch;
    }

    public void setKeywordSearch(String keywordSearch) {
        this.keywordSearch = keywordSearch;
    }

    public PDDocument getDoc() {
        return doc;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setDoc(PDDocument doc) {
        this.doc = doc;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public PDFTextStripper getStripper() {
        return pdfTextStripper;
    }

    public void setStripper(PDFTextStripper stripper) {
        this.pdfTextStripper = stripper;
    }


}
