package org.example.useCases;

import org.example.Main;
import org.example.useCaseInterface.ReaderInterface;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaDocReader implements Callable<String> {

    private BufferedReader reader;

    private StringBuilder outputText;

    private File file;

    private String keywordSearch;

    public JavaDocReader(String keywordSearch){
        this.keywordSearch = keywordSearch;
    }

    public JavaDocReader(){

    }

    public void setReader(File file) throws FileNotFoundException {
        this.file = file;
        reader = new BufferedReader(new FileReader(file));
    }

    public void setOutputText() throws IOException {
        outputText = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null){
            outputText.append(line);
            outputText.append(System.lineSeparator());
        }
        reader.close();

    }

    public String getKeywordSearch() {
        return keywordSearch;
    }

    public void setKeywordSearch(String keywordSearch) {
        this.keywordSearch = keywordSearch;
    }

    public StringBuilder getOutputText(){
        return outputText;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void setOutputText(StringBuilder outputText) {
        this.outputText = outputText;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String call() throws Exception {
        Pattern pattern;
        Matcher matcher;
        //changing searchedText to lowercase to reduce my brain's strain
        var converToString = getOutputText().toString();
        var inputText = converToString.toLowerCase();

        //pattern to scan for
        pattern = Pattern.compile(keywordSearch);

        //scans text
        matcher = pattern.matcher(inputText);

        if (matcher.find()) {
            return getFile().getName();
        } else
            return null;
    }
}
