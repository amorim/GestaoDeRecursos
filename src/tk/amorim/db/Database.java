package tk.amorim.db;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tk.amorim.decorator.User;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lucas on 01/09/2017.
 */
public abstract class Database {
    AtomicInteger atomic = new AtomicInteger(1000);
    ArrayList<User> users = new ArrayList<User>();

    public ArrayList<User> getUser(User filter) throws Exception {
        throw new NotImplementedException();
    }
    public int getUniqueIdForInstance() throws Exception {
        return atomic.getAndIncrement();
    }
    public void addUser(User user) throws Exception {
        throw new NotImplementedException();
    }
}
