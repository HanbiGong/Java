/**********************************************
 Workshop # 3
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-12-01
 **********************************************/
package ca.senecacollege.application.workshop3.repositories;

import java.util.ArrayList;
import java.util.List;

import ca.senecacollege.application.workshop3.models.User;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepository() {
        // default users
        User u1 = new User();
        u1.setUsername("admin");
        u1.setPassword("1234");
        u1.setEmail("admin@gmail.com");

        User u2 = new User();
        u2.setUsername("test");
        u2.setPassword("password");
        u2.setEmail("test@gmail.com");

        users.add(u1);
        users.add(u2);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public boolean validateLogin(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                    u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
