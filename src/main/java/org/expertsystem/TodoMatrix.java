package org.expertsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;

public class TodoMatrix {
    private HashMap<String, TodoQuarter> todoQuarters;

    public TodoMatrix(){
        todoQuarters = new HashMap<>();
        todoQuarters.put("IU", new TodoQuarter());
        todoQuarters.put("IN", new TodoQuarter());
        todoQuarters.put("NN", new TodoQuarter());
        todoQuarters.put("NU", new TodoQuarter());
    }

    public HashMap<String, TodoQuarter> getQuarters(){
        return todoQuarters;
    }

    public TodoQuarter getQuarter(String status){
        return todoQuarters.get(status);
    }

    public void addItem(String title, LocalDate deadline, boolean isImportant){
        boolean isUrgent = false;
        if (deadline.getDayOfYear() - LocalDate.now().getDayOfYear() < 4){
            isUrgent = true;
        }

        String statusImportant = isImportant ? "I" : "N";
        String statusUrgent = isUrgent ? "U" : "N";
        String status = statusImportant + statusUrgent;

        if (todoQuarters.containsKey(status)){
            getQuarter(status).addItem(title, deadline);
            return;
        }

        getQuarter(status).addItem(title, deadline);
        sortQuarters();
    }

    public void addItemsFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split("\\|");

                String[] dayMonth = values[1].split("-");

                int day = Integer.parseInt(dayMonth[0]);
                int month = Integer.parseInt(dayMonth[1]);

                String title = values[0];

                LocalDate deadline = LocalDate.of(2020, month, day);

                boolean isImportant = values.length > 2 ? true : false;

                addItem(title, deadline, isImportant);
            }
            sortQuarters();
        } catch (Exception e) {
            System.out.println("Can't read file");
        }
    }

    public void saveItemsToFile(String fileName) {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (String status : todoQuarters.keySet()) {
                TodoQuarter todoQuarter = todoQuarters.get(status);
                for (TodoItem todoItem : todoQuarter.getItems()) {
                    String line = String.format("%s|%d-%d|%s\n", todoItem.getTitle(), todoItem.getDeadline().getDayOfMonth(),
                            todoItem.getDeadline().getMonthValue(), status.charAt(0) == 'I' ? "important" : "");
                    writer.write(line);
                }
            }

            writer.close();
        }
        catch (Exception e) {
            System.out.println("Can't write to file");
        }
    }

    public void archiveItems(){
        for (TodoQuarter todoQuarter : todoQuarters.values()) {
            todoQuarter.archiveItems();
        }
    }

    public String toString(){
        String todoQuarterString = "";
        for (String key : todoQuarters.keySet()) {
            TodoQuarter todoQuarter = todoQuarters.get(key);
            todoQuarterString += key + "\n";
            todoQuarterString += todoQuarter.toString();
        }
        return todoQuarterString;
    }

    public String toStringTable() throws NullPointerException{
      /* There program check which todoItem is the longest and returns it's length. Then we check if
      it's even because we need it further when divide by 2 to center text. */
        int longestItemLength = 0;
        for (String key : todoQuarters.keySet()) {
            TodoQuarter todoQuarter = todoQuarters.get(key);
            if (longestItemLength < todoQuarter.findLongestItem()){
                longestItemLength = todoQuarter.findLongestItem();
            }
        }
        if (longestItemLength % 2 == 1){ longestItemLength += 1;}

        String urgent = "URGENT";
        String notUrgent = "NOT URGENT";
        String important = "IMPORTANT";
        String notImportant = "NOT IMPORTANT";

        // in case there is no todoitems or its' length is lower than length of shortest column header, width of table sets default to 20.
        if (longestItemLength < urgent.length()) { longestItemLength = 20;}

      /* There it makes table header like:

        |            URGENT              |           NOT URGENT           |
      --|--------------------------------|--------------------------------|--

      Program counts how many empty spaces must add before and after word Urgent and Not Urgent. Then uses repeat function to multiply empty spaces by proper number. */

        StringBuilder tablestring = new StringBuilder();

        int spacesCountHeaderUrgent = (longestItemLength - urgent.length())/2;
        int spacesCountHeaderNotUrgent = (longestItemLength - notUrgent.length())/2;

        if (longestItemLength < urgent.length()){ spacesCountHeaderUrgent = urgent.length();}
        if (longestItemLength < notUrgent.length()){ spacesCountHeaderNotUrgent = notUrgent.length();}

        tablestring.append("  |" + " ".repeat(spacesCountHeaderUrgent) + urgent + " ".repeat(spacesCountHeaderUrgent) + "|");
        tablestring.append(" ".repeat(spacesCountHeaderNotUrgent) + notUrgent + " ".repeat(spacesCountHeaderNotUrgent) + "|\n");

        String dividingLine = "--|" + "-".repeat(longestItemLength) + "|" + "-".repeat(longestItemLength) + "|--\n";

        tablestring.append(dividingLine);

      /* There it makes rest of table. It looks similar as in table header but instead of words, we have the element of each quarter.

      I | 1. [ ] 9-6  go to the doctor   |1. [ ] 14-6 buy a ticket        |
      M |                                |2. [ ] 21-7 Ann birthday        |

      What's more, we print lines as much times as IMPORTANT word length because we print single characters every line on the left side
      (as in example I and M). It gives result of made UI quarter. */

        int uiSize = todoQuarters.get("IU").getItems().size();
        int niSize = todoQuarters.get("IN").getItems().size();

        // There it checks the height of row (UI and NI quarters). In case that list of quarters' todoItems is bigger than
        // length of word 'Important' program will print additional empty lines (withouth letters on beggining).
        int firstRowHeight = Math.max(Math.max(important.length(), uiSize), niSize);

        for (int i = 0; i < firstRowHeight; i++) {
            appendIMPORTANTLetters(important, i, tablestring);
            // here it checks if index (i) is not bigger or equal quarters' list size (checks if it can take that index from list)
            if (i < uiSize) {
                int spaceFiller = longestItemLength - todoQuarters.get("IU").getItem(i).toString().length();
                tablestring.append(todoQuarters.get("IU").getItem(i).toString());
                tablestring.append(" ".repeat(spaceFiller));
                tablestring.append("|");
            } else {
                // while there is no more elements in that quarter
                tablestring.append(" ".repeat(longestItemLength) + "|");
            }
            if (i < niSize){
                int spaceFiller = longestItemLength - todoQuarters.get("IN").getItem(i).toString().length();
                tablestring.append(todoQuarters.get("IN").getItem(i).toString());
                tablestring.append(" ".repeat(spaceFiller));
                tablestring.append("|\n");
            } else {
                // while there is no more elements in that quarter
                tablestring.append(" ".repeat(longestItemLength) + "|\n");
            }
        }
        // Line diving Important and Not important
        tablestring.append(dividingLine);

        // This part works the same. There is only quarter status changed

        int unSize = todoQuarters.get("NU").getItems().size();
        int nnSize = todoQuarters.get("NN").getItems().size();

        int secondRowHeight = Math.max(Math.max(notImportant.length(), unSize), nnSize);

        for (int i = 0; i < secondRowHeight; i++){
            appendNOTIMPORTANTLetters(notImportant, i, tablestring);
            if (i < unSize){
                int spaceFiller = longestItemLength - todoQuarters.get("NU").getItem(i).toString().length();
                tablestring.append(todoQuarters.get("NU").getItem(i).toString());
                tablestring.append(" ".repeat(spaceFiller));
                tablestring.append("|");
            } else{
                tablestring.append(" ".repeat(longestItemLength) + "|");
            }
            if (i < nnSize){
                int spaceFiller = longestItemLength - todoQuarters.get("NN").getItem(i).toString().length();
                tablestring.append(todoQuarters.get("NN").getItem(i).toString());
                tablestring.append(" ".repeat(spaceFiller));
                tablestring.append("|\n");
            } else {
                tablestring.append(" ".repeat(longestItemLength) + "|\n");
            }
        }
        // Line closing table
        tablestring.append(dividingLine);


        return tablestring.toString();
    }

    private void appendIMPORTANTLetters(String important, int i, StringBuilder tableString){
        if (i < important.length()) {
            tableString.append(important.charAt(i));
        }
        else {
            tableString.append(" ");
        }
        tableString.append(" |");
    }

    private void appendNOTIMPORTANTLetters(String notImportant, int i, StringBuilder tableString){
        if (i < notImportant.length()){
            tableString.append(notImportant.charAt(i));
        }
        else {
            tableString.append(" ");
        }
        tableString.append(" |");
    }

    private void sortQuarters(){
        for (String quarterName : todoQuarters.keySet()){
            TodoQuarter todoQuarter = todoQuarters.get(quarterName);
            todoQuarter.sortTodoItems();
        }
    }
}
