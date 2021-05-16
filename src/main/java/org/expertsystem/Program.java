package org.expertsystem;

import org.expertsystem.tools.DataPrint;
import org.expertsystem.tools.InputProvider;

import java.io.IOException;
import java.time.LocalDate;

public class Program {
    private TodoMatrix todoMatrix = new TodoMatrix();
    private InputProvider inputProvider = new InputProvider();
    private DataPrint dataPrint = new DataPrint();

    public void userMenu() throws IOException {
        String fileName = "EisenhowerMatrix.txt";

        dataPrint.printMenu();
        int userOption = inputProvider.takeIntegerInput("Choose your option: ");
        dataPrint.clearScreen();
        switch (userOption) {
            case 1:{
                addTodoItem();
                userMenu();
                break;}

            case 2:
                markItem();
                userMenu();
                break;

            case 3:
                unmarkItem();
                userMenu();
                break;

            case 4:
                removeItem();
                userMenu();
                break;

            case 5:
                todoMatrix.archiveItems();
                userMenu();
                break;

            case 6:
                todoMatrix.addItemsFromFile(fileName);
                userMenu();
                break;

            case 7:
                todoMatrix.saveItemsToFile(fileName);
                userMenu();
                break;

            case 8:
                showChosenStatus();
                userMenu();
                break;

            case 9:
                System.out.println(todoMatrix.toStringTable());
                userMenu();
                break;

            case 0:
                break;
        }
    }

    private void addTodoItem() throws IOException {
        String itemTitle = inputProvider.takeInputFromUser("Enter title of todoItem: ");

        String userItemDeadline = inputProvider.takeInputFromUser("Enter deadline (day-month): ");
        String[] itemDeadlineDayMonth = userItemDeadline.split("-");
        int day = Integer.parseInt(itemDeadlineDayMonth[0]);
        int month = Integer.parseInt(itemDeadlineDayMonth[1]);
        LocalDate itemDeadline = LocalDate.of(2020, month, day);

        boolean isImportant = false;
        String questionIfImportant = inputProvider.takeInputFromUser("Is it importatnt? T/n: ");
        if (questionIfImportant.equals("T") || questionIfImportant.equals("t")){
            isImportant = true;
        }
        todoMatrix.addItem(itemTitle, itemDeadline, isImportant);
    }

    private void markItem(){
        System.out.println(todoMatrix.toString());
        try{
            String statusFromUser = inputProvider.takeInputFromUser("Choose quarter name (IU/IN/NU/NN): ");
            String indexFromUser = inputProvider.takeInputFromUser("Enter number of item to mark: ");
            int indexFromUserInteger = Integer.parseInt(indexFromUser);
            if (indexFromUserInteger -1 >= todoMatrix.getQuarter(statusFromUser).getItems().size()){
                System.out.println("There is no index as entered");
                userMenu();
            }
            todoMatrix.getQuarter(statusFromUser).getItem(indexFromUserInteger - 1).mark();
        }
        catch (NullPointerException | IOException e){
            System.out.println("Wrong quarter name");
        }
    }

    private void unmarkItem(){
        System.out.println(todoMatrix.toString());
        try{
            String statusFromUserB = inputProvider.takeInputFromUser("Choose quarter name (IU/IN/NU/NN): ");
            String indexFromUserB = inputProvider.takeInputFromUser("Enter number of item to unmark: ");
            int indexFromUserIntegerB = Integer.parseInt(indexFromUserB);
            if (indexFromUserIntegerB -1 >= todoMatrix.getQuarter(statusFromUserB).getItems().size()){
                System.out.println("There is no index as entered");
                userMenu();
            }
            todoMatrix.getQuarter(statusFromUserB).getItem(indexFromUserIntegerB - 1).unmark();
        }
        catch (NullPointerException | IOException e){
            System.out.println("Wrong quarter name");
        }
    }

    private void removeItem(){
        System.out.println(todoMatrix.toString());
        try{
            String statusFromUserC = inputProvider.takeInputFromUser("Choose quarter name (IU/IN/NU/NN): ");
            String indexFromUserC = inputProvider.takeInputFromUser("Enter number of item to remove: ");
            int indexFromUserIntegerC = Integer.parseInt(indexFromUserC);
            if (indexFromUserIntegerC -1 >= todoMatrix.getQuarter(statusFromUserC).getItems().size()){
                System.out.println("There is no index as entered");
                userMenu();
            }
            todoMatrix.getQuarter(statusFromUserC).removeItem(indexFromUserIntegerC - 1);
        }
        catch (NullPointerException | IOException e){
            System.out.println("Wrong quarter name");
        }
    }

    private void showChosenStatus(){
        try{
            String statusFromUserD = inputProvider.takeInputFromUser("Choose quarter name (IU/IN/NU/NN): ");
            System.out.println("\nStatus: " + statusFromUserD);
            System.out.println(todoMatrix.getQuarter(statusFromUserD).toString());
        }
        catch (NullPointerException | IOException e){
            System.out.println("Wrong quarter name");
        }
    }
}
