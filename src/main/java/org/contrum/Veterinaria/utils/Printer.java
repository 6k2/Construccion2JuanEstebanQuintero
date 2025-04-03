package org.contrum.Veterinaria.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Printer {

    private static final Scanner mainReader = new Scanner(System.in);

    private static final int DEFAULT_SCREEN_WIDTH = 80;

    @FunctionalInterface
    public interface LineCommand {
        String apply(String text, int screenWidth);
    }

    private static final Map<String, LineCommand> commands = new HashMap<>();

    static {
        commands.put("center", (text, screenWidth) -> {
            int textLength = text.length();
            int padding = (screenWidth - textLength) / 2;
            if (padding < 0) padding = 0;
            String pad = " ".repeat(padding);
            return pad + text;
        });

        commands.put("border", (text, screenWidth) -> {
            text = text.trim();
            if (text.isEmpty()) {
                return "-".repeat(screenWidth);
            } else {
                int textLength = text.length();
                int totalDashes = screenWidth - textLength;
                if (totalDashes < 0) totalDashes = 0;
                int leftDashes = totalDashes / 2;
                int rightDashes = totalDashes - leftDashes;
                return "-".repeat(leftDashes) + text + "-".repeat(rightDashes);
            }
        });
    }

    public static void print(String message) {
        System.out.println(parseLine(message));
    }

    public static void print(List<String> messages) {
        for (String msg : messages) {
            System.out.println(parseLine(msg));
        }
    }

    public static void print(String... messages) {
        for (String msg : messages) {
            System.out.println(parseLine(msg));
        }
    }

    public static String read() {
        return mainReader.nextLine();
    }

    public static boolean readBoolean() {
        while (true) {
            String option = Printer.read().toLowerCase();
            switch (option) {
                case "si", "yes", "y":
                    return true;
                case "no", "n":
                    return false;
                default:
                    Printer.print("Elige una opcion valida.");
                    break;
            }
        }
    }

    public static void registerCommand(String command, LineCommand action) {
        commands.put(command, action);
    }

    private static int getScreenWidth() {
        return DEFAULT_SCREEN_WIDTH;
    }

    private static String parseLine(String line) {
        int screenWidth = getScreenWidth();
        for (Map.Entry<String, LineCommand> entry : commands.entrySet()) {
            String token = "<" + entry.getKey() + ">";
            if (line.contains(token)) {
                line = line.replace(token, "").trim();
                line = entry.getValue().apply(line, screenWidth);
            }
        }
        return line;
    }
}
