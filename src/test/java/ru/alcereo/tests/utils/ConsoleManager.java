package ru.alcereo.tests.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by alcereo on 20.05.17.
 */
public class ConsoleManager {

    Map<String, Executor> commandsMap = new HashMap<>();

    public ConsoleManager addCommand(String name, Executor executor){
        commandsMap.put(name, executor);
        return this;
    }

    public static ConsoleManager startWith(String name, Executor executor){
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.addCommand(name, executor);
        return consoleManager;
    }

    public void run() {

        Scanner sc = new Scanner(System.in);
        String command;

        System.out.println("Enter command:");
        while (!"exit".equals(command = sc.next())) {

            Executor executor = commandsMap.get(command);

            if (executor==null)
                System.err.println("Command not find: " + command);
            else
                executor.execute();

            System.out.println("Enter command:");
        }


        System.out.println("Exited");

    }

}
