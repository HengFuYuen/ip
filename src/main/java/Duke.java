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
        Task[] tasks = new Task[100];
        int taskNum = 0;

        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            System.out.println("____________________________________________________________");
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskNum; i++) {
                    Task currentTask = tasks[i];
                    System.out.println((i + 1) + ".[" + currentTask.getStatusIcon() + "] "
                            + currentTask.getDescription());
                }
            } else if (command.startsWith("done")) {
                int finishedTaskNum = Integer.parseInt(command.replace("done ", "")) - 1;
                Task finishedTask = tasks[finishedTaskNum];
                finishedTask.markAsDone();
                System.out.println("Nice! I've marked this task as done:" + System.lineSeparator()
                        + "  [" + finishedTask.getStatusIcon() + "] " + finishedTask.getDescription());
            } else {
                tasks[taskNum] = new Task(command);
                taskNum++;
                System.out.println("added: " + command);
            }
            System.out.println("____________________________________________________________");
        }
    }
}
