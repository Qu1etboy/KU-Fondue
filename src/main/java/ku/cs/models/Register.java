package ku.cs.models;

public class Register {
    UserList userList;
    public Register(UserList userList) {
        this.userList = userList;
    }

    /**
     * Check if user is not exist in data file
     *
     * @param username a username to check
     * @return true if and only if user is not found; false otherwise
     */
    public boolean checkUsername(String username) {
        User user = userList.findUserByUsername(username);
        return user == null;
    }

    /*
    * Check if username is valid to the rule or not
    * 1. username no more than 20 character
    * 2. contain only letter and digit
    * */
    public boolean validUsername(String username) {
        return username.length() <= 20 && username.matches("^[a-zA-Z0-9]*$");
    }

    /**
     * Check if password and confirm password are match
     *
     * @param password A password from passwordTextField
     * @param confirmPassword A confirmPassword from confirmPasswordTextField
     * @return true if and only if password and confirm password match; false otherwise
     */
    public boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }


}
