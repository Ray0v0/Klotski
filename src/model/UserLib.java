package model;

import ai.BFSMap;

import java.io.*;
import java.util.ArrayList;

public class UserLib {

    ArrayList<User> users;
    private static String file = "data/UserLib/users.ser";

    public UserLib() {
        if (!new File(file).exists()) {
            users = new ArrayList<>();
        } else {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                users = (ArrayList<User>) in.readObject();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addUser(User user) {
        for (User value : users) {
            if (value.username.equals(user.username)) {
                return false;
            }
        }
        users.add(user);
        save();
        return true;
    }

    public boolean removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            save();
            return true;
        }
        return false;
    }

    public boolean has(User user) {
        for (User value : users) {
            if (value.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(users);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class User implements Serializable {
        public String username;
        public String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof User user) {
                return user.username.equals(username) && user.password.equals(password);
            }
            return false;
        }
    }
}
