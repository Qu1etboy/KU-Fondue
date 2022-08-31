package ku.cs.models;

public class Login {
    UserList userList;

    public Login(UserList userList) {
        this.userList = userList;
    }

    /**
     * A method to check if username is already exist or not
     *
     * @param username a username to check
     * @return A user's object if found; null otherwise
     */
    public User checkUsername(String username) {
        return userList.findUserByUsername(username);
    }

    /**
     * A method to check password is the same in user.csv
     *
     * @param password a password that contain in user.csv
     * @param inputPassword a password that user type in login page
     * @return true if and only if password match password in data file; false otherwise
     */
    public boolean checkPassword(String password, String inputPassword) {
        return password.equals(inputPassword);
    }

}
