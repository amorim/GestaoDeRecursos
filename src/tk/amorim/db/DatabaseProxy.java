package tk.amorim.db;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tk.amorim.decorator.User;
import tk.amorim.model.Activity;
import tk.amorim.model.Resource;

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
    public UserFiltering getUsers() throws Exception {
        if (validated)
            return super.getUsers();
        throw new Exception("Not authenticated");
    }
    public int getUniqueIdForInstance() throws Exception {
        if (validated)
            return super.getUniqueIdForInstance();
        throw new Exception("Not authenticated");
    }
    public void addUser(User user) throws Exception {
        if (validated) {
            super.addUser(user);
            return;
        }
        throw new Exception("Not authenticated");
    }
    public void addActivity(Activity activity) throws Exception {
        if (validated) {
            super.addActivity(activity);
            return;
        }
        throw new Exception("Not authenticated");
    }
    public ActivityFiltering getActivities() throws Exception {
        if (validated) {
            return super.getActivities();
        }
        throw new Exception("Not authenticated");
    }
    public void addResource(Resource res) throws Exception {
        if (validated) {
            super.addResource(res);
            return;
        }
        throw new Exception("Not authenticated");
    }
}
