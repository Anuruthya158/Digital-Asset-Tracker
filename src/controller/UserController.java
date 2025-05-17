package controller;

import java.util.List;

import dao.UserDAO;

public class UserController {
    private UserDAO userDAO = new UserDAO();

    public boolean registerUser(String username, String password, String role) {
        return userDAO.registerUser(username, password, role);
    }
    
    public boolean deleteUser(String username) {
        UserDAO userDAO = new UserDAO();
        return userDAO.deleteUser(username);
    }
    
    public List<String> getAllUsers() {
        UserDAO userDAO = new UserDAO();
        return userDAO.getAllUsers();
    }

    public String getUserRole(String username) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserRole(username);
    }

}
