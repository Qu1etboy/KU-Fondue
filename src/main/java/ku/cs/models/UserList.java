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
}
