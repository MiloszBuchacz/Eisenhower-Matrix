package org.expertsystem.tools;

public class DataPrint {

    public void printMenu(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                         + "|                         M   E   N   U                             |\n"
                         + "|-------------------------------------------------------------------|\n"
                         + "|1- add item               |6- load from file                       |\n"
                         + "|2- mark item              |7- save to file                         |\n"
                         + "|3- unmark item            |8- show chosen status                   |\n"
                         + "|4- remove item            |9- show Eisenhower Matrix               |\n"
                         + "|5- archive list           |0- EXIT                                 |\n"
                         + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
