package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserList {
    private List<User> userList;

    public UserList() {
        userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * A method to find user in userList using username
     *
     * @param username A username to find
     * @return A user's object if found; null otherwise
     */
    public User findUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * A method to find user in userList using user id
     *
     * @param id A id use to find user
     * @return A user's object if found; null otherwise
     */
    public User findUserById(String id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public UserList filterBy(Filterer<User> filterer) {
        UserList filteredUserList = new UserList();
        for (User user : userList) {
            if (filterer.filter(user)) {
                filteredUserList.addUser(user);
            }
        }
        return filteredUserList;
    }

    /**
     * Update a user information in userList by matching with unique id
     *
     * @param user An updated user object that you want to update in userList
     */
    public void updateUser(User user) {
        userList = userList.stream()
                .map(u -> u.getId().equals(user.getId()) ? user : u)
                .collect(Collectors.toList());
    }

    public void updateUserAgency(Agency agency, Agency newAgency) {
        for (User user : userList) {
            if (user.getAgency() != null && user.getAgency().getId().equals(agency.getId())) {
                user.setAgency(newAgency);
            }
        }
    }

    /*
     * Check if username is valid to the rule or not
     * 1. username no more than 20 character
     * 2. contain only letter and digit
     * */
    public boolean validUsername(String username) {
        return username.length() <= 20 && username.matches("^[a-zA-Z0-9]*$");
    }
}
