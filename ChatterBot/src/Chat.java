//import java.util.Scanner;
//
//first ver
//
//public class Chat {
//    public static void main(String[] args) {
//        String[] repliesToIllegalRequest1 = {"what ", "say I should say "};
//        String[] repliesToIllegalRequest2 = {"whaaat ", "say say "};
//        ChatterBot_og[] bots = {new ChatterBot_og(repliesToIllegalRequest1, "Bonnie"),
//                new ChatterBot_og(repliesToIllegalRequest2, "Clyde")};
//        String statement = "Hello";
//        Scanner scanner = new Scanner(System.in);
//
//        while (!statement.equalsIgnoreCase("exit")) {
//            for (ChatterBot_og bot : bots) {
//                statement = bot.replyTo(statement);
//                System.out.println(bot.getName() + " said: " + statement);
//                scanner.nextLine();
//            }
//        }
//    }
//}


import java.util.Scanner;

public class Chat {
    /**
     * The main entry point of the Chat program.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        // Create ChatterBot objects
        ChatterBot[] bots = createBots();

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a statement or just press Enter (write 'exit' to stop): ");
        String statement = scanner.nextLine();

        while (!statement.equalsIgnoreCase("exit")) {
            for (ChatterBot bot : bots) {
                statement = bot.replyTo(statement);
                System.out.println(bot.getName() + ": " + statement);
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    /**
     * Creates an array of ChatterBot objects with predefined replies.
     *
     * @return An array of ChatterBot objects.
     */
    private static ChatterBot[] createBots() {
        return new ChatterBot[]{
                new ChatterBot("Bonnie",
                        // Replies to illegal requests
                        new String[]{
                                "say i should say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                                "What do you want? ",
                                "i don't want to say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "! ",
                                "i have no idea what is " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                                ChatterBot.REQUEST_PREFIX + "say whaat " + "whaaaat?? ",
                        },
                        // Replies to legal requests
                        new String[]{
                                "you want me to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? do"
                                        + " you? " + "alright: " +
                                        ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                                "Great! i will say: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                                "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? okay: "
                                        + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                        }
                ),
                new ChatterBot(
                        "Clyde",
                        // Replies to illegal requests
                        new String[]{
                                "what's " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "?!",
                                "say what? " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "? what's"
                                        + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "?",
                                "i can't say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST
                                        + "! it's a bad word", "whaaaat!? ",
                                ChatterBot.REQUEST_PREFIX + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                                "say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + " yourself! "
                        },
                        // Replies to legal requests
                        new String[]{
                                "hey! " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? Okay ;) ",
                                "are you sure? okay then... " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "sounds really good! ",
                                "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? okay: "
                                        + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + " ",
                        }
                )
        };
    }
}
