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
        /**
         * Modifies the given text according to the command and screen width.
         *
         * @param text the text to be modified
         * @param screenWidth the width of the screen, used to calculate the
         *                    necessary padding
         * @return the modified text
         */
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

    /**
     * Prints a message to the console, optionally applying one or more of the
     * following line commands:
     *
     * <ul>
     *     <li>{@code center}: Centers the given text in the screen.</li>
     *     <li>{@code border}: Pads the given text with dashes on the left and right
     *     sides to create a border.</li>
     * </ul>
     *
     * Example:
     * <pre>
     *     Printer.print("Hola, mundo! &lt;border&gt;");
     * </pre>
     *
     * @param message the message to print
     */
    public static void print(String message) {
        System.out.println(parseLine(message));
    }

    /**
     * Prints a list of messages to the console, optionally applying one or more
     * line commands to each message.
     *
     * Each message in the list will be processed by the {@code parseLine} method
     * and then printed. Line commands such as {@code center} and {@code border}
     * can be used within each message.
     *
     * @param messages the list of messages to print
     */
    public static void print(List<String> messages) {
        for (String msg : messages) {
            System.out.println(parseLine(msg));
        }
    }

    /**
     * Prints a variable number of messages to the console, optionally applying
     * one or more line commands to each message.
     *
     * Each message will be processed by the {@code parseLine} method and then
     * printed. Line commands such as {@code center} and {@code border} can be
     * used within each message.
     *
     * @param messages the list of messages to print
     */
    public static void print(String... messages) {
        for (String msg : messages) {
            System.out.println(parseLine(msg));
        }
    }

    /**
     * Reads a line of input from the user and returns it as a string.
     *
     * This method does not apply any line commands to the input string.
     *
     * @return the line of input entered by the user
     */
    public static String read() {
        return mainReader.nextLine();
    }

    /**
     * Reads a boolean value from the user, prompting until a valid boolean
     * value is entered.
     *
     * Valid boolean values are:
     * <ul>
     *     <li>si, yes, y: true</li>
     *     <li>no, n: false</li>
     * </ul>
     *
     * If the user enters an invalid value, a message is printed prompting the
     * user to enter a valid option.
     *
     * @return the boolean value entered by the user
     */
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

    /**
     * Registers a line command.
     *
     * <p>Registered commands can be used to modify the text displayed by
     * {@link #print(String)} and related methods. The command is matched
     * exactly, so "foo" will not match "<foo>".
     *
     * @param command the command to be registered
     * @param action the action to be performed when the command is matched
     */
    public static void registerCommand(String command, LineCommand action) {
        commands.put(command, action);
    }

    /**
     * Gets the width of the screen in characters.
     *
     * @return the width of the screen in characters
     */
    private static int getScreenWidth() {
        return DEFAULT_SCREEN_WIDTH;
    }

    /**
     * Parses a line of text and applies all registered line commands to it.
     *
     * <p>This method iterates over all registered commands and applies them
     * to the line of text if the command matches. If multiple commands match,
     * they are applied in the order in which they were registered.
     *
     * @param line the line of text to parse
     * @return the parsed line of text
     */
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
