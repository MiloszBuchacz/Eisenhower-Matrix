package org.expertsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class TodoQuarter {
    private ArrayList<TodoItem> todoItems;

    public TodoQuarter(){
        this.todoItems = new ArrayList<>();
    }

    public void addItem(String title, LocalDate deadline){

        TodoItem todoItem = new TodoItem(title, deadline);

        todoItems.add(todoItem);
        sortTodoItems();
    }

    public void removeItem(int index){
        todoItems.remove(index);
    }

    public void archiveItems(){
        for(int index = 0; index < todoItems.size(); index++) {
            if(todoItems.get(index).getStatus()) {
                todoItems.remove(index);
            }
        }
    }

    public TodoItem getItem(int index){
        return todoItems.get(index);
    }

    public ArrayList<TodoItem> getItems(){
        return todoItems;
    }

    public String toString(){
        int number = 0;
        String todoQuarterString = "";
        for (TodoItem todoItem : todoItems) {
            String todoItemString = todoItem.toString();
            number ++;
            todoQuarterString += String.format("%s. %s\n", number, todoItemString);
        }
        return todoQuarterString;
    }

    public int findLongestItem(){
        todoItems.sort(Comparator.comparing(TodoItem::getDeadline));
        int longestItemLength = 0;
        for (TodoItem todoItem : todoItems) {
            if (todoItem.toString().length() > longestItemLength){
                longestItemLength = todoItem.toString().length();
            }
        }

        return longestItemLength;
    }

    public void sortTodoItems(){
        boolean sorted = false;
        TodoItem temp;
        while(!sorted) {
            sorted = true;
            for (int itemIndex = 0; itemIndex < todoItems.size() - 1; itemIndex++) {

                if (todoItems.get(itemIndex).getDeadline().isAfter(todoItems.get(itemIndex + 1).getDeadline())) {
                    temp = todoItems.get(itemIndex);
                    todoItems.set(itemIndex, todoItems.get(itemIndex + 1));
                    todoItems.set(itemIndex + 1, temp);
                    sorted = false;
                }
            }
        }
    }
}
