package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Immanuel", "Kant", (byte) 79);
        userService.saveUser("Georg", "Hegel", (byte) 61);
        userService.saveUser("Arthur", "Schopenhauer", (byte) 72);
        userService.saveUser("Friedrich", "Nietzsche", (byte) 55);

        ;
        System.out.println(userService.getAllUsers());

        userService.removeUserById(3);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
//        userService.getAllUsers();
        userService.dropUsersTable();


    }
}
