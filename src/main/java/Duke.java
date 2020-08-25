import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Duke" + System.lineSeparator() + "What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        String[] commands = new String[100];
        int commandNum = 0;

        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            System.out.println("____________________________________________________________");
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (command.equals("list")){
                for (int i = 0; i < commandNum; i++) {
                    System.out.println((i + 1) + ". " + commands[i]);
                }
            } else {
                commands[commandNum] = command;
                commandNum++;
                System.out.println("added: " + command);
            }
            System.out.println("____________________________________________________________");
        }
    }
}
