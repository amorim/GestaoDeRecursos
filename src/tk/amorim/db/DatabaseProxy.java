package tk.amorim.db;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tk.amorim.decorator.User;

import java.util.ArrayList;

/**
 * Created by lucas on 01/09/2017.
 */
public class DatabaseProxy extends Database {
    private String user, password;
    private boolean validated = false;
    public DatabaseProxy(String user, String password) {
        this.user = user;
        this.password = password;
        if (checkCredentials()) {
            this.validated = true;
        }
    }

    private boolean checkCredentials() {
        return user.equalsIgnoreCase("admin") && password.equalsIgnoreCase("1234");
    }
    public ArrayList<User> getUser(User filter) throws Exception {
        if (validated)
            return super.getUser(filter);
        throw new Exception("Not authenticated");
    }
    public int getUniqueIdForInstance() throws Exception {
        if (validated)
            return super.getUniqueIdForInstance();
        throw new Exception("Not authenticated");
    }
    public void addUser(User user) throws Exception {
        if (validated)
            super.addUser(user);
        throw new Exception("Not authenticated");
    }
}
