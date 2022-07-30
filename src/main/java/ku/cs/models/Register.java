package ku.cs.models;

public class Register {

    /*
    * method use to check if user input is correct or not
    * */
    public boolean isValid() {
        // ...

        return true;
    }

    /*
    * check that username is already exist or not
    * */
    public boolean checkUsername(String username) {
        // check from user.csv

        return true;
    }

    /*
    * check if username is valid to the rule or not
    * */
    public boolean validUsername(String username) {
        // ...

        return true;
    }


    public boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }


}
