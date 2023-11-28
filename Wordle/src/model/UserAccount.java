package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a user account for the Wordle game.
 */
public class UserAccount implements Serializable {
    private Wordle game;
    private String password;
    private String username;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private ArrayList<Integer> allAttempts;

    /**
     * Constructs a UserAccount object with the given username and password.
     *
     * @param username The username for the user account.
     * @param password The password for the user account.
     */
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        allAttempts = new ArrayList<Integer>();
        game = new Wordle();
        gamesPlayed = 0;
        gamesWon = 0;
    }

    /**
     * Returns the password for the user account.
     *
     * @return The password for the user account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username for the user account.
     *
     * @return The username for the user account.
     */
    public String getUserName() {
        return username;
    }

    /**
     * Returns the Wordle game object associated with the user account.
     *
     * @return The Wordle game object.
     */
    public Wordle getUserData() {
        return game;
    }

    /**
     * Sets the Wordle game object associated with the user account.
     *
     * @param newState The new state of the Wordle game.
     */
    public void setUserData(Wordle newState) {
        this.game = newState;
    }

    /**
     * Starts a new Wordle game for the user account.
     */
    public void newGame() {
        game = new Wordle();
        gamesPlayed++;
    }

    /**
     * Returns the number of games won by the user.
     *
     * @return The number of games won.
     */
    public Integer getGamesWon() {
        return gamesWon;
    }

    /**
     * Returns the total number of games played by the user.
     *
     * @return The total number of games played.
     */
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Increments the count of games won by the user and updates the attempts list.
     */
    public void incrementWins() {
        setAttempts();
        gamesWon++;
    }

    /**
     * Updates the attempts list with the length of the current attempt in the Wordle game.
     */
    public void setAttempts() {
        allAttempts.add(findArrayLength());
        System.out.println(allAttempts.get(0));
    }

    /**
     * Returns the list of attempts made by the user.
     *
     * @return The list of attempts.
     */
    public ArrayList<Integer> getAttempt() {
        return allAttempts;
    }

    /**
     * Finds the length of the attempts array in the Wordle game.
     *
     * @return The length of the attempts array.
     */
    private int findArrayLength() {
        int count = 0;
        for (Attempt attempt : game.getAttempts()) {
            if (!attempt.getAttempt().equals("")) {
                count += 1;
            }
        }
        return count;
    }
}
