package tk.amorim.db;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tk.amorim.decorator.User;
import tk.amorim.model.Activity;
import tk.amorim.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by lucas on 01/09/2017.
 */
public abstract class Database {
    private AtomicInteger atomic = new AtomicInteger(1000);
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Resource> resources = new ArrayList<>();

    public UserFiltering getUsers() throws Exception {
        return new UserFiltering(users);
    }
    public int getUniqueIdForInstance() throws Exception {
        return atomic.getAndIncrement();
    }
    public void addUser(User user) throws Exception {
        users.add(user);
    }
    public void addActivity(Activity activity) throws Exception {
        activities.add(activity);
    }
    public ActivityFiltering getActivities() throws Exception {
        return new ActivityFiltering(this.activities);
    }

    public void addResource(Resource res) throws Exception {
        resources.add(res);
    }
}
