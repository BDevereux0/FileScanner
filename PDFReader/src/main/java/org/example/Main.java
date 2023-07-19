package org.example;

import org.example.service.Menu;
import org.example.service.SearchService;
import org.example.useCases.JavaDocReader;
import org.example.useCases.PDFReader;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Menu programStart = new Menu();
        programStart.executeMenuOrder();


    }
}