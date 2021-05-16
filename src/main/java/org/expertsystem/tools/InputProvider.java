package org.expertsystem.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputProvider {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String takeInputFromUser(String messageForUser) throws IOException {
        System.out.print(messageForUser);
        String userAnswer = reader.readLine();
        return userAnswer;
    }

    public int takeIntegerInput(String messageForUser) throws IOException {
        System.out.print(messageForUser);
        return Integer.parseInt(reader.readLine());
    }
}
