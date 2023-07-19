package org.example.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Menu {

    private Scanner scanner = new Scanner(System.in);

    //This method is essentially the controller. It is fed from the other methods in this class.
    public void executeMenuOrder() throws IOException {
        SearchService service = new SearchService();
        //this array contains user input for the values: Directory Path & keyword search
        String[] filePathAndKeyword;
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        //Step 1 - ask user which type of file.
        int fileType = determineFileType();

        //Step 2 - Run operation based on the file type.
        if (fileType == 1) {
            //step 3 - Get user input: Directory and keyword search

            //User input is generated from the getFilePathAndSearchWord() method.
            filePathAndKeyword = getFilePathAndSearchWord();

            //This does 2 things: processStackSearch takes in a Stack object and will execute the search function via Service.
            //populateFileList() returns a stack based on the array value of filePathAndKeyword ar[0] = file path ar[1] = keyword
            processStackSearch(populateFileList(filePathAndKeyword[0]), filePathAndKeyword[1], fileType);

            searchAgain();
        } else if (fileType == 2) {
            System.out.println("Opening wordDoc");
            //TODO, need to use external library. Apache has one called POI
            searchAgain();
        } else if (fileType == 3) {
            filePathAndKeyword = getFilePathAndSearchWord();
            processStackSearch(populateFileList(filePathAndKeyword[0]), filePathAndKeyword[1], fileType);
            searchAgain();
        }
    }

    public void processStackSearch(Stack<String> stack, String keyword, int fileType) {
        SearchService service = new SearchService();
        var size = stack.size();

        try {
            String result = "";
            for (int i = 0; i < size; i++) {
                if (fileType == 1) {
                    service.processPDF(stack.pop(), keyword);
                } else if (fileType == 2) {
                    //implement MS word doc
                } else if (fileType == 3) {
                    service.processJavaDoc(stack.pop(), keyword);
                }
                /*if (result != null) {
                    System.out.println(result);
                }*/
            }
        } catch (IOException e) {
            System.out.println("File not found: " + stack.pop());
        }
    }


    public Stack<String> populateFileList(String directory) {

        Stack<String> fileContainer = new Stack<>();
        File file = new File(directory);
        File[] files = file.listFiles();
        if (files != null) {
            for (File x : files) {
                if (x.isFile()) {
                    fileContainer.push(x.getPath());
                } else {
                    //these lines add the String objects into the stack otherwise the stack
                    //is cleared each time the method is recursively called.
                    Stack<String> subDirectoryFiles = populateFileList(x.getPath());
                    fileContainer.addAll(subDirectoryFiles);
                }
            }
        }
        return fileContainer;
    }


    public String[] getFilePathAndSearchWord() {

        String[] ar = new String[2];
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        System.out.println("Enter the file path");
        ar[0] = scanner.next();

        scanner.nextLine();

        System.out.println("Enter the search keyword");
        ar[1] = scanner.nextLine();
        return ar;
    }

    public void searchAgain() {
        int searchAction;

        System.out.println("""
                Do you want to search again?
                1 - yes
                0 - no""");
        try {
            searchAction = scanner.nextInt();


            if (searchAction == 1) {
                try {
                    executeMenuOrder();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //close program?
                scanner.close();
                System.out.println("good bye");
            }
        }catch (InputMismatchException f){
            f.getCause();
        }
    }

    public int determineFileType() {
        int userAction = 0;
        try {
            System.out.println("""
                    Which type of document is this?
                    1 - PDF
                    2 - msWord
                    3 - javaReadable""");

            userAction = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scanner.nextLine(); //flushes inputStream, otherwise program will crash when searchAgain() is called
            try {
                searchAgain();
            } catch (InputMismatchException f) {
                f.printStackTrace();
            }
        }

        return userAction;
    }
}
