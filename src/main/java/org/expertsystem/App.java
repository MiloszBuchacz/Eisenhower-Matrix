package org.expertsystem;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Eisenhower Matrix");
        Program program = new Program();
        program.userMenu();
    }
}
