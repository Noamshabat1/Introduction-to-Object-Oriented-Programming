import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Exercise1_Tests {
    private double percentagedTournament(int rounds, int size, int streak, Player[] players) {
        int wins1 = 0;
        int ties = 0;

        for (int i = 0; i < rounds; i++) {
            Mark res = (new Game(i % 2 == 0 ? players[0] : players[1], i % 2 == 0 ? players[1] : players[0], size, streak,
                    new VoidRenderer()).run());

            switch (res) {
                case X:
                    if (i % 2 == 0) {
                        wins1++;
                    }
                    break;
                case O:
                    if (i % 2 != 0) {
                        wins1++;
                    }
                    break;
            }
        }
        return (double) wins1 / rounds;
    }

    @Test
    public void test_genius_clever() {
        runTestWithDifferentRounds("genius", "clever");
    }

    @Test
    public void test_genius_whatever() {
        runTestWithDifferentRounds("genius", "whatever");
    }

    @Test
    public void test_clever_whatever() {
        runTestWithDifferentRounds("clever", "whatever");
    }

    private void runTestWithDifferentRounds(String playerOneType, String playerTwoType) {
        Player[] players = {
                new PlayerFactory().buildPlayer(playerOneType),
                new PlayerFactory().buildPlayer(playerTwoType)
        };

        int[] roundsArray = {15000, 10000, 5000, 1000, 700, 500, 300};
        for (int rounds : roundsArray) {
            for (int boardSize = 4; boardSize < 10; boardSize++) {
                for (int winStrike = 3; winStrike < 10; winStrike++) {
                    Assertions.assertTrue(percentagedTournament(rounds, boardSize, winStrike, players) >= 0.55, "size: " + boardSize + " Strike: " + winStrike + " rounds: " + rounds);
                }
            }
        }
    }
}
