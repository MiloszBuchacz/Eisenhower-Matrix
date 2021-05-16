package org.expertsystem;

import java.time.LocalDate;

public class TodoItem {
    private String title;
    private LocalDate deadline;
    private boolean isDone;

    public TodoItem(String title, LocalDate deadline){
        this.title = title;
        this.deadline = deadline;
        isDone = false;
    }

    public String getTitle(){
        return title;
    }
    public LocalDate getDeadline(){
        return deadline;
    }
    public boolean getStatus(){
        return isDone;
    }

    public void mark(){
        isDone = true;
    }
    public void unmark(){
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String toString(){
        String markCharacter = isDone ? "x" : " ";
        int day = deadline.getDayOfMonth();
        int month = deadline.getMonthValue();
        String todoItemString = String.format("[%s] %s-%s %s", markCharacter, day, month, title);
        return todoItemString;
    }
}
