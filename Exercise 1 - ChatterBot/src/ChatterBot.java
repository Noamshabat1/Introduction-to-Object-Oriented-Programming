import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */

class ChatterBot {

    // Constants for request handling
    static final String REQUEST_PREFIX = "say ";
    static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
    static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";

    // Bot properties
    private final String name;
    private final Random rand = new Random();
    private final String[] legalRequestsReplies;
    private final String[] repliesToIllegalRequest;

    /**
     * Constructor for ChatterBot.
     *
     * @param name                    Name of the bot.
     * @param repliesToIllegalRequest Possible replies to illegal requests.
     * @param legalRequestsReplies    Possible replies to legal requests.
     */
    ChatterBot(String name, String[] repliesToIllegalRequest, String[] legalRequestsReplies) {
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        this.legalRequestsReplies = new String[legalRequestsReplies.length];
        this.name = name;

        for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
            this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
        }

        for (int i = 0; i < legalRequestsReplies.length; i = i + 1) {
            this.legalRequestsReplies[i] = legalRequestsReplies[i];
        }
    }

    /**
     * Returns the name of the bot.
     *
     * @return The name of the bot.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Responds to a given statement by determining whether it is legal or illegal.
     * If legal, extracts the requested phrase and generates a response.
     * If illegal, generates a response with a random pattern.
     *
     * @param statement The input statement.
     * @return The bot's response to the statement.
     */
    String replyTo(String statement) {
        if (isLegal(statement)) {
            return replyToLegalRequest(statement);
        }
        return replyToIllegalRequest(statement);
    }

    /**
     * Generates a response to both legal and illegal request with a random pattern.
     *
     * @param statement   The request statement.
     * @param placeholder The Place-holder to use for the statement string
     * @param replies     The Possible replies that can be answered
     * @return The bot's response to a request.
     */

    String replacePlaceholderInARandomPattern(String[] replies, String statement, String placeholder) {
        int randomIndex = rand.nextInt(replies.length);
        String reply = replies[randomIndex];
        return reply.replaceAll(placeholder, statement);
    }


    /**
     * Generates a response to a legal request by replacing the placeholder with a requested phrase.
     *
     * @param statement The legal request statement.
     * @return The bot's response to the legal request.
     */
    String replyToLegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(this.legalRequestsReplies,
                statement.replaceFirst(REQUEST_PREFIX, ""), PLACEHOLDER_FOR_REQUESTED_PHRASE);
    }

    /**
     * Generates a response to illegal request by replacing the placeholder with a requested phrase.
     *
     * @param statement The illegal request statement.
     * @return The bot's response to the illegal request.
     */
    String replyToIllegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(this.repliesToIllegalRequest, statement,
                PLACEHOLDER_FOR_ILLEGAL_REQUEST);
    }

    /**
     * Checks if a statement is legal by verifying if it starts with the proper sentence.
     *
     * @param statement The input statement.
     * @return True if the statement is legal; otherwise, false.
     */
    boolean isLegal(String statement) {
        return statement.startsWith(REQUEST_PREFIX);
    }
}
