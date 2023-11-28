package model.interfaces;

/**
 * The Loggable interface represents an entity that can be logged in and out and can perform account creation.
 */
public interface Loggable {
    /**
     * Attempts to log in with the specified username and password.
     *
     * @param newUsername The username to log in with.
     * @param newPassword The password to log in with.
     * @return An integer representing the result of the login attempt.
     *         -1: Login unsuccessful due to incorrect credentials.
     *         0: Login successful.
     *         1: User is already logged in.
     */
    public abstract int logIn(String newUsername, String newPassword);

    /**
     * Creates a new account with the specified username and password.
     *
     * @param newUsername The username for the new account.
     * @param newPassword The password for the new account.
     * @return An integer representing the result of the account creation attempt.
     *         -1: Account creation unsuccessful due to duplicate username.
     *         0: Account creation successful.
     */
    public abstract int createAccount(String newUsername, String newPassword);

    /**
     * Logs out the current user.
     *
     * @return An integer representing the result of the logout attempt.
     *         -1: Logout unsuccessful because no user is currently logged in.
     *         0: Logout successful.
     */
    public abstract int logOut();
}
