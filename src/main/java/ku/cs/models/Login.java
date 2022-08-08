package ku.cs.models;

public class Login {
    /*
    * check username exist
    * check user was not banned
    * check password
    * */
    public boolean isValid() {
        return true;
    }

    /*
    * check if user exist and return user object
    * */
    public void checkUsername(String username) {

    }

    /*
    * check if input password is the same as in the database or not
    * */
    public boolean checkPassword(String password, String inputPassword) {
        return password.equals(inputPassword);
    }


}
