import java.util.Locale;

/**
 * A factory for creating player objects of different types.
 *
 * This class is responsible for generating player objects based on their type as specified by a string.
 * It supports the creation of various types of players such as human, whatever, clever, and genius.
 */
public class PlayerFactory {
    Player player;

    /**
     * Default Constructor for PlayerFactory.
     */
    public PlayerFactory() {
    }

    /**
     * Creates and returns a player object based on the specified type.
     *
     * @param playerName The type of player to create. Supported values include "human", "whatever",
     *                   "clever", and "genius".
     * @return A Player object of the specified type. Returns null if the type is not recognized.
     */
    public Player buildPlayer(String playerName) {
        switch (playerName.toLowerCase(Locale.ENGLISH)) {
            case "human":
                this.player = new HumanPlayer();
                break;

            case "whatever":
                this.player = new WhateverPlayer();
                break;

            case "clever":
                this.player = new CleverPlayer();
                break;

            case "genius":
                this.player = new GeniusPlayer();
                break;

            default:
                this.player = null;
        }
        return this.player;
    }
}